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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sewatech.logwex.utils.ReflectionUtils;

import static org.junit.Assert.*;

/**
 * Created : 7 ao√ªt 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class ConfigurationTest {
    private Configuration configuration;
    private static final String ONE = "One";
    private static final String TWO = "Two";
    private static final String THREE = "Three";
    private static final String[] EMPTY_STRINGS = new String[]{};
    private static final String[] ONE_STRINGS = new String[]{ONE};
    private static final String[] THREE_STRINGS = new String[]{ONE, TWO, THREE};

    @Before
    public void setUp() throws Exception {
        configuration = new Configuration();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testExtractNonEmptyElements() throws Exception {
        assertArrayEquals(ONE_STRINGS, callExtract(ONE));
        assertArrayEquals(THREE_STRINGS, callExtract(ONE + "," + TWO + "," + THREE));
    }

    @Test
    public void testExtractEmptyElements() throws Exception {
        assertArrayEquals(EMPTY_STRINGS, callExtract(null));
        assertArrayEquals(EMPTY_STRINGS, callExtract(""));
        assertArrayEquals(EMPTY_STRINGS, callExtract("   "));
        assertArrayEquals(EMPTY_STRINGS, callExtract(","));
        assertArrayEquals(EMPTY_STRINGS, callExtract(" , , "));
    }

    @Test
    public void testExtractMixedElements() throws Exception {
        assertArrayEquals(ONE_STRINGS, callExtract("  " + ONE + "  "));
        assertArrayEquals(THREE_STRINGS, callExtract(ONE + " , " + TWO + "," + THREE + "  "));
        assertArrayEquals(THREE_STRINGS, callExtract(ONE + ",  ," + TWO + "," + THREE));
    }


    private String[] callExtract(String values) throws Exception {
        return (String[]) ReflectionUtils.invokeMethod(configuration, "extract", values);
    }
}
