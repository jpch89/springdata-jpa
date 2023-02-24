package tech.tuanzi.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;
import tech.tuanzi.pojo.Customer;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerMethodNameRepository extends PagingAndSortingRepository<Customer, Long> {
    List<Customer> findByCustName(String custName);

    boolean existsByCustName(String custName);

    // 增删改需要加上事务支持
    // 并通知 Spring Data JPA 是一个非查询操作
    @Transactional
    @Modifying
    int deleteByCustId(Long custId);

    List<Customer> findByCustNameLike(String custName);
}
