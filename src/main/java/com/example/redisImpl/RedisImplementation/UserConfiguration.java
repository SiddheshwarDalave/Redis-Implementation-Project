package com.example.redisImpl.RedisImplementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class UserConfiguration {
    //3 imp beans
    //1. connection factory\
    @Bean
    LettuceConnectionFactory getFactory(){

        //object
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();

        LettuceConnectionFactory lettuceConnectionFactory =new LettuceConnectionFactory(redisStandaloneConfiguration);

        return lettuceConnectionFactory;
    }
    //2. redis template
    @Bean
    RedisTemplate<String, User> getTemplate(){


        RedisTemplate<String, User> redisTemplate =new RedisTemplate<>();

        RedisSerializer<String> stringRedisSerializer=new StringRedisSerializer();
        //key
        redisTemplate.setKeySerializer(stringRedisSerializer);

        JdkSerializationRedisSerializer jdkSerializationRedisSerializer= new JdkSerializationRedisSerializer();
        //value
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        //hashvalueserializer
        redisTemplate.setHashKeySerializer(jdkSerializationRedisSerializer);

        redisTemplate.setConnectionFactory(getFactory());

        return  redisTemplate;
        
    }
    //3. map or object mapper
    @Bean
    ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

}
