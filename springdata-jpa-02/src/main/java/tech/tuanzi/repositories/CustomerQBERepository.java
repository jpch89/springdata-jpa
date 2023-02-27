package tech.tuanzi.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import tech.tuanzi.pojo.Customer;

public interface CustomerQBERepository extends PagingAndSortingRepository<Customer, Long>, QueryByExampleExecutor<Customer> {
}
