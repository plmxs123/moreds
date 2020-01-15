package com.example.moreds.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by admin on 2020/1/9 0009.
 */
@Configuration
@MapperScan(basePackages = DynamicDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "customSqlSessionFactory")
public class DynamicDataSourceConfig implements EnvironmentAware {

    static final String PACKAGE = "com.example.moreds.dao";
    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    // <datasourceId,envPath>
    private HashMap<String, String> envPathMap = new HashMap<String, String>() {{
        put("mysql1", "mysql1.datasource.");
        put("mysql2", "mysql2.datasource.");
        put("postgreSql", "postgreSql.datasource.");
    }};

    private HashMap<Object,Object> dataSourceMap = new HashMap<>();

    @Override
    public void setEnvironment(Environment environment) {
        envPathMap.keySet().forEach(datasourceId -> {
            try {
                initDatasource(environment, datasourceId, envPathMap.get(datasourceId));
            } catch (Exception e) {
                System.out.println("创建" + datasourceId + "数据库失败，原因：" + e);
            }
        });
    }

    private void initDatasource(Environment environment, String dataSourceId, String envPath) throws SQLException {
        //读取数据源
        DruidDataSource druidDataSource = buildDruidDataSource(environment, envPath);
        dataSourceMap.put(dataSourceId,druidDataSource);
        DataSourceHolder.dataSourceIdS.add(dataSourceId);
    }

    //拼装数据源
    private DruidDataSource buildDruidDataSource(Environment environment, String envPath) throws SQLException {
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, envPath);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(resolver.getProperty("driverClass"));
        dataSource.setUrl(resolver.getProperty("url"));
        dataSource.setUsername(resolver.getProperty("username"));
        dataSource.setPassword(resolver.getProperty("password"));
        dataSource.setMaxActive(Integer.parseInt(resolver.getProperty("maxActive")));
        dataSource.setMinIdle(Integer.parseInt(resolver.getProperty("minIdle")));
        dataSource.setMaxWait(Long.parseLong(resolver.getProperty("maxWait")));
        dataSource.setPoolPreparedStatements(Boolean.parseBoolean(resolver.getProperty("poolPreparedStatements")));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(
                Integer.parseInt(resolver.getProperty("maxPoolPreparedStatementPerConnectionSize")));
        dataSource.setKeepAlive(Boolean.parseBoolean(resolver.getProperty("keepAlive")));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(resolver.getProperty("minEvictableIdleTimeMillis")));
        dataSource.setValidationQuery(resolver.getProperty("validationQuery"));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(resolver.getProperty("testWhileIdle")));
        dataSource.setFilters(resolver.getProperty("filters"));

        return dataSource;
    }

    @Bean(name = "dataSource")
    public DynamicDataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Optional<Object> first = dataSourceMap.values().stream().findFirst();
        boolean present = first.isPresent();
        if (present) {
            dynamicDataSource.setDefaultTargetDataSource(first.get());
        } else {
            return null;
        }
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    @Primary
    @Bean(name = "customTransactionManager")
    public DataSourceTransactionManager customTransactionManager() throws SQLException {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        transactionManager.setNestedTransactionAllowed(true);
        transactionManager.setRollbackOnCommitFailure(true);
        return transactionManager;
    }

    @Primary
    @Bean(name = "customSqlSessionFactory")
    public SqlSessionFactory customSqlSessionFactory(DynamicDataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setUseGeneratedKeys(true);
        configuration.setUseColumnLabel(true);
        configuration.setJdbcTypeForNull(null);
        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }
}
