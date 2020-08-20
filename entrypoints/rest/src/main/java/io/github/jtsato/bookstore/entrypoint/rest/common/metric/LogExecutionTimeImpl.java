package io.github.jtsato.bookstore.entrypoint.rest.common.metric;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.Generated;
import lombok.SneakyThrows;

/**
 * @author Jorge Takeshi Sato
 */

@Generated
@Aspect
@Component
public class LogExecutionTimeImpl {

    private static final Logger log = LoggerFactory.getLogger(LogExecutionTimeImpl.class);

    @SneakyThrows
    @Around("@annotation(io.github.jtsato.bookstore.entrypoint.rest.common.metric.LogExecutionTime)")
    public Object logExecutionTime(final ProceedingJoinPoint joinPoint) {

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final Object proceed = joinPoint.proceed();
        stopWatch.stop();

        final String clazzName = StringUtils.substringAfterLast(joinPoint.getSignature().getDeclaringTypeName(), ".");
        log.debug("LogExecutionTime    -> {} executed in {} milliseconds.", clazzName, stopWatch.getTime());

        return proceed;
    }
}
