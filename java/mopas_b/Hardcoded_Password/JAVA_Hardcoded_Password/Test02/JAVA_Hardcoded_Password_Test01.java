/*
 * @(#) LinkProcessor.java 2.0,  2009. 04. 01
 * 
 * Copyright (c) 2009 �κ�����ũ���  All rights reserved.
 */
 
package kr.co.envision.kdb.itsm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimerTask;

import kr.co.envision.kdb.itsm.AssetLinker;
import kr.co.envision.fw.Config;
import kr.co.envision.fw.Logger;
import kr.co.envision.fw.QueryHandler;
import kr.co.envision.fw.UserList;
import kr.co.envision.fw.UserMap;
import kr.co.envision.kdb.model.a.*;

import org.apache.commons.lang.StringUtils;

/**
 * @author Envision Tech (rnd@cubesec.co.kr)
 * @version 2.0,  2009. 04. 01
 * 
 */
public class LinkProcessor extends TimerTask{

	/**
	 * 
	 */
	public LinkProcessor() {
	}

	/**
	 * �Ϲ�ġ ITSM ����
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		Logger.itsm("ITSM ���� �Ϲ�ġ - start");
		
		Connection itsm_conn = null;
		Connection rms_conn = null;
		
		try {
			
			itsm_conn = this.getITSMConnection();
			if(itsm_conn == null)
				Logger.itsm("ITSM �����ͺ��̽� �������");
			
			rms_conn = QueryHandler.instance().getConnection();
			if(rms_conn == null)
				Logger.itsm("RMS �����ͺ��̽� �������");
			rms_conn.setAutoCommit(false);
			
			//���ֿ��� ( ������� �ý��� �� ��ü ���� ������� ���� ���� )
			//this.processClass(rms_conn, itsm_conn);
			
			// ����� ����
			this.processUser(rms_conn, itsm_conn);
			
			// �ڻ꿬��
			this.processAsset(rms_conn, itsm_conn);
			
			// �ڻ���ѿ��� ( ������� �� ���� ���� �ý��� core system �� �������� ��ü )
			//this.processAuth(rms_conn, itsm_conn);
			
		} catch (Exception e) {
			Logger.itsm("ITSM ���� �Ϲ�ġ ���� - " + e);
		} finally {
			if( itsm_conn != null ) try{ itsm_conn.close(); }catch(SQLException ex){}
			if( rms_conn != null ) try{ rms_conn.close(); }catch(SQLException ex){}
		}
		Logger.itsm("ITSM ���� �Ϲ�ġ - end");		
	}

	/**
	 * ���ֿ���
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public void processClass(Connection rms_conn, Connection itsm_conn) throws Exception{

		Logger.itsm("	=> ���ֿ��� - start");
		ClassLinker classLinker = new ClassLinker();
		UserMap map = null;
		String full_nm = null;
		Long parent_oid = null;

		try{

			// ������̺� ��� ���� : A_CLASS_BACKUP
			classLinker.deleteBackup(rms_conn);
			// �������� ��� : A_CLASS -> A_CLASS_BACKUP
			classLinker.insertToBackup(rms_conn);
			// �������� ���� : A_CLASS
			classLinker.delete(rms_conn);

			// A_CLASS �ݿ� : ITSM -> A_CLASS
			UserList c_list = classLinker.selectList(itsm_conn);
			
			classLinker.update(c_list, rms_conn);
			
			// ������̺� ��ȸ �� �����׸� ������Ʈ : A_CLASS_BACKUP -> A_CLASS
			classLinker.updateUseYn(classLinker.selectBackup(rms_conn), rms_conn);

			// 2007.06.12 ���� 
			rms_conn.commit();
		}
		catch(Exception e){

			if(rms_conn != null) try{ rms_conn.rollback(); }catch(SQLException ex){}
			Logger.itsm("	=> ���ֿ������� - " + e);					
			throw e;
		}

		Logger.itsm("	=> ���ֿ��� - end");		
	}
	
	/**
	 * ���ֿ��� - test
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public void processClassTest(Connection rms_conn) throws Exception{

		Logger.itsm("processClassTest	=> ���ֿ��� - start");
		ClassLinker classLinker = new ClassLinker();
		UserMap map = null;
		String full_nm = null;
		Long parent_oid = null;

		try{

			// ������̺� ��� ����
			classLinker.deleteBackup(rms_conn);
			// �������� ���
			classLinker.insertToBackup(rms_conn);
			// �������� ����
			classLinker.delete(rms_conn);

			// A_CLASS �ݿ�
//			UserList c_list = classLinker.selectList(itsm_conn);

//			for( int i=0; i<c_list.size(); i++){
//
//				map = c_list.getMap(i);
//				parent_oid = map.get("PARENT_OID") == null ? null: new Long(map.getLong("PARENT_OID"));
//				full_nm = StringUtils.substring(map.getString("full_nm"), 4, map.getString("full_nm").length());
//				classLinker.update( new Long(map.getLong("class_oid")), map.getString("class_nm"), 
//								parent_oid, full_nm, map.getInteger("level"), rms_conn);
//			}
			
			// ������̺� ��ȸ �� �����׸� ������Ʈ
//			classLinker.updateUseYn(classLinker.selectBackup(rms_conn), rms_conn);

			// 2007.06.12 ���� 
			rms_conn.commit();
		}
		catch(Exception e){

			if(rms_conn != null) try{ rms_conn.rollback(); }catch(SQLException ex){}
			Logger.itsm("processClassTest	=> ���ֿ������� - " + e);					
			throw e;
		}

		Logger.itsm("processClassTest	=> ���ֿ��� - end");		
	}


	/**
	 * ����� ����
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public void processUser(Connection rms_conn, Connection itsm_conn) throws Exception{
		Logger.itsm("	=> ����ڿ��� - start");
		UserLinker userLinker = new UserLinker();
		UserMap map = null;
		try{
			UserList r_list = userLinker.selectListRMS();
			String[] userId = new String[r_list.size()];  
			
			UserList u_list = userLinker.selectListByUser(itsm_conn);

			userLinker.update(u_list, rms_conn);

			// ����� �����ȸ�� ã�Ƽ� ����
			for( int i=0; i<r_list.size(); i++)
				userId[i] = r_list.getMap(i).getString("USER_ID");
				
			//UserList i_list = userLinker.selectListITSM(userId, itsm_conn);
			
			boolean b = false;
			//String rms_user = null, itsm_user = null;
				
			//for( int i=0; i<r_list.size(); i++){
			//	rms_user = r_list.getMap(i).getString("user_id");
					
			//	for( int j=0; j<i_list.size(); j++){
			//		itsm_user = i_list.getMap(j).getString("user_id");
			//		if(rms_user.equals(itsm_user)){
			//			b = true;
			//			break;
			//		}
			//	}
					
				// �ھ� �ý��ۿ��� �޴��� ���� ó�� ��.
				//if( !b ){
				//	userLinker.deletePermission(rms_user, rms_conn);
				//	userLinker.delete(rms_user, rms_conn);
				//}else{
				//	b = false;
				//}
			//}
			rms_conn.commit();
		}catch(Exception e){
			if(rms_conn != null) try{ rms_conn.rollback(); }catch(SQLException ex){}
			Logger.itsm("	=> ����ڿ������� - " + e);		
			throw e;
		}		
		
		Logger.itsm("	=> ����ڿ��� - end");		
	}
	
	/**
	 * �ڻ꿬��
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public synchronized void processAsset(Connection rms_conn, Connection itsm_conn) throws Exception{
		Logger.itsm("	=> �ڻ꿬�� - start");		
		
		AssetLinker assetLinker = new AssetLinker();
		AssetMasDao assetMas    = new AssetMasDao();
		
		try{
			// A_ASSETMAS ���̺� �� ���� ���� �� ����Ÿ ����
			assetMas.deleteMasByClassOid(rms_conn);
			
			// �з�ü�� 1 ���� (������� �ý��� �� ��ü �з�ü�� ���)
			//this.dep1NoUpdate(rms_conn, itsm_conn);

			// �з�ü�� 2 ���� (������� �ý��� �� ��ü �з�ü�� ���)
			//this.dep2NoUpdate(rms_conn, itsm_conn);

			// �з�ü�� 3 ���� (������� �ý��� �� ��ü �з�ü�� ���)
			//this.dep3NoUpdate(rms_conn, itsm_conn);

			// ������� �ʴ� ���ֿ� �ش��ϴ� �����ڻ� ����
			//assetLinker.deleteItsmAsset(rms_conn);
			assetMas.deleteByClassOid(rms_conn);
			rms_conn.commit();
			
			// CI ���� ( ��ȿ�� ���ָ� + �����ڵ� )
			//UserMap c_map = assetLinker.selectListVaildClass(rms_conn);
			UserMap c_map = assetLinker.selectListVaildClass3(rms_conn);

			// ITSM ���� �ڻ� ���� �˻� ( AST_BASEELEMENT )
			UserList a_list = assetLinker.selectList(itsm_conn);
						
			//�ڻ� ���� ( A_ITSM_ASSET : �з�ü�� �� ����)
			assetLinker.update(a_list, c_map, rms_conn);
			rms_conn.commit();
			
			// ITSM ���� ���ε� �ڻ� ���� �˻� ( AST_SEARCHFROMBASE_RELATIONSHI )
			UserList a_link_list = assetLinker.a_link_list(itsm_conn);

			//�ڻ� ���� ( A_ITSM_ASSET : �з�ü�� �� ����)
			assetLinker.link_update(a_link_list, c_map, rms_conn);
			
			rms_conn.commit();
		}catch(Exception e){
			if(rms_conn != null) try{ rms_conn.rollback(); }catch(SQLException ex){}
			Logger.itsm("	=> �ڻ꿬������ - " + e);		
			throw e;
		}		
		
		Logger.itsm("	=> �ڻ꿬�� - end");		
	}
	
	/**
	 * ITSM Oracle Connection ����
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getITSMConnection() throws ClassNotFoundException, SQLException{
		Connection conn = null;
		Config conf = Config.getInstance();
		Class.forName(conf.getString("ITSM_CLASS"));
		conn = DriverManager.getConnection("ITSM_URL", "SECUSEL", "KSECUSEL");
		conn = DriverManager.getConnection(conf.getString("ITSM_URL"), conf.getString("SECUSEL"), conf.getString("KSECUSEL"));
		
		return conn;
	}
	
	/**
	 * �ڻ꿬�� �׽�Ʈ
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public synchronized void processAssetTest(Connection rms_conn) throws Exception{
		Logger.itsm("	=> �ڻ꿬�� - start");		
		
		AssetLinker assetLinker = new AssetLinker();
		try{
			
			//� -> ������ ���� ���� ����. 
			// A_ASSETMAS ���� (����� ������ ����� �׸�)
			//assetLinker.deleteAssetMasStatus(rms_conn);
			// A_ITSM_ASSET ���� (����� ������ ����� �׸�)
			//assetLinker.deleteItsmAssetStatus(rms_conn);
			rms_conn.commit();
		}
		catch(Exception e){

			if(rms_conn != null) try{ rms_conn.rollback(); }catch(SQLException ex){}
			Logger.itsm("	=> �ڻ꿬������ - " + e);		
			throw e;
		}		
		
		Logger.itsm("	=> �ڻ꿬�� - end");		
	}
	
	public static void main(String[] args){
		LinkProcessor processor = new LinkProcessor();
		processor.run();
	}

}
