package nl.deholtmans.datasources.multi;

import nl.deholtmans.datasources.customer.CustomerModel;
import nl.deholtmans.datasources.customer.CustomerRepository;
import nl.deholtmans.datasources.product.ProductModel;
import nl.deholtmans.datasources.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MultiDatasources {
  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;

  @Autowired
  public MultiDatasources( CustomerRepository customerRepository,
                           ProductRepository productRepository) {
    this.customerRepository = customerRepository;
    this.productRepository = productRepository;
  }

  @Transactional("customerTransactionManager")
  public void createCustomer( String firstName, String lastName, String email) {
    CustomerModel customer = new CustomerModel(email,firstName,lastName);
    customer = customerRepository.save(customer);
  }

  @Transactional("customerTransactionManager")
  public List<CustomerModel> getAllCustomers() {
    return customerRepository.findAll();
  }

  @Transactional("productTransactionManager")
  public void createProduct( String code, String name, double price) {
    ProductModel product = new ProductModel(code,name, price);
    product = productRepository.save(product);
  }

  @Transactional("productTransactionManager")
  public List<ProductModel> getAllProducts() {
    return productRepository.findAll();
  }
}
