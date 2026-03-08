package com.server.api.ebank.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.api.ebank.domain.enums.OperationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "operations")
@NoArgsConstructor
@AllArgsConstructor
public class Operations implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operationDate;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "favorite")
    private boolean favorite;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_operation_account_id"))
    private Account account;

    private String libel;

    /**
     * Method to set the creation timestamp before persisting the entity.
     */
    @PrePersist
    public void prePersist() {
        if (operationDate == null) {
            operationDate = LocalDateTime.now();
        }
    }
}
