package org.sewatech.logwex;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.security.Principal;

/**
 * Fills the MDC with some informations from HttpServletRequest and clear it.
 * <p/>
 * Created : 28 juil. 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
class MDCPreparer {
    private static final Logger logger = Logger.getLogger(MDCPreparer.class);
    private final Configuration configuration;

    public MDCPreparer(Configuration configuration) {
        logger.debug("Creating MDCPreparer");
        this.configuration = configuration;
    }


    /**
     * Fills the MDC
     *
     * @param request
     * @param servletContext for future use
     */
    public void fill(HttpServletRequest request, ServletContext servletContext) {
        logger.debug("MDCPreparer.fill");

        if (!(MDC.getContext() == null || MDC.getContext().isEmpty()) && logger.isInfoEnabled()) {
            logger.info("Pay attention, MDC is not empty, it has the further elements :" + MDC.getContext().keys());
        }

        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(request);
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getReadMethod() != null)
                this.putPropertyInMDC(request, descriptor.getName(), "request");
        }


        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null) {
            //MDC.put("request.userPrincipal", userPrincipal); Excluded for security reasons (cf Tomcat)
            final String username = userPrincipal.getName();
            MDC.put("request.userName", username);
            if (logger.isDebugEnabled()) logger.debug("request.userName = " + username);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            final String sessionId = session.getId();
            MDC.put("session.id", sessionId);
            if (logger.isDebugEnabled()) logger.debug("session.id = " + sessionId);
        }
    }


    /**
     * Put a property from the request in the MDC.<br>
     * If the property does not exist, it only log the information. If the property is null, it does nothing.
     *
     * @param request
     * @param propertyName
     */
    private void putPropertyInMDC(Object request, String propertyName, String prefix) {
        try {
            Object propertyValue = PropertyUtils.getProperty(request, propertyName);
            logger.debug(prefix + "." + propertyName + " = " + propertyValue);
            if (propertyValue != null) {
                MDC.put(prefix + "." + propertyName, propertyValue);
            }
        } catch (Exception e) {
            logger.warn("Problem while accessing the property " + propertyName, e);
        }
    }

    /**
     * Clears the MDC
     */
    public void clear() {
        logger.debug("MDCPreparer.clear");
        MDC.getContext().clear();
    }
}
