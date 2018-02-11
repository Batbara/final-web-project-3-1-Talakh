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

    /**
     * Constructor with request object to create list of its cookies
     *
     * @param request object
     */
    public CookieManager(HttpServletRequest request) {
        this();
        Cookie[] cookiesFromRequest = request.getCookies();
        if (cookiesFromRequest == null) {
            cookies = new ArrayList<>();
        } else {
            this.cookies = new ArrayList<>(Arrays.asList(cookiesFromRequest));
        }
    }


    /**
     * Checks if cookie with specified name is in request
     *
     * @param name String object with cookie name
     * @return true if cookie is in request
     * @throws NoSuchCookieInRequest When cookie wasn't found
     */
    public boolean isCookieInRequest(String name)throws NoSuchCookieInRequest {
        for (Cookie c : cookies) {
            if (c.getName().equals(name)) {
                return true;
            }
        }
        throw new NoSuchCookieInRequest("Cookie '" + name + "' not found");
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

    /**
     * Creates new cookie
     *
     * @param name Cookie name
     * @param value Cookie value
     * @return {@link Cookie} object
     */
    public Cookie makeCookie(String name, String value) {
        return makeCookie(name, value, defaultExpiry);
    }


    /**
     * Creates new cookie
     *
     * @param name Cookie name
     * @param value Cookie value
     * @param expiry Cookie expiry time in seconds
     * @return {@link Cookie} object
     */
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
