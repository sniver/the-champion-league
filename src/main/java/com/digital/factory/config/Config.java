package com.digital.factory.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class Config {
	@Autowired
	DataSource dataSource;
	
	@Autowired
	Environment env;
	
	@Value("classpath:mysql-schema.sql")
	private Resource mySQLSchemaScript;
	
	@PostConstruct
	public void dataSourceInitializer() {
		boolean runIntializer = Boolean.valueOf(env.getProperty("spring.datasource.runDataSeeds"));
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator());
		initializer.setEnabled(runIntializer);
		initializer.afterPropertiesSet();
	}
	
	
	private DatabasePopulator databasePopulator() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(mySQLSchemaScript);
		return populator;
	}
}
