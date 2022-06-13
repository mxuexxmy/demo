package edu.gues.demo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * LoginUrlDto
 *
 * @author mxuexxmy
 * @date 2021-08-06-12:16 AM
 */
public class LoginUrlDto {
    @Excel(name = "id（访问测试类型）",orderNum = "1",width = 20)
    private String id;
    @Excel(name = "type(请求类型)",orderNum = "2",width = 20)
    private String type;
    @Excel(name = "url(访问地址)",orderNum = "3",width = 40)
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LoginUrlDto{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
