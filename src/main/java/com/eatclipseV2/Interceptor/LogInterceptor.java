package com.eatclipseV2.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        log.info("==================== BEGIN ====================");
        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, method);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("REQUEST [{}][{}][{}]", logId, requestURI, method);
        log.info("==================== END ======================");
        if (ex != null) {
            log.error("afterCompletion error", ex);
        }
    }
}
