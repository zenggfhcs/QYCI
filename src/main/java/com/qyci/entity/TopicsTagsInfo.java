package com.qyci.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * topics_tags_info
 * @author 
 */
@Data
public class TopicsTagsInfo implements Serializable {
    private Long id;

    private TopicsTag tagInfo;

    private Integer topicsInfo;

    private static final long serialVersionUID = 1L;
}