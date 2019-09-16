package org.fundaciobit.blueprint.common.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Logged
@Interceptor
public class LoggerInterceptor {

    private static final Logger log = Logger.getLogger(LoggerInterceptor.class.getName());

    @AroundInvoke
    public Object logCall(InvocationContext context) throws Exception {
        String fullMethodName =
                context.getTarget().getClass().getSimpleName()
                        + "." + context.getMethod().getName();
        log.info("Call: " + fullMethodName);
        Object result = context.proceed();
        log.info(fullMethodName + " return");
        return result;
    }
}
