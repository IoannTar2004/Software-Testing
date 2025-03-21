package com.example.dbtest.services;

import lombok.Data;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Data
public class PunishmentService {

    private final Map<String, Integer> punishmentMap = new ConcurrentHashMap<>();
    private AtomicBoolean cancel = new AtomicBoolean(false);

    @Async
    public CompletableFuture<Void> addToPunishmentMap(String playerName) {
        punishmentMap.put(playerName, 0);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> incrementTime() {
        while (!cancel.get()) {
            Set<String> playerSet = punishmentMap.keySet();
            for (String playerName : playerSet) {
                int time = punishmentMap.get(playerName);
                if (playerName.equals("Ivan") && time > 5)
                    throw new IllegalStateException();

                punishmentMap.put(playerName, time + 1);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> removeFromPunishmentMap(int maxTime) {
        while (!cancel.get()) {
            Set<String> playerSet = punishmentMap.keySet();
            for (String playerName : playerSet) {
                int time = punishmentMap.get(playerName);
                if (time >= maxTime)
                    punishmentMap.remove(playerName);
            }
        }

        return CompletableFuture.completedFuture(null);
    }

    public void setCancel(boolean cancel) {
        this.cancel.set(cancel);
    }
}
