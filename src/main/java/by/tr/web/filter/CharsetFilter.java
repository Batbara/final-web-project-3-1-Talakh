package by.tr.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private static final Logger logger = Logger.getLogger(CharsetFilter.class);
    private static final String ENCODING_PARAMETER = "pageEncoding";
    private String encoding;

    public void init(FilterConfig fConfig) throws ServletException {
        encoding = fConfig.getInitParameter(ENCODING_PARAMETER);
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }
}