package edu.gues.demo.controller;

import edu.gues.demo.entity.Demo;
import edu.gues.demo.entity.DemoTwo;
import edu.gues.demo.entity.UserDTO;
import edu.gues.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;

/**
 * <h2> TestController  </h2>
 *
 * @author mxuexxmy
 * @date 2022/1/17 13:20
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public String test(ArrayList<UserDTO> userDTOList)  {
        log.info("传递消息：" + userDTOList);
        return "success";
    }

    @GetMapping("/demo")
    public DemoTwo test1()  {
        return testService.test();
    }


    /**
     * 计量设备上传文件存储文件名
     * @param request 文件
     * @return
     */
    @PutMapping("/file/name")
    public void importFile(){
        testService.importFile();
    }



    /**
     * 计量设备上传文件存储文件名
     * @param request 文件
     * @return
     */
    @PostMapping("/upload/file")
    public void uploadFile(){
        testService.importFile();
    }


}
