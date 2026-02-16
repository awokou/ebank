package com.server.api.ebank.exception;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Classe représentant la structure d'une réponse d'erreur API.
 */
@Data
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

    /**
     * Constructeur de la classe ErrorResponse.
     *
     * @param status  Code HTTP de l'erreur
     * @param error   Détails des erreurs de validation
     * @param message Message d'erreur
     * @param path    URL de la ressource ou endpoint
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
