package com.project.demo.repository;

import com.project.demo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO event (timestamp, event_number) VALUES (?1, ?2)", nativeQuery = true)
    void insertRaw(Timestamp timestamp, int eventNumber);
}

