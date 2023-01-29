package tech.tuanzi.repositories;

import org.springframework.data.repository.CrudRepository;
import tech.tuanzi.pojo.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
