package com.server.api.ebank.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "card")
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cardNumber;
    private String cardHolderName;
    private LocalDate expirationDate;

    @Column(nullable = false)
    private boolean isBlocked;

    private boolean onlinePayment;
    private boolean internationalPayment;
    private boolean bypassed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_card_customer_id"), nullable = false)
    private Customer customer;
}
