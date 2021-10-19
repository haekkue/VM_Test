package kr.co.kaeri.member.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import kr.co.CommonAction;
import kr.co.kaeri.login.vo.UsrInfoVO;
import kr.co.kaeri.login.vo.mngLoginVO;
import kr.co.kaeri.member.dao.MemberDAO;
import kr.co.kaeri.member.vo.MemberVO;
import kr.co.kaeri.system.manager.dao.ManagerDAO;
import kr.co.kaeri.system.manager.vo.ManagerVO;
import kr.co.kaeri.worklog.dao.WorklogDAO;
import kr.co.util.share.RequestSetter;
import kr.co.util.util.AjaxOut;
import kr.co.util.util.GetPrintStackTraceIntoString;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class MemberAction extends CommonAction implements Preparable, ModelDriven<MemberVO>{
	private static Logger logger = Logger.getLogger(MemberAction.class);
	ResourceBundle bundle = ResourceBundle.getBundle("globalResource");
	String SERVER_DOMAIN = bundle.getString("SERVER_DOMAIN");
	
	RequestSetter reqSetter = new RequestSetter();
	String ret=SUCCESS;

	MemberDAO dao;
	private MemberVO vo;
	private ArrayList<MemberVO> list;
	

	public MemberVO getModel() {
		return vo;
	}

	public void prepare() throws Exception {
		vo=new MemberVO();
		dao=new MemberDAO();
		list=new ArrayList<MemberVO>();
	}

	public MemberVO getVo() {
		return vo;
	}

	public void setVo(MemberVO vo) {
		this.vo = vo;
	}

	public ArrayList<MemberVO> getList() {
		return list;
	}

	public void setList(ArrayList<MemberVO> list) {
		this.list = list;
	}

	private String find_type;
	private String find_id;

	public String getFind_type() {
		return find_type;
	}

	public void setFind_type(String find_type) {
		this.find_type = find_type;
	}

	public String getFind_id() {
		return find_id;
	}

	public void setFind_id(String find_id) {
		this.find_id = find_id;
	}

	/**
	 * Ȩ������ ȸ������/ȸ���������� ��
	 * @return
	 * @throws Exception
	 */
	public String MemberForm() throws Exception {
		logger.info("MemberAction :: MemberForm() called");
		String ret=SUCCESS;

		String mem_name = "";
		String mem_dupinfo = "";
		String mem_scicode = "";
		String message = "";
		String returnUrl = "";

		try{
			HttpSession session = ServletActionContext.getRequest().getSession();
			UsrInfoVO UsrInfo = new UsrInfoVO();

			// �׽�Ʈ ���
			/*
			String mem_name = "ȫ�浿";
			String mem_dupinfo = "TEST_mem_dupinfo";
			session.setAttribute("mem_name","ȫ�浿");
			session.setAttribute("mem_dupinfo","TEST_mem_dupinfo");
			*/

			// ���� ���
			UsrInfo = (UsrInfoVO)session.getAttribute("UsrInfo");
			mem_name = UsrInfo.getSs_member_nm();
			mem_dupinfo = UsrInfo.getDupinfo();
			mem_scicode = UsrInfo.getScicode();
			System.out.println("�Ǹ����� �� �������� ������ �̸�="+mem_name);
			System.out.println("�Ǹ����� �� �������� ������ �ߺ�����Ȯ��Ű="+mem_dupinfo);
			System.out.println("�Ǹ����� �� �������� ������ SCIŰ="+mem_scicode);

			//���� ������ �����ϸ� ȸ����������
			if(UsrInfo.getSs_member_id() != null){
				vo = dao.GetMemberView(UsrInfo.getSs_member_id());
				ret = SUCCESS;
			}else{
				if(mem_name != null || mem_dupinfo != null){
					//ȸ���ߺ�üũ
					if(isDupInfoCheck(mem_name, mem_dupinfo)){
						message = "�̹� ���Ե� ȸ���Դϴ�.";
						returnUrl = ""+SERVER_DOMAIN+"/Member02.do?usr_menu_cd=0106010000";
						reqSetter.getRequest().setAttribute("message", message);
						reqSetter.getRequest().setAttribute("returnUrl", returnUrl);
						ret = "warning";
					}else{
						System.out.println("��������!");
						vo.setMem_name(mem_name);
						vo.setMem_dupinfo(mem_dupinfo);
						vo.setMem_scicode(mem_scicode);
						ret = SUCCESS;
					}
				}else{
					System.out.println("��������!");
					ret = "login";
				}
			}
		}catch(Exception e){
			ret = ERROR;
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
		}
		return ret;
	}

	/**
	 * Ȩ������ �ߺ�Ű Ȯ��
	 * @param mem_name
	 * @param mem_dupinfo
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean isDupInfoCheck(String mem_name, String mem_dupinfo) throws Exception{
		logger.info("MemberAction :: isDupInfoCheck() called");
		HashMap map = new HashMap();
		map.put("mem_name", mem_name);
		map.put("mem_dupinfo", mem_dupinfo);
		System.out.println("�����̸�>>>"+mem_name);
		System.out.println("�����ڵ�>>>"+mem_dupinfo);

		try{
			MemberDAO mdao = new MemberDAO();
			int count = mdao.getDupInfo(map);
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
			return false;
		}
	}

	/**
	 * Ȩ������ ���̵� �ߺ� üũ
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void MemberIdCheck() throws Exception{
		logger.info("MemberAction :: MemberIdCheck() called");
		String sb = "ERROR";
		HttpSession session = ServletActionContext.getRequest().getSession();
		int check=dao.MemberIdCheck(vo.getMem_userid());

		if(check > 0){
			sb = "DUPLICATION"; // �ߺ��� ���ڿ�
		}else{
			sb = "SUCCESS";
		}

		AjaxOut.out(sb, "UTF-8");
	}

	/**
	 * Ȩ������ ȸ������ �ű�ȸ�� ��� ����
	 * @return
	 * @throws Exception
	 */	
	public String insertMember() throws Exception {
		logger.info("MemberAction :: insertMember() called");
		String ret=SUCCESS;
		HttpSession session = ServletActionContext.getRequest().getSession();
		UsrInfoVO UsrInfo = new UsrInfoVO();
		UsrInfo = (UsrInfoVO)session.getAttribute("UsrInfo");

		try{
			vo.setMem_name(UsrInfo.getSs_member_nm());
			vo.setMem_dupinfo(UsrInfo.getDupinfo());
			vo.setMem_scicode(UsrInfo.getScicode());

			vo.setMem_connip((reqSetter.getRequest().getRemoteAddr()));

			boolean insertCheck = false;
			insertCheck = dao.insertMember(vo);

			if(insertCheck){
				UsrInfo.setSs_member_id(vo.getMem_userid());
				UsrInfo.setSs_member_nm(vo.getMem_name());
				UsrInfo.setSs_member_email(vo.getMem_email());
				UsrInfo.setSs_member_tel(vo.getMem_tel());
				UsrInfo.setSs_member_hp(vo.getMem_hp());
				UsrInfo.setSs_member_zip(vo.getMem_zipcode());
				UsrInfo.setSs_member_addr1(vo.getMem_addr1());
				UsrInfo.setSs_member_addr2(vo.getMem_addr2());
				UsrInfo.setSs_member_bir(vo.getMem_bir());
				UsrInfo.setScicode(vo.getMem_scicode());
				UsrInfo.setDupinfo(vo.getMem_dupinfo());

				session.setAttribute("UsrInfo",UsrInfo);

				//SSL ���� �� https�� http�� �ٽ� �ٲ���
				reqSetter.getResponse().sendRedirect(""+SERVER_DOMAIN+"/completeMemberJoin.do?usr_menu_cd=0106010000");
			}else{
				ret=ERROR;
			}

		}catch(Exception e){
			ret = ERROR;
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
		}

		return ret;
	}

	/**
	 * Ȩ������ ȸ�� ������������ ����
	 * @return
	 * @throws Exception
	 */
	public String modifyMember() throws Exception {
		logger.info("MemberAction :: modifyMember() called");
		HttpSession session = ServletActionContext.getRequest().getSession();
		UsrInfoVO UsrInfo = new UsrInfoVO();
		UsrInfo = (UsrInfoVO)session.getAttribute("UsrInfo");
		String ret = SUCCESS;
		String message = "";
		String returnUrl = "";
		try{
			vo.setMem_userid(UsrInfo.getSs_member_id());
			dao.modifyMemberInfo(vo);

			message = "ȸ�������� �����Ǿ����ϴ�.";
			returnUrl = ""+SERVER_DOMAIN+"/MemberForm.do?usr_menu_cd=0106010000"; //SSL ���� �� https�� http�� �ٽ� �ٲ���
			reqSetter.getRequest().setAttribute("message", message);
			reqSetter.getRequest().setAttribute("returnUrl", returnUrl);
			ret = "warning";
		}catch(Exception e){

		}

		return ret;
	}

	/**
	 * ������ ȸ������ �� ����
	 * @return
	 * @throws Exception
	 */
	public String SetMemberRegistration() throws Exception {
		logger.info("MemberAction :: SetMemberRegistration() called");
		String ret=SUCCESS;
		HttpSession session = ServletActionContext.getRequest().getSession();
		UsrInfoVO UsrInfo = new UsrInfoVO();

		try{
			if(vo.getMem_seq().equals("")){
				boolean insertCheck = false;
				insertCheck = dao.insertMember(vo);
				if(insertCheck){
					UsrInfo.setSs_member_id(vo.getMem_userid());
					UsrInfo.setSs_member_nm(vo.getMem_name());
					UsrInfo.setSs_member_email(vo.getMem_email());
					UsrInfo.setSs_member_tel(vo.getMem_tel());
					UsrInfo.setSs_member_hp(vo.getMem_hp());
					UsrInfo.setSs_member_zip(vo.getMem_zipcode());
					UsrInfo.setSs_member_addr1(vo.getMem_addr1());
					UsrInfo.setSs_member_addr2(vo.getMem_addr2());
					UsrInfo.setSs_member_bir(vo.getMem_bir());
					UsrInfo.setScicode(vo.getMem_scicode());
					UsrInfo.setDupinfo(vo.getMem_dupinfo());
					session.setAttribute("UsrInfo",UsrInfo);
				}else{
					ret=ERROR;
				}
			}else{
				dao.modifyMember(vo);
				ret = INPUT;
			}
		}catch(Exception e){
			ret = ERROR;
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
		}

		return ret;
	}

	/**
	 * ��ȸ�� �Ǹ�����
	 * @return
	 */
	public String NonmemberConfirm(){
		return SUCCESS;
	}

	/**
	 * ���̵� ��� ã�� �Ǹ����� �� ������ �̵� ����
	 * @return
	 */
	public String sendIdPw() throws Exception{
		logger.info("MemberAction :: sendIdPw() called");
		ret = SUCCESS;

		// �׽�Ʈ ���
		//session.setAttribute("mem_name","ȫ�浿");
		//session.setAttribute("mem_dupinfo","TEST_mem_dupinfo");

		try{
			HttpSession session = ServletActionContext.getRequest().getSession();
			UsrInfoVO UsrInfo = new UsrInfoVO();
			UsrInfo = (UsrInfoVO)session.getAttribute("UsrInfo");

			if(UsrInfo.getSs_member_id() != null){
				reqSetter.getResponse().sendRedirect("/main.do");
			}else{
				String mem_name = UsrInfo.getSs_member_nm();
				String mem_dupinfo = UsrInfo.getDupinfo();

				if(!isDupInfoCheck(mem_name, mem_dupinfo)){
					return ERROR;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
		}
		return ret;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doSendIdPw() throws Exception{ 
		String ret=SUCCESS;
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		UsrInfoVO UsrInfo = new UsrInfoVO();
		UsrInfo = (UsrInfoVO)session.getAttribute("UsrInfo");
		
	    String mem_name = UsrInfo.getSs_member_nm();
		String mem_dupinfo = UsrInfo.getDupinfo();
		
		try {
			String message="";
			String password="";
			
			Random r=new Random();
			HashMap map=new HashMap();
			
			if(find_type.equals("id")){
				find_id = dao.getUserId(mem_dupinfo);
				ret=find_id;
			}
			
			vo.setMem_dupinfo(mem_dupinfo);
			
			if(find_type.equals("password")){//�ӽú�й�ȣ ����
				
				if(dao.findUserIdHpPw(vo)){

					for(int i=0; i<6; i++){
						password += r.nextInt()+1;
					}
					map.put("mem_pwsswd", password);
					map.put("mem_dupinfo", mem_dupinfo);
					message="["+mem_name+"]ȸ������ �ӽ� ��й�ȣ�� ["+password+"] �Դϴ�. -�������а�-";
					
					HashMap smsMap=new HashMap();
					
					smsMap.put("tr_phone", vo.getMem_hp());
					smsMap.put("tr_callback", vo.getMem_hp());
					smsMap.put("tr_msg", message);
					
					
					dao.setUserPw(map);
					ret="PWSEND";
				}else{
					ret = ERROR;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
		}
		AjaxOut.out(ret, "UTF-8");
	}

	/**
	 * ������ ȸ�� ���
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getMemberList() throws IOException{
		logger.info("MemberAction :: getMemberList() called");
		String ret=SUCCESS;
		HashMap map = new HashMap();
		
		try{
			if(vo.getSearch_keyword() != null && !"".equals(vo.getSearch_keyword())){
				if("1".equals(vo.getSearch_type())){
					map.put("mem_userid", vo.getSearch_keyword());
				}else if("2".equals(vo.getSearch_type())){
					map.put("mem_name", vo.getSearch_keyword());
				}
			}
			if(vo.getMem_employee_yn() != null && !"".equals(vo.getMem_employee_yn()) && "Y".equals(vo.getMem_employee_yn())){
				map.put("mem_employee_yn", "Y");
			}

			vo.setPageScale(15);
			vo.setScreenScale(10);

			map.put("startNum", vo.getStartNum());
			map.put("endNum", vo.getEndNum());

			vo.setTotal(dao.getMemberCount(map));
			list = dao.getMemberList(map);

			//MngRoleDAO roledao = new MngRoleDAO();
			//reqSetter.getRequest().setAttribute("roleList", roledao.GetRoleList() );
		}catch(Exception e){
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
			return ERROR;
		}
		return ret;
	}

	/**
	 * ������ ȸ�� �б�
	 * @return
	 * @throws IOException
	 */
	public String getMemberView() throws IOException{
		logger.info("MemberAction :: getMemberView() called");
		String ret=SUCCESS;
		try{
			if(vo.getMem_userid() != null && !"".equals(vo.getMem_userid())){
				vo = dao.GetMemberView(vo.getMem_userid());
			}
		}catch(Exception e){
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
			return ERROR;
		}
		return ret;
	}

	/**
	 * ������ ȸ�� ���� ����
	 * @return
	 * @throws IOException
	 */
	public String setMemberDelete() throws IOException{
		logger.info("MemberAction :: setMemberDelete() called");
		String ret=SUCCESS;
		try{
			dao.setMemberDelete(vo.getMem_userid());
		}catch(Exception e){
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
			return ERROR;
		}
		return ret;
	}

	/**
	 * Ȩ������ ȸ�� Ż�� ����
	 * @return
	 * @throws IOException
	 */
	public String deleteMember() throws IOException{
		logger.info("MemberAction :: deleteMember() called");
		String ret=SUCCESS;
		try{
			HttpSession session = ServletActionContext.getRequest().getSession();
			dao.setMemberDelete(reqSetter.getParameter("mem_userid"));
			session.removeAttribute("UsrInfo");
		}catch(Exception e){
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
			return ERROR;
		}
		return ret;
	}

	/**
	 * �����ȣ �˻�
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String getZipcodeList() throws IOException{
		logger.info("MemberAction :: getZipcodeList() called");
		String ret=SUCCESS;
		try{
			if( vo.getSrhKeyword() != null && !vo.getSrhKeyword().equals("")) {
				list = dao.getZipcodeList(vo.getSrhKeyword());
			}
		}catch(Exception e){
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg",GetPrintStackTraceIntoString.getErrorMsg(e));
			return ERROR;
		}
		return ret;
	}

	/**
	 * Post_check
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public String Post_check() throws Exception{
		logger.info("MemberAction :: Post_check() called");
		String ret = SUCCESS;
		try{
			String addr="";
			ArrayList postList = new ArrayList();
			if(vo.getFirst_yn().equals("N")){
				postList = dao.Post_check(vo.getAddr());
				reqSetter.getRequest().setAttribute("postList", postList);
			}
			
		}catch(Exception e){
			ret=ERROR;
			e.printStackTrace();
			reqSetter.getRequest().setAttribute("errMsg", GetPrintStackTraceIntoString.getErrorMsg(e));
		}
		return ret;
	}

	/**
	 * �����˾� ȸ������ ���
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String GetMemInfo()throws Exception{	
		logger.info("MemberAction :: GetMemInfo() called");
		String ret = SUCCESS;
		try{
			HashMap param = new HashMap();
			if (vo.getSearchType() != null && !"".equals(vo.getSearchType())) {
				if (vo.getSearchType().equals("1"))
					param.put("mem_name", vo.getSearchName());
				if (vo.getSearchType().equals("2"))
					param.put("mem_userid", vo.getSearchName());
			}
			list = dao.GetMemInfo(param);
		}catch (Exception e){
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * �����ڰ��� �������� ���� Ajax
	 * @throws Exception
	 */
	public void SetAdminRole()throws Exception{
		logger.info("MemberAction :: SetAdminRole() called");

		ManagerVO avo = new ManagerVO(); 
		mngLoginVO mvo = new mngLoginVO();
		ManagerDAO mngdao = new ManagerDAO();
		HttpSession session = ServletActionContext.getRequest().getSession();
		mvo =(mngLoginVO)session.getAttribute("mngLoginVO");

		String rs = null;

		if("0".equals(reqSetter.getParameter("role_num"))){
			//������ ���� ����
			avo.setAdm_id(vo.getMem_userid());
			rs = mngdao.deleteAdmin(avo);
		}else{
			//������ ���� ����
			avo.setAdm_id(vo.getMem_userid());
			rs = mngdao.deleteAdmin(avo);

			//������ ��� ����
			if(rs == "1"){
				vo = dao.GetMemberView(vo.getMem_userid());
				avo.setAdm_id(vo.getMem_userid());
				avo.setAdm_pw(vo.getMem_pwsswd());
				avo.setAdm_nm(vo.getMem_name());
				avo.setRole_num(reqSetter.getParameter("role_num"));
				avo.setDept_nm(reqSetter.getParameter("dept_nm"));
				avo.setReg_id(mvo.getAdm_id());

				rs = mngdao.insertAdmin(avo);
			}
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<result>");
		if(rs == "1"){
			sb.append("<rs>success</rs>");
		}else{
			sb.append("<rs>error</rs>");
		}
		sb.append("</result>");
		AjaxOut.out(sb.toString(), "UTF-8");

		//work log
		WorklogDAO wdao = new WorklogDAO();
		HashMap<String, String> workMap = new HashMap<String, String>();
		workMap.put("mng_menu_cd", reqSetter.getParameter("menu_cd"));
		workMap.put("work_kind", "���");
		workMap.put("work_content", "������ ��� �Ǵ� ����");
		wdao.insertWorkLog(workMap);
	}
}