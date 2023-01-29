package tech.tuanzi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.tuanzi.config.SpringDataJPAConfig;
import tech.tuanzi.pojo.Customer;
import tech.tuanzi.repositories.CustomerRepository;

@ContextConfiguration(classes= SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDataJpaPagingAndSortTest {
    @Autowired
    CustomerRepository repository;

    @Test
    public void testPaging() {
        // 这里要传一个 Pageable 接口对象
        // PageRequest 类的 of 静态方法，可以返回一个 PageRequest 对象
        // 而 PageRequest 对象是 AbstractPageRequest 抽象类的子类
        // AbstractPageRequest 抽象类实现了 Pageable 接口以及 Serializable 接口
        Page<Customer> all = repository.findAll(PageRequest.of(0, 2));
        System.out.println(all);
        // 总页数
        System.out.println(all.getTotalPages());
        // 总条数
        System.out.println(all.getTotalElements());
        // 当前页所有数据
        System.out.println(all.getContent());
    }

    @Test
    public void testSort() {
        Sort sort = Sort.by("custId").descending();
        Iterable<Customer> all = repository.findAll(sort);
        System.out.println(all);
    }

    @Test
    public void testSortTypeSafe() {
        Sort.TypedSort<Customer> sortType = Sort.sort(Customer.class);
        Sort sort = sortType.by(Customer::getCustId).descending();
        Iterable<Customer> all = repository.findAll(sort);
        System.out.println(all);
    }
}
