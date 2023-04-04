package tech.tuanzi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tech.tuanzi.config.SpringDataJPAConfig;
import tech.tuanzi.repositories.CustomerQueryDSLRepository;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringRunner.class)
public class QueryDSLTest {
    @Autowired
    CustomerQueryDSLRepository repository;

    @Test
    public void test01() {

    }
}
