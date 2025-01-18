package com.orders.gateway.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoggingFilter implements javax.servlet.Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, javax.servlet.ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Логирование входящего запроса
        LOGGER.info("Incoming Request: Method={}, URI={}, Headers={}",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                httpRequest.getHeaderNames());

        chain.doFilter(request, response);

        // Логирование исходящего ответа
        LOGGER.info("Outgoing Response: Status={}", httpResponse.getStatus());
    }
}
