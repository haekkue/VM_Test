public class test3
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

    private void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;
        data = request.getParameter("name");

        // ???Ⱦ??? 
        response.getWriter().write(data);
    }
}