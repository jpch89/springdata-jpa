package tech.tuanzi.repositories;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import tech.tuanzi.pojo.Customer;

public interface CustomerQueryDSLRepository
        extends PagingAndSortingRepository<Customer, Long>,
        QuerydslPredicateExecutor<Customer> {
}
