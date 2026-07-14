package com.abia.fraudauth.transaction;

import com.abia.fraudauth.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private boolean flagged;

    @Column(nullable = false)
    private int riskScore;

    private String riskReason;

    public Long getId() { return id; }
    public User getUser() { return user; }
    public BigDecimal getAmount() { return amount; }
    public String getCountry() { return country; }
    public Instant getCreatedAt() { return createdAt; }
    public boolean isFlagged() { return flagged; }
    public int getRiskScore() { return riskScore; }
    public String getRiskReason() { return riskReason; }

    public void setUser(User user) { this.user = user; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setCountry(String country) { this.country = country; }
    public void setFlagged(boolean flagged) { this.flagged = flagged; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
    public void setRiskReason(String riskReason) { this.riskReason = riskReason; }
}
