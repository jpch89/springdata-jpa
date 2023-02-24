package tech.tuanzi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.tuanzi.config.SpringDataJPAConfig;
import tech.tuanzi.pojo.Customer;
import tech.tuanzi.repositories.CustomerMethodNameRepository;

import java.util.List;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MethodNameTest {
    @Autowired
    CustomerMethodNameRepository repository;

    @Test
    public void test01() {
        List<Customer> list = repository.findByCustName("团子");
        System.out.println(list);
    }

    @Test
    public void test02() {
        boolean exists = repository.existsByCustName("团子");
        System.out.println(exists);
    }

    @Test
    public void test03() {
        int i = repository.deleteByCustId(9L);
        System.out.println(i);
    }

    @Test
    public void test04() {
        List<Customer> list = repository.findByCustNameLike("%子");
        System.out.println(list);
    }
}
