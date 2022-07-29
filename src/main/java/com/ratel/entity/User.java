package com.ratel.entity;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * @author bobo
 */
public class User {

    @JSONField(name = "ID")
    private Long id;
    @JSONField(name = "Name")
    private String name;
    @JSONField(name = "Score")
    private Integer score;

    public User() {
    }

    public User(Long id, String name, Integer score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
