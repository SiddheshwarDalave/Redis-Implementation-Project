package com.example.redisImpl.RedisImplementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    RedisTemplate<String, User> redisTemplate;

    @Autowired
    ObjectMapper objectMapper; //for map we use


    @PostMapping("/add-value")
        public void addValue(@RequestParam("key") String key,
                             @RequestBody() User user){

        redisTemplate.opsForValue().set(key,user);

        }

    @GetMapping("/get-value")
        public User getValue(@RequestParam("key") String key){

        return redisTemplate.opsForValue().get(key);
    }

    @PostMapping("/lpush")
    public void lpush(@RequestParam("key") String key, @RequestBody User user){

        Long listLength = redisTemplate.opsForList().leftPush(key, user);

    }

    @PostMapping("/rpush")
    //public void rpush(@RequestParam("key") String key, @RequestBody User user){
    public void rpush(@RequestParam("key") String key, @RequestBody() User user){
     Long listLength=redisTemplate.opsForList().rightPush(key, user);

        //Long listLengt = redisTemplate.opsForList().rightPush(key, user);

    }

    @GetMapping("/get-list")
        public List<User> getLpush(@RequestParam("key") String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }

    @GetMapping("/lpop")
    public User lpop(@RequestParam("key") String key){

        User user = (User)redisTemplate.opsForList().leftPop(key);

        return user;
    }

    @GetMapping("/rpop")
    public User rpop(@RequestParam("key") String key){

        User user = (User)redisTemplate.opsForList().rightPop(key);

        return user;
    }

    @GetMapping("/lrange")
    public List<User> lrange(@RequestParam("key") String key,
                             @RequestParam("start") int start,
                             @RequestParam("end") int end){

        return redisTemplate.opsForList().range(key, start, end);
//        return retrievedItems.stream().map(element -> (User) element).collect(Collectors.toList());
    }

    @PostMapping("/hashset")
        public void setHashValue(@RequestParam("key") String key, @RequestBody() User user){
            Map map=objectMapper.convertValue(user, Map.class);
            redisTemplate.opsForHash().putAll(key,map);
    }
    @GetMapping("/hashgetall")
    public User hashgetAll(@RequestParam("key") String key){

        Map map = redisTemplate.opsForHash().entries(key);
        return objectMapper.convertValue(map, User.class);
    }
}
