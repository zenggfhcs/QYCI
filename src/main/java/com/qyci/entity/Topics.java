package com.qyci.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * topics
 *
 * @author
 */
@Data
public class Topics implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 发布者id
     */
    private User from;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 标签
     */
    private List<TopicsTag> tags;

    private Boolean review;

    private Boolean reviewResult;

    private static final long serialVersionUID = 1L;
}