package com.example.dbtest;

import com.example.dbtest.controllers.AuthorizationController;
import com.example.dbtest.entities.Player;
import com.example.dbtest.repositories.PlayerRepository;
import jakarta.transaction.Transactional;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class ControllerIntegrationTest {
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
//        liquibase.rollback("1.0", new Contexts());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
        container.close();
    }

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    AuthorizationController authorizationController;

    final String DEFAULT_NAME = "Ivan";
    final String DEFAULT_PASSWORD = "123456";
    final String DEFAULT_PHONE = "+79168423875";

    @BeforeEach
    void initDB() {
        Player player = new Player(DEFAULT_NAME, DEFAULT_PASSWORD, DEFAULT_PHONE);
        playerRepository.save(player);
    }

    @Test
    @Transactional
    void loginTest() {
        String request = new JSONObject().put("name", DEFAULT_NAME).put("password", DEFAULT_PASSWORD).toString();
        JSONObject response = new JSONObject(authorizationController.login(request));
        assertEquals("ok", response.get("status"));
    }

    @Test
    @Transactional
    void loginWrongDataTest() {
        String request = new JSONObject()
                .put("name", "Vova")
                .put("password", DEFAULT_PASSWORD).toString();
        JSONObject response = new JSONObject(authorizationController.login(request));
        assertEquals("Неверный логин или пароль", response.get("status"));
    }

    @Test
    @Transactional
    void loginNonValidDataTest() {
        String request = new JSONObject()
                .put("name", "Vov")
                .put("password", DEFAULT_PASSWORD).toString();
        JSONObject response = new JSONObject(authorizationController.login(request));
        assertEquals("Произошла ошибка валидации", response.get("status"));

        String request1 = new JSONObject()
                .put("name", DEFAULT_NAME)
                .put("password", "12").toString();
        response = new JSONObject(authorizationController.login(request1));
        assertEquals("Произошла ошибка валидации", response.get("status"));
    }

    @Test
    @Transactional
    void registrationTest() {
        String request = new JSONObject()
                .put("name", "Vova")
                .put("password", DEFAULT_PASSWORD)
                .put("phone", DEFAULT_PHONE).toString();
        JSONObject response = new JSONObject(authorizationController.registration(request));
        assertEquals("ok", response.get("status"));
    }

    @Test
    @Transactional
    void registrationPlayerExistsTest() {
        String request = new JSONObject()
                .put("name", DEFAULT_NAME)
                .put("password", DEFAULT_PASSWORD)
                .put("phone", DEFAULT_PHONE).toString();
        JSONObject response = new JSONObject(authorizationController.registration(request));
        assertEquals("Игрок с таким ником уже существует", response.get("status"));
    }

    @Test
    @Transactional
    void registrationNonValidDataTest() {
        String request1 = new JSONObject()
                .put("name", "Vov")
                .put("password", "12")
                .put("phone", "+11").toString();
        JSONObject response = new JSONObject(authorizationController.login(request1));
        assertEquals("Произошла ошибка валидации", response.get("status"));
    }

    @Test
    void phonesAreNullTests() {
        boolean allNull = playerRepository.findAll().stream().allMatch(p -> p.getPhone() == null);
        assertTrue(allNull);
    }
}
