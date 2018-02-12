package by.tr.web.listener;

import by.tr.web.controller.constant.FrontControllerParameter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class LocaleSetter implements
        HttpSessionListener {

    public LocaleSetter() {
    }

    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String defaultLocale = FrontControllerParameter.Language.getDefault();
        session.setAttribute(FrontControllerParameter.LOCALE, defaultLocale);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
    }


}
