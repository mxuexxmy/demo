package edu.gues.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/4/19 20:03
 * @version: 1.0
 */
@Data
public class Demo {

    private String res;


    private Long count;


    private List<DemoTwo> data;

}
