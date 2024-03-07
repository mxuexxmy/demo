package edu.gues.demo;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/10/20 1:33
 * @version: 1.0
 */
public class KnowledgeTest {

    @Test
    public void testAskQuestion() throws Exception {
        Knowledge knowledge = new Knowledge();

        String answer = knowledge.askQuestion("question?");

        setFinalStaticField(Knowledge.class, "CREW_CODE", "1111");

        answer = knowledge.askQuestion1("question?");

        System.out.println(answer);
    }

    @Test
    public void testAskQuestion1() throws Exception {
        Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        field.setAccessible(true);
        String s = "111111";
        int oldValueSize = Knowledge.CREW_CODE.length();
        int newValueSize = s.length();

        int length = oldValueSize;
        if (newValueSize > oldValueSize) {
            length = newValueSize;
        }
        char[] value;

               value =  (char[])field.get(Knowledge.CREW_CODE) ;
        System.out.println(value);
        // 如果新的数量小于等于老的数量
        if (newValueSize >=  oldValueSize) {
            for (int i = 0; i < newValueSize; i++) {
                value[i] =  s.charAt(i);
            }
        } else {
            // 通过
            for (int i = 0; i < oldValueSize; i++) {
                if (i < newValueSize) {
                    value[i] =  s.charAt(i);
                } else {
                    value[i] = 0;
                }

            }
        }
        System.out.println(Knowledge.CREW_CODE.hashCode());
        System.out.println("111" + Knowledge.CREW_CODE);
        System.out.println(Knowledge.CREW_CODE.hashCode());

        System.out.println(Knowledge.CREW_CODE);
        System.out.println(Knowledge.CREW_CODE.hashCode());

        System.out.println(Knowledge.CREW_CODE + "1111");

        String temp = Knowledge.CREW_CODE;
        System.out.println(temp);

    }



    private static void setFinalStaticField(Class<?> clazz, String fieldName, Object value)
            throws ReflectiveOperationException {

        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, value);
    }
}
