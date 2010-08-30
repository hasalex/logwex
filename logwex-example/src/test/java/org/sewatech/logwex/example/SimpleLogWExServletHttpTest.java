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

package org.sewatech.logwex.example;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebClient;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.sewatech.logwex.utils.ReflectionUtils;

import javax.swing.text.html.HTML;
import java.io.OutputStream;

import static junit.framework.Assert.*;

/**
 * Created : 23 août 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class SimpleLogWExServletHttpTest {
    private SimpleLogWExServlet servlet;
    private static final String SERVER_NAME = "localhost";
    private static final String SERVER_PORT = "9999";
    private static final String SCHEME = "http";
    private static final String CONTEXT_ROOT = "/logwex-example";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testHome() throws Exception {
        WebConversation wc = new WebConversation();
        WebRequest homeRequest = new GetMethodWebRequest(SCHEME + "://" + SERVER_NAME + ":" + SERVER_PORT + CONTEXT_ROOT);
        WebResponse homeResponse = wc.getResponse(homeRequest);
        assertEquals("Response status from home page", 200, homeResponse.getResponseCode());
    }

    @Test
    public void testSimpleLog() throws Exception {
        WebConversation wc = new WebConversation();
        WebRequest logRequest = new GetMethodWebRequest(SCHEME + "://" + SERVER_NAME + ":" + SERVER_PORT + CONTEXT_ROOT + "/test.log");
        WebResponse logResponse = wc.getResponse(logRequest);
        assertEquals("Response status from log servlet", 200, logResponse.getResponseCode());
        HTMLElement logsElement = logResponse.getElementWithID("logs");

        assertNotNull("logs element", logsElement);
        assertEquals("log text", "INFO - action", logsElement.getText());

    }

    @Test
    public void testEnhancedLog() throws Exception {
        WebConversation wc = new WebConversation();
        Object servletPath = "/test.log";
        WebRequest logRequest = new GetMethodWebRequest(SCHEME + "://" + SERVER_NAME + ":" + SERVER_PORT + CONTEXT_ROOT + servletPath);
        logRequest.setParameter("pattern", "%X{request.scheme};%X{request.serverName};%X{request.serverPort};" +
                "%X{request.contextPath};%X{request.servletPath};%X{request.protocol};%X{request.method};" +
                "%X{request.secure};%X{session.id}");
        WebResponse logResponse = wc.getResponse(logRequest);
        assertEquals("Response status from log servlet", 200, logResponse.getResponseCode());
        HTMLElement logsElement = logResponse.getElementWithID("logs");

        assertNotNull("logs element", logsElement);
        StringBuffer expected = new StringBuffer(SCHEME).append(";").append(SERVER_NAME).append(";").append(SERVER_PORT)
                .append(";").append(CONTEXT_ROOT).append(";").append(servletPath).append(";").append("HTTP/1.1")
                .append(";").append(logRequest.getMethod()).append(";").append("false").append(";")
                .append(logResponse.getClient().getCookieValue("JSESSIONID"));
        assertEquals("log text", expected.toString(), logsElement.getText());
    }

}
