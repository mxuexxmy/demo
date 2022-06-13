package edu.gues.demo.testinterface;

/**
 * <h2> InterfaceOfOne 接口1 </h2>
 *
 * @author mxuexxmy
 * @date 10/24/2021 3:54 PM
 */
public interface InterfaceOfOne {

    String name = "龙兴";

    default String getNameOfField() {
        return name;
    }

    void test(String str);
}
