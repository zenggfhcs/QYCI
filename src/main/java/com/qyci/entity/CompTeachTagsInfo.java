package com.qyci.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * comp_teach_tags_info
 * @author 
 */
@Data
public class CompTeachTagsInfo implements Serializable {
    private Integer id;

    private CompTeachTag tagInfo;

    private Integer teachInfo;

    private static final long serialVersionUID = 1L;
}