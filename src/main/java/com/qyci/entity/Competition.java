package com.qyci.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

/**
 * competition
 * @author ztc
 */
@Data
public class Competition implements Serializable {
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
    private String name;

    private String organizer;

    private String coOrganizer;

    private String contact;

    private Integer maxEnrollCount;

    /**
     * 内容
     */
    private String content;

    private Date publishTime;

    public Date getEnrollStartTime() {

        return new Date(enrollStartTime.getTime()) {
            @Override
            public String toString() {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return dateformat.format(this);
            }
        };
    }

    public Date getEnrollEndTime() {
        return new Date(enrollEndTime.getTime()) {
            @Override
            public String toString() {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return dateformat.format(this);
            }
        };
    }

    public Date getActStartTime() {
        return new Date(actStartTime.getTime()) {
            @Override
            public String toString() {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return dateformat.format(this);
            }
        };
    }

    public Date getActEndTime() {
        return new Date(actEndTime.getTime()) {
            @Override
            public String toString() {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return dateformat.format(this);
            }
        };
    }

    private Date enrollStartTime;

    private Date enrollEndTime;

    private Date actStartTime;

    private Date actEndTime;

    private Byte topping;

    private Boolean visible;

    private Boolean review;

    private Boolean reviewResult;

    private static final long serialVersionUID = 1L;

    public void setEnrollStartTime(String enrollStartTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(enrollStartTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.enrollStartTime = date;
    }

    public void setEnrollEndTime(String enrollEndTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(enrollEndTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.enrollEndTime = date;
    }

    public void setActStartTime(String actStartTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(actStartTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.actStartTime = date;
    }

    public void setActEndTime(String actEndTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(actEndTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.actEndTime = date;
    }
}