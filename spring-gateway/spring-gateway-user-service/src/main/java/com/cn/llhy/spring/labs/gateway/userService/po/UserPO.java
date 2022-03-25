package com.cn.llhy.spring.labs.gateway.userService.po;

/**
 * @author Huangyong
 * @version 1.0.0
 * @Description
 * @createTime 2022-3-22 14:46
 */
public class UserPO {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
