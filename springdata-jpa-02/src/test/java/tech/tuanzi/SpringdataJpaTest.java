package tech.tuanzi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.tuanzi.config.SpringDataJPAConfig;
import tech.tuanzi.pojo.Customer;
import tech.tuanzi.repositories.CustomerRepository;

import java.util.Optional;

// 基于 JUnit4 的 Spring 单元测试
// XML 配置的测试
// @ContextConfiguration("/spring.xml")
// Java Config 配置的测试
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringdataJpaTest {
    @Autowired
    CustomerRepository repository;

    @Test
    public void testR() {
        Optional<Customer> byId = repository.findById(1L);
        System.out.println(byId.get());
    }

    @Test
    public void testC() {
        Customer customer = new Customer();
        customer.setCustName("李四");
        repository.save(customer);
    }

    @Test
    public void testU() {
        Customer customer = new Customer();
        customer.setCustId(3L);
        customer.setCustName("李四");
        repository.save(customer);
    }

    @Test
    public void testD() {
        Customer customer = new Customer();
        customer.setCustId(3L);
        customer.setCustName("李四");
        repository.delete(customer);
    }
}
