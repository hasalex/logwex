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

import java.util.Enumeration;

/**
 * User: Alexis Hassler
 */
public class ToStringHelper {
    public static String toString(Enumeration<?> keys) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        while (keys.hasMoreElements()) {
            builder.append(keys.nextElement()).append(", ");
        }
        builder.append(']');
        return null;    }
}
