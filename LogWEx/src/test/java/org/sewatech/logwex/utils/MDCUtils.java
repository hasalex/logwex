package org.sewatech.logwex.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import java.util.Map;

/**
 * Created : 9 août 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class MDCUtils {
    private static final Logger logger = Logger.getLogger(MDCUtils.class);

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
