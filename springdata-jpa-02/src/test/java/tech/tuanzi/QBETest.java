package tech.tuanzi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tech.tuanzi.config.SpringDataJPAConfig;
import tech.tuanzi.pojo.Customer;
import tech.tuanzi.repositories.CustomerQBERepository;

import java.util.List;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringRunner.class)
public class QBETest {
    @Autowired
    CustomerQBERepository repository;

    /**
     * 简单示例：按照客户名称、客户地址动态条件查询
     */
    @Test
    public void test01() {
        // 实体类，其实就是查询条件
        Customer customer = new Customer();
        customer.setCustName("王五");
        customer.setCustAddress("beijing");

        // 传入实体类，构建查询示例
        // 这里的 Example 全路径应为 org.springframework.data.domain
        // 而不能选 org.hibernate.criterion
        Example<Customer> example = Example.of(customer);

        List<Customer> list = (List<Customer>) repository.findAll(example);
        System.out.println(list);
    }

    /**
     * 通过匹配器限制条件
     */
    @Test
    public void test02() {
        Customer customer = new Customer();
        customer.setCustName("不存在");
        customer.setCustAddress("jing");

        ExampleMatcher matcher = ExampleMatcher.matching()
                // 忽略 custName 属性
                .withIgnorePaths("custName")
                // 设置忽略大小写
                .withIgnoreCase("custAddress")
                // 对所有字符串的条件进行结尾匹配，即 custName 和 custAddress 都生效
                // .withStringMatcher(ExampleMatcher.StringMatcher.ENDING);
                // 针对单个条件进行限制，要使用 withMatcher
                // .withMatcher("custAddress", m -> m.endsWith());
                // 这是采用方法引用的形式（由 IDEA 自动生成）
                // .withMatcher("custAddress", ExampleMatcher.GenericPropertyMatcher::endsWith);
                // 还可以采用链式调用，忽略大小写，这样上面就不用写 withIgnoreCase 了
                // 注意这里是 ExampleMatcher.GenericPropertyMatchers，后面有 s
                // 会使 withIgnoreCase 失效，需要在这里单独设置 .ignoreCase()
                .withMatcher("custAddress", ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase());

        Example<Customer> example = Example.of(customer, matcher);

        List<Customer> list = (List<Customer>) repository.findAll(example);
        System.out.println(list);
    }
}
