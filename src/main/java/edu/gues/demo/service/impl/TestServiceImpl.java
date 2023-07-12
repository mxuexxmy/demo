package edu.gues.demo.service.impl;

import edu.gues.demo.entity.Demo;
import edu.gues.demo.entity.DemoTwo;
import edu.gues.demo.mapper.TestMapper;
import edu.gues.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/4/19 19:58
 * @version: 1.0
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public DemoTwo test() {
        return testMapper.test();
    }
}
