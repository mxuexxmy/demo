package edu.gues.demo.util;

import com.sun.tools.javac.util.List;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/12/1 23:02
 * @version: 1.0
 */
public class BigDecimalStandardDeviationCalculator {

    public static void main(String[] args) {
        List<BigDecimal> measurements = List.of(
                new BigDecimal("98.2"),
                new BigDecimal("99.0"),
                new BigDecimal("100.5"),
                new BigDecimal("98.8"),
                new BigDecimal("101.2")
        );

        // 设置精度
        MathContext mathContext = new MathContext(10);

        // 调用方法计算标准差
        BigDecimal standardDeviation = calculateStandardDeviation(measurements, mathContext);

        System.out.println("Standard Deviation: " + standardDeviation);
    }

    private static BigDecimal calculateStandardDeviation(List<BigDecimal> measurements, MathContext mathContext) {
        if (measurements.isEmpty()) {
            return BigDecimal.ZERO; // 避免除以0错误，可以根据需求返回其他值
        }

        DescriptiveStatistics stats = new DescriptiveStatistics();

        for (BigDecimal measurement : measurements) {
            stats.addValue(measurement.doubleValue());
        }

        // 获取样本标准差
        BigDecimal stdDev = new BigDecimal(stats.getStandardDeviation(), mathContext);

        return stdDev;
    }

}
