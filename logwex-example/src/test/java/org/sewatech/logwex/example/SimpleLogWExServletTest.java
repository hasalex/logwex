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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.sewatech.logwex.utils.ReflectionUtils;

import java.io.OutputStream;

import static junit.framework.Assert.*;

/**
 * Created : 23 août 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class SimpleLogWExServletTest {
    private SimpleLogWExServlet servlet;

    @Before
    public void setUp() throws Exception {
        servlet = new SimpleLogWExServlet();
    }

    @Test
    public void testInit() throws Exception {
        Logger logger = Logger.getLogger(SimpleLogWExServlet.class);

        servlet.init();
        System.out.println("Log OK ? " + logger.isInfoEnabled());
        logger.info("Message");
        OutputStream stream = (OutputStream)ReflectionUtils.getFieldValue(servlet, "stream");
        //stream.flush();
        final String logs = stream.toString();
        assertEquals("Log text with SimpleLayout", "INFO - Message\n", logs);
        System.out.println(logs);
    }

    @Test
    public void testChangePattern() throws Exception {
        Logger logger = Logger.getLogger(SimpleLogWExServlet.class);

        servlet.init();
        servlet.changePattern("%p%n");
        logger.info("Message");
        OutputStream stream = (OutputStream)ReflectionUtils.getFieldValue(servlet, "stream");
        //stream.flush();
        final String logs = stream.toString();
        assertEquals("Log text with PatternLayout %p%n", "INFO\n", logs);
        System.out.println(logs);

    }

}
