package edu.gues.demo.mapper;

import edu.gues.demo.entity.Demo;
import edu.gues.demo.entity.DemoTwo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/4/19 19:59
 * @version: 1.0
 */
@Mapper
public interface TestMapper {


    DemoTwo test();
}
