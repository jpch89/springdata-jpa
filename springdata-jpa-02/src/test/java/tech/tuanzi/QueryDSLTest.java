package tech.tuanzi;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tech.tuanzi.config.SpringDataJPAConfig;
import tech.tuanzi.pojo.QCustomer;
import tech.tuanzi.repositories.CustomerQueryDSLRepository;


@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringRunner.class)
public class QueryDSLTest {
    @Autowired
    CustomerQueryDSLRepository repository;

    @Test
    public void test01() {
        QCustomer customer = QCustomer.customer;

        // 通过 id 查找
        // BooleanExpression 实现了 Predicate 接口
        BooleanExpression eq = customer.custId.eq(1L);
        System.out.println(repository.findOne(eq));
    }

    /**
     * 查询客户范围 in
     * id > 大于
     * 地址 精确
     */
    @Test
    public void test02() {
        QCustomer customer = QCustomer.customer;
        BooleanExpression and = customer.custName.in("团子", "王五")
                .and(customer.custId.gt(0L))
                .and(customer.custAddress.eq("Beijing"));
        System.out.println(repository.findAll(and));
    }
}
