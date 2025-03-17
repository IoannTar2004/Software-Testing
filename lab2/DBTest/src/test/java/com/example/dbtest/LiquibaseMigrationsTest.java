package com.example.dbtest;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseMigrationsTest {
    static final String DOCKER_IMAGE = "postgres:16";
    static Liquibase liquibase;
    static Connection connection;

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DOCKER_IMAGE);

    @BeforeAll
    static void setUp() throws Exception{
        container.start();

        connection = DriverManager.getConnection(
                container.getJdbcUrl(), container.getUsername(), container.getPassword());
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
        liquibase = new Liquibase("db/db.changelog-master.xml",
                new ClassLoaderResourceAccessor(), database);
    }

    @AfterAll
    static void containerClose() throws SQLException {
        connection.close();
        container.close();
    }

    @Test
    void migrationsTest() throws LiquibaseException {
        liquibase.update(); // Применяем миграции
//        liquibase.rollback(1, null);
    }

}
