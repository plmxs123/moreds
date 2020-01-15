package com.example.moreds.config;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @date 2020/1/9 0009
 */
public class DataSourceHolder {
    //线程本地环境
    private static final ThreadLocal<String> contextHolders = new ThreadLocal<>();
    private static final ThreadLocal<String> standbyHolders = new ThreadLocal<>();
    //数据源列表
    public static List<String> dataSourceIdS = new ArrayList<>();

    //设置数据源
    public static void setDataSource(String dataSourceType) {
        String first = contextHolders.get();
        if (StringUtils.hasText(first)) {
            standbyHolders.set(first);
        }
        contextHolders.set(dataSourceType);
    }

    //获取数据源
    public static String getDataSource() {
        String first = contextHolders.get();
        if (StringUtils.hasText(first)) {
            return first;
        } else {
            return standbyHolders.get();
        }
    }

    //清除数据源
    public static void clearDataSource() {
        contextHolders.set(standbyHolders.get());
    }

    //判断指定DataSrouce当前是否存在
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIdS.contains(dataSourceId);
    }
}
