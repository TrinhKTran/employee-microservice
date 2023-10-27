package com.bootcamptoprod.embedded.mongo.repository;

import com.bootcamptoprod.embedded.mongo.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest()
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    @Order(1)
    @Test
    void testDb() {
        Assertions.assertThat(mongoTemplate.getDb()).isNotNull();
    }

    @Order(2)
    @Test
    void testSave() {
        User user = new User();
        user.setId("1");
        user.setName("Foo Bar");
        user.setEmail("foo.bar@gmail.com");

        User user2 = new User();
        user2.setId("2");
        user2.setName("Foo Bar");
        user2.setEmail("foo.bar@gmail.com");


        userRepository.save(user);
        userRepository.save(user2);

        User result = mongoTemplate.findById("1", User.class);

        assertEquals(result.getId(), user.getId());
        assertEquals(result.getEmail(), user.getEmail());
        assertEquals(result.getName(), user.getName());
    }

    @Order(3)
    @Test
    void testFindById() {
        Optional<User> userOptional = userRepository.findById("1");
        assertTrue(userOptional.isPresent());
        User result = userOptional.get();
        assertEquals("1", result.getId());
        assertEquals("Foo Bar", result.getName());
        assertEquals("foo.bar@gmail.com", result.getEmail());
    }

    @Order(4)
    @Test
    void testDeleteById() {
        userRepository.deleteById("1");
        assertFalse(userRepository.existsById("1"));
    }
}