package com.example.dbtest.repositories;

import com.example.dbtest.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findByModel(String model);
    void deleteByModel(String model);
}
