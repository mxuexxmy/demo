package edu.gues.demo.util;

/**
 * 序列号工具
 *
 * @author mxuexxmy
 */
public class SerialNumberUtil {

    /**
     * 获取数字
     *
     * @param str 系列号
     * @return java.lang.String
     */
    public static String recursionQuery(String str) {
        String prefix = "0";
        if (!str.startsWith(prefix)) {
            return str;
        }
        return recursionQuery(str.substring(prefix.length()));
    }

    /**
     * 构造序列号
     *
     * @param nun 数字
     * @param digit 构造长度
     * @return java.lang.String
     */
    public static String buildSerialNumber(Integer nun, int digit) {
        String numStr = String.valueOf(nun);
        if (digit == numStr.length()) {
            return numStr;
        }
        StringBuilder newNumStr = new StringBuilder();
        String prefix = "0";
        for (int i = 0; i < digit - numStr.length(); i++) {
            newNumStr.append(prefix);
        }
        newNumStr.append(numStr);
        return newNumStr.toString();
    }

}
