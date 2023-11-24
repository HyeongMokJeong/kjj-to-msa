package com.kjj.auth.aop;

import com.kjj.auth.util.SlackTools;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthServiceExceptionAspect {
    private final SlackTools slackTools;

    @Pointcut("execution(* com.kjj.auth.service..*.*(..))")
    private void servicePackagePointcut() {}

    @AfterThrowing(pointcut = "servicePackagePointcut()", throwing = "ex")
    public void handleServiceException(JoinPoint joinPoint, Exception ex) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        slackTools.sendRequestDetailToSlack(joinPoint, ex, request);
        throw ex;
    }
}
