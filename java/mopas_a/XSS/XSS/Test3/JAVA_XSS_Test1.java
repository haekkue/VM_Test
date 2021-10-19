package kr.co.util.taglib;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class PagingTag extends BodyTagSupport{
	private final String JAVASCRIPT_FUNCTION_HEADER = "_";
	private final String JAVASCRIPT_FUNCTION_NAME = "Paging";

	protected String name;
	protected String action;
	protected String method;
	protected boolean formgen;
	protected int start;

	protected int total;
	protected int pagescale = 10;
	protected int screenscale = 10;

	protected String totalParam = "total";
	protected String startParam = "start";
	protected String pagescaleParam = "pagescale";
	protected String screenscaleParam = "screenscale";

	protected String css;
	protected String js;

	@SuppressWarnings("rawtypes")
	private ArrayList argumentList;

	protected int totalPages = 0; // 총 페이지 수
	protected int curPage = 0; // 현제 페이지
	protected int pageGroupNo = 0; // 페이지 그룹 번호
	protected int curFirstPage = 0; // 현제 페이지 그룹의 시작번호
	protected int curLastPage = 0; // 현제 페이지 그룹의 끝 번호
	protected int lastPageNo = 0; // 마지막 페이지 번호
	protected int prevPageGroupStart = 0; // 이전 페이지 그룹의 시작번호
	protected int nextPageGroupStart = 0; // 다음 페이지 그룹의 시작번호

	@SuppressWarnings("rawtypes")
	public PagingTag() {
		super();
		argumentList = new ArrayList();
	}

	/**
	 * 새로운 인자 생성
	 * @param name
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	protected void addArgument(String name, String value) {
		if (name != null && value != null && !name.equals("") && !value.equals("")) {
			argumentList.add(new PagingArgument(name, value));
		}
	}

	/**
	 * 페이징 자바스크립트 함수
	 * @return
	 */
	protected String getJavaScript() {
		if( js != null && !js.equals("") )
			return js;
		else
			return JAVASCRIPT_FUNCTION_HEADER + name + JAVASCRIPT_FUNCTION_NAME;
	}

	private void initPaging() {
		// 총페이지
		totalPages = total / pagescale;
		if (total % pagescale > 0)
			totalPages += 1;

		// 현제 페이지
		curPage = start / pagescale + 1;

		// 페이지 인덱싱 그룹의 순번
		pageGroupNo = curPage / screenscale;
		if (curPage % screenscale > 0)
			pageGroupNo += 1;

		// 페이지 인덱싱 내에서 보여질 첫 페이지
		curFirstPage = (pageGroupNo - 1) * screenscale + 1;

		// 페이지 인덱싱 내에서 보여질 마지막 페이지
		curLastPage = curFirstPage + screenscale - 1;
		if (curLastPage > totalPages)
			curLastPage = totalPages;

		// 마지막 페이지 글 번호
		if (totalPages > 0)
			lastPageNo = pagescale * (totalPages - 1);

		// 이전 페이지 인덱싱 그룹의 첫 번째 글 번호
		if (pageGroupNo > 1)
			prevPageGroupStart = (pageGroupNo - 2) * screenscale * pagescale;

		// 다음 페이지 인덱싱 그룹의 첫 번째 글 번호
		if (curLastPage < totalPages)
			nextPageGroupStart = pageGroupNo * screenscale * pagescale;
	}

	public void initTag() {
		argumentList.clear();
	}

	// ############ [ Tag Event Start ] ############ //
	public void doInitBody() throws JspException {
		StringBuffer html = null;
		JspWriter out = null;

		try {
			html = new StringBuffer();
			out = getPreviousOut();

			if( formgen ) {
				// 페이징 자바스크립트 생성
				html.append("<script type=\"text/javascript\"> \n");
				html.append("<!-- \n");

				html.append("function " + getJavaScript() + "(start) { \n");

				html.append("document.forms['" + name + "']['" + startParam + "'].value=start; \n");
				html.append("document.forms['" + name + "'].submit(); \n");

				html.append("}");

				html.append("//--> \n");
				html.append("</script> \n");
			}

			out.print(html.toString());

			// 페이징 정보 초기화
			initPaging();

		} catch (Exception e) {
			System.out.println("paging exception : " + e);
		}

	}

	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}

	public int doStartTag() {
		initTag(); // 페이징 태그를 초기화 합니다.

		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() {
		StringBuffer html = null;
		JspWriter out = null;
		BodyContent content = null;

		try {
			html = new StringBuffer();
			content = getBodyContent();
			out = this.getPreviousOut();

			if (formgen) {
				// 폼 태그 생성
				html.append("<form ");

				if (name != null)
					html.append("name=\"" + name + "\" ");

				if (action != null)
					html.append("action=\"" + action + "\" ");

				if (method != null)
					html.append("method=\"" + method + "\" ");

				html.append("> \n");

				// 기본 페이징 정보 설정
				html.append("<input type=\"hidden\" name=\"" + startParam + "\" value=\"" + start + "\" /> \n");
				html.append("<input type=\"hidden\" name=\"" + totalParam + "\" value=\"" + total + "\" /> \n");
				html.append("<input type=\"hidden\" name=\"" + pagescaleParam + "\" value=\"" + pagescale + "\" /> \n");
				html.append("<input type=\"hidden\" name=\"" + screenscaleParam + "\" value=\"" + screenscale + "\" /> \n");

				// 기타 인자
				PagingArgument arg = null;
				for (int i = 0; i < argumentList.size(); i++) {
					arg = (PagingArgument) argumentList.get(i);

					html.append("<input type=\"hidden\" name=\"" + arg.getName() + "\" value=\"" + arg.getValue() + "\" /> \n");
				}

				html.append("</form>");
			}

			out.print(html.toString());
			out.print(content.getString());

		} catch (Exception e) {
			System.out.println("exception : " + e);
		}

		return EVAL_PAGE;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isFormgen() {
		return formgen;
	}
	public void setFormgen(boolean formgen) {
		this.formgen = formgen;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPagescale() {
		return pagescale;
	}
	public void setPagescale(int pagescale) {
		this.pagescale = pagescale;
	}
	public int getScreenscale() {
		return screenscale;
	}
	public void setScreenscale(int screenscale) {
		this.screenscale = screenscale;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getPagescaleParam() {
		return pagescaleParam;
	}
	public void setPagescaleParam(String pagescaleParam) {
		this.pagescaleParam = pagescaleParam;
	}
	public String getScreenscaleParam() {
		return screenscaleParam;
	}
	public void setScreenscaleParam(String screenscaleParam) {
		this.screenscaleParam = screenscaleParam;
	}
	public String getStartParam() {
		return startParam;
	}
	public void setStartParam(String startParam) {
		this.startParam = startParam;
	}
	public String getTotalParam() {
		return totalParam;
	}
	public void setTotalParam(String totalParam) {
		this.totalParam = totalParam;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public String getJs() {
		return js;
	}
	public void setJs(String js) {
		this.js = js;
	}
}