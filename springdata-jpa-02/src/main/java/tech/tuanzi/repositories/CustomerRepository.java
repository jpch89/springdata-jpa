package tech.tuanzi.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import tech.tuanzi.pojo.Customer;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
    // 增删改查

    // 查询
    // 注：查全部可以省略 SELECT
    // 位置参数
    // @Query("from Customer where custName=?1")
    // 具名参数，必须使用 @Param 注解，否则报错
    @Query("from Customer where custName=:custName")
    List<Customer> findCustomerByCustName(@Param("custName") String custName);

    // 修改
    @Query("update Customer c set c.custName=:custName where c.custId=:id")
    @Transactional
    @Modifying
    int updateCustomer(@Param("custName") String custName, @Param("id") Long id);

    // 删除
    @Query("delete from Customer c where c.custId=?1")
    @Transactional
    @Modifying
    int deleteCustomer(Long id);

    // 新增
    @Query("insert into Customer(custName) select c.custName from Customer c where c.custId=?1")
    @Transactional
    @Modifying
    int insertCustomerBySelect(Long id);

    // SQL
    @Query(value = "select * from tb_customer where cust_name=:custName", nativeQuery = true)
    List<Customer> findCustomers(@Param("custName") String custName);
}
