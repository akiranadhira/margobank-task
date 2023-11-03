package com.margobank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    private final FilterUtility filterUtility;

    public RequestTraceFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if(isTraceIdPresent(requestHeaders)) {
            logger.debug("margobank-trace-id found in RequestTraceFilter : {}",
                    filterUtility.getTraceId(requestHeaders));
        } else {
            String traceId = generateTraceId();
            exchange = filterUtility.setTraceId(exchange, traceId);
            logger.debug("margobank-trace-id generated in RequestTraceFilter : {}", traceId);
        }
        return chain.filter(exchange);
    }

    private boolean isTraceIdPresent(HttpHeaders requestHeaders) {

        return filterUtility.getTraceId(requestHeaders) != null;
    }
    private String generateTraceId() {
        return java.util.UUID.randomUUID().toString();
    }

}
