package tech.tuanzi.test;

import org.junit.Before;
import org.junit.Test;
import tech.tuanzi.pojo.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {
    EntityManagerFactory factory;

    @Before
    public void before() {
        // 第一个参数指定持久化单元名称，也就是 persistence.xml 中配置的 persistence-unit 的 name 值
        // 第二个参数可以接收一个 Map，用于设置 persistence.xml 中的配置项，这里由于所有配置已经写在配置文件中，就不采用这种方式了
        factory = Persistence.createEntityManagerFactory("hibernateJPA");
        // factory = Persistence.createEntityManagerFactory("openJPA");
    }

    @Test
    public void testC() {
        // JPA 的 entityManagerFactory，相当于 Hibernate 中的 sessionFactory
        // JPA 的 entityManager，相当于 Hibernate 中的 session
        // 注意：这里省略了事务的回滚操作
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer customer = new Customer();
        customer.setCustName("张三");
        em.persist(customer);
        tx.commit();
    }

    // 立即查询
    @Test
    public void testR() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer customer = em.find(Customer.class, 1L);
        System.out.println("==========");
        System.out.println(customer);
        /*
        Hibernate: select customer0_.cust_id as cust_id1_0_0_, customer0_.cust_address as cust_add2_0_0_, customer0_.cust_name as cust_nam3_0_0_ from tb_customer customer0_ where customer0_.cust_id=?
        ==========
        Customer{custId=1, custName='圆子', custAddress='null'}
        */
        tx.commit();
    }

    // 延迟查询
    @Test
    public void testRLazy() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer customer = em.getReference(Customer.class, 1L);
        System.out.println("==========");
        System.out.println(customer);
        /*
        ==========
        Hibernate: select customer0_.cust_id as cust_id1_0_0_, customer0_.cust_address as cust_add2_0_0_, customer0_.cust_name as cust_nam3_0_0_ from tb_customer customer0_ where customer0_.cust_id=?
        Customer{custId=1, custName='圆子', custAddress='null'}
        */
        tx.commit();
    }

    @Test
    public void testU() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer customer = new Customer();
        // 如果注释掉这句话，就会插入一条新数据
        // customer.setCustId(5L);
        customer.setCustName("王五");
        // 如果指定了主键，就会更新。更新时会先查询，看是否有变化，如果有则更新，如果没有变化，则不更新。
        // 如果没有指定主键，直接插入。
        // 如果不想让它先查再更新，可以使用 JPQL。
        em.merge(customer);
        tx.commit();
    }

    @Test
    public void testUJpql() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        String jpql = "UPDATE Customer SET custName=:name WHERE custId=:id";
        em.createQuery(jpql)
                .setParameter("name", "李四")
                .setParameter("id", 5L)
                .executeUpdate();
        tx.commit();
    }

    @Test
    public void testUSql() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // 这里要写数据库表名 tb_customer 和列名 cust_name、cust_id
        String sql = "UPDATE tb_customer SET cust_name=:name WHERE cust_id=:id";
        em.createNativeQuery(sql)
                .setParameter("name", "王五")
                .setParameter("id", 5L)
                .executeUpdate();
        tx.commit();
    }

    @Test
    public void testDFail() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer customer = new Customer();
        customer.setCustId(5L);
        em.remove(customer);
        tx.commit();
    }

    @Test
    public void testDSuccess() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer customer = em.find(Customer.class, 5L);
        em.remove(customer);
        tx.commit();
    }

    @Test
    public void testStatus() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // 临时状态
        Customer customer = new Customer();
        // 持久状态
        customer = em.find(Customer.class, 5L);
        // 删除状态
        em.remove(customer);
        tx.commit();
    }

    @Test
    public void testCache1() {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Customer customer1 = em.find(Customer.class, 1L);
        Customer customer2 = em.find(Customer.class, 1L);
        tx.commit();
    }

    @Test
    public void testCache2() {
        EntityManager em1 = factory.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();
        Customer customer1 = em1.find(Customer.class, 1L);
        tx1.commit();

        EntityManager em2 = factory.createEntityManager();
        EntityTransaction tx2 = em1.getTransaction();
        tx2.begin();
        Customer customer2 = em2.find(Customer.class, 1L);
        tx2.commit();
    }
}
