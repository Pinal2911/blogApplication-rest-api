package com.springboot.blog.springbootblogrestapi.exception;


import com.springboot.blog.springbootblogrestapi.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

//annotations for custom exception handling
//ExceptionHandler - it is the annotation used to handle specific exceptions and sending the custom responses to the client
//ControllerAdvice - it is annotation to handle the exceptions globally
@ControllerAdvice
public class GlobalExceptionHandler {
//handling customized exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BlogAPiException.class)
    public ResponseEntity<ErrorDetails> handleBlogApiException(BlogAPiException exception,WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }
//handling global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handhleGlobalException(Exception exception,WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //handling customized messages for validation of arguments
    //eg : if title is less than 2 chr so instead of default exception msg customized exception message will be thrown

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest){
        Map<String,String> errors=new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)-> {
            String fieldName=((FieldError)error).getField();
            String messsage=error.getDefaultMessage();
            errors.put(fieldName,messsage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
