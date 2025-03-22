package com.example.dbtest;

import com.example.dbtest.services.GameService;
import com.example.dbtest.services.PunishmentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
@EnableAsync
public class AsyncTest {

    @Autowired
    GameService gameService;
    @Autowired
    PunishmentService punishmentService;

    @BeforeEach
    void init() {
        punishmentService.getPunishmentMap().clear();
        punishmentService.setCancel(false);
    }

    @Test
    void loadGameTest() {
        for (int i = 0; i < 3; i++) {
            var future = gameService.createGame(0);
            await().atMost(5, TimeUnit.SECONDS).until(future::isDone);
        }
    }

    @ParameterizedTest
    @ValueSource(longs = {500, 0, 999})
    void loadGameWithCancelTest(long wait) throws InterruptedException {
        var future = gameService.createGame(0);
        Thread.sleep(wait);
        assertFalse(future.isDone());
        gameService.cancelCreatingGame(0);
        await().atMost(5, TimeUnit.SECONDS).until(() -> !gameService.getLoadedGames().containsKey(0L));
    }

    @Test
    void punishmentAsyncTest() throws InterruptedException {
        var incrementFuture = punishmentService.incrementTime();
        var removeFuture = punishmentService.removeFromPunishmentMap(10);
        assertTrue(punishmentService.getPunishmentMap().isEmpty());
        for (int i = 0; i < 10; i++) {
            punishmentService.addToPunishmentMap(String.valueOf(i));
            Thread.sleep(500);
        }

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertTrue(new ArrayList<>(punishmentService.getPunishmentMap().values())
                    .stream().anyMatch(time -> time > 0));
        });
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            assertTrue(punishmentService.getPunishmentMap().isEmpty());
        });

        punishmentService.setCancel(true);
        incrementFuture.join();
        removeFuture.join();
    }

    @Test
    void punishmentMapOverflowTest() {
        punishmentService.addToPunishmentMap("Ivan");
        var future = punishmentService.incrementTime();
        await().atMost(6, TimeUnit.SECONDS).untilAsserted(() -> {
            ExecutionException exception = assertThrows(ExecutionException.class, future::get);
            assertTrue(exception.getCause() instanceof IllegalStateException);
        });
    }
}
