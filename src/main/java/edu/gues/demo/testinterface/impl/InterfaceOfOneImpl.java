package edu.gues.demo.testinterface.impl;

import edu.gues.demo.testinterface.InterfaceOfOne;
import edu.gues.demo.testinterface.InterfaceOfTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <h2> InterfaceOfOneImpl 测试接口一实现 </h2>
 *
 * @author mxuexxmy
 * @date 10/24/2021 4:09 PM
 */
@Service
public class InterfaceOfOneImpl implements InterfaceOfOne {

    @Resource
    private InterfaceOfTwo interfaceOfTwo;

    @Override
    public void test(String str) {
        interfaceOfTwo.testInterface(str);
    }
}
