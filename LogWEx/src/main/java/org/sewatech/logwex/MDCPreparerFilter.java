package org.sewatech.logwex;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created : 28 juil. 2010 
 *
 * @author Alexis Hassler
 * @since 1.0
 */
@SuppressWarnings({"WeakerAccess"})
public class MDCPreparerFilter implements Filter {

    private static final Logger logger = Logger.getLogger(MDCPreparerFilter.class);
    private MDCPreparer mdcPreparer;
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("MDCPreparerFilter.init");
        this.filterConfig = filterConfig;
        Configuration configuration = new Configuration();
        mdcPreparer = new MDCPreparer(configuration);
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("MDCPreparerFilter.doFilter");
        mdcPreparer.fill((HttpServletRequest) servletRequest, filterConfig.getServletContext());
        filterChain.doFilter(servletRequest, servletResponse);

    }

    public void destroy() {
        logger.debug("MDCPreparerFilter.destroy");
        mdcPreparer.clear();
        mdcPreparer = null;
    }
}
