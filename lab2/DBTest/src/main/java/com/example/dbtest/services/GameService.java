package com.example.dbtest.services;

import com.example.dbtest.utils.Utils;
import lombok.Getter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class GameService {

    @Getter
    private final Map<Long, Boolean> loadedGames = new ConcurrentHashMap<>();

    @Async
    public CompletableFuture<String> createGame(long id) {
        long delay = Utils.getRandomDelay(1000, 4000);

        loadedGames.put(id, false);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> future = scheduler.schedule(() -> loadedGames.put(id, true),
                delay, TimeUnit.MILLISECONDS);

        while (!loadedGames.get(id)) {}

        future.cancel(false);
        scheduler.close();
        loadedGames.remove(id);
        return CompletableFuture.completedFuture("Игра создана!");
    }

    @Async
    public CompletableFuture<String> cancelCreatingGame(long id) {
        loadedGames.put(id, true);
        return CompletableFuture.completedFuture("Создание игры прервано!");
    }
}
