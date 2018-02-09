package by.tr.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCacheFilter implements Filter {

    private static final String CACHE_CONTROL_FIELD = "Cache-Control";
    private static final String CACHE_CONTROL_FIELD_VALUE = "no-cache, no-store, must-revalidate";
    private static final String PRAGMA_FIELD = "Pragma";
    private static final String NO_CACHE = "no-cache";
    private static final String EXPIRES_FIELD = "Expires";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader(CACHE_CONTROL_FIELD, CACHE_CONTROL_FIELD_VALUE);
        response.setHeader(PRAGMA_FIELD, NO_CACHE);
        response.setDateHeader(EXPIRES_FIELD, 0);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
