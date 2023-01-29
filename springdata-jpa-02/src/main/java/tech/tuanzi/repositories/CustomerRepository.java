package tech.tuanzi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tech.tuanzi.pojo.Customer;

// public interface CustomerRepository extends CrudRepository<Customer, Long> {
// 实现分页和排序，可以继承 PagingAndSortingRepository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
