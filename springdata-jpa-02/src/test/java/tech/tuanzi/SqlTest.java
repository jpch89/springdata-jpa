package tech.tuanzi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.tuanzi.config.SpringDataJPAConfig;
import tech.tuanzi.pojo.Customer;
import tech.tuanzi.repositories.CustomerRepository;

import java.util.List;

@ContextConfiguration(classes= SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SqlTest {
    @Autowired
    CustomerRepository repository;

    @Test
    public void testR() {
        List<Customer> customers = repository.findCustomers("王五");
        System.out.println(customers);
    }
}
