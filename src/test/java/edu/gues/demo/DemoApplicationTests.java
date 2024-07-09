package edu.gues.demo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Sets;
import edu.gues.demo.entity.*;
import edu.gues.demo.enums.CarType;
import edu.gues.demo.enums.CutStatusEnum;
import edu.gues.demo.enums.PhysicalTargetExportEnum;
import edu.gues.demo.service.TestService;
import edu.gues.demo.test.TestInterface;
import edu.gues.demo.testinterface.impl.InterfaceOfOneImpl;
import edu.gues.demo.util.AsciiUtil;
import edu.gues.demo.util.DateRange;
import edu.gues.demo.util.PhysicalIndexCalculateUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootTest
class DemoApplicationTests {

    private static List<Object> list;
    private static Function keyExtractors;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor executors;

    @Autowired
    private TestService testService;

    static List<String> minusBrandCodeListOf15Minutes = new ArrayList<>(
            Arrays.asList("1326539", "30649731", "30819339", "32750057")
    );

    @Test
    void contextLoads() {
    }

    @Test
    public void test() {
        List<Demo> demoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Demo demo = new Demo();
            demo.setOne(RandomUtil.randomString(10));
            demo.setTwo(RandomUtil.randomString(8));
            demo.setThree(RandomUtil.randomString(8));
            demo.setCode(String.valueOf(RandomUtil.randomInt(10, 20)));
            demoList.add(demo);
        }
        String[] strings = new String[5];
        if (strings.length == 0L) {
            System.out.println("strings is empty");
        }
        for (int i = 0; i < 5; i++) {
            strings[i] = String.valueOf(RandomUtil.randomInt(10, 20));
        }
        System.out.println("处理前：");
        demoList.forEach(System.out::println);
        List<Demo> demos = demoList.stream().filter((x) -> {
            for (String s : strings) {
                if (s.equals(x.getCode())) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        System.out.println("第一种处理：");
        demos.forEach(System.out::println);
        List<Demo> demos2 = demoList.stream().filter((x) -> {
            return false;
        }).collect(Collectors.toList());
        for (String s : strings) {
            System.out.println(s);
        }
    }

    @Test
    public void test1() {
        System.out.println(CutStatusEnum.valueOf(1));
    }

    @Test
    public void test2() {
        System.out.println(AsciiUtil.sbc2dbcCase("云烟（紫）"));
        System.out.println(AsciiUtil.dbc2sbcCase("hello world"));
    }

    @Test
    public void test3() {
        Integer x = 1;
        if (x.equals(PhysicalTargetExportEnum.PRE_WAREHOUSING_QUALITY_INSPECTION.getCode())) {
            System.out.println("卷制包装入库前质量检验");
        } else {
            System.out.println("其他");
        }
    }

    @Test
    public void test4() {
        Demo demo = null;
        try {
            System.out.println(demo.getTwo());
        } finally {
            System.out.println(demo.getThree());
        }
    }

    @Test
    public void test5() {
        String str = "{'b' : 'c', 'd' : 'e'}";
        System.out.println(str);
        JSONObject jsonObject = JSONUtil.parseObj(str);
        Object object = jsonObject.get("b");
        System.out.println(jsonObject.get("b").toString());
        System.out.println(jsonObject.get("d"));
    }

    @Test
    public void test6() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(LocalDateTime.parse("2021-09-23 17:14:06", df));
    }

    @Test
    public void test7() {
        int flag = 1 & 1 & 0 & 1;
        System.out.println(flag);
    }

    @Test
    public void test8() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    @Test
    public void test9() {
        String str = "成功了";
        System.out.println(str.contains("成功"));
    }

    /**
     * <p> 去重 </p>
     *
     * @author mxuexxmy
     * @date 10/7/2021 9:39 PM
     */
    @Test
    public void test10() {
        List<Demo> demoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Demo demo = new Demo();
            demo.setOne("1");
            demo.setTwo("2");
            demo.setThree(RandomUtil.randomString(1));
            demo.setCode(String.valueOf(RandomUtil.randomInt(10, 20)));
            demoList.add(demo);
        }
        System.out.println("开始前");
        demoList.forEach(System.out::println);
        System.out.println("开始后");

        List<Demo> collect = listOfDistinctMore(demoList, Demo::getOne, Demo::getTwo);
        collect.forEach(System.out::println);
    }

    // Map 方式
    public static <T> Predicate<T> distinctByKey1(
            Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    // Set 方式
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }


    /**
     * <p> list 自定义去重 </p>
     *
     * @param list          集合
     * @param keyExtractors 去重关键字集合
     * @return java.util.List<T>
     * @author mxuexxmy
     * @date 10/8/2021 7:25 PM
     */
    public static <T> List<T> listOfDistinct(List<T> list, Function<? super T, ?> keyExtractors) {
        return list.stream().filter(distinctByKey(keyExtractors)).collect(Collectors.toList());
    }

    /**
     * <p> </p>
     *
     * @return java.util.List<T>
     * @author mxuexxmy
     * @date 10/8/2021 8:29 PM
     */
    @SafeVarargs
    public static <T> List<T> listOfDistinctMore(List<T> list, Function<? super T, ?>... keyExtractors) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().filter(distinctByKeys(keyExtractors)).collect(Collectors.toList());
    }

    /**
     * <p> list 自定义去重 </p>
     *
     * @param list 集合
     * @return java.util.List<T>
     * @author mxuexxmy
     * @date 10/8/2021 7:25 PM
     */
    public final <R> List<R> listOfDistinctDemo(List<R> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            return list;
        }
        return list.stream().filter(distinctByKeys(R::getClass)).collect(Collectors.toList());
    }

    @SafeVarargs
    private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    @Test
    public void test11() {
        String str = RandomUtil.randomString("0123456789", 1);
        switch (str) {
            case "01":
                System.out.println("01");
                break;
            case "02":
                System.out.println("02");
                break;
            default:
                System.out.println("其他");
                break;
        }
    }

    @Test
    public void test12() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        System.out.println(startDate);
        System.out.println("--------");
        System.out.println(endDate);
    }


    @Test
    public void test13() {
        Car car = new Car().setBrand("宝马").setType(CarType.SUV).setPlateNumber("ccc");
        System.out.println(car);
    }

    @Test
    public void test14() {
        Set<Integer> test1 = new HashSet<Integer>();
        test1.add(1);
        test1.add(2);
        test1.add(3);

        Set<Integer> test2 = new HashSet<Integer>();
        test2.add(1);
        test2.add(2);
        test2.add(3);
        test2.add(4);
        test2.add(5);
        test2.removeAll(test1);
        System.out.println(test2);
    }

    @Test
    public void test15() {
        InterfaceOfOneImpl one = new InterfaceOfOneImpl();
        System.out.println(one.getNameOfField());
    }

    @Test
    public void test16() {
        List<Demo> demoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Demo demo = new Demo();
            demo.setOne("1");
            demo.setTwo("2");
            demo.setThree(RandomUtil.randomString(1));
            demo.setCode(String.valueOf(RandomUtil.randomInt(10, 20)));
            demoList.add(demo);
        }
        demoList.forEach(System.out::println);
        demoList.forEach(System.out::println);
        Map<String, String> test = new HashMap<>();
        test.put("id", "1111");
        test.forEach((key, value) -> {
            System.out.println(key + " " + value);
        });
    }

    @Test
    public void test17() {
        LinkedHashSet<String> one = Sets.newLinkedHashSet();
        one.add("one");
        one.add("three");
        one.add("two");
        LinkedHashSet<String> two = Sets.newLinkedHashSet();
        two.add("one");
        two.add("two");
        two.add("three");
        if (one.equals(two)) {
            System.out.println("one == Two");
        }
    }

    @Test
    public void test18() {
        String startDate = LocalDate.now().toString();
        String endDate = LocalDate.now().withDayOfMonth(LocalDate.now().getDayOfMonth() - 3).toString();
        System.out.println("startDate:" + startDate);
        System.out.println("endDate:" + endDate);
    }

    @Test
    public void test19() {
        Test1 test1 = new Test1();
        test1.setBrand("2111");
        test1.setPlateNumber("111");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("1", 222);
        map.put("2", 333);
        test1.setDemo(map);
        System.out.println(test1);
    }

    @Test
    public void test20() {
        List<Map<String, Object>> list = new ArrayList<>();
    }

    @Test
    public void test21() {
        Set<String> set = Sets.newHashSet();
        set.add("乙班");
        set.add("甲班");
        set.add("丙班");
        set.add("丁班");
        set = set.stream().sorted(Comparator.reverseOrder())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        set.forEach(System.out::println);
    }

    @Test
    public void test22() {
        Test1 test1 = new Test1();
        test1.setBrand("A");
        test1.setPlateNumber("B");

        // 判断 brand 不为空
        if (Objects.nonNull(test1.getBrand())) {

        }


        System.out.println(strategyMethod(test1, "A"));
        System.out.println(strategyMethod(test1, "B"));
    }


    private String strategyMethod(Test1 test1, String code) {
        Map<String, String> strategies = new HashMap<>();
        strategies.put("A", test1.getBrand());
        strategies.put("B", test1.getPlateNumber());
        return strategies.get(code);
    }

    @Autowired
    private TestInterface testInterface;


    static class MessageHandler implements Runnable {
        String message;
        String topic;

        public MessageHandler(String topic, String message) {
            this.message = message;
        }

        public void run() {
            System.out.println("正在运行");
        }
    }

    ExecutorService pool = Executors.newFixedThreadPool(10);

    @Test
    public void test23() {
//        for (int i = 0; i < 20; i++) {
//            int finalI = i;
//            Thread t1 = new Thread(() -> {
//                try {
//                    //testInterface.apply("测试接口调用"+ finalI);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//            t1.start();
//        }


        pool.execute(new MessageHandler("111", "2222"));

    }

    @Test
    public void test24() {
        Test1 test1 = new Test1();
        test1.setBrand("A");
        test1.setPlateNumber("B");
        test1.setIsNull(Objects.nonNull(test1.getBrand()) ? "brand 不为空" : "brand为空");
        System.out.println(test1.getIsNull());
        System.out.println("-------");
        Test1 test2 = new Test1();
        test2.setPlateNumber("B");
        test2.setIsNull(Objects.nonNull(test2.getBrand()) ? "brand 不为空" : "brand为空");
        System.out.println(test2.getIsNull());

        List<Test1> test5 = new ArrayList<>();
        test5.add(test1);
        test5.add(test1);
        Test1 test3 = new Test1();
        Test1 test4 = test5.stream().findFirst().orElse(new Test1());
        test3.setIsNull(StrUtil.isNotBlank(test4.getBrand()) ? "brand 不为空" : "test4为空， brand为空");
        System.out.println(test3.getIsNull());
    }


    @Test
    public void test25() {
        for (int i = 0; i < 20; i++) {
            testInterface.apply("测试接口调用" + i);
        }
        System.out.println("111");
    }

    @Test
    public void test26() {
        List<Test1> test1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            test1.add(new Test1().setBrand(RandomUtil.randomString("abc", 3))
                    .setPlateNumber(RandomUtil.randomString("ab", 1)));

        }
        Map<String, List<Test1>> groupTest1 = test1.stream().collect(Collectors.groupingBy(Test1::getBrand));
        System.out.println(groupTest1);
    }

    @Test
    public void test27() {
        LocalDateTime time = LocalDateTime.now();
        String currentTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0).toString();
        System.out.println(time.toString());
        System.out.println(currentTime);
    }

    @Test
    public void test28() {
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(1).withSecond(0);
        DateTimeFormatter newDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateTime.format(newDateTimeFormatter);

        System.out.println(currentTime);
    }

    @Test
    public void test29() {
        DateTimeFormatter dateForMatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse("2017-03-13", dateForMatter);
        LocalDateTime ldt = ld.atStartOfDay();
        System.out.println(ldt.toString());
    }

    @Test
    public void test30() {
        DateTimeFormatter newDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String localDateTime = LocalDateTime.now().withSecond(0).format(newDateTimeFormatter);
        System.out.println(localDateTime);
    }

    public static List<DateRange> weeksCovering(LocalDate start, LocalDate end, WeekFields weekFields) {
        List<DateRange> result = new ArrayList<>();
        LocalDate date = start.with(TemporalAdjusters.previous(weekFields.getFirstDayOfWeek())).plusDays(1);
        while (date.compareTo(end) <= 0) {
            LocalDate weekStart = date;
            date = date.plusDays(6);
            LocalDate weekEnd = date;
            date = date.plusDays(1);
            result.add(new DateRange(weekStart, weekEnd));
        }
        return result;
    }

    public static void test(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, Locale locale) {
        LocalDate start = LocalDate.of(startYear, startMonth, startDay);
        LocalDate end = LocalDate.of(endYear, endMonth, endDay);
        List<DateRange> weeks = weeksCovering(start, end, WeekFields.of(locale));
        System.out.println(new DateRange(start, end) + ":");
        for (DateRange week : weeks)
            System.out.println("  " + week);
    }

    @Test
    public void test31() {
        test(2022, 2, 14, 2022, 2, 28, Locale.CHINA);
//         List<DateRange> dateRanges = weeksCovering(LocalDate.now().plusDays(-4), LocalDate.now(), WeekFields.of(Locale.GERMANY));
//         dateRanges.forEach(System.out::println);
//        LocalDate currentDate = LocalDate.now();
//        System.out.println(currentDate.getDayOfMonth());
    }

    @Test
    public void test32() {
        String[] str = new String[0];
        String test = "1123;3445;5555;2222";
        str = test.split(";");

        System.out.println(str.length);
    }

    @Test
    public void test33() {
        Test1 test = new Test1();
        System.out.println(Objects.nonNull(test.getPlateNumber()));
    }

    @Test
    public void test34() {
        String date = "2022-05";
        String newDate = date + "-04";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(newDate, formatter);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        System.out.println("开始时间：" + startDate + ";" + "结束时间:" + endDate);
    }


    @Test
    public void test36() {
        //  System.out.println(Math.sqrt(0) / 0 * 100);
        double a = 1;
        if (a != 0.0) {
            System.out.println("a不等于0");

        } else {
            System.out.println("a等于0");
        }
    }

    @Test
    public void test37() {
        String str = "2022-05-09 21:38:10.0";
        System.out.println(DateUtil.parseDateTime(str));

    }


    @Test
    public void test38() {
        String str = "硬包线";
        boolean isTure = str.contains("软包");
        System.out.println(isTure);

    }

    @Test
    public void test39() {
        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate.atStartOfDay());
    }

    @Test
    public void test50() {
        List<String> tests = new ArrayList<>();
        tests.add("98");
        tests.add("99");
        tests.add(null);
        tests.add(null);
        tests.add("97");
        OptionalDouble average = tests.stream().mapToDouble(entity -> Objects.isNull(entity) ? 0 : Double.parseDouble(entity)).average();
        if (average.isPresent()) {
            System.out.println(average.getAsDouble());
        }

    }

    @Test
    public void test51() {
        List<String> tests = new ArrayList<>();
        tests.add("98");
        tests.add("99");
        tests.add(null);
        tests.add(null);
        tests.add("97");
        OptionalDouble average = tests.stream().filter(Objects::nonNull).mapToDouble(Double::parseDouble).average();
        if (average.isPresent()) {
            System.out.println(average.getAsDouble());
        }

    }


    @Test
    public void test52() {
        List<String> tests = new ArrayList<>();
        tests.add(null);
        tests.add(null);
        tests.add(null);
        tests.add("");
        tests.add(null);
        long count = tests.stream().filter(Objects::nonNull).count();
        System.out.println(count);
    }

    @Test
    public void test53() {
        String str = "7.759820950639653E-7";
        Double result = Double.parseDouble(str);
        BigDecimal big10 = new BigDecimal(String.valueOf(result));

        System.out.println(big10.setScale(7, RoundingMode.HALF_UP).toPlainString());

    }

    @Test
    public void test54() {
        String dateTime = "2022-08-03 22:09:00";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @Test
    public void test55() {
        String dateTime = "2022-11-08 14:33:00";
        String nowTime = "2022-11-08 14:35:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(dateTime, formatter);
        LocalDateTime now = LocalDateTime.parse(nowTime, formatter);
        Long minutes = startTime.until(now, ChronoUnit.MINUTES);
        System.out.println(minutes);
    }

    @Test
    public void test56() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime queryDateTime = currentTime.plusMinutes(-10);
        System.out.println(queryDateTime);
    }

    @Test
    public void test57() {
        BigDecimal b1 = new BigDecimal("1.38655");
        BigDecimal b2 = b1.setScale(3, RoundingMode.HALF_EVEN);
        System.out.println(b2);
    }

    @Test
    public void test58() {
        BigDecimal b1 = new BigDecimal("0.58855");
        BigDecimal b2 = b1.scaleByPowerOfTen(3).setScale(0, RoundingMode.HALF_EVEN);
        System.out.println(b2);
    }

    @SneakyThrows
    @Test
    public void test59() {
        String checkDateTimeStr = "2022-11-08 12:00:00";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 调整日期
        Date checkDateTime = dateFormat.parse(checkDateTimeStr);
        System.out.println(dateFormat.format(checkDateTime));
    }

    @Test
    public void test60() {
        String emCode = "JJ-07";

        if (emCode.contains("JJ")) {
            System.out.println("JJ");
        }


    }


    @Test
    public void test61() {
        BigDecimal bigDecimal = new BigDecimal("999");

        System.out.println(bigDecimal.scaleByPowerOfTen(-3));

    }


    @Test
    public void test62() {
        List<BigDecimal> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new BigDecimal(RandomUtil.randomInt(10, 100)));
        }
        PhysicalIndexCalculateUtil physicalIndexCalculateUtil = new PhysicalIndexCalculateUtil(list, "80", "50", "65", 3);
        System.out.println("数组：" + list);
        System.out.println("avg:" + physicalIndexCalculateUtil.avg());
        System.out.println("min:" + physicalIndexCalculateUtil.min());
        System.out.println("max:" + physicalIndexCalculateUtil.max());
        System.out.println("cpk:" + physicalIndexCalculateUtil.cpk());
        System.out.println("sd:" + physicalIndexCalculateUtil.sd());
        System.out.println("unqualifiedQuantity:" + physicalIndexCalculateUtil.unqualifiedQuantity());
        System.out.println("exceededLimit:" + physicalIndexCalculateUtil.exceededLimit());
        System.out.println("exceededLowerLimit:" + physicalIndexCalculateUtil.exceededLowerLimit());
        System.out.println("cv:" + physicalIndexCalculateUtil.cv());
        System.out.println("cp:" + physicalIndexCalculateUtil.cp());
    }

    @Test
    public void test63() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("2");
        list.add("2");
        list.add("3");
        list.add("3");
        list.add("4");
        Set<String> set = new HashSet<>();
        list.forEach(entity -> {
            if (set.contains(entity)) {
                System.out.println("已经存在：" + entity);
            } else {
                System.out.println(entity);
            }
            set.add(entity);
        });
    }

    @Test
    public void test64() {
        System.out.println(new BigDecimal("3440").divide(new BigDecimal("1000")));
        System.out.println(new BigDecimal("3100").divide(new BigDecimal("1000")));
        System.out.println(new BigDecimal("2760").divide(new BigDecimal("1000")));
        System.out.println("--------------------------------");
        System.out.println(new BigDecimal("3443").setScale(2, RoundingMode.HALF_EVEN));
    }


    @Test
    public void test65() {
        List<UserDTO> userDTOS = new ArrayList<UserDTO>();
        for (int i = 0; i < 10; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setAge(RandomUtil.randomInt(10, 25));
            userDTO.setName(RandomUtil.randomString(4));
            userDTOS.add(userDTO);
        }
        userDTOS.sort(Comparator.comparing(UserDTO::getAge, Collections.reverseOrder()));
        userDTOS.forEach(System.out::println);
    }


    @Test
    public void test66() {
        BigDecimal value = new BigDecimal("100.123");
        int bit = value.scale();
        System.out.println("小数位数：" + bit);
    }

    @Test
    public void test67() {
        String str = "     ";

        if (StrUtil.isNotBlank(str)) {
            System.out.println(str.trim());
        } else {
            System.out.println("111");
        }

    }

    @Test
    public void test68() {
        List<String> dateTimeStr = new ArrayList<String>();
        dateTimeStr.add("2023-02-21 15:51:00");
        dateTimeStr.add("2023-02-01 15:51:00");
        dateTimeStr.add("2023-02-15 15:51:00");
        dateTimeStr.add("2023-02-22 15:51:00");
        dateTimeStr.add("2023-02-23 15:51:00");
        dateTimeStr.add("2023-05-26 15:51:00");

        List<LocalDateTime> dateTimes = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeStr.forEach(entity -> {
            dateTimes.add(LocalDateTime.parse(entity, dateTimeFormatter));
        });

        dateTimes.sort(Comparator.comparing(LocalDateTime::toString, Collections.reverseOrder()));

        dateTimes.forEach(System.out::println);


    }

    @Test
    public void test69() {
        List<String> dateTimeStr = new ArrayList<String>();
        dateTimeStr.add("2023-02-21 15:51:00");
        dateTimeStr.add("2023-02-01 15:51:00");
        dateTimeStr.add("2023-02-15 15:51:00");
        dateTimeStr.add("2023-02-22 15:51:00");
        dateTimeStr.add("2023-02-23 15:51:00");
        dateTimeStr.add("2023-05-26 15:51:00");

        List<CarDTO> dateTimes = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeStr.forEach(entity -> {
            CarDTO carDTO = new CarDTO();
            carDTO.setType(NumberUtil.roundStr(3, 1));
            carDTO.setLocalDateTime(LocalDateTime.parse(entity, dateTimeFormatter));
            dateTimes.add(carDTO);
        });

        dateTimes.sort(Comparator.comparing(CarDTO::getLocalDateTime, Collections.reverseOrder()));

        dateTimes.forEach(System.out::println);
        System.out.println("-------------");
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (currentDateTime.isBefore(dateTimes.get(0).getLocalDateTime())) {
            System.out.println("存在 " + dayCount(dateTimes, 5) + " 次！");
        }

    }


    public int dayCount(List<CarDTO> dateTimes, int day) {
        AtomicInteger count = new AtomicInteger();
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(-day);

        dateTimes.forEach(entity -> {
            if (!entity.getLocalDateTime().isBefore(startDateTime)) {
                count.getAndIncrement();
            }
        });
        return count.get();
    }


    @Test
    public void test70() {

        String upLimit = "3110";
        upLimit = new BigDecimal(upLimit).scaleByPowerOfTen(-3).toPlainString();

        String temp = "3110";
        temp = new BigDecimal(temp).divide(new BigDecimal("1000")).toPlainString();

        System.out.println(upLimit);
        System.out.println(temp);
        String setValue = "3000";

        String lowLimit = "2850";

        List<BigDecimal> dataList = new ArrayList<>();
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.85"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("2.76"));
        dataList.add(new BigDecimal("3.1106"));

        PhysicalIndexCalculateUtil physicalIndexCalculateUtil = new PhysicalIndexCalculateUtil(dataList, upLimit, setValue, lowLimit, 3);

        System.out.println(physicalIndexCalculateUtil.max());
        System.out.println(physicalIndexCalculateUtil.exceededLimit());

    }

    @Test
    public void test71() {
        String dateTime = "2023-03-08 17:15:59";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int minute = currentDateTime.getMinute();
        System.out.println(minute);
        // 时间点，60 分钟 ， 00 分钟
        if (minute == 0) {
            System.out.println("时间点，60 分钟 ， 00 分钟");
        }

        // 时间点 15、30 45 分钟
        if (minute == 15 || minute == 30 || minute == 45) {
            System.out.println("时间点 15、30 45 分钟");
        }

        // 时间点 20 40 分钟
        if (minute == 20 || minute == 40) {
            System.out.println("时间点 20 40 分钟");
        }

    }

    @Test
    public void test72() {
        minusBrandCodeListOf15Minutes.forEach(System.out::println);
    }

    @Test
    public void test73() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
    }

    @Test
    public void test74() {
        String str = "000022";
        String prefix = "0";

        if (str.startsWith(prefix)) {
            str = str.substring(prefix.length());
            if (str.startsWith(prefix)) {
                str = str.substring(prefix.length());
            }
        }

        System.out.println(str);
    }

    @Test
    public void test74Recursion() {
        String str = "000022";
        System.out.println(recursionQuery(str));
        System.out.println(buildSerialNumber(22, 6));
    }

    /**
     * 获取数字
     *
     * @param str 系列号
     * @return java.lang.String
     */
    private String recursionQuery(String str) {
        String prefix = "0";
        if (!str.startsWith(prefix)) {
            return str;
        }
        return recursionQuery(str.substring(prefix.length()));
    }

    /**
     * 构造序列号
     *
     * @param nun   数字
     * @param digit 构造长度
     * @return java.lang.String
     */
    private String buildSerialNumber(Integer nun, int digit) {
        String numStr = String.valueOf(nun);
        if (digit == numStr.length()) {
            return numStr;
        }
        StringBuilder newNumStr = new StringBuilder();
        String prefix = "0";
        for (int i = 0; i < digit - numStr.length(); i++) {
            newNumStr.append(prefix);
        }
        newNumStr.append(numStr);
        return newNumStr.toString();
    }

    @Test
    public void test75() {
        String startDateStr = "2022-12-17";
        String endDateStr = "2023-03-15";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);

        System.out.println(ChronoUnit.DAYS.between(startDate, endDate));

    }

    @Test
    public void test76() {
        // 需要判断当前时间段是否是凌晨00:00 - 04:00
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = "2023-03-01 01:20:23";
        LocalDateTime currentDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        int hour = currentDateTime.getHour();
        System.out.println(hour);

    }

    @Test
    public void test77() {
        List<String> str = new ArrayList<>();
        str.add("0.553");
        str.add("0.540");
        str.add("0.573");
        str.add("0.569");
        str.add("0.569");
        str.add("0.548");
        str.add("0.553");
        str.add("0.540");
        str.add("0.532");
        str.add("0.555");
        str.add("0.570");
        str.add("0.579");
        str.add("0.530");
        str.add("0.541");
        str.add("0.568");
        str.add("0.560");
        str.add("0.528");
        str.add("0.552");
        str.add("0.540");
        str.add("0.543");

        BigDecimal sum = new BigDecimal(0);
        for (String s : str) {
            sum = sum.add(new BigDecimal(s));
        }

        System.out.println(sum.divide(new BigDecimal(20), 3, RoundingMode.HALF_EVEN));

    }

    @Test
    public void test78() {
        testService.test();
    }

    @Test
    public void test79() {
        BigDecimal n = new BigDecimal("0.355");
        System.out.println(n.setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    public void test80() {
        // 查询机组名称
        String patternStr = "(.{2})\\s*$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("２３４".replace(" ", ""));
        String emCode = "";
        if (matcher.find()) {
            emCode = matcher.group();
        }

        System.out.println(emCode);

    }

    @Test
    public void test81() {
        String input = "a２３４１３４";
        //String input = "a";
        System.out.println(formatString(input));
    }


    @Test
    public void test82() {
        // 有效值集合
        List<Double> newIndexDataList = new ArrayList<>();
        //String str = "0.391,0.378,0.379,0.369,0.366,0.371,0.377,0.37,0.374,0.374,0.372,0.377,0.371,0.377,0.37,0.37,0.371,0.369,0.368,0.377,0.373,0.376,0.37,0.371,0.366,0.366,0.381,0.369,0.373,0.374";
        String str = "3.457,3.184,3.219,3.044,3.013,3.045,3.134,3.102,3.125,3.086,3.144,3.239,3.057,3.226,3.075,3.107,3.048,3.05,3.027,3.217,3.169,3.149,2.977,3.117,3.019,3,3.225,3.078,3.224,3.221";
        // 平均值
        String[] split = str.split(",");
        for (String s : split) {
            newIndexDataList.add(Double.valueOf(s));
        }

        double avg = newIndexDataList.stream().mapToDouble(Double::floatValue).average().orElse(0);

//        avg = Double.parseDouble(BigDecimal.valueOf(avg).setScale(3, RoundingMode.HALF_EVEN).toString());
        // 有效样本数
        double totalSumSampleCount = newIndexDataList.size();
        // 标准偏差
        double dataSd = 0;
        double newPfc = 0;
        for (double dataValue : newIndexDataList) {
            double cha = dataValue - avg;
            double pf = cha * cha;
            newPfc = newPfc + pf;
        }
        newPfc = newPfc / (totalSumSampleCount - 1);
        dataSd = Math.sqrt(newPfc);
        System.out.println(new BigDecimal(String.valueOf(dataSd)).setScale(4, RoundingMode.HALF_EVEN));
    }

    @Test
    public void test83() {
        List<String> s = new ArrayList<>();
        s.add("一");
        s.add("三");
        s.add("二");
        s.add("四");
        s.add("五");
        s.sort(Comparator.reverseOrder());
        System.out.println(s);
    }

    @Test
    public void test84() {
        String str = "2023-05-31 12:00:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime lastTime = LocalDateTime.parse(str, dateTimeFormatter);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate currentDate = LocalDate.now();
        LocalDate newCurrentDate = currentDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate startDate = lastTime.toLocalDate();
        LocalDate newStartDate = startDate.with(TemporalAdjusters.firstDayOfMonth());

        while (!newCurrentDate.isBefore(newStartDate)) {
            System.out.println(newStartDate);

            newStartDate = newStartDate.plusMonths(1);
        }
    }


    /**
     * 将机组编码字符串进行格式化
     * 例：a２３４１３４ 转为 a234134
     *
     * @param input 机组编码
     * @return java.lang.String
     */
    private String formatString(String input) {
        StringBuilder sb = new StringBuilder();
        if (StrUtil.isNotBlank(input)) {
            for (int i = 0; i < input.length(); i++) {
                int num = -1;
                try {
                    num = Integer.parseInt(String.valueOf(input.charAt(i)));
                } catch (Exception ignored) {

                } finally {
                    if (num != -1) {
                        sb.append(num);
                    } else {
                        sb.append(input.charAt(i));
                    }
                }
            }
        }
        return sb.toString();
    }

    @Test
    public void test85() {
        BigDecimal parameterValue = new BigDecimal("11.79");
        System.out.println(eligibility(parameterValue, true, "12.80", "12.30", "11.80", true, 2));
    }

    @Test
    public void test86() {
        String date = "2021-07-23";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(date, dateFormatter);
        System.out.println(currentDate.plusDays(23));
    }

    @Test
    public void test87() {
        // 处理机组软硬情况
        Map<String, Boolean> modelInfo = new HashMap<>();
        modelInfo.put("1", true);
        // 正常
        if (Objects.equals(true, modelInfo.get("2"))) {
            System.out.println("2存在");
        } else {
            System.out.println("2不存在");
        }

        // 异常方式1
        boolean isTure = modelInfo.get("2");
        if (Objects.equals(true, isTure)) {
            System.out.println("2存在");
        } else {
            System.out.println("2不存在");
        }

        // 异常方式2
        if (modelInfo.get("2")) {
            System.out.println("2存在");
        } else {
            System.out.println("2不存在");
        }
    }

    @Test
    public void test88() {
        List<String> strings = new ArrayList<>();
        strings.forEach(System.out::println);
    }

    @Test
    public void test89() {
        List<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.forEach(System.out::println);

    }

    @Test
    public void test90() {
        String newYearMonth = "2023-08" + "-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(newYearMonth, formatter);
        LocalDate endDate = LocalDate.now();
        // 如果是不同年或不同月份取月的最后的一天
        if (startDate.getYear() != endDate.getYear() || startDate.getMonth() != endDate.getMonth()) {
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        }

        System.out.println(endDate);
    }

    @Test
    public void test91() {
        List<DemoTwo> demoTwos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoTwo demoTwo = new DemoTwo();
            demoTwo.setTime(String.valueOf(i));
            demoTwos.add(demoTwo);
        }

        for (DemoTwo demoTwo : demoTwos) {
            demoTwo.setItemValue(demoTwo.getTime());
        }

        for (DemoTwo demoTwo : demoTwos) {
            System.out.println(demoTwo);
        }
    }

    @Test
    public void test92() {
        Date date = new Date();
        System.out.println(DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void test93() {
        Map<String, String> testMap = null;
        System.out.println(testMap.get("code"));
    }

    @Test
    public void test94() {
        String dataTimeStr = "1696929662422";
        Instant instant = Instant.ofEpochMilli(Long.parseLong(dataTimeStr));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);
        System.out.println(formattedDateTime);
    }

    @Test
    public void test95() {
        // 卷烟规律
        // 自产牌号 最后两位车号 31 -48

        // 白沙 倒数第二位是 车号 1-9 , 1-9 号生产白沙

    }

    @Test
    public void test97() throws NoSuchFieldException, IllegalAccessException {
        final String s1 = "Hello World";
        String s2 = s1.substring(6);
        System.out.println(s1);
        System.out.println(s2);

        Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        char[] value = (char[]) field.get(s1);
        value[6] = 'H';
        value[7] = 'e';
        value[8] = 'n';
        value[9] = 'r';
        value[10] = 'y';
        System.out.println(s1);
        System.out.println(s2);
    }

    @Test
    public void test96() {
        // 滤棒规律

        String input = "12345";

        if (input.length() >= 2) {
            String lastTwoDigits = input.substring(input.length() - 2);
            int lastTwoDigitsValue = Integer.parseInt(lastTwoDigits);
            System.out.println("最后两位数是：" + lastTwoDigitsValue);
        } else {
            System.out.println("字符串长度不足两位，无法提取最后两位数。");
        }
    }

    @Test
    public void test98() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        List<String> collect = list.stream().filter(e -> !"2".equals(e)).collect(Collectors.toList());
        System.out.println(collect);
    }


    @SneakyThrows
    @Test
    public void collectDemo() {
        executors.execute(() -> {
            while (true) {
                System.out.println("开始了");
                try {
                    System.out.println("睡眠了");
                    Thread.sleep(500);
                    System.out.println("睡醒了");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * 是否合格
     *
     * @param parameterValue  参数值
     * @param whetherUpLimit  是否操作上限
     * @param upLimit         上限值
     * @param setValue        中心值
     * @param lowLimit        下限值
     * @param whetherLowLimit 是否存在下限
     * @param bit             修约位数
     * @return java.lang.Boolean
     */
    public static Boolean eligibility(BigDecimal parameterValue,
                                      Boolean whetherUpLimit,
                                      String upLimit,
                                      String setValue,
                                      String lowLimit,
                                      Boolean whetherLowLimit,
                                      int bit) {

        //根据上下限的值和判定进行判断是否正常,true表示正常，false表示异常
        if (StrUtil.isNotBlank(upLimit)) {
            if (whetherUpLimit) {
                if (fourUpSixInto(parameterValue, bit).compareTo(fourUpSixInto(new BigDecimal(upLimit), bit)) > 0) {
                    return true;
                }
            } else {
                if (fourUpSixInto(parameterValue, bit).compareTo(fourUpSixInto(new BigDecimal(upLimit), bit)) >= 0) {
                    return true;
                }
            }
        }

        if (StrUtil.isNotBlank(lowLimit)) {
            if (whetherLowLimit) {
                if (fourUpSixInto(parameterValue, bit).compareTo(fourUpSixInto(new BigDecimal(lowLimit), bit)) < 0) {
                    return true;
                }
            } else {
                if (fourUpSixInto(parameterValue, bit).compareTo(fourUpSixInto(new BigDecimal(lowLimit), bit)) <= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    @Test
    public void test99() {
        String t1 = "0.840";
        String t2 = "0.845";

        System.out.println(fourUpSixInto(new BigDecimal(t2), 2));
    }

    @Test
    public void test100() {
        String text = "研究院";
        String s1 = StrUtil.subBefore(text, "院", false).replaceAll(" ","");
        String unit = s1 + "院";
        System.out.println(unit.trim());
        if ("院".equals(unit.trim())){
            System.out.println(unit);
        }
        System.out.println(s1);
        System.out.println(unit);
    }


    @Test
    public void test102() {
        DateTime parse = DateUtil.parse("20230707", "yyyyMMdd");
        System.out.println(parse);
        DateTime parse1 = DateUtil.parse("2023-07-07", "yyyy-MM-dd");
        System.out.println(parse1);
    }

    @Test
    public void test103() {
        String inputDateTimeString = "2023-11-14T16:00:00.000+00:00";

        // 将字符串解析为日期时间对象
        // 解析原始日期时间字符串为 ZonedDateTime 对象
        ZonedDateTime planStartTimeStr = ZonedDateTime.parse(inputDateTimeString);

        // 将日期时间对象转换为本地日期时间
        // 定义目标时区 (东八区)
        ZoneId targetZone = ZoneId.of("Asia/Shanghai");

        // 将原始 ZonedDateTime 转换为目标时区的 LocalDateTime
        LocalDateTime planStartTime = planStartTimeStr
                .withZoneSameInstant(targetZone)
                .toLocalDateTime();


        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 将日期时间格式化为字符串
        String outputDateString = planStartTime.format(formatter);

        // 输出结果
        System.out.println(outputDateString);
    }

    @Test
    public void test104() {
        System.out.println(new BigDecimal("1150")
                .scaleByPowerOfTen(-3).add(new BigDecimal("0.05")).toPlainString());
        System.out.println(new BigDecimal("850")
                .scaleByPowerOfTen(-3).subtract(new BigDecimal("0.05")).toPlainString());
    }

    @Test
    public void test105() {
        // 获取当前日期
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 获取当前日期的年月日部分
        LocalDateTime datePart = currentDateTime.toLocalDate().atStartOfDay();

        // 构建00:00时刻
        LocalDateTime midnight = datePart.with(LocalTime.MIDNIGHT);

        System.out.println("当前时间: " + currentDateTime);
        System.out.println("sdddd: " + datePart);
        System.out.println("当前日期的00:00时刻: " + midnight);

    }

    @Test
    public void test106() {

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        // 每一个班最后一个生成
        //由于排班有不是整点的情况，允许有 ± 1 分钟的误差
        LocalDateTime startTime = now.plusMinutes(-1);
        System.out.println(startTime);
        LocalDateTime endTime = now.plusMinutes(1);
        System.out.println(endTime);
        System.out.println(now.compareTo(startTime) >= 0);
        System.out.println( now.compareTo(endTime) <= 0);
        boolean res = now.compareTo(startTime) >= 0 || now.compareTo(endTime) <= 0;
        System.out.println(res);
    }


    @Test
    public void test107() {

        String str = "cs";

        System.out.println(str.length());
    }

    @Test
    public void copyFileLb() throws IOException {

        Path sourceFile = Paths.get("E:\\hlxd\\bd\\测试台文件\\分日期\\2022年");
        Path targetFile = Paths.get("E:\\hlxd\\bd\\测试台文件\\分牌号");

        // 定义拷贝选项，使用 StandardCopyOption.COPY_ATTRIBUTES 保留原始文件的属性
        CopyOption[] options = new CopyOption[] {
                StandardCopyOption.COPY_ATTRIBUTES
        };

        // 执行文件拷贝
        Files.copy(sourceFile, targetFile, options);

        System.out.println("文件拷贝完成！");

    }

    @Test
    public void test108() {
        Boolean s = true;
        String s1 = s.toString();
        System.out.println("-----字符串的Boolean----");
        System.out.println(s1);
        System.out.println("-----恢复的Boolean----");
        System.out.println(Boolean.parseBoolean(s1));

    }

    @Test
    public void test109() {
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("orange");
        list.add("banana");

        // 使用Collections.sort()进行排序

        // 输出排序后的列表
        System.out.println("List:");
        for (String item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void test110() {
        List<String> list = new ArrayList<>();
        list.add("orange");
        list.add("apple");
        list.add("banana");


        // 使用自定义的Comparator进行排序
        list.sort((s1, s2) -> {
            if (s1.equals("apple") && s2.equals("orange")) {
                return 1; // 将"orange"放在"apple"之后
            } else if (s1.equals("orange") && s2.equals("apple")) {
                return -1; // 将"apple"放在"orange"之前
            } else {
                return 0; // 其他情况下，不做变化
            }
        });

        // 输出排序后的列表
        System.out.println("Sorted List with Orange After Apple:");
        for (String item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void test111() {
        List<String> list = new ArrayList<>();
        list.add("banana2");
        list.add("orange");
        list.add("apple");
        list.add("banana");
        list.add("banana1");
        list.add("banana3");

        String apple = null;
        String orange;
        int appleLocation = 0;
        int orangeLocation = 0;
        int i = 0;
        for (String entity : list) {

            if (entity.equals("apple")) {
                apple = new String(entity);
                appleLocation = i;
            }
            if (entity.equals("orange")) {
                orange = new String(entity);
                orangeLocation = i;
            }
            i++;
        }

        // 交换位置

        for (String item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void test112() throws Exception {
        String data = readFileAsString("E:\\other\\data-file.txt");
        com.alibaba.fastjson.JSONObject dataBox = com.alibaba.fastjson.JSONObject.parseObject(data);

        Map<String, Map<String, List<Map<String, String>>>> historyData = (Map<String, Map<String, List<Map<String, String>>>>)  dataBox.get("data");
        if (CollectionUtil.isNotEmpty(historyData)) {
            List<TestBenchDataCompensationBO> testBenchDataCompensationList = new ArrayList<>();

            historyData.forEach((key, value) -> {

                // 1. test time data collection
                List<TestBenchData> testTimeDataList = new ArrayList<>();
                List<Map<String, String>> testTimeList = value.get("test_time");
                for (int i = 0; i < testTimeList.size(); i++) {
                    if (testTimeList.size() == 1) {
                        TestBenchData testBenchData = new TestBenchData();
                        testBenchData.setTime(testTimeList.get(i).get("time"));
                        testBenchData.setItemValue(testTimeList.get(i).get("variableValue"));
                        testTimeDataList.add(testBenchData);
                    } else if (i < testTimeList.size() - 1){
                        if (!Objects.equals(testTimeList.get(i).get("variableValue"), testTimeList.get(i + 1).get("variableValue"))) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(testTimeList.get(i).get("time"));
                            testBenchData.setItemValue(testTimeList.get(i).get("variableValue"));
                            testTimeDataList.add(testBenchData);
                        }
                    } else {
                        if (!Objects.equals(testTimeList.get(i -1).get("variableValue"), testTimeList.get(i).get("variableValue"))) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(testTimeList.get(i).get("time"));
                            testBenchData.setItemValue(testTimeList.get(i).get("variableValue"));
                            testTimeDataList.add(testBenchData);
                        }
                    }
                }

                // 2. all vent value data collection
                List<TestBenchData> allVentDataList = new ArrayList<>();
                List<Map<String, String>> allventValueList = value.get("allvent_value");
                for (int i = 0; i < allventValueList.size(); i++) {
                    if (allventValueList.size() == 1) {
                        TestBenchData testBenchData = new TestBenchData();
                        testBenchData.setTime(allventValueList.get(i).get("time"));
                        testBenchData.setItemValue(allventValueList.get(i).get("variableValue"));
                        allVentDataList.add(testBenchData);
                    } else if (i < allventValueList.size() - 1){
                        if (allventValueList.get(i).get("variableValue").length() > allventValueList.get(i + 1).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(allventValueList.get(i).get("time"));
                            testBenchData.setItemValue(allventValueList.get(i).get("variableValue"));
                            allVentDataList.add(testBenchData);
                        }
                    } else {
                        if (allventValueList.get(i -1).get("variableValue").length() > allventValueList.get(i).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(allventValueList.get(i).get("time"));
                            testBenchData.setItemValue(allventValueList.get(i).get("variableValue"));
                            allVentDataList.add(testBenchData);
                        }
                    }
                }

                // 3. circle value data collection
                List<TestBenchData> circleValueDataList = new ArrayList<>();
                List<Map<String, String>> circleValueList = value.get("circle_value");
                for (int i = 0; i < circleValueList.size(); i++) {
                    if (circleValueList.size() == 1) {
                        TestBenchData testBenchData = new TestBenchData();
                        testBenchData.setTime(circleValueList.get(i).get("time"));
                        testBenchData.setItemValue(circleValueList.get(i).get("variableValue"));
                        circleValueDataList.add(testBenchData);
                    } else if (i < circleValueList.size() - 1){
                        if (circleValueList.get(i).get("variableValue").length() > circleValueList.get(i + 1).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(circleValueList.get(i).get("time"));
                            testBenchData.setItemValue(circleValueList.get(i).get("variableValue"));
                            circleValueDataList.add(testBenchData);
                        }
                    } else {
                        if (allventValueList.get(i -1).get("variableValue").length() > circleValueList.get(i).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(circleValueList.get(i).get("time"));
                            testBenchData.setItemValue(circleValueList.get(i).get("variableValue"));
                            circleValueDataList.add(testBenchData);
                        }
                    }
                }

                // 4. length value data collection
                List<TestBenchData> lengthValueDataList = new ArrayList<>();
                List<Map<String, String>> lengthValueList = value.get("length_value");
                for (int i = 0; i < lengthValueList.size(); i++) {
                    if (lengthValueList.size() == 1) {
                        TestBenchData testBenchData = new TestBenchData();
                        testBenchData.setTime(lengthValueList.get(i).get("time"));
                        testBenchData.setItemValue(lengthValueList.get(i).get("variableValue"));
                        lengthValueDataList.add(testBenchData);
                    } else if (i < lengthValueList.size() - 1){
                        if (lengthValueList.get(i).get("variableValue").length() > lengthValueList.get(i + 1).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(lengthValueList.get(i).get("time"));
                            testBenchData.setItemValue(lengthValueList.get(i).get("variableValue"));
                            lengthValueDataList.add(testBenchData);
                        }
                    } else {
                        if (lengthValueList.get(i -1).get("variableValue").length() > lengthValueList.get(i).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(lengthValueList.get(i).get("time"));
                            testBenchData.setItemValue(lengthValueList.get(i).get("variableValue"));
                            lengthValueDataList.add(testBenchData);
                        }
                    }
                }

                // 5. pd value data collection
                List<TestBenchData> pdValueDataList = new ArrayList<>();
                List<Map<String, String>> pdValueList = value.get("pd_value");
                for (int i = 0; i < pdValueList.size(); i++) {
                    if (pdValueList.size() == 1) {
                        TestBenchData testBenchData = new TestBenchData();
                        testBenchData.setTime(pdValueList.get(i).get("time"));
                        testBenchData.setItemValue(pdValueList.get(i).get("variableValue"));
                        pdValueDataList.add(testBenchData);
                    } else if (i < pdValueList.size() - 1){
                        if (pdValueList.get(i).get("variableValue").length() > pdValueList.get(i + 1).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(pdValueList.get(i).get("time"));
                            testBenchData.setItemValue(pdValueList.get(i).get("variableValue"));
                            pdValueDataList.add(testBenchData);
                        }
                    } else {
                        if (pdValueList.get(i -1).get("variableValue").length() > pdValueList.get(i).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(pdValueList.get(i).get("time"));
                            testBenchData.setItemValue(pdValueList.get(i).get("variableValue"));
                            pdValueDataList.add(testBenchData);
                        }
                    }
                }

                // 6. weight value data collection
                List<TestBenchData> weightValueDataList = new ArrayList<>();
                List<Map<String, String>> weightValueList = value.get("weight_value");
                for (int i = 0; i < weightValueList.size(); i++) {
                    if (weightValueList.size() == 1) {
                        TestBenchData testBenchData = new TestBenchData();
                        testBenchData.setTime(weightValueList.get(i).get("time"));
                        testBenchData.setItemValue(weightValueList.get(i).get("variableValue"));
                        weightValueDataList.add(testBenchData);
                    } else if (i < weightValueList.size() - 1){
                        if (weightValueList.get(i).get("variableValue").length() > weightValueList.get(i + 1).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(weightValueList.get(i).get("time"));
                            testBenchData.setItemValue(weightValueList.get(i).get("variableValue"));
                            weightValueDataList.add(testBenchData);
                        }
                    } else {
                        if (weightValueList.get(i -1).get("variableValue").length() > weightValueList.get(i).get("variableValue").length()) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(weightValueList.get(i).get("time"));
                            testBenchData.setItemValue(weightValueList.get(i).get("variableValue"));
                            weightValueDataList.add(testBenchData);
                        }
                    }
                }


                // 9. test count data collection
                List<TestBenchData> testCountDataList = new ArrayList<>();
                List<Map<String, String>> testCountList = value.get("test_count");
                for (int i = 0; i < testCountList.size(); i++) {
                    if (testCountList.size() == 1) {
                        TestBenchData testBenchData = new TestBenchData();
                        testBenchData.setTime(testCountList.get(i).get("time"));
                        testBenchData.setItemValue(testCountList.get(i).get("variableValue"));
                        testCountDataList.add(testBenchData);
                    } else if (i < testCountList.size() - 1){
                        if (new BigDecimal(testCountList.get(i).get("variableValue"))
                                .compareTo(new BigDecimal(testCountList.get(i + 1).get("variableValue"))) > 0) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(testCountList.get(i).get("time"));
                            testBenchData.setItemValue(testCountList.get(i).get("variableValue"));
                            testCountDataList.add(testBenchData);
                        }
                    } else {
                        if (new BigDecimal(testCountList.get(i -1).get("variableValue"))
                                .compareTo(new BigDecimal(testCountList.get(i).get("variableValue"))) > 0) {
                            TestBenchData testBenchData = new TestBenchData();
                            testBenchData.setTime(testCountList.get(i).get("time"));
                            testBenchData.setItemValue(testCountList.get(i).get("variableValue"));
                            testCountDataList.add(testBenchData);
                        }
                    }
                }

                // 7. line name data collection
                List<TestBenchData> lineNameDataList = new ArrayList<>();
                List<Map<String, String>> lineNameList = value.get("line_name");
                DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd HH:mm:ss")
                        .optionalStart()
                        .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, true)
                        .optionalEnd()
                        .toFormatter();

                testCountDataList.forEach(entity -> {
                    TestBenchData testBenchData = new TestBenchData();
                    long getTime1 = LocalDateTime.parse(entity.getTime(), dateTimeFormatter)
                            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                    long minTime = 99999999;
                    for (Map<String, String> stringStringMap : lineNameList) {
                        long getTime2 = LocalDateTime.parse(stringStringMap.get("time"), dateTimeFormatter)
                                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                        long tempTime = Math.abs(getTime1 - getTime2);
                        if (Math.min(tempTime, minTime) <= minTime) {
                            minTime = tempTime;
                            minTime = Math.min(minTime, tempTime);
                            testBenchData.setTime(stringStringMap.get("time"));
                            testBenchData.setItemValue(stringStringMap.get("variableValue"));
                        }
                    }
                    lineNameDataList.add(testBenchData);
                });

                // 10.team name data collection
                List<TestBenchData> teamNameDataList = new ArrayList<>();
                List<Map<String, String>> teamNameList = value.get("team_name");
                testCountDataList.forEach(entity -> {
                    TestBenchData testBenchData = new TestBenchData();
                    long getTime1 = LocalDateTime.parse(entity.getTime(), dateTimeFormatter)
                            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                    long minTime = 99999999;
                    for (Map<String, String> stringStringMap : teamNameList) {
                        long getTime2 = LocalDateTime.parse(stringStringMap.get("time"), dateTimeFormatter)
                                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                        long tempTime = Math.abs(getTime1 - getTime2);
                        if (Math.min(tempTime, minTime) <= minTime) {
                            minTime = tempTime;
                            minTime = Math.min(minTime, tempTime);
                            testBenchData.setTime(stringStringMap.get("time"));
                            testBenchData.setItemValue(stringStringMap.get("variableValue"));
                        }
                    }
                    teamNameDataList.add(testBenchData);
                });

                // 8.sample name data collection
                List<TestBenchData> sampleNameDataList = new ArrayList<>();
                List<Map<String, String>> sampleNameList = value.get("sample_name");
                testCountDataList.forEach(entity -> {
                    TestBenchData testBenchData = new TestBenchData();
                    long getTime1 = LocalDateTime.parse(entity.getTime(), dateTimeFormatter)
                            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                    long minTime = 99999999;
                    for (Map<String, String> stringStringMap : sampleNameList) {
                        long getTime2 = LocalDateTime.parse(stringStringMap.get("time"), dateTimeFormatter)
                                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                        long tempTime = Math.abs(getTime1 - getTime2);
                        if (Math.min(tempTime, minTime) <= minTime) {
                            minTime = Math.min(minTime, tempTime);
                            testBenchData.setTime(stringStringMap.get("time"));
                            testBenchData.setItemValue(stringStringMap.get("variableValue"));
                        }
                    }
                    sampleNameDataList.add(testBenchData);
                });


                // test number
                int testNum = testTimeDataList.size();
                for (int i = 0; i < testNum; i++) {
                    TestBenchDataCompensationBO testBenchDataCompensationBO = new TestBenchDataCompensationBO();

                    testBenchDataCompensationBO.setDeviceId(key);
                    testBenchDataCompensationBO.setTestTime(testTimeDataList.get(i).getItemValue());
                    testBenchDataCompensationBO.setTestCount(Integer.valueOf(testCountDataList.get(i).getItemValue()));
                    testBenchDataCompensationBO.setLineName(lineNameDataList.get(i).getItemValue());
                    testBenchDataCompensationBO.setTeamName(teamNameDataList.get(i).getItemValue());
                    testBenchDataCompensationBO.setSampleName(sampleNameDataList.get(i).getItemValue());

                    testBenchDataCompensationBO.setLengthValue(lengthValueDataList.get(i).getItemValue());
                    testBenchDataCompensationBO.setCircleValue(circleValueDataList.get(i).getItemValue());
                    testBenchDataCompensationBO.setAllventValue(allVentDataList.get(i).getItemValue());
                    testBenchDataCompensationBO.setPdValue(pdValueDataList.get(i).getItemValue());
                    testBenchDataCompensationBO.setWeightValue(weightValueDataList.get(i).getItemValue());

                    testBenchDataCompensationList.add(testBenchDataCompensationBO);
                }

            });

            if (CollectionUtil.isNotEmpty(testBenchDataCompensationList)) {
                for (int i = 0; i < testBenchDataCompensationList.size(); i++) {
                    System.out.println("第" + (i + 1) + "个：" + testBenchDataCompensationList.get(i));
                    System.out.println("检验数量：" + testBenchDataCompensationList.get(i).getTestCount());
                    if (StrUtil.isNotBlank(testBenchDataCompensationList.get(i).getLengthValue())) {
                        String[] lens = testBenchDataCompensationList.get(i).getLengthValue().split(";");
                        System.out.println("长度的数量：" + lens.length);
                    }

                    if (StrUtil.isNotBlank(testBenchDataCompensationList.get(i).getHardValue())) {
                        String[] hands = testBenchDataCompensationList.get(i).getHardValue().split(";");
                        System.out.println("硬度的数量：" + hands.length);
                    }

                    if (StrUtil.isNotBlank(testBenchDataCompensationList.get(i).getAllventValue())) {
                        String[] allVents = testBenchDataCompensationList.get(i).getAllventValue().split(";");
                        System.out.println("总通风率的数量：" + allVents.length);
                    }

                    if (StrUtil.isNotBlank(testBenchDataCompensationList.get(i).getCircleValue())) {
                        String[] circles = testBenchDataCompensationList.get(i).getCircleValue().split(";");
                        System.out.println("圆周的数量：" + circles.length);
                    }

                    if (StrUtil.isNotBlank(testBenchDataCompensationList.get(0).getPdValue())) {
                        String[] pds = testBenchDataCompensationList.get(i).getPdValue().split(";");
                        System.out.println("吸阻的数量：" + pds.length);
                    }

                }
            }

        }
    }

    /**
     * read file as string
     * reference links：https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
     *
     * @param fileName file name
     * @return java.lang.String
     * @throws Exception exception
     */
    public static String readFileAsString(String fileName)
            throws Exception
    {
        String data = "";
        data = new String(
                Files.readAllBytes(Paths.get(fileName)));
        return data;
    }


    /**
     * <p> 四舍六入五五成双 </p>
     *
     * @param num         保留数字
     * @param countNumber 保留位数
     * @return java.lang.String
     * @author mxuexxmy
     * @date 2022/3/14 16:03
     */
    private static BigDecimal fourUpSixInto(BigDecimal num, int countNumber) {
        return num.setScale(countNumber, RoundingMode.HALF_EVEN);
    }


//    /**
//     * 功能描述：把同一个表格多个sheet测试结果重新输出，如果后续增加多个List<Map<String, Object>>对象，需要后面继续追加
//     * @ExcelEntiry sheet表格映射的实体对象
//     * @return
//     */
//    public static String exportSheet( Object...objects){
//        Workbook workBook = null;
//        try {
//            // 创建参数对象（用来设定excel得sheet得内容等信息）
//            ExportParams deptExportParams = new ExportParams();
//            // 设置sheet得名称
//            deptExportParams.setSheetName("登录用例");
//            // 设置sheet表头名称
//            deptExportParams.setTitle("测试用例");
//            // 创建sheet1使用得map
//            Map<String, Object> deptExportMap = new HashMap<>();
//            // title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
//            deptExportMap.put("title", deptExportParams);
//            // 模版导出对应得实体类型
//            deptExportMap.put("entity", LoginCaseDto.class);
//            // sheet中要填充得数据
//            deptExportMap.put("data", objects[0]);
//            ExportParams empExportParams = new ExportParams();
//            empExportParams.setTitle("被测RUL路径");
//            empExportParams.setSheetName("被测url");
//            // 创建sheet2使用得map
//            Map<String, Object> empExportMap = new HashMap<>();
//            empExportMap.put("title", empExportParams);
//            empExportMap.put("entity", LoginUrlDto.class);
//            empExportMap.put("data", objects[1]);
//            // 将sheet1、sheet2使用得map进行包装
//            List<Map<String, Object>> sheetsList = new ArrayList<>();
//            sheetsList.add(deptExportMap);
//            sheetsList.add(empExportMap);
//            // 执行方法
//            workBook = EasyPoiUtil.exportExcel(sheetsList, ExcelType.HSSF);
//            //String fileName = URLEncoder.encode("test", "UTF-8");
//            String filepath = (String) LoadStaticConfigUtil.getCommonYml( "testcaseexcel.cases");
//            FileOutputStream fos = new FileOutputStream(filepath);
//            workBook.write(fos);
//            fos.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if(workBook != null) {
//                try {
//                    workBook.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return "success";
//    }

}
