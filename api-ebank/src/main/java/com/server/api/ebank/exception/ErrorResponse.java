package com.server.api.ebank.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Classe représentant la structure d'une réponse d'erreur API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * UTC timestamp
     */
    private LocalDateTime timestamp;

    /**
     * Code HTTP
     */
    private int status;

    /**
     * Détails des erreurs de validation
     */
    private String error;

    /**
     * Message
     */
    private String message;

    /**
     * URL de la ressource ou endpoint
     */
    private String path;
}
