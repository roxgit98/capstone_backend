package davidegiliberti.capstone_backend.exceptions;

import davidegiliberti.capstone_backend.payloads.ErrorsRespDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsRespDTO handleBadRequest(BadRequestException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsRespDTO handleNotFound(NotFoundException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsRespDTO handleGenericErrors(Exception ex) {
        ex.printStackTrace();
        return new ErrorsRespDTO("C'è un problema nel server", LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsRespDTO handleNotFound(UnauthorizedException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsRespDTO handleForbidden(AuthorizationDeniedException ex) {
        return new ErrorsRespDTO("ERRORE, non hai i permessi per accedere", LocalDateTime.now());
    }
}
