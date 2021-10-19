package session;


import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;


public class SessionDisplay extends HttpServlet {


 public void doGet(HttpServletRequest req, HttpServletResponse res)
 throws ServletException, IOException {


  res.setContentType("text/plain");
  PrintWriter out = res.getWriter();


  HttpSession dummySession = req.getSession(true);
  dummySession.putValue("userID", "Watcher");
  HttpSessionContext context = dummySession.getSessionContext();


  int i = 0;
  Enumeration ids = context.getIds();
  out.println("========================================");   
  out.println("No  SessionID  UserID");   
  out.println("========================================");   
  while (ids.hasMoreElements()) {
   i++;
   String id = (String) ids.nextElement();
   HttpSession session = context.getSession(id);
   String user_id = (String)session.getValue("userID");
   out.print("(" + i + ")" + id );
   out.println("  userID = [" + user_id + "]");   
  }
  out.println("========================================");   
  out.println("Total Connect User : " + i );
  out.println("========================================");   
  out.flush();
 }
}