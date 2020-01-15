package com.example.moreds.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2020/1/13 0013.
 */
@Data
public class User implements Serializable{
    private static final long serialVersionUID = 6072609661176889768L;

    private Integer id;

    private String userName;

    private Date createTime;

    private String data;
}
