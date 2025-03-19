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

    @BeforeEach
    void initDB() {
        Player player = new Player(DEFAULT_NAME, DEFAULT_PASSWORD);
        playerRepository.save(player);
    }

    private String createResponse(String name, String password) {
        return new JSONObject().put("name", name).put("password", password).toString();
    }

    @Test
    @Transactional
    void loginTest() {
        String request = createResponse(DEFAULT_NAME, DEFAULT_PASSWORD);
        JSONObject response = new JSONObject(authorizationController.login(request));
        assertEquals("ok", response.get("status"));
    }

    @Test
    @Transactional
    void loginWrongDataTest() {
        String request = createResponse("Vova", DEFAULT_PASSWORD);
        JSONObject response = new JSONObject(authorizationController.login(request));
        assertEquals("Неверный логин или пароль", response.get("status"));
    }

    @Test
    @Transactional
    void loginNonValidDataTest() {
        String request = createResponse("Vov", DEFAULT_PASSWORD);
        JSONObject response = new JSONObject(authorizationController.login(request));
        assertEquals("Произошла ошибка валидации", response.get("status"));

        String request1 = createResponse(DEFAULT_NAME, "12");
        response = new JSONObject(authorizationController.login(request1));
        assertEquals("Произошла ошибка валидации", response.get("status"));
    }

    @Test
    @Transactional
    void registrationTest() {
        String request = createResponse("Vova", DEFAULT_PASSWORD);
        JSONObject response = new JSONObject(authorizationController.registration(request));
        assertEquals("ok", response.get("status"));
    }

    @Test
    @Transactional
    void registrationPlayerExistsTest() {
        String request = createResponse(DEFAULT_NAME, DEFAULT_PASSWORD);
        JSONObject response = new JSONObject(authorizationController.registration(request));
        assertEquals("Игрок с таким ником уже существует", response.get("status"));
    }

    @Test
    @Transactional
    void registrationNonValidDataTest() {
        String request = createResponse("Vov", DEFAULT_PASSWORD);
        JSONObject response = new JSONObject(authorizationController.registration(request));
        assertEquals("Произошла ошибка валидации", response.get("status"));

        String request1 = createResponse(DEFAULT_NAME, "12");
        response = new JSONObject(authorizationController.registration(request1));
        assertEquals("Произошла ошибка валидации", response.get("status"));
    }
}
