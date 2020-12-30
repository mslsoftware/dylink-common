package cn.net.vidyo.dylink.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class PropertyUtils {

    static char[] a_z = "abcdefghijklmnopqrstwvuxyz".toCharArray();
    static char[] A_Z = "abcdefghijklmnopqrstwvuxyz".toUpperCase().toCharArray();

    /**
     * a_bb_cc转变为ABbCc
     * 很小但很实用。主要是用来写数据库生成语句的二大核心之一。
     *
     * @param column
     * @return 返回信息
     */
    public static String columnToProperty(String column) {
        if (column == null || column.equals("")) {
            return "";
        }
        StringBuilder sb = new StringBuilder(column.length());
        // 当前的下标
        int i = 0;
        int length = column.length();
        for (int j = 0; j < length; j++) {
            if (column.charAt(j) == '_') {
                // 判断后面是否还有_
                while (column.charAt(++j) == '_') {
                }
                i = j;// i所对应的字符需要转换为大写字符
                char c = column.charAt(i);
                for (int k = 0; k < a_z.length; k++) {
                    if (a_z[k] == c) {
                        c = A_Z[k];
                        break;
                    }
                }
                sb.append(c);
            } else {
                sb.append(column.charAt(j));
            }
        }

        return sb.toString();
    }

    public static void copyProperties(Object sourceObject, Object targetObject) {
        copyProperties(sourceObject, targetObject, false);
    }

    public static void copyProperties(Object sourceObject, Object targetObject, boolean copyNullProperty) {
        if (sourceObject == null) {
            return;
        }
        if (targetObject == null) {
            return;
        }
        Class<? extends Object> sclassType = sourceObject.getClass();
        Class<? extends Object> tclassType = targetObject.getClass();
        Field[] declaredFields = sclassType.getDeclaredFields();
        for (Field filed : declaredFields) {
            if (filed.getName().indexOf("$") > 0) {
                continue;
            }
            String firstLetter = filed.getName().substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + filed.getName().substring(1);
            String setMethodName = "set" + firstLetter + filed.getName().substring(1);
            Method getMethod = null;
            Method setMethod = null;
            try {
                getMethod = sclassType.getMethod(getMethodName, new Class[]{});
                setMethod = tclassType.getMethod(setMethodName, new Class[]{filed.getType()});
            } catch (NoSuchMethodException e) {
                continue;
            }
            if (getMethod != null && setMethod != null) {
                Object value = null;
                try {
                    value = getMethod.invoke(sourceObject, new Object[]{});
                } catch (Exception e) {
                    continue;
                }
                if (value == null && !copyNullProperty) {
                    continue;
                }
                try {
                    setMethod.invoke(targetObject, new Object[]{value});
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

    //<editor-fold desc="get set Property Value">

    public static <T> T getPropertyValue(Object instance, String field) {
        BeanWrapper oBeanWrapper = new BeanWrapperImpl(instance);
        Object value = oBeanWrapper.getPropertyValue(field);
        return (T) value;
    }

    public static void setPropertyValue(Object instance, String field, Object value) {
        BeanWrapper oBeanWrapper = new BeanWrapperImpl(instance);
        oBeanWrapper.setPropertyValue(field, value);
    }
    //</editor-fold>

    //<editor-fold desc="addPropertyValue">
    public static void addPropertyValue(Object instance, String field, Object delta) {
        addPropertyMaxValue(instance, field, delta, null);
    }

    public static void addPropertyMaxValue(Object instance, String field, Object maxValue) {
        addPropertyMaxValue(instance, field, 1, maxValue);
    }

    public static void addPropertyMaxValue(Object instance, String field, Object delta, Object maxValue) {
        increasePropertyMinMaxValue(instance, field, delta, null, maxValue);
    }

    public static void addPropertyValues(Object instance, String... fields) {
        addPropertyValues(instance, 1, fields);
    }

    public static void addPropertyValues(Object instance, Object delta, String... fields) {
        addPropertyMaxValues(instance, delta, null, fields);
    }

    public static void addPropertyMaxValues(Object instance, Object maxValue, String... fields) {
        addPropertyMaxValues(instance, 1, maxValue, fields);
    }

    public static void addPropertyMaxValues(Object instance, Object delta, Object maxValue, String... fields) {
        increasePropertyMinMaxValues(instance, delta, null, maxValue, fields);
    }

    //</editor-fold>
    //<editor-fold desc="subPropertyValues">

    public static void subPropertyValue(Object instance, String field, Object delta) {
        subPropertyMinValue(instance, field, delta, null);
    }

    public static void subPropertyMinValue(Object instance, String field, Object minValue) {
        subPropertyMinValue(instance, field, -1, minValue);
    }

    public static void subPropertyMinValue(Object instance, String field, Object delta, Object minValue) {
        increasePropertyMinMaxValue(instance, field, delta, minValue, null);
    }

    public static void subPropertyValues(Object instance, Object delta, String... fields) {
        subPropertyMinValues(instance, delta, 0, fields);
    }

    public static void subPropertyMaxValues(Object instance, Object minValue, String... fields) {
        subPropertyMinValues(instance, -1, minValue, fields);
    }

    public static void subPropertyValues(Object instance, String... fields) {
        subPropertyMinValues(instance, -1, 0, fields);
    }

    public static void subPropertyMinValues(Object instance, Object delta, Object minValue, String... fields) {
        increasePropertyMinMaxValues(instance, delta, minValue, null, fields);
    }
    //</editor-fold>
    //<editor-fold desc="addMutiModelPropertyValue">

    public static <T> void addMutiModelPropertyValue(List<T> instances, String field, Object delta) {
        addMutiModelPropertyMaxValue(instances, field, delta, null);
    }

    public static <T> void addMutiModelPropertyMaxValue(List<T> instances, String field, Object maxValue) {
        addMutiModelPropertyMaxValue(instances, field, 1, maxValue);
    }

    public static <T> void addMutiModelPropertyMaxValue(List<T> instances, String field, Object delta, Object maxValue) {
        increaseMutiModelPropertyMinMaxValue(instances, field, delta, null, maxValue);
    }

    public static <T> void addMutiModelPropertyValues(List<T> instances, String... fields) {
        addMutiModelPropertyValues(instances, null, fields);
    }

    public static <T> void addMutiModelPropertyValues(List<T> instances, Object delta, String... fields) {
        addMutiModelPropertyMaxValues(instances, delta, null, fields);
    }

    public static <T> void addMutiModelPropertyMaxValues(List<T> instances, Object maxValue, String... fields) {
        addMutiModelPropertyMaxValues(instances, 1, maxValue, fields);
    }

    public static <T> void addMutiModelPropertyMaxValues(List<T> instances, Object delta, Object maxValue, String... fields) {
        increaseMutiModelPropertyMinMaxValues(instances, delta, null, maxValue, fields);
    }

    //</editor-fold>
    //<editor-fold desc="subMutiModelPropertyValue">
    public static <T> void subMutiModelPropertyValue(List<T> instances, String field, Object delta) {
        subMutiModelPropertyMinValue(instances, field, delta, 0);
    }

    public static <T> void subMutiModelPropertyMinValue(List<T> instances, String field, Object minValue) {
        subMutiModelPropertyMinValue(instances, field, 1, minValue);
    }

    public static <T> void subMutiModelPropertyMinValue(List<T> instances, String field, Object delta, Object minValue) {
        increaseMutiModelPropertyMinMaxValues(instances, field, delta, minValue);
    }


    public static <T> void subMutiModelPropertyValues(List<T> instances, String... fields) {
        subMutiModelPropertyMinValues(instances, -1, 0, fields);
    }

    public static <T> void subMutiModelPropertyValues(List<T> instances, Object delta, String... fields) {
        subMutiModelPropertyMinValues(instances, delta, 0, fields);
    }

    public static <T> void subMutiModelPropertyMinValues(List<T> instances, Object minValue, String... fields) {
        subMutiModelPropertyMinValues(instances, -1, minValue, fields);
    }

    public static <T> void subMutiModelPropertyMinValues(List<T> instances, Object delta, Object minValue, String... fields) {
        increaseMutiModelPropertyMinMaxValues(instances, delta, minValue, null, fields);
    }
    //</editor-fold>

    //<editor-fold desc="increasePropertyValue">
    public static void increasePropertyMinMaxValue(Object instance, String field, Object delta, Object minValue, Object maxValue) {
        Object value = getPropertyValue(instance, field);
        if (value == null) {
            return;
        }
        Object newValue = value;
        if (value.getClass().isAssignableFrom(Integer.class)) {
            newValue = (Integer) value + (Integer) delta;
            if (minValue != null && (Integer) minValue > (Integer) newValue) {
                newValue = minValue;
            }
            if (maxValue != null && (Integer) maxValue < (Integer) newValue) {
                newValue = maxValue;
            }
        }
        if (value.getClass().isAssignableFrom(Long.class)) {
            newValue = (Long) value + (Long) delta;
            if (minValue != null && (Long) minValue > (Long) newValue) {
                newValue = minValue;
            }
            if (maxValue != null && (Long) maxValue < (Long) newValue) {
                newValue = maxValue;
            }
        }
        if (value.getClass().isAssignableFrom(Float.class)) {
            newValue = (Float) value + (Float) delta;
            if (minValue != null && (Float) minValue > (Float) newValue) {
                newValue = minValue;
            }
            if (maxValue != null && (Float) maxValue < (Float) newValue) {
                newValue = maxValue;
            }

        }
        if (value.getClass().isAssignableFrom(Double.class)) {
            newValue = (Double) value + (Double) delta;
            if (minValue != null && (Double) minValue > (Double) newValue) {
                newValue = minValue;
            }
            if (maxValue != null && (Double) maxValue < (Double) newValue) {
                newValue = maxValue;
            }

        }
        setPropertyValue(instance, field, newValue);
    }

    public static void increasePropertyMinMaxValues(Object instance, Object delta, Object minValue, Object maxValue, String... fields) {
        if (fields == null || fields.length == 0) {
            return;
        }
        for (String field : fields) {
            increasePropertyMinMaxValue(instance, field, delta, minValue, maxValue);
        }
    }

    public static void increasePropertyValue(Object instance, String field, Object delta) {
        increasePropertyMinMaxValue(instance, field, delta, null, null);
    }

    public static void increasePropertyValues(Object instance, Object delta, String... fields) {
        increasePropertyMinMaxValues(instance, delta, null, null, fields);
    }

    //</editor-fold>
    //<editor-fold desc="increaseMutiModelPropertyValue">
    public static <T> void increaseMutiModelPropertyMinMaxValue(List<T> instances, String field, Object delta, Object minValue, Object maxValue) {
        for (T instance : instances) {
            increasePropertyMinMaxValue(instance, field, delta, minValue, maxValue);
        }
    }

    public static <T> void increaseMutiModelPropertyMinMaxValues(List<T> instances, Object delta, Object minValue, Object maxValue, String... fields) {
        if(instances==null)
            return;
        for (T instance : instances) {
            increasePropertyMinMaxValues(instance, delta, minValue, maxValue, fields);
        }
    }

    public static <T> void increaseMutiModelPropertyValue(List<T> instances, String field, Object delta) {
        increaseMutiModelPropertyMinMaxValue(instances, field, delta, 0, null);
    }

    public static <T> void increaseMutiModelPropertyValues(List<T> instances, Object delta, String... fields) {
        increaseMutiModelPropertyMinMaxValues(instances, delta, 0, null, fields);
    }
    //</editor-fold>
}



