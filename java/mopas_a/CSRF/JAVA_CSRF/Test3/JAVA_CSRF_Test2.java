public class test4
{
    private bool verify(HttpServletRequest request) throws Throwable
    {
        HttpSession session = request.getSession(true);
        String trustedToken = (String) session.getAttribute("token");

        if (request.getParamter("token") == null || !request.getParameter("token").equals(trustedToken))
            return false;
        return true;
    }

    private void good(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        data = request.getParameter("name");
        if (verify(request)) {
            response.getWriter().write(data);
        }
    }

    private void good2(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        Boolean res = verify(request);
    
        if (res) {
            String data;
            data = request.getParameter("name");
    
            // 보안약점 
            response.getWriter().write(data);
        }
    }
    
    private void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;
        data = request.getParameter("name");
    
        // 보안약점 
        response.getWriter().write(data);
        
        Boolean res = verify();
    
        if (res) {
            //
        }
    }
}