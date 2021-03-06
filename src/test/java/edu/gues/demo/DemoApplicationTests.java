package edu.gues.demo;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Sets;
import edu.gues.demo.entity.Car;
import edu.gues.demo.entity.LoginCaseDto;
import edu.gues.demo.entity.LoginUrlDto;
import edu.gues.demo.entity.Test1;
import edu.gues.demo.enums.CarType;
import edu.gues.demo.enums.CutStatusEnum;
import edu.gues.demo.enums.PhysicalTargetExportEnum;
import edu.gues.demo.test.TestInterface;
import edu.gues.demo.testinterface.InterfaceOfOne;
import edu.gues.demo.testinterface.impl.InterfaceOfOneImpl;
import edu.gues.demo.util.AsciiUtil;
import edu.gues.demo.util.DateRange;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static cn.hutool.json.XMLTokener.entity;
import static java.util.stream.Collectors.joining;

@SpringBootTest
class DemoApplicationTests {

    private static List<Object> list;
    private static Function keyExtractors;

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
        System.out.println("????????????");
        demoList.forEach(System.out::println);
        List<Demo> demos = demoList.stream().filter((x) -> {
            for (String s : strings) {
                if (s.equals(x.getCode())) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        System.out.println("??????????????????");
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
        System.out.println(AsciiUtil.sbc2dbcCase("???????????????"));
        System.out.println(AsciiUtil.dbc2sbcCase("hello world"));
    }

    @Test
    public void test3() {
        Integer x = 1;
        if (x.equals(PhysicalTargetExportEnum.PRE_WAREHOUSING_QUALITY_INSPECTION.getCode())) {
            System.out.println("?????????????????????????????????");
        } else {
            System.out.println("??????");
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
        String str = "?????????";
        System.out.println(str.contains("??????"));
    }

    /**
     * <p> ?????? </p>
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
        System.out.println("?????????");
        demoList.forEach(System.out::println);
        System.out.println("?????????");

        List<Demo> collect = listOfDistinctMore(demoList, Demo::getOne, Demo::getTwo);
        collect.forEach(System.out::println);
    }

    // Map ??????
    public static <T> Predicate<T> distinctByKey1(
            Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    // Set ??????
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }


    /**
     * <p> list ??????????????? </p>
     *
     * @param list          ??????
     * @param keyExtractors ?????????????????????
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
     * <p> list ??????????????? </p>
     *
     * @param list ??????
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
                System.out.println("??????");
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
        Car car = new Car().setBrand("??????").setType(CarType.SUV).setPlateNumber("ccc");
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
        set.add("??????");
        set.add("??????");
        set.add("??????");
        set.add("??????");
        set = set.stream().sorted(Comparator.reverseOrder())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        set.forEach(System.out::println);
    }

    @Test
    public void test22() {
        Test1 test1 = new Test1();
        test1.setBrand("A");
        test1.setPlateNumber("B");

        // ?????? brand ?????????
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
            System.out.println("????????????");
        }
    }

    ExecutorService pool = Executors.newFixedThreadPool(10);

    @Test
    public void test23() {
//        for (int i = 0; i < 20; i++) {
//            int finalI = i;
//            Thread t1 = new Thread(() -> {
//                try {
//                    //testInterface.apply("??????????????????"+ finalI);
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
        test1.setIsNull(Objects.nonNull(test1.getBrand()) ? "brand ?????????" : "brand??????");
        System.out.println(test1.getIsNull());
        System.out.println("-------");
        Test1 test2 = new Test1();
        test2.setPlateNumber("B");
        test2.setIsNull(Objects.nonNull(test2.getBrand()) ? "brand ?????????" : "brand??????");
        System.out.println(test2.getIsNull());

        List<Test1> test5 = new ArrayList<>();
        test5.add(test1);
        test5.add(test1);
        Test1 test3 = new Test1();
        Test1 test4 = test5.stream().findFirst().orElse(new Test1());
        test3.setIsNull(StrUtil.isNotBlank(test4.getBrand()) ? "brand ?????????" : "test4????????? brand??????");
        System.out.println(test3.getIsNull());
    }


    @Test
    public void test25() {
        for (int i = 0; i < 20; i++) {
            testInterface.apply("??????????????????" + i);
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
        System.out.println("???????????????" + startDate + ";" + "????????????:" + endDate);
    }


    @Test
    public void test36() {
      //  System.out.println(Math.sqrt(0) / 0 * 100);
        double a = 1;
        if (a != 0.0) {
            System.out.println("a?????????0");

        } else {
            System.out.println("a??????0");
        }
    }

    @Test
    public void test37() {
         String str = "2022-05-09 21:38:10.0";
        System.out.println(DateUtil.parseDateTime(str));

    }


    @Test
    public void test38() {
        String str = "?????????";
        boolean isTure = str.contains("??????");
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




//    /**
//     * ???????????????????????????????????????sheet???????????????????????????????????????????????????List<Map<String, Object>>?????????????????????????????????
//     * @ExcelEntiry sheet???????????????????????????
//     * @return
//     */
//    public static String exportSheet( Object...objects){
//        Workbook workBook = null;
//        try {
//            // ?????????????????????????????????excel???sheet?????????????????????
//            ExportParams deptExportParams = new ExportParams();
//            // ??????sheet?????????
//            deptExportParams.setSheetName("????????????");
//            // ??????sheet????????????
//            deptExportParams.setTitle("????????????");
//            // ??????sheet1?????????map
//            Map<String, Object> deptExportMap = new HashMap<>();
//            // title????????????ExportParams????????????????????????ExportParams????????????sheetName
//            deptExportMap.put("title", deptExportParams);
//            // ?????????????????????????????????
//            deptExportMap.put("entity", LoginCaseDto.class);
//            // sheet?????????????????????
//            deptExportMap.put("data", objects[0]);
//            ExportParams empExportParams = new ExportParams();
//            empExportParams.setTitle("??????RUL??????");
//            empExportParams.setSheetName("??????url");
//            // ??????sheet2?????????map
//            Map<String, Object> empExportMap = new HashMap<>();
//            empExportMap.put("title", empExportParams);
//            empExportMap.put("entity", LoginUrlDto.class);
//            empExportMap.put("data", objects[1]);
//            // ???sheet1???sheet2?????????map????????????
//            List<Map<String, Object>> sheetsList = new ArrayList<>();
//            sheetsList.add(deptExportMap);
//            sheetsList.add(empExportMap);
//            // ????????????
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
