package edu.gues.demo;

import java.lang.reflect.Field;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/10/20 1:17
 * @version: 1.0
 */
public class MyClass1 {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        try {
            // 获取字段
            Field field = MyClass.class.getDeclaredField("CREW_CODE");

            // 将字段的可访问性设置为true，以允许修改
            field.setAccessible(true);

            // 设置新值
            field.set(null, "new_value");

            // 打印修改后的值
            System.out.println(MyClass.CREW_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
    class MyClass {
        public static final String CREW_CODE = "0000";
    }


