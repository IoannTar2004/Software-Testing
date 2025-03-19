package com.example.dbtest;

import com.example.dbtest.entities.Car;
import com.example.dbtest.entities.Player;
import com.example.dbtest.repositories.CarRepository;
import com.example.dbtest.repositories.PlayerRepository;
import jakarta.transaction.Transactional;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class CRUDTest {

    static final String DOCKER_IMAGE = "postgres:16";
    static Liquibase liquibase;
    static Connection connection;

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DOCKER_IMAGE);

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.liquibase.change-log", () -> "classpath:db/db.changelog-master.xml");
        registry.add("spring.liquibase.enabled", () -> true);
    }

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    CarRepository carRepository;

    @BeforeAll
    static void setup() throws Exception {
        container.start();

        connection = DriverManager.getConnection(
                container.getJdbcUrl(), container.getUsername(), container.getPassword());
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
        liquibase = new Liquibase("db/db.changelog-master.xml",
                new ClassLoaderResourceAccessor(), database);

        liquibase.update(new Contexts());  // Применяем миграции
        liquibase.rollback("1.0", new Contexts());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
        container.close();
    }

    @BeforeEach
    void initDB() {
        Car car = new Car("Porsche 911 Turbo (996)", 300, 92);
        Player player = new Player("Ivan", "123456", "+79168423875");
        player.getCars().add(car);
        car.getPlayers().add(player);
        carRepository.save(car);
        playerRepository.save(player);
    }

    @Test
    @Transactional
    void readTest() {
        Car car = carRepository.findByModel("Porsche 911 Turbo (996)").orElseThrow();
        Player player = playerRepository.findByName("Ivan").orElseThrow();
        assertEquals(300, car.getSpeed());
        assertEquals("Porsche 911 Turbo (996)", player.getCars().get(0).getModel());
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
