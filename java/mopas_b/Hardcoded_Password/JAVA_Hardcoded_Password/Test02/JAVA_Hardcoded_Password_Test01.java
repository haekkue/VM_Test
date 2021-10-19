/*
 * @(#) LinkProcessor.java 2.0,  2009. 04. 01
 * 
 * Copyright (c) 2009 인비젼테크놀러지  All rights reserved.
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
	 * 일배치 ITSM 연동
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		Logger.itsm("ITSM 연동 일배치 - start");
		
		Connection itsm_conn = null;
		Connection rms_conn = null;
		
		try {
			
			itsm_conn = this.getITSMConnection();
			if(itsm_conn == null)
				Logger.itsm("ITSM 데이터베이스 연결실패");
			
			rms_conn = QueryHandler.instance().getConnection();
			if(rms_conn == null)
				Logger.itsm("RMS 데이터베이스 연결실패");
			rms_conn.setAutoCommit(false);
			
			//범주연동 ( 위험관리 시스템 내 자체 범주 사용으로 연동 안함 )
			//this.processClass(rms_conn, itsm_conn);
			
			// 사용자 연동
			this.processUser(rms_conn, itsm_conn);
			
			// 자산연동
			this.processAsset(rms_conn, itsm_conn);
			
			// 자산권한연동 ( 산업은행 내 권한 관리 시스템 core system 과 연동으로 대체 )
			//this.processAuth(rms_conn, itsm_conn);
			
		} catch (Exception e) {
			Logger.itsm("ITSM 연동 일배치 실패 - " + e);
		} finally {
			if( itsm_conn != null ) try{ itsm_conn.close(); }catch(SQLException ex){}
			if( rms_conn != null ) try{ rms_conn.close(); }catch(SQLException ex){}
		}
		Logger.itsm("ITSM 연동 일배치 - end");		
	}

	/**
	 * 범주연동
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public void processClass(Connection rms_conn, Connection itsm_conn) throws Exception{

		Logger.itsm("	=> 범주연동 - start");
		ClassLinker classLinker = new ClassLinker();
		UserMap map = null;
		String full_nm = null;
		Long parent_oid = null;

		try{

			// 백업테이블 목록 삭제 : A_CLASS_BACKUP
			classLinker.deleteBackup(rms_conn);
			// 범주정보 백업 : A_CLASS -> A_CLASS_BACKUP
			classLinker.insertToBackup(rms_conn);
			// 범주정보 삭제 : A_CLASS
			classLinker.delete(rms_conn);

			// A_CLASS 반영 : ITSM -> A_CLASS
			UserList c_list = classLinker.selectList(itsm_conn);
			
			classLinker.update(c_list, rms_conn);
			
			// 백업테이블 조회 후 관리항목 업데이트 : A_CLASS_BACKUP -> A_CLASS
			classLinker.updateUseYn(classLinker.selectBackup(rms_conn), rms_conn);

			// 2007.06.12 범주 
			rms_conn.commit();
		}
		catch(Exception e){

			if(rms_conn != null) try{ rms_conn.rollback(); }catch(SQLException ex){}
			Logger.itsm("	=> 범주연동실패 - " + e);					
			throw e;
		}

		Logger.itsm("	=> 범주연동 - end");		
	}
	
	/**
	 * 범주연동 - test
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public void processClassTest(Connection rms_conn) throws Exception{

		Logger.itsm("processClassTest	=> 범주연동 - start");
		ClassLinker classLinker = new ClassLinker();
		UserMap map = null;
		String full_nm = null;
		Long parent_oid = null;

		try{

			// 백업테이블 목록 삭제
			classLinker.deleteBackup(rms_conn);
			// 범주정보 백업
			classLinker.insertToBackup(rms_conn);
			// 범주정보 삭제
			classLinker.delete(rms_conn);

			// A_CLASS 반영
//			UserList c_list = classLinker.selectList(itsm_conn);

//			for( int i=0; i<c_list.size(); i++){
//
//				map = c_list.getMap(i);
//				parent_oid = map.get("PARENT_OID") == null ? null: new Long(map.getLong("PARENT_OID"));
//				full_nm = StringUtils.substring(map.getString("full_nm"), 4, map.getString("full_nm").length());
//				classLinker.update( new Long(map.getLong("class_oid")), map.getString("class_nm"), 
//								parent_oid, full_nm, map.getInteger("level"), rms_conn);
//			}
			
			// 백업테이블 조회 후 관리항목 업데이트
//			classLinker.updateUseYn(classLinker.selectBackup(rms_conn), rms_conn);

			// 2007.06.12 범주 
			rms_conn.commit();
		}
		catch(Exception e){

			if(rms_conn != null) try{ rms_conn.rollback(); }catch(SQLException ex){}
			Logger.itsm("processClassTest	=> 범주연동실패 - " + e);					
			throw e;
		}

		Logger.itsm("processClassTest	=> 범주연동 - end");		
	}


	/**
	 * 사용자 연동
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public void processUser(Connection rms_conn, Connection itsm_conn) throws Exception{
		Logger.itsm("	=> 사용자연동 - start");
		UserLinker userLinker = new UserLinker();
		UserMap map = null;
		try{
			UserList r_list = userLinker.selectListRMS();
			String[] userId = new String[r_list.size()];  
			
			UserList u_list = userLinker.selectListByUser(itsm_conn);

			userLinker.update(u_list, rms_conn);

			// 사용자 삭제된목록 찾아서 삭제
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
					
				// 코아 시스템에서 메뉴별 권한 처리 함.
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
			Logger.itsm("	=> 사용자연동실패 - " + e);		
			throw e;
		}		
		
		Logger.itsm("	=> 사용자연동 - end");		
	}
	
	/**
	 * 자산연동
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public synchronized void processAsset(Connection rms_conn, Connection itsm_conn) throws Exception{
		Logger.itsm("	=> 자산연동 - start");		
		
		AssetLinker assetLinker = new AssetLinker();
		AssetMasDao assetMas    = new AssetMasDao();
		
		try{
			// A_ASSETMAS 테이블 외 연동 범위 외 데이타 삭제
			assetMas.deleteMasByClassOid(rms_conn);
			
			// 분류체계 1 연동 (위험관리 시스템 내 자체 분류체계 사용)
			//this.dep1NoUpdate(rms_conn, itsm_conn);

			// 분류체계 2 연동 (위험관리 시스템 내 자체 분류체계 사용)
			//this.dep2NoUpdate(rms_conn, itsm_conn);

			// 분류체계 3 연동 (위험관리 시스템 내 자체 분류체계 사용)
			//this.dep3NoUpdate(rms_conn, itsm_conn);

			// 사용하지 않는 범주에 해당하는 연동자산 삭제
			//assetLinker.deleteItsmAsset(rms_conn);
			assetMas.deleteByClassOid(rms_conn);
			rms_conn.commit();
			
			// CI 범주 ( 유효한 범주명 + 범주코드 )
			//UserMap c_map = assetLinker.selectListVaildClass(rms_conn);
			UserMap c_map = assetLinker.selectListVaildClass3(rms_conn);

			// ITSM 에서 자산 정보 검색 ( AST_BASEELEMENT )
			UserList a_list = assetLinker.selectList(itsm_conn);
						
			//자산 연동 ( A_ITSM_ASSET : 분류체계 외 연동)
			assetLinker.update(a_list, c_map, rms_conn);
			rms_conn.commit();
			
			// ITSM 에서 매핑된 자산 정보 검색 ( AST_SEARCHFROMBASE_RELATIONSHI )
			UserList a_link_list = assetLinker.a_link_list(itsm_conn);

			//자산 연동 ( A_ITSM_ASSET : 분류체계 외 연동)
			assetLinker.link_update(a_link_list, c_map, rms_conn);
			
			rms_conn.commit();
		}catch(Exception e){
			if(rms_conn != null) try{ rms_conn.rollback(); }catch(SQLException ex){}
			Logger.itsm("	=> 자산연동실패 - " + e);		
			throw e;
		}		
		
		Logger.itsm("	=> 자산연동 - end");		
	}
	
	/**
	 * ITSM Oracle Connection 생성
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
	 * 자산연동 테스트
	 * @param rms_conn
	 * @param itsm_conn
	 * @throws Exception
	 */
	public synchronized void processAssetTest(Connection rms_conn) throws Exception{
		Logger.itsm("	=> 자산연동 - start");		
		
		AssetLinker assetLinker = new AssetLinker();
		try{
			
			//운영 -> 비운영으로 변경 관리 안함. 
			// A_ASSETMAS 삭제 (운영에서 비운영으로 변경된 항목)
			//assetLinker.deleteAssetMasStatus(rms_conn);
			// A_ITSM_ASSET 삭제 (운영에서 비운영으로 변경된 항목)
			//assetLinker.deleteItsmAssetStatus(rms_conn);
			rms_conn.commit();
		}
		catch(Exception e){

			if(rms_conn != null) try{ rms_conn.rollback(); }catch(SQLException ex){}
			Logger.itsm("	=> 자산연동실패 - " + e);		
			throw e;
		}		
		
		Logger.itsm("	=> 자산연동 - end");		
	}
	
	public static void main(String[] args){
		LinkProcessor processor = new LinkProcessor();
		processor.run();
	}

}
