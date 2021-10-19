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

	protected int totalPages = 0; // �� ������ ��
	protected int curPage = 0; // ���� ������
	protected int pageGroupNo = 0; // ������ �׷� ��ȣ
	protected int curFirstPage = 0; // ���� ������ �׷��� ���۹�ȣ
	protected int curLastPage = 0; // ���� ������ �׷��� �� ��ȣ
	protected int lastPageNo = 0; // ������ ������ ��ȣ
	protected int prevPageGroupStart = 0; // ���� ������ �׷��� ���۹�ȣ
	protected int nextPageGroupStart = 0; // ���� ������ �׷��� ���۹�ȣ

	@SuppressWarnings("rawtypes")
	public PagingTag() {
		super();
		argumentList = new ArrayList();
	}

	/**
	 * ���ο� ���� ����
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
	 * ����¡ �ڹٽ�ũ��Ʈ �Լ�
	 * @return
	 */
	protected String getJavaScript() {
		if( js != null && !js.equals("") )
			return js;
		else
			return JAVASCRIPT_FUNCTION_HEADER + name + JAVASCRIPT_FUNCTION_NAME;
	}

	private void initPaging() {
		// ��������
		totalPages = total / pagescale;
		if (total % pagescale > 0)
			totalPages += 1;

		// ���� ������
		curPage = start / pagescale + 1;

		// ������ �ε��� �׷��� ����
		pageGroupNo = curPage / screenscale;
		if (curPage % screenscale > 0)
			pageGroupNo += 1;

		// ������ �ε��� ������ ������ ù ������
		curFirstPage = (pageGroupNo - 1) * screenscale + 1;

		// ������ �ε��� ������ ������ ������ ������
		curLastPage = curFirstPage + screenscale - 1;
		if (curLastPage > totalPages)
			curLastPage = totalPages;

		// ������ ������ �� ��ȣ
		if (totalPages > 0)
			lastPageNo = pagescale * (totalPages - 1);

		// ���� ������ �ε��� �׷��� ù ��° �� ��ȣ
		if (pageGroupNo > 1)
			prevPageGroupStart = (pageGroupNo - 2) * screenscale * pagescale;

		// ���� ������ �ε��� �׷��� ù ��° �� ��ȣ
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
				// ����¡ �ڹٽ�ũ��Ʈ ����
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

			// ����¡ ���� �ʱ�ȭ
			initPaging();

		} catch (Exception e) {
			System.out.println("paging exception : " + e);
		}

	}

	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}

	public int doStartTag() {
		initTag(); // ����¡ �±׸� �ʱ�ȭ �մϴ�.

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
				// �� �±� ����
				html.append("<form ");

				if (name != null)
					html.append("name=\"" + name + "\" ");

				if (action != null)
					html.append("action=\"" + action + "\" ");

				if (method != null)
					html.append("method=\"" + method + "\" ");

				html.append("> \n");

				// �⺻ ����¡ ���� ����
				html.append("<input type=\"hidden\" name=\"" + startParam + "\" value=\"" + start + "\" /> \n");
				html.append("<input type=\"hidden\" name=\"" + totalParam + "\" value=\"" + total + "\" /> \n");
				html.append("<input type=\"hidden\" name=\"" + pagescaleParam + "\" value=\"" + pagescale + "\" /> \n");
				html.append("<input type=\"hidden\" name=\"" + screenscaleParam + "\" value=\"" + screenscale + "\" /> \n");

				// ��Ÿ ����
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