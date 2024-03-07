package edu.gues.demo.service;

import edu.gues.demo.entity.Demo;
import edu.gues.demo.entity.DemoTwo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2023/4/19 19:58
 * @version: 1.0
 */
public interface TestService {

    DemoTwo test();

    void importFile();
}
