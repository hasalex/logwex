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

import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sewatech.logwex.utils.MDCUtils;
import org.sewatech.logwex.utils.ReflectionUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created : 7 ao√ªt 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class MDCPreparerTest {
    private MDCPreparer mdcPreparer;

    @Before
    public void setUp() throws Exception {
        Configuration configuration = new Configuration();
        mdcPreparer = new MDCPreparer(configuration);
    }

    @After
    public void tearDown() throws Exception {
        mdcPreparer.clear();
    }

    @Test
    public void testFill() throws Exception {
        final String serverName = "test-server";
        final int serverPort = 9999;
        final String userName = "test-user";
        final String sessionId = "0123456789";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServerName()).thenReturn(serverName);
        when(request.getServerPort()).thenReturn(serverPort);
        Principal userPrincipal = mock(Principal.class);
        when(userPrincipal.getName()).thenReturn(userName);
        when(request.getUserPrincipal()).thenReturn(userPrincipal);
        HttpSession session = mock(HttpSession.class);
        when(session.getId()).thenReturn(sessionId);
        when(request.getSession(anyBoolean())).thenReturn(session);

        ServletContext context = mock(ServletContext.class);

        mdcPreparer.fill(request, context);

        assertFalse("MDC should not be empty", MDCUtils.isMDCEmpty(false));
        assertEquals("Value of server name", serverName, MDC.get("request.serverName"));
        assertEquals("Value of server port", serverPort, MDC.get("request.serverPort"));
        assertEquals("Value of user name", userName, MDC.get("request.userName"));
        assertEquals("Value of session id", sessionId, MDC.get("session.id"));

    }

    @Test
    public void testFillWithEmptyRequest() throws Exception {
        assertTrue("MDC should be empty when starting a new test", MDCUtils.isMDCEmpty(true));
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletContext context = mock(ServletContext.class);

        mdcPreparer.fill(request, context);
        assertTrue("MDC should be empty after filling with empty request", MDCUtils.isMDCEmpty(false));
    }

    @Test
    public void testPutPropertyInMDC() throws Exception {
        final String serverName = "test-server";
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServerName()).thenReturn(serverName);
        ReflectionUtils.invokeMethod(mdcPreparer, "putPropertyInMDC", request, "serverName", "test");
        assertEquals("Value of server name", serverName, MDC.get("test.serverName"));
    }

}
