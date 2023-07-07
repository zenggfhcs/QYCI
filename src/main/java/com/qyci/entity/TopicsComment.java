package com.qyci.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * topics_comment
 * @author 
 */
@Data
public class TopicsComment implements Serializable {
    /**
     * id
     */
    private Long id;

    private Integer topicsId;

    /**
     * 评论者id
     */
    private User from;

    /**
     * 被回复者id
     */
    private User to;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论发布时间
     */
    private Date publishTime;

    private Byte topping;

    private Boolean visible;

    private Boolean review;

    private Boolean reviewResult;

    private static final long serialVersionUID = 1L;
}