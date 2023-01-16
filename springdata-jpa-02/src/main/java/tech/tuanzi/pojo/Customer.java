package tech.tuanzi.pojo;

import javax.persistence.*;

/**
 * @author Patrick Ji
 */
@Entity  // 将本类作为 Hibernate 的实体类
@Table(name = "tb_customer")  // 映射的数据库表名
public class Customer {
    /**
     * @Id：声明主键的配置
     * @GeneratedValue：配置主键的生成策略，strategy 的取值有以下几种。
     * GenerationType.IDENTITY：自增，MySQL。底层数据库必须支持自动增长。
     * GenerationType.SEQUENCE：序列，Oracle。底层数据库必须支持序列。
     * GenerationType.TABLE：JPA 提供的一种机制，通过数据库表生成主键。
     * GenerationType.AUTO：由程序自动地帮我们选择主键生成策略。
     * @Column：配置属性和字段的映射关系，name 为数据库表中字段的名称。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId;  // 客户的主键
    @Column(name = "cust_name")
    private String custName;
    @Column(name = "cust_address")
    private String custAddress;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custAddress='" + custAddress + '\'' +
                '}';
    }
}
