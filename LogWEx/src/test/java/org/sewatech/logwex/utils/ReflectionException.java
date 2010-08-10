package org.sewatech.logwex.utils;

/**
 * Created : 8 août 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
class ReflectionException extends Exception {
    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Exception cause) {
        super(message, cause);
    }
}
