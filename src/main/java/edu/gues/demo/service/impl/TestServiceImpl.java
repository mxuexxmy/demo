package edu.gues.demo.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.smallbun.screw.core.util.StringUtils;
import com.alibaba.fastjson.JSON;
import edu.gues.demo.entity.Demo;
import edu.gues.demo.entity.DemoTwo;
import edu.gues.demo.mapper.TestMapper;
import edu.gues.demo.service.TestService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

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

    @Override
    public void importFile() {
        try {
            File pdf = new File("C://Users//19897//Documents//6.数字式压差计.pdf");
            MultipartFile file = new MockMultipartFile(pdf.getName(), pdf.getName(),
                    null, new FileInputStream(pdf));;
            // 计量设备上传文件更新设备有限期时间
            PDDocument pdDocument = PDDocument.load(file.getInputStream());
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setSortByPosition(true);
            String text = pdfTextStripper.getText(pdDocument);
            String s1 = StrUtil.subBefore(text, "院", false).replaceAll(" ","");
            String unit = s1 + "院";
//            if ("院".equals(unit.trim())){
//                unit = measureInfo.getDetectionUnit();
//            }
//            measureInfo.setDetectionUnit(unit);
            String year = null;
            String[] stringsOne = null;
            if (text.contains("检定日期")){
                stringsOne = text.split("检定日期");
            }else if (text.contains("校准日期")){
                stringsOne = text.split("校准日期");
            }else if (text.contains("测试日期")){
                stringsOne = text.split("测试日期");
            } else if (text.contains("校 准 日 期 年 月 日")) {
                stringsOne = text.split("校 准 日 期 年 月 日");
            } else if (text.contains("校准日期年月日")) {
                stringsOne = text.split("校 准 日 期 年 月 日");
            } else if (text.contains("校 准 日 期")){
                stringsOne = text.split("校 准 日 期");
            }
            if (Objects.nonNull(stringsOne) && stringsOne.length != 1){
                String  s = stringsOne[1];
                String sub = s.substring(0, s.indexOf("日")).trim();
                String replace = sub.replace(" ", "");
                String replaceOne = replace.replace("年", "-");
                year = replaceOne.replace("月", "-");
            }
            if (StringUtils.isNotBlank(year)){
                DateTime parse = DateUtil.parse(year, "yyyy-MM-dd");

                // 设置确认日期

                // 更改有效日期
//                String ca = measureInfo.getCalibrationInterval();
//                if ("0".equals(ca)){
//                    measureInfo.setExpiryDate(null);
//                }else {
//                    Calendar instance = Calendar.getInstance();
//                    instance.setTime(measureInfo.getDetectionDate());
//                    instance.add(Calendar.MONTH,Integer.valueOf(ca));
//                    instance.add(Calendar.DAY_OF_MONTH, -1);
//
//                    measureInfo.setExpiryDate(instance.getTime());
//                }
            }
//            String fileName = measureInfo.getReserved1();
//            if (StringUtils.isNotBlank(fileName)){
//                StringBuilder sb = new StringBuilder();
//                String[] split = fileName.split(",");
//                String s = split[split.length-1];
//                sb.append(s).append(",").append(files.getFiles());
//                measureInfo.setReserved1(sb.toString());
//            }else {
//                measureInfo.setReserved1(files.getFiles());
//            }
//            measureInfo.setUpdateTime(LocalDateTime.now());
//            measureInfo.setUpdateUserCode(UserUtils.getCode(UserUtils.getInfo()));
//            measureInfo.setUpdateUserName(UserUtils.getName(UserUtils.getInfo()));
//            this.baseMapper.updateById(measureInfo);
//            //更新对应设备年度校准计划
//            EmCalibrationPlan emCalibrationPlan = new EmCalibrationPlan();
//            emCalibrationPlan.setEmCode(measureInfo.getSolidNumber());
//            Date expiryDate = measureInfo.getExpiryDate();
//            LocalDateTime expiryDateTime = expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//            emCalibrationPlan.setPlanExeTime(expiryDateTime);
//            emCalibrationPlanClient.updatePlanByCode(emCalibrationPlan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
