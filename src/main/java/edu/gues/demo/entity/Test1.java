package edu.gues.demo.entity;

import edu.gues.demo.enums.CarType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author LX
 */
@Data
@Accessors(chain = true)
public class Test1 {
    private String plateNumber;
    private String brand;
    private String isNull;
    private Map<String, Object> demo;
}
