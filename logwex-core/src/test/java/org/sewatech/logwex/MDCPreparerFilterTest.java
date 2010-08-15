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
import org.junit.Before;
import org.junit.Test;
import org.sewatech.logwex.utils.MDCUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.security.Principal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created : 9 ao√ªt 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class MDCPreparerFilterTest {
    private MDCPreparerFilter mdcPreparerFilter;

    @Before
    public void setUp() throws Exception {
        mdcPreparerFilter = new MDCPreparerFilter();
        final FilterConfig filterConfig = mock(FilterConfig.class);
        mdcPreparerFilter.init(filterConfig);
    }

    @Test
    public void testDoFilter() throws Exception {
        assertNotNull("MDCPreparer should not be null", getMDCPreparer());

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

        HttpServletResponse response = mock(HttpServletResponse.class);
        final FilterChain filterChain = mock(FilterChain.class);

        mdcPreparerFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(request).getSession(false);
        assertFalse("MDC should not be empty", MDCUtils.isMDCEmpty(false));
        assertEquals("Value of server name", serverName, MDC.get("request.serverName"));
        assertEquals("Value of server port", serverPort, MDC.get("request.serverPort"));
        assertEquals("Value of user name", userName, MDC.get("request.userName"));
        assertEquals("Value of session id", sessionId, MDC.get("session.id"));
    }

    @Test
    public void testDoFilterWithEmptyRequest() throws Exception {
        assertNotNull("MDCPreparer should not be null", getMDCPreparer());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        final FilterChain filterChain = mock(FilterChain.class);

        mdcPreparerFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(request).getSession(false);

    }

    @Test
    public void testDestroy() throws Exception {
        assertNotNull("MDCPreparer should not be null", getMDCPreparer());
        mdcPreparerFilter.destroy();
        assertNull("MDCPreparer should be null", getMDCPreparer());
        assertTrue("MDC should be empty after destroy", MDCUtils.isMDCEmpty(false));
    }

    private MDCPreparer getMDCPreparer() throws Exception {
        Field mdcPreparerField = mdcPreparerFilter.getClass().getDeclaredField("mdcPreparer");
        mdcPreparerField.setAccessible(true);
        return (MDCPreparer) mdcPreparerField.get(mdcPreparerFilter);
    }
}
