package org.sewatech.logwex.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Created : 8 août 2010
 *
 * @author Alexis Hassler
 * @since 1.0
 */
public class ReflectionUtils {
    private static final Logger logger = Logger.getLogger(ReflectionUtils.class);

    public static Object invokeMethod(Class<?> targetType, String methodName, Class<?>[] argumentTypes, Object target, Object[] arguments) throws ReflectionException {
        try {
            Method method = targetType.getDeclaredMethod(methodName, argumentTypes);
            method.setAccessible(true);
            return method.invoke(target, arguments);
        } catch (Exception e) {
            logger.error(e);  //To change body of catch statement use File | Settings | File Templates.
            throw new ReflectionException("Cannot call the method " + methodName, e);
        }
    }

    /**
     * <p>Invoke the first method found with same name and all arguments assignable</p>
     *
     * @param target
     * @param methodName
     * @param arguments
     * @return
     * @throws ReflectionException
     */
    public static Object invokeMethod(Object target, String methodName, Object... arguments) throws ReflectionException {
        Class<?> targetType = target.getClass();
        Class<?>[] argumentTypes = new Class<?>[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] == null) {
                argumentTypes[i] = null;
            } else {
                argumentTypes[i] = arguments[i].getClass();
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Trying to invoke the method " + targetType.getName() + "." + methodName);
        }

        Method[] methods = targetType.getDeclaredMethods();
        for (Method method : methods) {
            if (isMethodAssignable(method, methodName, argumentTypes)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Method found " + method);
                }
                try {
                    method.setAccessible(true);
                    return method.invoke(target, arguments);
                } catch (Exception e) {
                    logger.warn("Fail to call method " + method + ", trying to find an other one", e);
                }
            }
        }
        throw new ReflectionException("Cannot call the method " + methodName + " : not found");
    }

    private static boolean isMethodAssignable(Method method, String methodName, Class<?>[] argumentTypes) {
        if (!methodName.equals(method.getName())) {
            return false;
        }
        Class<?>[] methodArgumentTypes = method.getParameterTypes();
        if (argumentTypes.length != methodArgumentTypes.length) {
            return false;
        }
        for (int i = 0; i < argumentTypes.length; i++) {
            if ((argumentTypes[i] != null) && (!methodArgumentTypes[i].isAssignableFrom(argumentTypes[i]))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the sum of the object transformation cost for each class in the source
     * argument list.
     *
     * @param srcArgs  The source arguments
     * @param destArgs The destination arguments
     * @return The total transformation cost
     */
    private static float getTotalTransformationCost(Class[] srcArgs, Class[] destArgs) {

        float totalCost = 0.0f;
        for (int i = 0; i < srcArgs.length; i++) {
            Class srcClass, destClass;
            srcClass = srcArgs[i];
            destClass = destArgs[i];
            totalCost += getObjectTransformationCost(srcClass, destClass);
        }

        return totalCost;
    }

    /**
     * Gets the number of steps required needed to turn the source class into the
     * destination class. This represents the number of steps in the object hierarchy
     * graph.
     *
     * @param srcClass  The source class
     * @param destClass The destination class
     * @return The cost of transforming an object
     */
    private static float getObjectTransformationCost(Class srcClass, Class destClass) {
        float cost = 0.0f;
        while (destClass != null && !destClass.equals(srcClass)) {
            if (destClass.isInterface() && destClass.isAssignableFrom(srcClass)) {
                // slight penalty for interface match.
                // we still want an exact match to override an interface match, but
                // an interface match should override anything where we have to get a
                // superclass.
                cost += 0.25f;
                break;
            }
            cost++;
            destClass = destClass.getSuperclass();
        }

        /*
            * If the destination class is null, we've travelled all the way up to
            * an Object match. We'll penalize this by adding 1.5 to the cost.
        */
        if (destClass == null) {
            cost += 1.5f;
        }

        return cost;
    }

}
