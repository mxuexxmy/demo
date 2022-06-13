package edu.gues.demo.entity;

import edu.gues.demo.enums.CarType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * $description
 * </p>
 *
 * @author mxuexxmy
 * @date 9/12/2021 9:33 PM
 */
@Data
@Accessors(chain = true)
public class Car {
  private String plateNumber;
  private CarType type;
  private String brand;
}
