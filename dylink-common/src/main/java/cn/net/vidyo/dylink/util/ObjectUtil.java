package cn.net.vidyo.dylink.util;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.FatalBeanException;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.*;

public class ObjectUtil {

    /**
     * 复制部分属性
     *
     * @param source
     * @param clazz
     * @param specificProperties
     * @return
     */
    public static <T> T copyPropertiesSpecific(Object source, Class<T> clazz,
                                               String... specificProperties) {
        T target = BeanUtils.instantiate(clazz);
        return copyPropertiesSpecific(source, target, specificProperties);
    }

    public static <T> T copyPropertiesSpecific(Object source, T target,
                                               String... specificProperties) {
        if (source == null || target == null) {
            return target;
        }
        try {
            if (specificProperties == null) {
                return target;
            }
            List<String> specificList = Arrays.asList(specificProperties);
            copySpecificProperties(source, target, specificList);
            return target;
        } catch (Exception e) {
        }
        return target;
    }

    public static void increaseColumnValue(Object t, String fieldName, Object delta) {
        final BeanWrapper wrapper = new BeanWrapperImpl(t);
        Object value = wrapper.getPropertyValue(fieldName);

        if (value instanceof Short) {
            value = (short) value + (short) delta;
        }
        if (value instanceof Integer) {
            value = (Integer) value + (Integer) delta;
        }
        if (value instanceof Long) {
            value = (Long) value + (Long) delta;
        }
        if (value instanceof Float) {
            value = (Float) value + (Float) delta;
        }
        if (value instanceof Double) {
            value = (Double) value + (Double) delta;
        }
        wrapper.setPropertyValue(fieldName, value);
    }


    private static void copySpecificProperties(final Object source,
                                               final Object target, final Iterable<String> properties) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);
        for (final String propertyName : properties) {
            trg.setPropertyValue(propertyName,
                    src.getPropertyValue(propertyName));
        }
    }

    public static <T> T copyPropertiesByNotDefaultValue(T source, T target, String... ignoreProperties) {
        if (source == null || target == null) return target;
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        PropertyDescriptor[] var7 = targetPds;
        int var8 = targetPds.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            PropertyDescriptor targetPd = var7[var9];
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }

                            Object value = readMethod.invoke(source);
                            if (isDefaultValue(value)) {
                                continue;
                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Throwable var15) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", var15);
                        }
                    }
                }
            }
        }
        return target;
    }

    public static boolean isDefaultValue(Object value) {
        if (value == null) return true;
        if (value instanceof Byte) {
            return isDefaultValue(value, 0);
        }
        if (value instanceof Short) {
            return isDefaultValue(value, 0);
        }
        if (value instanceof Integer) {
            return isDefaultValue(value, 0);
        }
        if (value instanceof Long) {
            return isDefaultValue(value, 0L);
        }
        if (value instanceof Float) {
            return isDefaultValue(value, 0.0f);
        }
        if (value instanceof Double) {
            return isDefaultValue(value, 0.0d);
        }
        if (value instanceof Character) {
            return isDefaultValue(value, null);
        }
        if (value instanceof String) {
            return isDefaultValue(value, null);
        }
        if (value instanceof String) {
            return isDefaultValue(value, "");
        }
        if (value instanceof Boolean) {
            return isDefaultValue(value, 0);
        }
        if (value instanceof Date) {
            return isDefaultValue(value, null);
        }
        if (value instanceof Timestamp) {
            return isDefaultValue(value, null);
        }
        return true;
    }

    public static boolean isDefaultValue(Object value, Object defaultValue) {
        if (value == null) return true;
        if (value == defaultValue) {
            return true;
        }
        if (value instanceof Short) {
            return (short) value < (short) defaultValue;
        }
        if (value instanceof Integer) {
            return (int) value < (int) defaultValue;
        }
        if (value instanceof Long) {
            return (long) value < (long) defaultValue;
        }
//        if (value instanceof Character) {
//            return isDefaultValue(value, 	new Character('u0000'));
//        }
        return false;
    }

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = MapUtil.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 获取单个对象指定键的值
     *
     * @param t
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> Object getFieldValueByFieldName(T t, String fieldName) {
        Class clazz = t.getClass();
        Field resultField=getClassDeclaredFieldByFildName(clazz,true,fieldName);
        if(resultField==null){
            return null;
        }
        Object obj = null;
        resultField.setAccessible(true);
        try {
            obj = resultField.get(t);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static List<Field> getClassDeclaredFields(Class classz,boolean mappedSuperclassSupport){
        List<Field> fields=new ArrayList<>();
        Field[] fs = classz.getDeclaredFields();
        for (Field f : fs) {
            fields.add(f);
        }
        if(mappedSuperclassSupport){
            List<Field> list =getClassDeclaredFields(classz.getSuperclass(),mappedSuperclassSupport);
            fields.addAll(list);
        }
        return fields;
    }
    public static Field getClassDeclaredFieldByFildName(Class classz,boolean mappedSuperclassSupport,String fieldName){
        if(classz==null){
            return null;
        }
        Field[] fs = classz.getDeclaredFields();
        for (Field f : fs) {
            if(f.getName().equals(fieldName)){
                return f;
            }
        }
        if(mappedSuperclassSupport){
            return getClassDeclaredFieldByFildName(classz.getSuperclass(),mappedSuperclassSupport,fieldName);
        }
        return null;
    }
//    /**
//     * 将List<T>转换为List<Map<String, Object>>
//     * @param objList
//     * @return
//     */
//    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
//        List<Map<String, Object>> list = ListUtil.newArrayList();
//        if (objList != null && objList.size() > 0) {
//            Map<String, Object> map = null;
//            T bean = null;
//            for (int i = 0,size = objList.size(); i < size; i++) {
//                bean = objList.get(i);
//                map = beanToMap(bean);
//                list.add(map);
//            }
//        }
//        return list;
//    }

//    /**
//     * 将List<Map<String,Object>>转换为List<T>
//     * @param maps
//     * @param clazz
//     * @return
//     * @throws InstantiationException
//     * @throws IllegalAccessException
//     */
//    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) throws InstantiationException, IllegalAccessException {
//        List<T> list = ListUtil.newArrayList();
//        if (maps != null && maps.size() > 0) {
//            Map<String, Object> map = null;
//            T bean = null;
//            for (int i = 0,size = maps.size(); i < size; i++) {
//                map = maps.get(i);
//                bean = clazz.newInstance();
//                mapToBean(map, bean);
//                list.add(bean);
//            }
//        }
//        return list;
//    }
}
