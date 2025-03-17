package com.example.dbtest;

import com.example.dbtest.entities.Car;
import com.example.dbtest.entities.Player;
import com.example.dbtest.repositories.CarRepository;
import com.example.dbtest.repositories.PlayerRepository;
import com.example.dbtest.services.PlayerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

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
    static void setUp() {
        container.start();
    }

    @AfterAll
    static void tearDown() {
        container.close();
    }

    @Test
    @Transactional
    void createAndReadTest() {
        Car car = new Car("Porsche 911 Turbo (996)");
        carRepository.save(car);
        Player player = new Player("Ivan", "123456");
        player.getCars().add(car);
        playerRepository.save(player);

        Player savedPlayer = playerRepository.findById(1L).orElseThrow();
        Car savedCar = carRepository.findById(1).orElseThrow();
        assertEquals("Ivan", savedPlayer.getName());
        assertEquals("Porsche 911 Turbo (996)", savedCar.getModel());
        assertEquals("Porsche 911 Turbo (996)", savedPlayer.getCars().get(0).getModel());
    }
}
