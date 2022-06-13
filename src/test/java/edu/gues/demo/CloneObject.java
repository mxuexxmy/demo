package edu.gues.demo;

import edu.gues.demo.entity.Car;
import edu.gues.demo.entity.CarDTO;
import edu.gues.demo.enums.CarType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * $description
 * </p>
 *
 * @author mxuexxmy
 * @date 9/12/2021 9:19 PM
 */
public class CloneObject {

    /**
     * @param str
     */
    @Test
    public void Test1(String str) {
        Car car = new Car();
        car.setBrand("11");
        car.setType(CarType.SUV);
        car.setPlateNumber("11");
        CarDTO carDTO = new CarDTO();
        BeanUtils.copyProperties(car, carDTO);

        System.out.println("carDTO:" + carDTO);

        carDTO.setBrand("22");

        System.out.println("car:" + car);
        System.out.println("carDTO:" + carDTO);
    }

    /**
     * <p> </p>
     *
     * @param null
     * @return null
     * @author mxuexxmy
     * @date 9/12/2021 9:52 PM
     */
    public String demo(String str) {
        return "hello word";
    }
}
