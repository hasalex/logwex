/*
 * Copyright 2010 Sewatech
 *
 * This file is part of LogWEx.
 *
 * LogWEx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published bythe Free Software Foundation, in version 3 of the
 * License.
 *
 * LogWEx is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with LogWEx.  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.sewatech.logwex;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.security.Principal;
import java.util.Hashtable;

/**
 * <p>Fill the MDC with some data from HttpServletRequest and clear it.<p/>
 * 
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
     * <p>Fill the MDC. Retrieve all the readable properties of the request.</p>
     * <p>Each property is placed in the MDC under the key request.<i>propertyName</i>. The only nested properties retrieved
     * are the session id, under the key session.id, and the user name under key request.userName.</p>
     * <p>The inputStream property is excluded.</p>
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
            if (descriptor.getReadMethod() != null && !"inputStream".equals(descriptor.getName()))
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
        final Hashtable context = MDC.getContext();
        if (context != null)
            context.clear();
    }
}
