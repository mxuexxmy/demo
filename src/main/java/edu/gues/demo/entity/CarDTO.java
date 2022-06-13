package edu.gues.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mxuexxmy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    private String plateNumber;
    private String type;
    private String brand;
}
