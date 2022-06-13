package edu.gues.demo.enums;

/**
 * PhysicalTargetExportEnum 物理指标导出类型枚举
 *
 * @author mxuexxmy
 * @date 2021-08-18-2:30 PM
 */
public enum PhysicalTargetExportEnum {

    /**
     * 卷制包装入库前质量检验
     */
    PRE_WAREHOUSING_QUALITY_INSPECTION(1, "卷制包装入库前质量检验"),

    /**
     * 卷制包装在线质量检验
     */
    ONLINE_QUALITY_INSPECTION(2, "卷制包装在线质量检验"),

    /**
     * 滤棒在线质量检验
     */
    ONLINE_QUALITY_INSPECTION_OF_FILTER_RODS(3, "滤棒在线质量检验");

    int code;

    String message;

    PhysicalTargetExportEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * <p>
     * $description
     * </p>
     *
     * @return
     * @author mxuexxmy
     * @date 9/12/2021 9:33 PM
     */
    public static String valueOf(Integer value) {
        if (value != null) {
            for (PhysicalTargetExportEnum cs : PhysicalTargetExportEnum.values()) {
                if (cs.getCode() == value) {
                    return cs.getMessage();
                }
            }
        }
        return "";
    }

}