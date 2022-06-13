package edu.gues.demo.testinterface.impl;

import edu.gues.demo.testinterface.InterfaceOfTwo;
import org.springframework.stereotype.Service;

/**
 * <h2> InterfaceOfTwoImpl 测试接口二实现 </h2>
 *
 * @author mxuexxmy
 * @date 2022/1/15 13:03
 */
@Service
public class InterfaceOfTwoImpl implements InterfaceOfTwo {


    @Override
    public void testInterface(String str) {
        System.out.println(str);
    }
}
