package com.hmall.eval.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {
    
    public static final String USER_KEY = "user";

    private static HttpSession getSession(Object obj, boolean isNew) {
        if (obj instanceof HttpParam) {
            return ((HttpParam) obj).getSession(isNew);
        } else {
            return ((HttpServletRequest) obj).getSession(isNew);
        }
    }

    //세션을 심는다.
    public static final void setSession(Object req, Object obj) {
        HttpSession session = getSession(req, true);
        session.setMaxInactiveInterval(60*60);
        session.setAttribute(USER_KEY, obj);
    }

    //SessionObject객체를 얻는다.
    public static SessionObject getSession(Object req) {
        HttpSession session = getSession(req, false);
        return session == null ? null : (SessionObject) session.getAttribute(USER_KEY);
//        try{
//			HttpSession session = ((HttpServletRequest) req).getSession();
//			if (session == null)
//				return null;
//			else
//				return (SessionObject) session.getAttribute(USER_KEY);
//
//		}catch(IllegalStateException e){
//			return null;
//		}catch(NullPointerException e1){
//			return null;
//		}
    }

    //현재 로그인한 사용자인지 검사한다.
    public static boolean isLogin(Object req) {
        SessionObject obj = getSession(req);

        return obj == null ? false : true;
    }

	//로그아웃을 클릭한 경우 세션을 해제한다.
    public static void invalidate(Object req) {
        HttpSession session = getSession(req, false);
        if (session != null)
            session.invalidate();
    }
}
