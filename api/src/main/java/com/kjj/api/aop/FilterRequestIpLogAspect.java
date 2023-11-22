package com.kjj.api.aop;

import com.kjj.api.exception.tool.SlackTools;
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
public class FilterRequestIpLogAspect {

    private final SlackTools slackTools;

    @Pointcut("execution(* com.kjj.api.auth..*.*(..))")
    private void authPointcut() {}

    /**
     * 에러 발생 시 slack 메시지 알림 전송하는 AOP
     */
    @AfterThrowing(pointcut = "authPointcut()", throwing = "ex")
    public void handleAuthException(JoinPoint joinPoint, Exception ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        slackTools.sendRequestDetailSlackMessage(ex, request);
    }
}