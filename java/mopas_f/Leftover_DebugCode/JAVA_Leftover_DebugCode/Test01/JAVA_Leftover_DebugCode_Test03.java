package kr.co.util.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import kr.co.kaeri.system.usrMenu.dao.UsrMenuDAO;
import kr.co.kaeri.system.usrMenu.vo.UsrMenuVO;

public class Child_Type_Tag extends BodyTagSupport{
	String usr_menu_cd = null;

	public String getUsr_menu_cd() {
		return usr_menu_cd;
	}
	public void setUsr_menu_cd(String usr_menu_cd) {
		this.usr_menu_cd = usr_menu_cd;
	}

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		StringBuffer sb = new StringBuffer();
		int tsort=1;

		System.out.println("확인합니다....첫줄.");

		try {
			UsrMenuDAO dao = new UsrMenuDAO();
			UsrMenuVO vo = new UsrMenuVO();
			UsrMenuVO uvo = new UsrMenuVO();

			System.out.println("확인합니다....둘째줄.");

			vo = dao.GetUsrMenuChildType(usr_menu_cd);//선택된 메뉴
			uvo = dao.GetUsrMenuChildType(vo.getMenu_pcd());//상위 메뉴

			String child_type = "";
			if(uvo.getChild_type()!=null&&!uvo.getChild_type().equals("")){
				child_type = uvo.getChild_type();
				System.out.println("타입확인="+uvo.getChild_type());
			}
			//삭제할 메뉴
			boolean del_yn = true;
			ArrayList del_list = new ArrayList();
			del_list.add("0401030300");

			System.out.println("확인합니다.");
			//삭제할 메뉴
			if(!child_type.equals("") && child_type!=null){
				if(child_type.equals("TAB")){
					System.out.println("탭영역입니다.");
					ArrayList list = new ArrayList();
					UsrMenuVO vo1 = new UsrMenuVO();
					UsrMenuVO vo2 = new UsrMenuVO();
					UsrMenuVO vo3 = new UsrMenuVO();
					vo1 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,2)+"00000000");//선택된 메뉴의 상위 상세정보
					vo2 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,4)+"000000");//선택된 메뉴의 상위 상세정보
					vo3 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,6)+"0000");//선택된 메뉴의 상위 상세정보
					String child_menu_cd = vo.getMenu_pcd();

					list = dao.GetTopParentMenu(child_menu_cd);//선택된 메뉴의 상위값으로 검색된 하위 메뉴들(선택된 메뉴와 동일한 메뉴들 list)

					sb.append("<div id=\"moveTab\">\n");
					sb.append("<div id=\"moveTab_ov\"></div>\n");
					sb.append("<ul>\n");
					for(int i=0;i<list.size();i++){
						UsrMenuVO svo = new UsrMenuVO();
						svo=(UsrMenuVO)list.get(i);
						if(svo.getUsr_menu_cd() != null && ( "06".equals(svo.getUsr_menu_cd().substring(0,2))  || "07".equals(svo.getUsr_menu_cd().substring(0,2))    )){
							tsort = Integer.parseInt(vo.getUsr_menu_cd().substring(0,2));
						}else{
							tsort = Integer.parseInt(vo1.getMenu_sort());
						}
						for(int d=0;d<del_list.size();d++){
							if(svo.getUsr_menu_cd().equals(del_list.get(d))){
								del_yn=false;
							}
						}
						if(!del_yn)break;

						sb.append("<li>\n<a href=\"");
						sb.append(svo.getMenu_url());
						if(svo.getIs_link().equals("N"))sb.append("\"  target=\"_blank\" ");
						else{
							sb.append("&amp;tsort="+tsort+"&amp;tcsort="+vo2.getMenu_sort()+"&amp;csort="+vo3.getMenu_sort());
							sb.append("&amp;is_info_offerer="+svo.getIs_info_offerer()+"&amp;is_satisfaction="+svo.getIs_satisfaction()+"&amp;tpl_num="+svo.getTpl_num()+"\"");
						}
						sb.append(" >");
						sb.append(svo.getMenu_nm());
						sb.append("</a>\n</li>\n");
					}
					sb.append("</ul>\n");
					sb.append("</div>\n");

					sb.append("<script type=\"text/javascript\">\n");
					//sb.append("//위치할 숫자를 입력하세요");
					sb.append("moveTab_init("+vo.getMenu_sort()+");\n");
					sb.append("</script>\n");

				}else if(child_type.equals("BOX")){ // 2012-01-06 박재민 BOX 부분 수정완료
					System.out.println("박스영역입니다.");
					ArrayList list = new ArrayList();
					UsrMenuVO vo1 = new UsrMenuVO();
					UsrMenuVO vo2 = new UsrMenuVO();
					UsrMenuVO vo3 = new UsrMenuVO();
					UsrMenuVO vo4 = new UsrMenuVO();
					vo1 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,2)+"00000000");//선택된 메뉴의 상위 상세정보
					vo2 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,4)+"000000");//선택된 메뉴의 상위 상세정보
					vo3 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,6)+"0000");//선택된 메뉴의 상위 상세정보
					vo4 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,8)+"00");//선택된 메뉴의 상위 상세정보
					String child_menu_cd = vo.getMenu_pcd();

					list = dao.GetTopParentMenu(child_menu_cd);//선택된 메뉴의 상위값으로 검색된 하위 메뉴들(선택된 메뉴와 동일한 메뉴들 list)

					sb.append("<div class=\"tab_menu_t\"><img src=\"/images/web/common/tab_menu_t.gif\" alt=\"TABMENU\" /></div>\n");

					sb.append("<div class=\"tab_menu\">\n");
					/*
					if(usr_menu_cd.substring(0,2).equals("22")){
						sb.append("<strong class=\"moveTab2_ti\"><img src=\"/sub/welfare/images/woman/common/moveTab2_ti.gif\" alt=\"TABMENU\" /></strong>");
					}else{
						sb.append("<strong class=\"moveTab2_ti\"><img src=\"/commcms/images/moveTab2_ti.gif\" alt=\"TABMENU\" /></strong>\n");
					}
					*/
					sb.append("<ul>\n");
					for(int i=0;i<list.size();i++){
						UsrMenuVO svo = new UsrMenuVO();
						svo=(UsrMenuVO)list.get(i);
						if(svo.getUsr_menu_cd() != null && ( "06".equals(svo.getUsr_menu_cd().substring(0,2))  || "07".equals(svo.getUsr_menu_cd().substring(0,2))    )){
							tsort = Integer.parseInt(vo.getUsr_menu_cd().substring(0,2));
						}else{
							tsort = Integer.parseInt(vo1.getMenu_sort());
						}
						for(int d=0;d<del_list.size();d++){
							if(svo.getUsr_menu_cd().equals(del_list.get(d))){
								del_yn=false;
							}
						}
						if(!del_yn)break;
						sb.append("<li>\n<a href=\"");
						sb.append(svo.getMenu_url());
						if(svo.getIs_link().equals("N"))sb.append("\" target=\"_blank\" ");
						else{
							//sb.append("&amp;tsort="+tsort+"&amp;tcsort="+vo2.getMenu_sort()+"&amp;csort="+vo3.getMenu_sort());
							sb.append("&amp;tsort=" + tsort + "&amp;tcsort=" + vo3.getMenu_sort() + "&amp;csort=" + vo4.getMenu_sort());
							sb.append("&amp;is_info_offerer=" + svo.getIs_info_offerer() + "&amp;is_satisfaction=" + svo.getIs_satisfaction());
							sb.append("&amp;tpl_num=" + svo.getTpl_num()+"\"");
								if(vo.getUsr_menu_cd().equals(svo.getUsr_menu_cd())){
									sb.append(" class=\"ov\"");
								}
						}
						sb.append(" >");
						sb.append(svo.getMenu_nm());
						sb.append("</a>\n</li>\n");
					}
					sb.append("</ul>\n");
					sb.append("<div class=\"clear\"></div>\n");
					sb.append("</div>\n");

					sb.append("<div class=\"tab_menu_b\"></div>\n");

				}else if(child_type.equals("MIX")){
					System.out.println("믹스영역입니다.");
					ArrayList list = new ArrayList();
					ArrayList sub_list = new ArrayList();
					UsrMenuVO vo1 = new UsrMenuVO();
					UsrMenuVO vo2 = new UsrMenuVO();
					UsrMenuVO vo3 = new UsrMenuVO();
					vo1 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,2)+"00000000");//선택된 메뉴의 상위 상세정보
					vo2 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,4)+"000000");//선택된 메뉴의 상위 상세정보
					vo3 = dao.GetUsrMenuChildType(usr_menu_cd.substring(0,6)+"0000");//선택된 메뉴의 상위 상세정보
					//String child_menu_cd = vo.getMenu_pcd();
					String child_menu_cd = usr_menu_cd.substring(0,6)+"0000";
					list = dao.GetTopParentMenu(child_menu_cd);//선택된 메뉴의 상위값으로 검색된 하위 메뉴들(선택된 메뉴와 동일한 메뉴들 list)

					sb.append("<div id=\"moveTab\">\n");
					sb.append("<div id=\"moveTab_ov\"></div>\n");
					sb.append("<ul>\n");
					if(list.size()>0){
						for(int i=0;i<list.size();i++){
							UsrMenuVO svo = new UsrMenuVO();
							svo=(UsrMenuVO)list.get(i);
							if(svo.getUsr_menu_cd() != null && ( "06".equals(svo.getUsr_menu_cd().substring(0,2))  || "07".equals(svo.getUsr_menu_cd().substring(0,2))    )){
								tsort = Integer.parseInt(vo.getUsr_menu_cd().substring(0,2));
							}else{
								tsort = Integer.parseInt(vo1.getMenu_sort());
							}
							for(int d=0;d<del_list.size();d++){
								if(svo.getUsr_menu_cd().equals(del_list.get(d))){
									del_yn=false;
								}
							}
							if(!del_yn)break;
							sb.append("<li>\n<a href=\"");
							sb.append(svo.getMenu_url());
							if(svo.getIs_link().equals("N"))sb.append("\" target=\"_blank\" ");
							else{
								sb.append("&amp;tsort="+tsort+"&amp;tcsort="+vo2.getMenu_sort()+"&amp;csort="+vo3.getMenu_sort());
								sb.append("&amp;is_info_offerer="+svo.getIs_info_offerer()+"&amp;is_satisfaction="+svo.getIs_satisfaction()+"&amp;tpl_num="+svo.getTpl_num()+"\"");
							}
							sb.append(" >");
							sb.append(svo.getMenu_nm());
							sb.append("</a>\n</li>\n");
						}
					}
					sb.append("</ul>\n");
					sb.append("</div>\n");

					sb.append("<script type=\"text/javascript\">\n");
					//sb.append("//위치할 숫자를 입력하세요");
					sb.append("moveTab_init("+uvo.getMenu_sort()+");\n");
					sb.append("</script>\n");
					System.out.println("usr_menu_cd ="+usr_menu_cd);
					System.out.println("usr_menu_cd ="+usr_menu_cd);
					System.out.println("usr_menu_cd ="+usr_menu_cd);

					sub_list = dao.GetTopParentMenu(vo.getMenu_pcd());
					sb.append("<div class=\"bott_mar_20\"></div>\n");
					sb.append("<div class=\"tabAc\">\n");
					sb.append("<strong class=\"moveTab2_ti\"><img src=\"/commcms/images/moveTab2_ti.gif\" alt=\"TABMENU\" /></strong>\n");
					sb.append("<ul>\n");
					if(sub_list.size()>0){
						for(int j=0;j<sub_list.size();j++){
							UsrMenuVO ssvo = new UsrMenuVO();
							ssvo=(UsrMenuVO)sub_list.get(j);
							sb.append("<li>\n<a href=\"");
							sb.append(ssvo.getMenu_url()+"&amp;tsort="+tsort+"&amp;tcsort="+vo2.getMenu_sort()+"&amp;csort="+vo3.getMenu_sort());
							sb.append("&amp;is_info_offerer="+ssvo.getIs_info_offerer()+"&amp;is_satisfaction="+ssvo.getIs_satisfaction());
							sb.append("&amp;tpl_num="+ssvo.getTpl_num()+"\"");
							if(vo.getUsr_menu_cd().equals(ssvo.getUsr_menu_cd())){
								sb.append(" class=\"tab_ov\"");
							}
							if(ssvo.getIs_link().equals("N"))sb.append("\" target=\"_blank\" ");
							sb.append(" >");
							sb.append(ssvo.getMenu_nm());
							sb.append("</a>\n</li>\n");
						}
					}
					sb.append("</ul>\n");
					sb.append("</div>\n");
				}
			}

			out.print(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		} finally {}
		return EVAL_PAGE;
	}
}