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

package org.sewatech.logwex.utils;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created : 19 août 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class StringOutputStreamTest {
    private static final Logger logger = Logger.getLogger(StringOutputStreamTest.class);
    private StringOutputStream stream;

    @Before
    public void setUp() throws Exception {
        stream = new StringOutputStream();
    }

    @Test
    public void testWrite() throws Exception {
        String text = "AZERTY";
        stream.write(text.getBytes());
        assertEquals(text, stream.toString());
    }

    @Test
    public void testClean() throws Exception {
        String text = "AZERTY";
        stream.write(text.getBytes());
        stream.clean();
        assertEquals("", stream.toString());
    }

}
