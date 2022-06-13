package edu.gues.demo.test;

import cn.hutool.core.util.RandomUtil;
import edu.gues.demo.testinterface.InterfaceOfOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Random;

/**
 * <h2> Test 测试 </h2>
 *
 * @author mxuexxmy
 * @date 2022/1/14 21:26
 */
@Component
public class TestInterface {

    @Autowired
    private InterfaceOfOne interfaceOfOne;


    @Async
    public void apply(String str)  {
        interfaceOfOne.test(str);
    }



}
