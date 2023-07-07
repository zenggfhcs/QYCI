package com.qyci.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * competition_teaching_info
 *
 * @author ztc
 */
@Data
public class CompTeach implements Serializable {
    public CompTeach() {
    }

    /**
     * 主键，自增长
     */
    private Integer id;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 发布者id
     */
    private User from;

    /**
     * 视频静止封面
     */
    private String cover;

    /**
     * 视频链接
     */
    private String link;

    /**
     * 简介
     */
    private String synopsis;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    private Byte topping;

    private Boolean visible;

    private Boolean review;

    private Boolean reviewResult;

    private static final long serialVersionUID = 1L;
}