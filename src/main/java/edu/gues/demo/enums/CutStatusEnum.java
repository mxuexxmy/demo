package edu.gues.demo.enums;

/***
 *
 * @author mxuexxmy
 * @date 8/11/2021 12:00 PM
 */
public enum CutStatusEnum {

    USING(1, "在用"),
    EXPIRED(0, "过期");

    int code;
    String message;

    CutStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (CutStatusEnum cs : CutStatusEnum.values()) {
                if (cs.getCode() == value) {
                    return cs.getMessage();
                }
            }
            return "";
        }
    }

}
