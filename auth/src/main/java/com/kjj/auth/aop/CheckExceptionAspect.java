package com.kjj.auth.aop;

import com.kjj.auth.tool.SlackTools;
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
public class CheckExceptionAspect {
    private final SlackTools slackTools;

    @Pointcut("execution(* com.kjj.auth.security.filter.*.*(..))")
    private void authPointcut() {}

    @AfterThrowing(pointcut = "authPointcut()", throwing = "ex")
    public void handleServiceException(JoinPoint joinPoint, Exception ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        slackTools.sendRequestDetailToSlack(joinPoint, ex, request);
    }
}
