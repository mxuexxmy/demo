package edu.gues.demo.entity;

import lombok.Data;

/**
 * <h2> test bench data compensation bo  </h2>
 *
 * @author mxuexxmy
 * @date 2022/5/10 20:22
 */
@Data
public class TestBenchDataCompensationBO {

    /**
     * 设备编码
     */
    private String deviceId;
    /**
     * 机台号
     */
    private String lineName;
    /**
     * 综合测试台设备类型
     */
    private String machDeviceId;
    /**
     * 牌号
     */
    private String sampleName;

    /**
     * 检测的数量
     */
    private Integer testCount;

    /**
     * 班次对应班组
     */
    private String teamName;
    /**
     * 测量时间
     */
    private String testTime;
    /**
     * 总通风度
     */
    private String allventValue;
    /**
     * 硬度
     */
    private String hardValue;
    /**
     * 圆周
     */
    private String circleValue;
    /**
     * 长度
     */

    private String lengthValue;
    /**
     * 圆度
     */
    private String ovalityValue;
    /**
     * 吸阻
     */
    private String pdValue;
    /**
     * 重量
     */
    private String weightValue;
}
