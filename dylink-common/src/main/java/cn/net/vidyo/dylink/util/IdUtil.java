package cn.net.vidyo.dylink.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class IdUtil {

    public static String buildExtension(int style,int id){
        long  ids=id+1000000000;
        return String.valueOf(style)+ids;
    }
    public static String buildClassKey(int roomId){
        return buildExtension(1,roomId);
    }
    public static String buildUserKey(int userId){
        return buildExtension(2,userId);
    }
    public static String buildUsername(int userId){
        long  id=userId+1000000000;
        return "u"+id;
    }
    public static  String buildDateKey(){
        // 将时间对象格式化为字符串
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(now);
    }
    public static  String buildGuid(){
        return UUID.randomUUID().toString();
    }
    public static  String buildKey(){
        return buildKey("");
    }
    public static  String buildKey(String prest){
        return prest+buildGuid().replace("-","");
    }
    /**
     * 方法1：生成随机数字和字母组合
     * @param length
     * @return
     */
    public static String buildNumKey(String preString,int length) {
        Random random = new Random();
        StringBuffer valSb = new StringBuffer();
        String charStr = "0123456789";
        int charLength = charStr.length();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charLength);
            valSb.append(charStr.charAt(index));
        }
        return preString+valSb.toString();
    }
    public static String buildNumKey(int length) {
        return buildNumKey("",length);
    }
    /**
     * 方法1：生成随机数字和字母组合
     * @param length
     * @return
     */
    public static String getCharAndNum(int length) {
        Random random = new Random();
        StringBuffer valSb = new StringBuffer();
        String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        int charLength = charStr.length();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charLength);
            valSb.append(charStr.charAt(index));
        }
        return valSb.toString();
    }

    /**
     * 方法2：生成随机数字和字母组合
     * @param length
     * @return
     */
    public static String getCharAndNum2(int length) {
        StringBuffer valSb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 字符串
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;  // 取得大写字母还是小写字母
                valSb.append((char) (choice + random.nextInt(26)));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                // 数字
                valSb.append(String.valueOf(random.nextInt(10)));
            }
        }
        return valSb.toString();
    }

    /**
     * 方法3：生成随机数字和字母组合
     * @param length
     * @return
     */
    public static String getCharAndNumr3(int length) {
        StringBuffer valSb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            String charOrNum = Math.round(Math.random()) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 字符串
                int choice = Math.round(Math.random()) % 2 == 0 ? 65 : 97;  // 取得大写字母还是小写字母
                valSb.append((char) (choice + Math.round(Math.random()*25)));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                // 数字
                valSb.append(String.valueOf(Math.round(Math.random()*9)));
            }
        }
        return valSb.toString();
    }
}
