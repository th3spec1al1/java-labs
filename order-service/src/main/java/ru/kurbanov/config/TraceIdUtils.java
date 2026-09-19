package ru.kurbanov.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TraceIdUtils {

    private static final String TRACE_ID_MDC_KEY = "traceId";

    public String getCurrentTraceId() {
        String traceId = MDC.get(TRACE_ID_MDC_KEY);
        return traceId != null ? traceId : UUID.randomUUID().toString();
    }
}
