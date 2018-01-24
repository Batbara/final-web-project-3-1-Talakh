package by.tr.web.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CookieManager {
    private List<Cookie> cookies;
    private int defaultExpiry;

    public CookieManager() {
        cookies = new ArrayList<>();
        defaultExpiry = 30 * 24 * 60 * 60;
    }

    public CookieManager(HttpServletRequest request) {
        this();
        Cookie[] cookiesFromRequest = request.getCookies();
        if (cookiesFromRequest == null) {
            cookies = new ArrayList<>();
        } else {
            this.cookies = new ArrayList<>(Arrays.asList(cookiesFromRequest));
        }
    }

    public boolean isCookieInRequest(String name) {
        for (Cookie c : cookies) {
            if (c.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public int getDefaultExpiry() {
        return defaultExpiry;
    }

    public void setDefaultExpiry(int defaultExpiry) {
        this.defaultExpiry = defaultExpiry;
    }

    public Cookie takeCookie(String name) throws CookieNotFoundException {
        for (Cookie c : cookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        throw new CookieNotFoundException("Cannot find cookie " + name);
    }

    public String takeCookieValue(String name) throws CookieNotFoundException {
        Cookie cookie = takeCookie(name);
        return cookie.getValue();
    }

    public Cookie makeCookie(String name, String value) {
        return makeCookie(name, value, defaultExpiry);
    }


    public Cookie makeCookie(String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        addCookieToList(cookie);
        return cookie;
    }

    private void addCookieToList(Cookie cookie) {
        String cookieName = cookie.getName();
        String cookieValue = cookie.getValue();
        int cookieAge = cookie.getMaxAge();

        for (Cookie c : cookies) {

            if (c.getName().equals(cookieName)) {
                c.setValue(cookieValue);
                c.setMaxAge(cookieAge);
                return;
            }
        }
        cookies.add(cookie);
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = Arrays.asList(cookies);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}
