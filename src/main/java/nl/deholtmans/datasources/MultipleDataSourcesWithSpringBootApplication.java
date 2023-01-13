package nl.deholtmans.datasources;

import nl.deholtmans.datasources.multi.MultiDatasources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultipleDataSourcesWithSpringBootApplication implements CommandLineRunner {
  @Autowired
  private MultiDatasources multiDatasources;

  public static void main(String[] args) {
    SpringApplication.run(MultipleDataSourcesWithSpringBootApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    multiDatasources.createCustomer( "jj", "hol", "jj@hol.com");
    multiDatasources.createCustomer( "dd", "hol", "dd@hol.com");
    System.out.println( "Customers: ");
    multiDatasources.getAllCustomers().forEach( System.out::println);

    multiDatasources.createProduct( "jcode", "holname", 123.00);
    multiDatasources.createProduct( "dcode", "holname", 234.00);
    System.out.println( "Products: ");
    multiDatasources.getAllProducts().forEach( System.out::println);
  }
}
