package com.qyci.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * comp_teach_tag
 * @author ztc
 */
@Data
public class CompTeachTag implements Serializable {
    private Integer id;

    private String name;

    private Integer parents;

    private static final long serialVersionUID = 1L;
}