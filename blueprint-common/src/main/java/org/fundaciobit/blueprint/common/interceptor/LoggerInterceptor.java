package org.fundaciobit.blueprint.common.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interceptor per loguejar les cridades als mètodes de la classe interceptada.
 * @author Antoni
 */
@Logged
@Interceptor
public class LoggerInterceptor {

    private static final Logger LOG = Logger.getLogger(LoggerInterceptor.class.getName());

    /**
     * Intercepta un mètode de negoci i fa un log a l'inici i al final.
     * @param context Contexte d'invocació.
     * @return El resultat del mètode interceptar.
     * @throws Exception Llança la mateixa excepció que el mètode invocat.
     */
    @AroundInvoke
    public Object logCall(InvocationContext context) throws Exception {
        String fullMethodName =
                context.getTarget().getClass().getSimpleName()
                        + "." + context.getMethod().getName();
        LOG.log(Level.INFO, "Call: {0}", fullMethodName);
        Object result = context.proceed();
        LOG.log(Level.INFO, "{0} return", fullMethodName);
        return result;
    }
}
