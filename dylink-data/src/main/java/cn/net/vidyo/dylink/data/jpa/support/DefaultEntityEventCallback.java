package cn.net.vidyo.dylink.data.jpa.support;

import cn.net.vidyo.dylink.data.jpa.EntityEventCallback;
import cn.net.vidyo.dylink.data.jpa.Event;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultEntityEventCallback implements EntityEventCallback {

    static Map<Class, Map<Event, Method>> classEventMethodMap = new LinkedHashMap<Class, Map<Event, Method>>();

    public static Map<Event, Method> getClassEventMethods(Class aClass) {
        if (classEventMethodMap.containsKey(aClass)) {
            return classEventMethodMap.get(aClass);
        }
        Map<Event, Method> eventMap = getEventMap(aClass);
        classEventMethodMap.put(aClass, eventMap);
        return eventMap;
    }

    public static Method getClassEventMethod(Class aClass, Event event) {
        Map<Event, Method> eventMethods = getClassEventMethods(aClass);
        if (eventMethods.containsKey(event)) {
            return eventMethods.get(event);
        }
        return null;
    }

    public static <T> Map<Event, Method> getEventMap(Class<T> clazz) {
        Class superclazz = clazz;
        Map<Event, Method> ret = new HashMap<Event, Method>();

        while (superclazz != null && !superclazz.equals(Object.class)) {
            Method[] methods = superclazz.getMethods();
            for (Method method : methods) {
                if (!ret.containsKey(Event.PrePersist) && method.getAnnotation(PrePersist.class) != null) {
                    ret.put(Event.PrePersist, method);
                } else if (!ret.containsKey(Event.PreUpdate) && method.getAnnotation(PreUpdate.class) != null) {
                    ret.put(Event.PreUpdate, method);
                } else if (!ret.containsKey(Event.PreDelete) && method.getAnnotation(PreRemove.class) != null) {
                    ret.put(Event.PreDelete, method);
                } else if (!ret.containsKey(Event.PostLoad) && method.getAnnotation(PostLoad.class) != null) {
                    ret.put(Event.PostLoad, method);
                } else if (!ret.containsKey(Event.PostPersist) && method.getAnnotation(PostPersist.class) != null) {
                    ret.put(Event.PostPersist, method);
                } else if (!ret.containsKey(Event.PostUpdate) && method.getAnnotation(PostUpdate.class) != null) {
                    ret.put(Event.PostUpdate, method);
                } else if (!ret.containsKey(Event.PostDelete) && method.getAnnotation(PostRemove.class) != null) {
                    ret.put(Event.PostDelete, method);
                }
            }
            superclazz = superclazz.getSuperclass();
        }
        return ret;
    }

    @Override
    public void batchInvokeEvent(Collection targets, Event event) {
        if (targets.isEmpty())
            return;

        for (Object target : targets) {
            invokeEvent(target, event);
        }
    }

    @Override
    public void invokeEvent(Object target, Event event) {
        if (target == null) return;
        Method method = getClassEventMethod(target.getClass(), event);
        if (method != null) {
            try {
                method.invoke(target);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
