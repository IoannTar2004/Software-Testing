package com.example.dbtest;

import com.example.dbtest.entities.Car;
import com.example.dbtest.entities.Player;
import com.example.dbtest.repositories.CarRepository;
import com.example.dbtest.repositories.PlayerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class CRUDTest {

    static final String DOCKER_IMAGE = "postgres:16";

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DOCKER_IMAGE);

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

//    @Autowired
//    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private CarRepository carRepository;

    @BeforeAll
    static void setup() {
        container.start();
    }

    @AfterAll
    static void tearDown() {
        container.close();
    }

    @BeforeEach
    void initDB() {
        Car car = new Car("Porsche 911 Turbo (996)");
        Player player = new Player("Ivan", "123456");
        player.getCars().add(car);
        car.getPlayers().add(player);
        carRepository.save(car);
        playerRepository.save(player);
    }

    @Test
    @Transactional
    void readTest() {
        var car = carRepository.findByModel("Porsche 911 Turbo (996)").orElseThrow();
        Player savedPlayer = playerRepository.findByName("Ivan").orElseThrow();
        assertEquals("Porsche 911 Turbo (996)", savedPlayer.getCars().get(0).getModel());
    }


    @Test
    @Transactional
    void updateTest() {
        Player player = playerRepository.findByName("Ivan").orElseThrow();
        Car car = carRepository.findByModel("Porsche 911 Turbo (996)").orElseThrow();

        player.setName("Vova");
        car.setModel("Ford GT-40");
        playerRepository.save(player);
        carRepository.save(car);

        playerRepository.findByName("Vova").orElseThrow();
        Car savedCar = carRepository.findByModel("Ford GT-40").orElseThrow();
        assertEquals("Vova", savedCar.getPlayers().get(0).getName());
    }

    @Test
    @Transactional
    void deleteTest() {
        playerRepository.deleteByName("Ivan");
        carRepository.deleteByModel("Ford GT-40");

        assertThrows(NoSuchElementException.class, () -> playerRepository.findByName("Ivan").orElseThrow());
        assertThrows(NoSuchElementException.class, () -> carRepository.findByModel("Ford GT-40").orElseThrow());
    }
}
