package org.fundaciobit.blueprint.common.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Logged
@Interceptor
public class LoggerInterceptor {

    @Inject
    private Logger logger;

    @AroundInvoke
    public Object logCall(InvocationContext context) throws Exception {
        String fullMethodName =
                context.getTarget().getClass().getSimpleName()
                        + "." + context.getMethod().getName();
        logger.info("Call: " + fullMethodName);
        Object result = context.proceed();
        logger.info(fullMethodName + " return");
        return result;
    }
}
