package edu.gues.demo.util;

import com.sun.tools.javac.util.List;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/12/1 23:04
 * @version: 1.0
 */
public class BigDecimalMeanCalculator {

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

        // 调用方法计算平均值
        BigDecimal mean = calculateMean(measurements, mathContext);

        System.out.println("Mean: " + mean);
    }

    private static BigDecimal calculateMean(List<BigDecimal> measurements, MathContext mathContext) {
        if (measurements.isEmpty()) {
            return BigDecimal.ZERO; // 避免除以0错误，可以根据需求返回其他值
        }

        // 使用 BigDecimal 进行精确计算
        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal measurement : measurements) {
            sum = sum.add(measurement);
        }

        BigDecimal size = new BigDecimal(measurements.size());
        return sum.divide(size, mathContext).setScale(3, RoundingMode.HALF_EVEN);
    }

}
