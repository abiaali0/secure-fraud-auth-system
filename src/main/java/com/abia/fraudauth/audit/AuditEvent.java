package com.abia.fraudauth.audit;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "audit_events")
public class AuditEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String actor;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false, length = 1000)
    private String details;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public String getActor() { return actor; }
    public String getAction() { return action; }
    public String getDetails() { return details; }
    public Instant getCreatedAt() { return createdAt; }

    public void setActor(String actor) { this.actor = actor; }
    public void setAction(String action) { this.action = action; }
    public void setDetails(String details) { this.details = details; }
}
