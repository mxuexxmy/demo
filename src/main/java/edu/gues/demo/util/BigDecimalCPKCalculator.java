package edu.gues.demo.util;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/12/1 22:40
 * @version: 1.0
 */
public class BigDecimalCPKCalculator {

    public static void main(String[] args) {
        List<BigDecimal> measurements = new ArrayList<>(
        );

        // 设置规格限
        BigDecimal usl = new BigDecimal("105.0");
        BigDecimal lsl = new BigDecimal("95.0");

        // 设置精度
        MathContext mathContext = MathContext.DECIMAL64;

        // 调用方法计算 CPK
        BigDecimal cpk = calculateCPK(measurements, usl, lsl, mathContext);

        System.out.println("CPK: " + cpk);
    }

    private static BigDecimal calculateCPK(List<BigDecimal> measurements, BigDecimal usl, BigDecimal lsl, MathContext mathContext) {
        if (measurements.isEmpty()) {
            return BigDecimal.ZERO; // 避免除以0错误，可以根据需求返回其他值
        }

        DescriptiveStatistics stats = new DescriptiveStatistics();

        for (BigDecimal measurement : measurements) {
            stats.addValue(measurement.doubleValue());
        }

        // 获取样本均值
        BigDecimal mean = new BigDecimal(stats.getMean(), mathContext);

        // 计算样本标准差
        StandardDeviation standardDeviation = new StandardDeviation();
        double stdDev = standardDeviation.evaluate(measurements.stream().mapToDouble(BigDecimal::doubleValue).toArray());
        BigDecimal stdDevBigDecimal = new BigDecimal(stdDev, mathContext);

        // 计算 CPK
        BigDecimal three = new BigDecimal(3, mathContext);
        BigDecimal upperCpk = usl.subtract(mean).divide(three.multiply(stdDevBigDecimal), mathContext);
        BigDecimal lowerCpk = mean.subtract(lsl).divide(three.multiply(stdDevBigDecimal), mathContext);

        // 取较小值作为最终 CPK 值
        return upperCpk.min(lowerCpk).setScale(3, RoundingMode.HALF_EVEN);
    }

}
