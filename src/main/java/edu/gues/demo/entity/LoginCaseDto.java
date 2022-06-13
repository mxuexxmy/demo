package edu.gues.demo.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;


/**
 * LoginCaseDto
 *
 * @author mxuexxmy
 * @date 2021-08-06-12:10 AM
 */
public class LoginCaseDto {
    @Excel(name = "flag(0是反向，1是正向)",orderNum = "1",width = 20)
    private String flag;
    @Excel(name = "urlid(访问id)",orderNum = "2",width = 20)
    private String urlid;
    @Excel(name = "name(登录账号)",orderNum = "3",width = 20)
    private String name;
    @Excel(name = "pwd(登录密码)",orderNum = "4",width = 20)
    private String pwd;
    @Excel(name = "desc(期望提示语)",orderNum = "5",width = 40)
    private String desc;
    @Excel(name = "actual(实际测试结果)",orderNum = "6",width = 40 )
    private String actual;
    @Excel(name = "urlpath(被测路径)",orderNum = "7",width = 40 )
    private String urlpath;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUrlid() {
        return urlid;
    }

    public void setUrlid(String urlid) {
        this.urlid = urlid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getUrlpath() {
        return urlpath;
    }

    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath;
    }

    @Override
    public String toString() {
        return "LoginCaseDto{" +
                "flag='" + flag + '\'' +
                ", urlid='" + urlid + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", desc='" + desc + '\'' +
                ", actual='" + actual + '\'' +
                ", urlpath='" + urlpath + '\'' +
                '}';
    }
}