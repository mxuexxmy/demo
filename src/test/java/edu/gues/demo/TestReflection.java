package edu.gues.demo;

import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/10/20 0:25
 * @version: 1.0
 */
@Configuration
public class TestReflection {


    public static void main(String[] args) throws Exception {
        Field nameField = OneCity.class.getDeclaredField("name");

        Field modifiersField = Field.class.getDeclaredField("modifiers"); //①
        modifiersField.setAccessible(true);
        modifiersField.setInt(nameField, nameField.getModifiers() & ~Modifier.FINAL); //②

        nameField.setAccessible(true); //这个同样不能少，除非上面把 private 也拿掉了，可能还得 public
        nameField.set(null, "Shenzhen");
        System.out.println(OneCity.getName()); //输出 Shenzhen
    }
}

class OneCity {
    public static final String name = "Beijing";
    public static String getName() {
        return name;
    }

}
