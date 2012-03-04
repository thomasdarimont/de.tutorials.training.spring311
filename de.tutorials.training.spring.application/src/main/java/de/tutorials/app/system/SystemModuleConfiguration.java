package de.tutorials.app.system;

import java.sql.Driver;
import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:/config/system.properties")
@ImportResource("classpath:/spring/spring-data-config.xml")
// At the time of this writing 04.02.2012 Spring-Data-JPA is not able to be
// fully configured via javaconfig
// therefore we have to include one small xml file...
@ComponentScan(basePackages = { "de.tutorials.app" })
public class SystemModuleConfiguration {

    @Inject
    protected Environment env;

    @Bean
    public DataSource dataSource() {
	if (Arrays.asList(env.getActiveProfiles()).contains("testing")) {
	    // Wir testen mit einer embedded h2 db
	    return new EmbeddedDatabaseBuilder()
		    .setType(EmbeddedDatabaseType.H2)
		    .addScript("classpath:/db/schema.sql").build();
	} else {
	    SimpleDriverDataSource ds = new SimpleDriverDataSource();
	    ds.setDriverClass(env.getPropertyAsClass(
		    "persistence.db.driverClass", Driver.class));
	    ds.setUrl(env.getProperty("persistence.db.url"));
	    ds.setUsername(env.getProperty("persistence.db.username"));
	    ds.setPassword(env.getProperty("persistence.db.password"));
	    return ds;
	}
    }

    @Bean
    public JpaTransactionManager transactionManager() {
	return new JpaTransactionManager(entityManagerFactory());
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	em.setDataSource(dataSource());
	em.setJpaVendorAdapter(jpaVendorAdapter());
	em.setPackagesToScan("de.tutorials.app"); // this implicitly generates
						  // an appropriate
						  // persistence.xml for us at
						  // runtime .
	em.afterPropertiesSet();
	return em.getObject();
    }

    @Bean
    public PersistenceExceptionTranslator hibernateExceptionTranslator() {
	return new HibernateExceptionTranslator();
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
	HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
	adapter.setShowSql(env.getProperty("persistence.hibernate.showSql",
		Boolean.class));
	adapter.setGenerateDdl(env.getProperty(
		"persistence.hibernate.generateDdl", Boolean.class));
	adapter.setDatabasePlatform(env
		.getProperty("persistence.hibernate.databasePlatform"));
	return adapter;
    }
}
