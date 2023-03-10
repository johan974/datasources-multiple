package nl.deholtmans.datasources.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "customerEntityManagerFactory",
        transactionManagerRef = "customerTransactionManager",
        basePackages = {"nl.deholtmans.datasources.customer"}
)
public class CustomerConfig {
  @Primary
  @Bean(name = "customerDataSource")
  @ConfigurationProperties(prefix = "mysql.datasource")
  public DataSource customerDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = "customerEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory( EntityManagerFactoryBuilder builder,
          @Qualifier("customerDataSource") DataSource dataSource) {
    return builder
            .dataSource(dataSource)
            .packages("nl.deholtmans.datasources.customer")
            .persistenceUnit("db1")
            .build();
  }

  @Primary
  @Bean(name = "customerTransactionManager")
  public PlatformTransactionManager customerTransactionManager(
          @Qualifier("customerEntityManagerFactory") EntityManagerFactory
                  customerEntityManagerFactory
  ) {
    return new JpaTransactionManager(customerEntityManagerFactory);
  }
}
