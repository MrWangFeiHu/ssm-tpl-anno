package com.tpl.ssm.anno.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 测试实体
 */
public class TestEntity {

    /**
     * 主键编号
     */
    private Long id;

    /**
     * 测试名称
     */
    private String name;

    /**
     * 扩展字段
     */
    @JsonIgnore
    @TableField(exist = false)
    private Map<String, Object> ext = new LinkedHashMap<>(5);

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

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }
}
