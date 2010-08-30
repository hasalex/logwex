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

import java.io.IOException;
import java.io.OutputStream;

/**
* Created : 19 août 2010
*
* @author Alexis Hassler
* @since 1.0
*/
class StringOutputStream extends OutputStream {
        private static final Logger logger = Logger.getLogger(StringOutputStream.class);
    private StringBuilder string = new StringBuilder();

    @Override
    public void write(int b) throws IOException {
        this.string.append((char) b);
    }

    public void clean() {
        string.setLength(0);
    }

    public String toString() {
        final String result = this.string.toString();
        if (logger.isDebugEnabled())
            logger.debug("Result of StringOutputStream : " + result);
        return result;
    }
}
