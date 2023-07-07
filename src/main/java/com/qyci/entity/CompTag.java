package com.qyci.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * comp_tag
 * @author 
 */
@Data
public class CompTag implements Serializable {
    private Integer id;

    private String name;

    private Integer parents;

    private static final long serialVersionUID = 1L;
}