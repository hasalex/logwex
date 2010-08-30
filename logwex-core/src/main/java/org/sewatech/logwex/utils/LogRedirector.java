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

import java.io.PrintStream;

/**
 * Created : 15 août 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class LogRedirector {
    private static final Logger logger = Logger.getLogger(LogRedirector.class);

    private static StringOutputStream output = new StringOutputStream();

    public static void initialize() {
        logger.debug("Redirecting logs...");

        System.setOut(new PrintStream(output));

    }

    public static void endup() {
        logger.debug("Redirecting logs...");

        System.setOut(System.out);

    }
    public static String getLogs() {
        logger.debug("Getting logs...");

        String result = output.toString();
        output.clean();
        return result;
    }

}
