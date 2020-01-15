package com.example.moreds.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by admin on 2020/1/9 0009.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DataSourceHolder.getDataSource();
        System.out.println(dataSource);
        return dataSource;
    }
}
