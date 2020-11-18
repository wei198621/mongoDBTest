package com.tiza.leo.springBootMongo.entity;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
/**
 * @author leowei
 * @date 2020/11/19  - 0:19
 */

@Document(collection="t_order")
public class Order {
    @Id
    @Indexed
    private ObjectId id;
    @Field("order_num")
    private String orderNum;
    private Date createDate;
    // 映射忽略的字段，该字段不会保存到mongodb。
    @Transient
    private String cc;

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public String getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Order(ObjectId id, String orderNum, Date createDate) {
        super();
        this.id = id;
        this.orderNum = orderNum;
        this.createDate = createDate;
    }
    public Order() {
        super();
    }
    @Override
    public String toString() {
        return "Order [id=" + id + ", orderNum=" + orderNum + ", createDate="
                + createDate + "]";
    }

}
