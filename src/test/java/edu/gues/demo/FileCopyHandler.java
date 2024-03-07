package edu.gues.demo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @description: TODO
 * @author: mxuexxmy
 * @date: 2024/1/24 12:52
 * @version: 1.0
 */
public class FileCopyHandler {

    @Test
    public void copy() {
        // 源文件
        String sourceDirectory = "E:\\hlxd\\bd\\测试台文件\\分日期";
        // 目标文件
        String targetDirectory = "E:\\hlxd\\bd\\测试台文件\\分牌号";

        // 获取当前项目的工作目录
        Path currentWorkingDir = Paths.get(sourceDirectory);

        // 使用Files.list获取当前目录下的所有文件
        try (Stream<Path> paths = Files.list(currentWorkingDir)) {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 年
            paths.forEach(path -> {
                System.out.println("年:" + path);
                try {
                    // 月
                    Stream<Path> path1 = Files.list(path);
                    path1.forEach(pathMonth -> {
                        System.out.println("月:" + pathMonth);
                        try {
                            // 日
                            Stream<Path> path2 = Files.list(pathMonth);
                            path2.forEach(pathDay -> {
                                System.out.println("日:" + pathDay);
                                // 读取每个文件
                                try {
                                    Stream<Path> path3 = Files.list(pathDay);
                                    AtomicInteger num = new AtomicInteger();
                                    path3.forEach(fileInfo -> {
                                        System.out.println("每个文件:" + fileInfo);
                                        String fileName = fileInfo.getFileName().toString();
                                        System.out.println("文件名:" + fileName);
                                        if (fileName.contains("细支荷花滤棒") && !fileName.contains("测试") && num.get() < 2) {
                                            num.getAndIncrement();
                                            Path sourceFile = Paths.get(fileInfo.toUri());
                                            Path targetFile = Paths.get(targetDirectory + "\\" + fileName);
                                            // 定义拷贝选项，使用 StandardCopyOption.COPY_ATTRIBUTES 保留原始文件的属性
                                            CopyOption[] options = new CopyOption[]{
                                                    StandardCopyOption.COPY_ATTRIBUTES
                                            };
                                            // 执行文件拷贝
                                            try {
                                                Files.copy(sourceFile, targetFile, options);
                                                System.out.println(fileName + "文件拷贝完成！");
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        // 复制文件
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
