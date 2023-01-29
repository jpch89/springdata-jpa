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
public class JpqlTest {
    @Autowired
    CustomerRepository repository;

    @Test
    public void testR() {
        List<Customer> customers = repository.findCustomerByCustName("圆子");
        System.out.println(customers);
    }

    @Test
    public void testU() {
        int result = repository.updateCustomer("王五", 7L);
        System.out.println(result);
    }

    @Test
    public void testD() {
        int result = repository.deleteCustomer(7L);
        System.out.println(result);
    }

    @Test
    public void testC() {
        int result = repository.insertCustomerBySelect(6L);
        System.out.println(result);
    }
}
