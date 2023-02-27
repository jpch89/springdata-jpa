package tech.tuanzi.repositories;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import tech.tuanzi.pojo.Customer;

public interface CustomerSpecificationsRepository extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

}
