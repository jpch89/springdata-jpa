package tech.tuanzi.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;
import tech.tuanzi.pojo.Customer;

import java.util.List;

/**
 * @author Patrick Ji
 */
public class HibernateTest {
    // 数据库的会话工厂
    private SessionFactory sf;

    @Before
    public void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        // 根据服务注册类创建一个元数据资源集，同时构建元数据，并生成应用唯一的 session 工厂。
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Test
    public void testC() {
        // 通过 session 可以进行持久化操作
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Customer customer = new Customer();
            customer.setCustName("团子");
            session.save(customer);
            tx.commit();
        }
    }

    @Test
    public void testR() {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Customer customer = session.find(Customer.class, 1L);
            System.out.println("==========");
            System.out.println(customer);
            tx.commit();
        }
    }

    @Test
    public void testRLazy() {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Customer customer = session.load(Customer.class, 1L);
            System.out.println("==========");
            System.out.println(customer);
            tx.commit();
        }
    }

    @Test
    public void testU1() {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Customer customer = new Customer();
            customer.setCustId(1L);
            customer.setCustAddress("火星");
            // update 更新
            // 注意：没有设置的字段会更新为 null
            session.update(customer);
            tx.commit();
        }
    }

    @Test
    public void testU2() {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Customer customer = new Customer();
            customer.setCustId(1L);
            customer.setCustName("圆子");
            // 如果设置了 id，就更新
            // 如果没有设置 id，就创建
            session.saveOrUpdate(customer);
            tx.commit();
        }
    }

    @Test
    public void testD() {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Customer customer = new Customer();
            customer.setCustId(2L);
            session.remove(customer);
            tx.commit();
        }
    }

    @Test
    public void testRHql1() {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            String hql = "FROM Customer";
            List<Customer> resultList = session.createQuery(hql, Customer.class).getResultList();
            System.out.println(resultList);
            tx.commit();
        }
    }

    @Test
    public void testRHql2() {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            // 如果写成这样，会报错
            // Legacy-style query parameters (`?`) are no longer supported; use JPA-style ordinal parameters (e.g., `?1`) instead
            // String hql = "FROM Customer WHERE custId=?";
            // 如果写成 ?0，会有红色波浪线提示：Query parameters are numerated starting from 1
            String hql = "FROM Customer WHERE custId=?1";
            List<Customer> resultList = session
                    .createQuery(hql, Customer.class)
                    .setParameter(1, 1L)
                    .getResultList();
            System.out.println(resultList);
            tx.commit();
        }
    }

    @Test
    public void testRHql3() {
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            String hql = "FROM Customer WHERE custId=:id";
            List<Customer> resultList = session
                    .createQuery(hql, Customer.class)
                    .setParameter("id", 1L)
                    .getResultList();
            System.out.println(resultList);
            tx.commit();
        }
    }
}
