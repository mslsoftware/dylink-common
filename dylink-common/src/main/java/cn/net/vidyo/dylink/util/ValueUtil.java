package cn.net.vidyo.dylink.util;

import java.math.BigDecimal;
import java.util.Map;

/**
 * User: 马生录（mason
 * Date: 13-10-8
 * Time: 下午5:02
 */
public class ValueUtil {


    //<editor-fold desc="Map value">
    public static String mapValueToString(Map map,String key) {
        return mapValueToString(map,key, "");
    }
    public static String mapValueToString(Map map,String key,String defaultValue) {
        if(map==null || !map.containsKey(key)){
            return defaultValue;
        }
        return toString(map.get(key), defaultValue);
    }

    public static int mapValueToInt(Map map,String key) {

        return mapValueToInt(map,key, 0);
    }

    public static int mapValueToInt(Map map,String key, int defaultValue) {
        String value = mapValueToString(map,key, "");
        if (value == "")
            return defaultValue;
        value=value.replace(".0", "");
        return Integer.valueOf(value);
    }

    public static float mapValueToFloat(Map map,String key) {
        return mapValueToFloat(map,key, 0);
    }

    public static float mapValueToFloat(Map map,String key, int defaultValue) {
        String value = mapValueToString(map,key, "");
        if (value == "")
            return defaultValue;
        value=value.replace(".0", "");
        return Float.valueOf(value);
    }

    public static long mapValueToLong(Map map,String key) {
        return mapValueToLong(map,key, 0);
    }

    public static long mapValueToLong(Map map,String key, long defaultValue) {
        String value = mapValueToString(map,key, "");
        if (value == "")
            return defaultValue;
        value=value.replace(".0", "");
        return Long.valueOf(value);
    }

    public static double mapValueToDouble(Map map,String key) {
        return mapValueToDouble(map,key, 0);
    }

    public static double mapValueToDouble(Map map,String key, double defaultValue) {
        String value = mapValueToString(map,key, "");
        if (value == "")
            return defaultValue;
        return Double.valueOf(value);
    }

    public static short mapValueToShort(Map map,String key) {
        short val = 0;
        return mapValueToShort(map,key, val);
    }

    public static short mapValueToShort(Map map,String key, short defaultValue) {
        String value = mapValueToString(map,key, "");
        if (value == "")
            return defaultValue;
        value=value.replace(".0", "");
        return Short.valueOf(value);
    }

    public static boolean mapValueToBoolean(Map map,String key) {
        return mapValueToBoolean(map,key, false);
    }

    public static boolean mapValueToBoolean(Map map,String key, boolean defaultValue) {
        String value = mapValueToString(map,key, "");
        if (value == "")
            return defaultValue;
        return Boolean.valueOf(value);
    }

    public static byte mapValueToByte(Map map,String key) {
        byte val = 0;
        return mapValueToByte(map,key, val);
    }

    public static byte mapValueToByte(Map map,String key, byte defaultValue) {
        String value = mapValueToString(map,key, "");
        if (value == "")
            return defaultValue;
        return Byte.parseByte(value);
    }
    public static  <E extends Enum<E>> E mapValueToEnum(Map map,String key, Class<E> eClass, E defaultValue){
        String value = mapValueToString(map,key, "");
        if (value == "")
            return defaultValue;
        return E.valueOf(eClass,value);
    }
    public static  <E extends Enum<E>> E mapValueToEnum(Map map,String key, Class<E> eClass){
        return mapValueToEnum(map,key,eClass,null);
    }
    //</editor-fold>
    //<editor-fold desc="Base object value">
    public static String toString(Object data) {
        return toString(data, "");
    }

    public static String toString(Object data, String defaultValue) {
        if (data == null)
            return defaultValue;
        return data.toString();
    }

    public static int toInt(Object data) {
        return toInt(data, 0);
    }

    public static int toInt(Object data, int defaultValue) {
        String value = toString(data, "");
        if (value == "")
            return defaultValue;
        value=value.replace(".0", "");
        return Integer.valueOf(value);
    }

    public static float toFloat(Object data) {
        return toFloat(data, 0);
    }

    public static float toFloat(Object data, int defaultValue) {
        String value = toString(data, "");
        if (value == "")
            return defaultValue;
        value=value.replace(".0", "");
        return Float.valueOf(value);
    }

    public static long toLong(Object data) {
        return toLong(data, 0);
    }

    public static long toLong(Object data, long defaultValue) {
        String value = toString(data, "");
        if (value == "")
            return defaultValue;
        value=value.replace(".0", "");

        if(value.indexOf("E")>0){
            BigDecimal bd = new BigDecimal(value);
            return bd.longValue();
        }else {
            return Long.valueOf(value);
        }
    }

    public static double toDouble(Object data) {
        return toDouble(data, 0);
    }

    public static double toDouble(Object data, double defaultValue) {
        String value = toString(data, "");
        if (value == "")
            return defaultValue;
        return Double.valueOf(value);
    }

    public static short toShort(Object data) {
        short val = 0;
        return toShort(data, val);
    }

    public static short toShort(Object data, short defaultValue) {
        String value = toString(data, "");
        if (value == "")
            return defaultValue;
        value=value.replace(".0", "");
        return Short.valueOf(value);
    }

    public static boolean toBoolean(Object data) {
        return toBoolean(data, false);
    }

    public static boolean toBoolean(Object data, boolean defaultValue) {
        String value = toString(data, "");
        if (value == "")
            return defaultValue;
        return Boolean.valueOf(value);
    }


    public static char toChar(Object data) {
        return toChar(data, '0');
    }

    public static char toChar(Object data, char defaultValue) {
        String value = toString(data, "");
        if (value == "")
            return defaultValue;
        return value.charAt(0);
    }

    public static byte toByte(Object data) {
        byte val = 0;
        return toByte(data, val);
    }

    public static byte toByte(Object data, byte defaultValue) {
        String value = toString(data, "");
        if (value == "")
            return defaultValue;
        return Byte.parseByte(value);
    }
    public static  <E extends Enum<E>> E toEnum(Object data, Class<E> eClass, E defaultValue){
        String value = toString(data, "");
        if (value == "")
            return defaultValue;
        return E.valueOf(eClass,value);
    }
    public static  <E extends Enum<E>> E toEnum(Object data, Class<E> eClass){
        return toEnum(data,eClass,null);
    }
    //</editor-fold>
}
