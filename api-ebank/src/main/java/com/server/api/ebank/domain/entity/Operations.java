package com.server.api.ebank.domain.entity;

import com.server.api.ebank.domain.enums.OperationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "operations")
@NoArgsConstructor
@AllArgsConstructor
public class Operations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType type;

    @Column(nullable = false)
    private BigDecimal amount;

    private boolean favorite;
    private String description;
    private String libel;
    private LocalDateTime operationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_operation_account_id"))
    private Account account;

    @PrePersist
    public void prePersist() {
        operationDate = LocalDateTime.now();
    }
}
