package org.example.orders;

import lombok.*;

@Data
public class Order {
    private String firstName;
    private String lastName;
    private String adress;
    private Integer metroStation; // приходит String, отправляем Integer
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private String[] colour;
    private Integer id;
    private Integer track;
    private Integer status;
    private Boolean cancelled;
    private Boolean finished;
    private Boolean inDelivey;
    private String courierFirstName;
    private String createdAt;
    private String updateAt;
}
