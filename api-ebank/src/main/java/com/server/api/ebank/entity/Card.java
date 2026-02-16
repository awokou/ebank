package com.server.api.ebank.entity;

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
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    private boolean isEnabled;
    private boolean onlinePayment;
    private boolean internationalPayment;
    private boolean bypassed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_card_customer_id"), nullable = false)
    private Customer customer;

    /**
     * Method to set the expirationDate timestamp before persisting the entity.
     */
    @PrePersist
    public void prePersist() {
        if (expirationDate == null) {
            expirationDate = LocalDate.now();
        }
    }
}
