package tech.tuanzi;

import com.alibaba.druid.util.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tech.tuanzi.config.SpringDataJPAConfig;
import tech.tuanzi.pojo.Customer;
import tech.tuanzi.repositories.CustomerSpecificationsRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringRunner.class)
public class SpecificationTest {
    @Autowired
    CustomerSpecificationsRepository repository;
    @Autowired
    EntityManager entityManager;

    @Test
    public void testR() {
        List<Customer> cusotmers = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // root 可以用来获取列
                // criteriaBuilder 可以设置查询各种条件（<，>，in 等等）
                // query 用于组合查询条件（oder by、where）
                return null;
            }
        });
    }

    /**
     * 查询客户范围（in）
     * id > 大于
     * 地址 精确
     */
    @Test
    public void testR2() {
        List<Customer> cusotmerList = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // root.get("列名") 获取列
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // criteriaBuilder 设置条件
                // 参数 1：字段
                // 参数 2：值
                Predicate predicate = criteriaBuilder.equal(custAddress, "beiJing");

                return predicate;
            }
        });
        System.out.println(cusotmerList);
    }

    /**
     * 设置多个条件
     */
    @Test
    public void testR3() {
        List<Customer> cusotmerList = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // root.get("列名") 获取列
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // criteriaBuilder 设置条件
                Predicate custAddressP = criteriaBuilder.equal(custAddress, "beiJing");
                Predicate custIdP = criteriaBuilder.greaterThan(custId, 6L);
                CriteriaBuilder.In<String> in = criteriaBuilder.in(custName);
                in.value("团子").value("王五");

                Predicate and = criteriaBuilder.and(custAddressP, custIdP, in);

                return and;
            }
        });
        System.out.println(cusotmerList);
    }

    /**
     * 动态条件
     */
    @Test
    public void testR4() {
        Customer params = new Customer();
        // params.setCustAddress("beijing");
        params.setCustId(0L);
        params.setCustName("王五,团子");

        List<Customer> cusotmerList = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // root.get("列名") 获取列
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // criteriaBuilder 设置条件
                // 声明列表，用于存放条件，不能用数组，因为数组是定长的
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(params.getCustAddress())) {
                    list.add(criteriaBuilder.equal(custAddress, "beijing"));
                }
                if (params.getCustId() > -1) {
                    list.add(criteriaBuilder.greaterThan(custId, 0L));
                }
                if (!StringUtils.isEmpty(params.getCustName())) {
                    CriteriaBuilder.In<String> in = criteriaBuilder.in(custName);
                    in.value("团子").value("王五");
                    list.add(in);
                }

                Predicate and = criteriaBuilder.and(list.toArray(new Predicate[list.size()]));

                return and;
            }
        });
        System.out.println(cusotmerList);
    }

    /**
     * 支持排序，但是不支持分组
     */
    @Test
    public void testR5() {
        Customer params = new Customer();
        // params.setCustAddress("beijing");
        params.setCustId(0L);
        params.setCustName("王五,团子");

        List<Customer> cusotmerList = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // root.get("列名") 获取列
                Path<Long> custId = root.get("custId");
                Path<String> custName = root.get("custName");
                Path<String> custAddress = root.get("custAddress");

                // criteriaBuilder 设置条件
                // 声明列表，用于存放条件，不能用数组，因为数组是定长的
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(params.getCustAddress())) {
                    list.add(criteriaBuilder.equal(custAddress, "beijing"));
                }
                if (params.getCustId() > -1) {
                    list.add(criteriaBuilder.greaterThan(custId, 0L));
                }
                if (!StringUtils.isEmpty(params.getCustName())) {
                    CriteriaBuilder.In<String> in = criteriaBuilder.in(custName);
                    in.value("团子").value("王五");
                    list.add(in);
                }

                Predicate and = criteriaBuilder.and(list.toArray(new Predicate[list.size()]));

                Order desc = criteriaBuilder.desc(custId);

                Predicate predicate = query.where(and).orderBy(desc).getRestriction();

                return predicate;
            }
        });
        System.out.println(cusotmerList);
    }

    /**
     * 如果想要实现分组，需要注入 EntityManager
     */
    @Test
    public void testR6() {
        // 以下三个参数，相当于 toPredicate 方法的 root, query, criteriaBuilder
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = criteriaBuilder.createQuery();
        Root<Customer> root = query.from(Customer.class);
    }
}
