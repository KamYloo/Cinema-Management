package kamylo.CinemaBackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobaleException {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorDetail> AuthExceptionHandler(AuthException e, WebRequest req) {
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> UserExceptionHandler(UserException e, WebRequest req) {
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieException.class)
    public ResponseEntity<ErrorDetail> MovieExceptionHandler(MovieException e, WebRequest req) {
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShowTimeException.class)
    public ResponseEntity<ErrorDetail> ShowTimeExceptionHandler(ShowTimeException e, WebRequest req) {
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatException.class)
    public ResponseEntity<ErrorDetail> SeatExceptionHandler(SeatException e, WebRequest req) {
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ErrorDetail> ReservationExceptionHandler(ReservationException e, WebRequest req) {
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetail> handleNoHandlerFoundException(NoHandlerFoundException e, WebRequest req) {
        ErrorDetail errorDetail = new ErrorDetail("Endpoint not found", e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest req) {
        ErrorDetail errorDetail = new ErrorDetail(e.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
    }
}
