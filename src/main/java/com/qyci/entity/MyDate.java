package com.qyci.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate extends Date {
    @Override
    public String toString() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateformat.format(this);
    }
}
