package com.kjj.noauth.util;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Slf4j
@Component
public class SlackTools {
    @Value("${slack.webhook.url}") private String slackUrl;
    private final Slack slack = Slack.getInstance();

    public void sendRequestDetailToSlack(JoinPoint joinpoint, Exception e, HttpServletRequest request) {
        try {
            slack.send(slackUrl, payload(p -> p.text("[AUTH Server]")
                    .attachments(List.of(generateFilterRequestSlackAttachment(joinpoint, e, request)))));
        } catch (IOException exception) {
            log.warn("Slack 전송 에러");
        }
    }

    private Attachment generateFilterRequestSlackAttachment(JoinPoint joinpoint, Exception e, HttpServletRequest request) {
        LocalDate time = LocalDate.now();
        return Attachment.builder()
                .color("ff0000")
                .title(time + " Request 로그")
                .fields(List.of(
                        generateSlackField("Method", joinpoint.getClass().getSimpleName()),
                        generateSlackField("Exception", e.getClass().getSimpleName()),
                        generateSlackField("URI", request.getRequestURI()),
                        generateSlackField("Method", request.getMethod()),
                        generateSlackField("Query String", request.getQueryString()),
                        generateSlackField("IP", request.getRemoteAddr())
                        )
                )
                .build();
    }


    private Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .valueShortEnough(false)
                .build();
    }
}
