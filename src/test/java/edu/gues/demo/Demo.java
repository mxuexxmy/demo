package edu.gues.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Demo
 *
 * @author mxuexxmy
 * @date 2021-07-27-12:19 AM
 */
public class Demo {

    private String one;

    private String two;

    private String three;

    private String code;

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Demo demo = (Demo) o;
        return Objects.equals(one, demo.one) && Objects.equals(two, demo.two) && Objects.equals(three, demo.three) && Objects.equals(code, demo.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(one, two, three, code);
    }

    @Override
    public String toString() {
        return "Demo{" +
                "one='" + one + '\'' +
                ", two='" + two + '\'' +
                ", three='" + three + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
