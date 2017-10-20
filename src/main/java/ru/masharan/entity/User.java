package ru.masharan.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "test_user_data_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;
}
