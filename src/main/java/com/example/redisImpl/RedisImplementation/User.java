package com.example.redisImpl.RedisImplementation;

import lombok.Data;

import java.io.Serializable;
@Data
public class User implements Serializable {
    int id;

    String name;

    String city;
}
