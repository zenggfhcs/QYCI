package com.qyci.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * comp_teach_comment
 * @author ztc
 */
@Data
public class CompTeachComment implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 评论所属的teach
     */
    private Integer teach;

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
     * 置顶标识
     */
    private Boolean topping;

    /**
     * 评论发布时间
     */
    private Date publishTime;

    private static final long serialVersionUID = 1L;
}