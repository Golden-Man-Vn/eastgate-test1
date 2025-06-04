package com.project.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp timestamp;
    private Integer eventNumber;

    public Event(Timestamp timestamp, Integer eventNumber){
        this.timestamp = timestamp;
        this.eventNumber = eventNumber;
    }

    public Event() {

    }
}

