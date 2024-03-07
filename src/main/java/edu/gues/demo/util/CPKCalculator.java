package edu.gues.demo.util;

import com.sun.tools.javac.util.List;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.math.BigDecimal;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/12/1 22:33
 * @version: 1.0
 */
public class CPKCalculator {

    public static void main(String[] args) {
        List<BigDecimal> measurements = List.of(
                new BigDecimal("98.2"),
                new BigDecimal("99.0"),
                new BigDecimal("100.5"),
                new BigDecimal("98.8"),
                new BigDecimal("101.2")
        );

        // 设置规格限
        BigDecimal usl = new BigDecimal("105.0");
        BigDecimal lsl = new BigDecimal("95.0");

        // 调用方法计算 CPK
        double cpk = calculateCPK(measurements, usl, lsl);

        System.out.println("CPK: " + cpk);
    }

    private static double calculateCPK(List<BigDecimal> measurements, BigDecimal usl, BigDecimal lsl) {
        if (measurements.isEmpty()) {
            return 0.0; // 避免除以0错误，可以根据需求返回其他值
        }

        DescriptiveStatistics stats = new DescriptiveStatistics();

        for (BigDecimal measurement : measurements) {
            stats.addValue(measurement.doubleValue());
        }

        // 获取样本均值
        double mean = stats.getMean();

        // 计算样本标准差
        StandardDeviation standardDeviation = new StandardDeviation();
        double stdDev = standardDeviation.evaluate(measurements.stream().mapToDouble(BigDecimal::doubleValue).toArray());

        // 计算 CPK
        double upperCpk = (usl.doubleValue() - mean) / (3 * stdDev);
        double lowerCpk = (mean - lsl.doubleValue()) / (3 * stdDev);

        // 取较小值作为最终 CPK 值
        return Math.min(upperCpk, lowerCpk);
    }

}
