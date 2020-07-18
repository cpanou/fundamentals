package gr.haec.employees.registry.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorMapper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CompanyNotFoundException.class, DepartmentNotFoundException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        Error error = new Error("Generic Error",300);
        if( ex instanceof CompanyNotFoundException)
            error = new Error("Company not Found", 1);
        if( ex instanceof DepartmentNotFoundException)
            error = new Error("Department not Found", 2);
        return handleExceptionInternal(ex, error,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
