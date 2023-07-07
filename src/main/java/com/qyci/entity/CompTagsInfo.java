package com.qyci.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * comp_tags_info
 * @author 
 */
@Data
public class CompTagsInfo implements Serializable {
    private Long id;

    private Integer compId;

    private Integer tagId;

    private static final long serialVersionUID = 1L;
}