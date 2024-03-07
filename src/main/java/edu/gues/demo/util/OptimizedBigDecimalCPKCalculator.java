package edu.gues.demo.util;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/12/1 22:54
 * @version: 1.0
 */
public class OptimizedBigDecimalCPKCalculator {

    public static void main(String[] args) {
//        List<BigDecimal> measurements = List.of(
//                new BigDecimal("98.2"),
//                new BigDecimal("99.0"),
//                new BigDecimal("100.5"),
//                new BigDecimal("98.8"),
//                new BigDecimal("101.2")
//        );

        String str = "1.066,1.033,1.03,1,1.064,1.038,0.999,0.992,1.059,1.046,1.037,1.023,1.052,1.047,1.038,1.052,1.023,1.046,1.049,1.041,0.993,0.96,0.992,0.987,1.035,1.012,1.04,1.09,1.094,1.044,1.066,1.04,1.008,1.004,1.034,0.987,1.065,1.034,1.062,0.995,0.999,1.069,1.036,0.995,1.044,1.053,1.103,1.007,1.024,1.009,1.043,0.969,1.088,1.039,1.028,1.039,0.997,1.085,0.978,1.006,1.095,1.014,0.955,1.031,0.997,0.967,1.071,1.088,1.049,1.042,1,1.049,0.963,1.045,1.019,1.056,1.148,0.997,0.953,1.019,1.006,0.996,1.061,1.028,1.023,0.948,1.096,1.127,1.03,1.015,1.017,1.044,0.998,1.007,0.974,0.997,0.962,1.026,0.999,1.039,0.938,1.001,1.017,1.015,0.996,1.011,1.011,1.008,1.048,1.083,1.018,1.006,0.981,0.985,0.996,1.052,0.991,0.988,1.018,0.984,1.037,1.092,1.002,1.061,0.986,1.028,1.055,1.03,1.13,1.075,1.033,1.016,1.099,1.036,1.075,0.999,0.999,1.012,1.054,0.978,1.041,1.048,0.983,0.966,0.998,1.041,0.962,0.976,1.023,1.041,1.061,1.051,1,1.047,1.031,1.042,1.017,1.004,1.008,1.101,1.05,1.032,1.072,0.99,0.999,1.033,0.943,0.994,1.079,1.015,1.086,0.992,1.035,0.949,0.997,1.005,1.029,1.013,1.06,0.997,1.058,1.042,1.047,1.005,1.032,0.998,1.002,0.985,1.06,1.018,1.061,1.022,1.109,0.961,1.055,1.008,1.057,0.966,0.946,0.995,0.989,1.016,1.013,1.003,1.001,0.91,0.995,1.045,0.927,1.058,1.039,1.013,1.006,1.006,1.039,1.048,1.077,1,1.003,1.032,1.057,0.995,1.057,1.036,1.043,1.045,1.034,1.033,1.034,1.095,1.013,1.105,1.001,1.021,0.993,1.057,1.088,1.02,1.019,1.051,1.076,1.046,1.001,1.039,0.991,1.033,1.075,1.03,0.997,1.039,0.985,1.012,1.023,1.045,1.076,1.01,0.998,1.063,1,1.041,0.992,1.021,1.005,0.975,1.048,0.964,1.026,1.021,1.033,0.955";
        String[] split = str.split(",");
        List<BigDecimal> measurements = new ArrayList<BigDecimal>();

        for (String s : split) {
            measurements.add(new BigDecimal(s));
        }
        // 设置规格限
        BigDecimal usl = new BigDecimal("1.200");
        BigDecimal lsl = new BigDecimal("0.800");

        // 设置精度
        MathContext mathContext = new MathContext(10);

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
        BigDecimal stdDev = new BigDecimal(stats.getStandardDeviation(), mathContext);

        // 计算 CPK
        BigDecimal three = new BigDecimal(3, mathContext);
        BigDecimal upperCpk = usl.subtract(mean).divide(three.multiply(stdDev), mathContext);
        BigDecimal lowerCpk = mean.subtract(lsl).divide(three.multiply(stdDev), mathContext);

        // 取较小值作为最终 CPK 值
        return upperCpk.min(lowerCpk).setScale(3, BigDecimal.ROUND_HALF_DOWN);
    }

}
