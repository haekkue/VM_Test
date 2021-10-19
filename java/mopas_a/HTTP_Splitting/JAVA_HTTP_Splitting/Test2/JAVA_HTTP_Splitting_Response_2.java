public class CS
{
	Application	application;

public void bad(HttpServletRequest request, HttpServletResponse response)
{
	String path = request.getParameter("path");
	String name = request.getParameter("name");
	String sysPath = application.getRealPath("/");

	if(path.indexOf("..") > -1 || path == null || path.length() < 10 || path.indexOf("fileUpload") < 0) {
		return;
	}

	File file = new File(sysPath + path);
	String tmp = path.substring(path.lastIndexOf("/"), path.length());
	String ext = tmp.substring(tmp.lastIndexOf("."), tmp.length());
// 	name = name.ext;

	response.setContentType("application/octet-stream");

	String Agent = request.getHeader("USER-AGENT");

	if (Agent.indexOf("MSIE") >= 0) {
		int i = Agent.indexOf('M', 2);
		String IEV = Agent.substring(i + 5, i + 8);
		if (IEV.equalsIgnoreCase("5.5")) {
			// 보안약점
			response.setHeader("Content-Disposition", "filename="+name);
		} else {
			// 보안약점
			response.setHeader("Content-Disposition", "attachment;filename="+name+path);
		}
	} else {
		// 보안약점
		response.setHeader("Content-Disposition", "attachment;filename="+name);
	}

	response.setHeader("Content-Length", "" + file.length());

	// ............
	
}
}