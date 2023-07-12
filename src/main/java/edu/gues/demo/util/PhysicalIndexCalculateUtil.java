package edu.gues.demo.util;

import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 物理指标计算工具类
 *
 * @author <a href="mailto:longxing@hualongxunda.com">longxing</a>
 */
public class PhysicalIndexCalculateUtil {

    /**
     * 检测值
     */
    private List<BigDecimal> detectionValueArray;

    /**
     * 上限值
     */
    private String upperLimitValue;

    /**
     * 下限值
     */
    private String lowerLimitValue;

    /**
     * 中心值
     */
    private String centerValue;

    /**
     * 保留位数
     */
    private int countNumber;


    public PhysicalIndexCalculateUtil() {

    }

    public PhysicalIndexCalculateUtil(List<BigDecimal> detectionValueArray,
                                      String upperLimitValue,
                                      String lowerLimitValue,
                                      String centerValue,
                                      int countNumber) {
        this.detectionValueArray = detectionValueArray;
        this.upperLimitValue = upperLimitValue;
        this.lowerLimitValue = lowerLimitValue;
        this.centerValue = centerValue;
        this.countNumber = countNumber;
    }

    /**
     * 取平均值值
     *
     * @return float
     */
    public BigDecimal avg() {
        BigDecimal avg = new BigDecimal("0");
        BigDecimal sum = detectionValueArray.stream()
                .map(Objects::requireNonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long count = detectionValueArray.stream().filter(Objects::nonNull).count();
        if (count > 0) {
            avg =  sum.divide(new BigDecimal(count), RoundingMode.HALF_UP);
        }
        return avg;
    }

    /**
     * 取最大值
     *
     * @return float
     */
    public BigDecimal max() {
        Optional<BigDecimal> max = detectionValueArray.stream()
                .map(Objects::requireNonNull)
                .max(Comparator.naturalOrder());
        return max.orElse(null);
    }

    /**
     * 取最小值
     *
     * @return float
     */
    public BigDecimal min() {
        Optional<BigDecimal> max = detectionValueArray.stream()
                .map(Objects::requireNonNull)
                .min(Comparator.naturalOrder());
        return max.orElse(null);
    }

    /**
     * CPK 计算
     *
     * @return float
     */
    public BigDecimal cpk() {
        BigDecimal cpkValue = new BigDecimal("0");
        if (StrUtil.isBlank(this.centerValue)) {
            return cpkValue;
        }
        if (StrUtil.isBlank(this.lowerLimitValue)) {
            return cpkValue;
        }
        if (StrUtil.isBlank(this.upperLimitValue)) {
            return cpkValue;
        }
        double avg = this.avg().doubleValue();
        double dataSd = this.sd().doubleValue();
        // CPK
        double dataCp =  (Double.parseDouble(this.upperLimitValue) - Double.parseDouble(this.lowerLimitValue)) /  (6 * dataSd);
        double ca = (avg - Double.parseDouble(this.centerValue)) / ( (Double.parseDouble(this.upperLimitValue) -
                Double.parseDouble(this.lowerLimitValue)) / 2 );
        double cpk = dataCp * (1 - Math.abs(ca));
        return new BigDecimal(String.valueOf(cpk));
    }

    /**
     * 标准偏差计算
     *
     * @return float
     */
    public BigDecimal sd() {
        // 有效值集合
        double totalSumSampleCount = this.detectionValueArray.size();
        List<Double> newIndexDataList = new ArrayList<>();
        this.detectionValueArray.forEach(entity -> {
          newIndexDataList.add(Double.valueOf(entity.toPlainString()));
        });
        // 标准偏差
        double avg = this.avg().doubleValue();
        double dataSd = 0;
        double newPfc = 0;
        for (double dataValue : newIndexDataList) {
            double cha = dataValue - avg;
            double pf = cha * cha;
            newPfc = newPfc + pf;
        }
        newPfc = newPfc / (totalSumSampleCount - 1);
        dataSd = Math.sqrt(newPfc);
        return new BigDecimal(String.valueOf(dataSd));
    }

    /**
     * 不合格数量
     *
     * @return int
     */
    public int unqualifiedQuantity() {
        int num = 0;

        if (StrUtil.isNotBlank(this.upperLimitValue)) {
            BigDecimal temp = new BigDecimal(this.upperLimitValue);
            for (BigDecimal entity : this.detectionValueArray) {
                if (fourUpSixInto(entity, this.countNumber).compareTo(temp) > 0) {
                    num++;
                }
            }
        }

        if (StrUtil.isNotBlank(this.lowerLimitValue)) {
            BigDecimal temp = new BigDecimal(this.lowerLimitValue);
            for (BigDecimal entity : this.detectionValueArray) {
                if (fourUpSixInto(entity, this.countNumber).compareTo(temp) < 0) {
                    num++;
                }
            }
        }


        return num;
    }

    /**
     * 超上限数
     *
     * @return int
     */
    public int exceededLimit() {
        int num = 0;

        if (StrUtil.isNotBlank(this.upperLimitValue)) {
            BigDecimal temp = new BigDecimal(this.upperLimitValue);
            for (BigDecimal entity : this.detectionValueArray) {
                if (fourUpSixInto(entity, this.countNumber).compareTo(temp) > 0) {
                    num++;
                }
            }
        }

        return num;
    }

    /**
     * 超下限数
     *
     * @return int
     */
    public int exceededLowerLimit() {
        int num = 0;

        if (StrUtil.isNotBlank(this.lowerLimitValue)) {
            BigDecimal temp = new BigDecimal(this.lowerLimitValue);
            for (BigDecimal entity : this.detectionValueArray) {
                if (fourUpSixInto(entity, this.countNumber).compareTo(temp) < 0) {
                    num++;
                }
            }
        }

        return num;
    }

    public BigDecimal cv() {
        BigDecimal cvValue = new BigDecimal("0");

        if (StrUtil.isBlank(this.upperLimitValue)) {
            return cvValue;
        }

        double lowerLimitValue = Double.parseDouble(this.lowerLimitValue);
        double sd = this.sd().doubleValue();
        double cv = (sd / lowerLimitValue) * 100.00;
        return new BigDecimal(String.valueOf(cv));
    }

    public BigDecimal cp() {
        BigDecimal cpValue = new BigDecimal("0");
        if (StrUtil.isBlank(this.upperLimitValue)) {
            return cpValue;
        }

        if (StrUtil.isBlank(this.lowerLimitValue)) {
            return cpValue;
        }

        double lowerLimitValue = Double.parseDouble(this.lowerLimitValue);
        double upperLimitValue = Double.parseDouble(this.upperLimitValue);
        double sd = this.sd().doubleValue();

        double cp = (upperLimitValue - lowerLimitValue) / (6.0 * sd);
        return new BigDecimal(String.valueOf(cp));
    }

    /**
     * <p> 四舍六入五五成双 </p>
     *
     * @param num         保留数字
     * @param countNumber 保留位数
     * @return java.lang.String
     * @author mxuexxmy
     * @date 2022/3/14 16:03
     */
    public static BigDecimal fourUpSixInto(BigDecimal num, int countNumber) {
        return num.setScale(countNumber, RoundingMode.HALF_EVEN);
    }


}
