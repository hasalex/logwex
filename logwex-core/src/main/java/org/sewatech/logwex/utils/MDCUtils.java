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
import org.apache.log4j.MDC;

import java.util.Map;

/**
 * Utility methods for MDC
 *
 * Created : 9 août 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class MDCUtils {
    private static final Logger logger = Logger.getLogger(MDCUtils.class);


    /**
     * Check if the MDC is empty.
     *
     * @param strict
     * @return
     */
    public static boolean isMDCEmpty(boolean strict) {
        logger.debug("isMDCEmpty ?");
        Map contextMap = MDC.getContext();
        if (contextMap == null || MDC.getContext().isEmpty()) return true;
        if (strict) return false;

        for (Object object : contextMap.values()) {
            if (object instanceof Number && !object.equals(0)) {
                return false;
            }
        }
        return true;
    }
}
