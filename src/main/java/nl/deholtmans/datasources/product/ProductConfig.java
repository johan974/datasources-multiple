package nl.deholtmans.datasources.product;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager",
        basePackages = {"nl.deholtmans.datasources.product"}
)

public class ProductConfig {


  @Bean(name = "productDataSource")
  @ConfigurationProperties(prefix = "postgres.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "productEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean
  barEntityManagerFactory(EntityManagerFactoryBuilder builder,
                          @Qualifier("productDataSource") DataSource dataSource
  ) {
    return builder
            .dataSource(dataSource)
            .packages("nl.deholtmans.datasources.product")
            .persistenceUnit("db2")
            .build();
  }

  @Bean(name = "productTransactionManager")
  public PlatformTransactionManager productTransactionManager(
          @Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory
  ) {
    return new JpaTransactionManager(productEntityManagerFactory);
  }
}
