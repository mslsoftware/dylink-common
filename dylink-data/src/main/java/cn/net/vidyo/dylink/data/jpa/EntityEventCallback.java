package cn.net.vidyo.dylink.data.jpa;

import java.util.Collection;

public interface EntityEventCallback {
    void batchInvokeEvent(Collection targets, Event event);
    void invokeEvent(Object target, Event event);
}
