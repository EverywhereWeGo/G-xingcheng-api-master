package com.bfd.api.mybatis;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.bfd.api.utils.LogType;
import com.bfd.api.utils.LogUtil;
import com.github.pagehelper.PageHelper;

@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {

    @Autowired
    DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.bfd.api.*.domain");

        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            LogUtil.getLogger(LogType.Run).error("classpath:mapper/*.xml --> mapper解析错误", e);
            throw new RuntimeException(e);
        }
    }

//	@Bean(name = "userTransaction")
//	public UserTransaction userTransaction() throws Throwable {
//		UserTransactionImp userTransactionImp = new UserTransactionImp();
//		userTransactionImp.setTransactionTimeout(10000);
//		return userTransactionImp;
//	}
//
//	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
//	public TransactionManager atomikosTransactionManager() throws Throwable {
//		UserTransactionManager userTransactionManager = new UserTransactionManager();
//		userTransactionManager.setForceShutdown(false);
//		return userTransactionManager;
//	}
	
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
//		try {
//			UserTransaction userTransaction = userTransaction();
//			JtaTransactionManager manager = new JtaTransactionManager(userTransaction, atomikosTransactionManager());
//			return manager;
//		} catch (Throwable e) {
//			return null;
//		}
    }
}
