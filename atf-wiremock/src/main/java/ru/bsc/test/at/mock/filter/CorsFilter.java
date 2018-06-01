package ru.bsc.test.at.mock.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sdoroshin on 03.08.2017.
 *
 */
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        //no initialization logic
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Headers", "*");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        ((HttpServletResponse)response).addHeader("Allow", "GET, POST, PUT, DELETE, OPTIONS");
        if ("OPTIONS".equals(((HttpServletRequest)request).getMethod())) {
            ((HttpServletResponse)response).setStatus(200);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //no destroy logic
    }
}
