package ra.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.exception.LoginException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginFall(LoginException loginException){
        return new ResponseEntity<>(loginException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String>  loginFail(AccessDeniedException accessDeniedException){
        return new ResponseEntity<>("Ban khong du tu cach truy cap", HttpStatus.UNAUTHORIZED);
    }
}
