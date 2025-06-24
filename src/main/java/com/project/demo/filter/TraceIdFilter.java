package com.project.demo.filter;

import co.elastic.apm.api.ElasticApm;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;

import java.io.IOException;

@Log4j2
@WebFilter("/*")
public class TraceIdFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        var traceId = MDC.get("traceId");
        log.info("doFilter(): traceId: " + traceId);

        traceId = ElasticApm.currentTransaction().getTraceId();
        log.info("doFilter(): ElasticApm traceId: " + traceId);

        MDC.put("traceId", traceId);
        traceId = MDC.get("traceId");
        log.info("doFilter(): MDC traceId: " + traceId);

        try {
            chain.doFilter(req, res);
            traceId = MDC.get("traceId");
            log.info("doFilter(): MDC traceId 2: " + traceId);

        } finally {
            MDC.remove("traceId");
        }
    }
}

