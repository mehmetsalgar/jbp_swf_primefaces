package org.salgar.primefaces.portal.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.salgar.primefaces.portal.wrapper.PrimefacesPortalReponseWrapper;

public class PrimefacesPortalFilter implements Filter {
    private static final String TAG_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><partialResponse>";
    private static final String TAG_END = "</partialResponse>";

    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        String path = ((HttpServletRequest) request).getPathInfo();
        if (path.indexOf(".") < 0) {
            PrintWriter out = response.getWriter();
            try {
                PrimefacesPortalReponseWrapper portalReponseWrapper = new PrimefacesPortalReponseWrapper(
                        (HttpServletResponse) response);
                chain.doFilter(request, portalReponseWrapper);

                if (portalReponseWrapper.getContentType() != null
                        && portalReponseWrapper.getContentType().indexOf("text/html") >= 0) {
                    String result = portalReponseWrapper.toString();

                    int start = result.indexOf(TAG_START);
                    if (start >= 0) {
                        int end = result.indexOf(TAG_END);
                        result = result.substring(start, end + TAG_END.length());
                        response.setContentType("text/xml");
                    }
                                        
                    out.write(result);
                } else {
                    out.write(portalReponseWrapper.toString());
                }
            } finally {
                out.close();
            }
        } else if (path.indexOf("core.js") >= 0) {
            String core ="";
            PrintWriter out = response.getWriter();
            out.write(core);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

}
