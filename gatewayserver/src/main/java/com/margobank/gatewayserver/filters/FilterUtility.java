package com.margobank.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

    public static final String TRACE_ID = "margobank-trace-id";
    public String getTraceId(HttpHeaders requestHeaders) {
        if(requestHeaders.get(TRACE_ID) != null) {
            List<String> requestHeaderList = requestHeaders.get(TRACE_ID);
            if(requestHeaderList != null && requestHeaderList.stream().findFirst().isPresent()) {

                return requestHeaderList.stream().findFirst().get();
            }
        }
        return null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name,value).build()).build();
    }

    public ServerWebExchange setTraceId(ServerWebExchange exchange, String traceId) {
        return this.setRequestHeader(exchange, TRACE_ID, traceId);
    }
}
