package com.project.demo.framework;

import ch.qos.logback.core.joran.sanity.Pair;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

    public class TenantContext {
    private static final ThreadLocal<String> currentContext = ThreadLocal.withInitial(() -> "public");

    public static void setTenant(String tenantId) {
        currentContext.set(tenantId);
    }

    public static String getTenant() {
        String s = currentContext.get();
        if(s == null || s.isEmpty()){
            s = "public";
        }
        return s;
    }

    public static void clear() {
        currentContext.remove();
    }

    public static Map.Entry<String, String> getContext() {
        AbstractMap.SimpleEntry<String, String> entry = new AbstractMap.SimpleEntry<>(
                Thread.currentThread().getName(),
                getTenant());
        return entry;
    }
}
