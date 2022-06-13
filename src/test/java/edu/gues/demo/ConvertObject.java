package edu.gues.demo;

import cn.hutool.core.util.RandomUtil;
import edu.gues.demo.entity.Car;
import edu.gues.demo.entity.CarDTO;
import edu.gues.demo.enums.CarType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * stream 转换对象
 */
public class ConvertObject {


    @Test
    public void test1() {
        List<Car> carList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Car car = new Car();
            car.setBrand(RandomUtil.randomString(5));
            car.setType(CarType.SUV);
            car.setPlateNumber(RandomUtil.randomString(5));
            carList.add(car);
        }

        List<CarDTO> cars=
                carList.stream()
                        .map(car -> new CarDTO(car.getPlateNumber(), car.getType().name(), car.getBrand()))
                        .collect(Collectors.toList());

        cars.forEach(System.out::println);
    }
}
