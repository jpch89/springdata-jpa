package tech.tuanzi;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import tech.tuanzi.config.SpringDataJPAConfig;
import tech.tuanzi.pojo.Customer;
import tech.tuanzi.pojo.QCustomer;
import tech.tuanzi.repositories.CustomerQueryDSLRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
        BooleanExpression expression = customer.custName.in("团子", "王五")
                .and(customer.custId.gt(0L))
                .and(customer.custAddress.eq("Beijing"));
        System.out.println(repository.findAll(expression));
    }

    @Test
    public void test03() {
        Customer params = new Customer();
        params.setCustId(0L);
        params.setCustName("王五,圆子");

        QCustomer customer = QCustomer.customer;
        // 初始条件：永远成立的条件，类似于 1 == 1
        BooleanExpression expression = customer.isNotNull().or(customer.isNull());
        expression = params.getCustId() > -1 ?
                expression.and(customer.custId.gt(params.getCustId())) :
                expression;
        expression = !StringUtils.isEmpty(params.getCustName()) ?
                expression.and(customer.custName.in(params.getCustName().split(","))) :
                expression;
        expression = !StringUtils.isEmpty(params.getCustAddress()) ?
                expression.and(customer.custName.eq(params.getCustAddress())) :
                expression;

        System.out.println(repository.findAll(expression));
    }

    // 使用 @Autowired 注入，有线程安全问题
    // @Autowired
    // @PersistenceContext 可以为一个线程绑定一个 EntityManager，避免线程安全问题
    @PersistenceContext
    EntityManager em;

    /**
     * 自定义列查询、分组
     * 需要使用原生态的方式，Specification 也是一样
     * 而通过 Repository 进行查询，列、表都是固定的
     */
    @Test
    public void test04() {
        JPAQueryFactory factory = new JPAQueryFactory(em);

        QCustomer customer = QCustomer.customer;

        // 构建基于 QueryDSL 的查询

        // JPAQuery<Customer> customerJPAQuery = factory.select(customer)
        //         .from(customer)
        //         .where(customer.custId.eq(1L))
        //         .orderBy(customer.custId.desc());

        // select id, custName from
        JPAQuery<Tuple> tupleJPAQuery = factory.select(customer.custId, customer.custName)
                .from(customer)
                .where(customer.custId.eq(1L))
                .orderBy(customer.custId.desc());

        // 执行查询
        List<Tuple> fetch = tupleJPAQuery.fetch();

        // 处理返回数据
        for (Tuple tuple : fetch) {
            System.out.println(tuple.get(customer.custId));
            System.out.println(tuple.get(customer.custName));
        }
    }

    @Test
    public void test05() {
        JPAQueryFactory factory = new JPAQueryFactory(em);

        QCustomer customer = QCustomer.customer;

        // 构建基于 QueryDSL 的查询
        JPAQuery<Long> longJPAQuery = factory.select(customer.custId.sum())
                .from(customer)
                .orderBy(customer.custId.desc());

        // 执行查询
        List<Long> fetch = longJPAQuery.fetch();

        // 处理返回数据
        for (Long sum : fetch) {
            System.out.println(sum);
        }
    }
}
