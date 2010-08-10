package org.sewatech.logwex;

import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * <p>Centralized configuration. Not yet useful.<p/>
 * <p/>
 * Created : 28 juil. 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
final class Configuration {
    private static final Logger logger = Logger.getLogger(Configuration.class);
    private static final String SEPARATOR = ",";

    /**
     * Changes a comma separated list into an array of string. Trims and eliminate empty strings.
     *
     * @param value Comma separated list of values
     * @return array of non-empty string values
     */
    private String[] extract(String value) {
        logger.debug("Configuration.extract");
        if ((value == null) || ("".equals(value.trim()))) {
            logger.debug("Nothing to extract from value '" + value + "'");
            return new String[0];
        }
        String[] values = value.split(SEPARATOR);
        int emptyLines = 0;
        for (int i = 0; i < values.length; i++) {
            int line = i - emptyLines;
            values[line] = values[line].trim();
            if (values[line].isEmpty()) {
                System.arraycopy(values, line + 1, values, line, values.length - line - 1);
                emptyLines++;
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug((values.length - emptyLines) + " element(s) found");
        }
        return Arrays.copyOf(values, values.length - emptyLines);
    }
}
