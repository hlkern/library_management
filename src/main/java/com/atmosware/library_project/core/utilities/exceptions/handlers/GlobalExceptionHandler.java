package com.atmosware.library_project.core.utilities.exceptions.handlers;

import com.atmosware.library_project.business.concretes.UserManager;
import com.atmosware.library_project.core.utilities.exceptions.problemDetails.BusinessProblemDetails;
import com.atmosware.library_project.core.utilities.exceptions.problemDetails.UnexpectedProblemDetails;
import com.atmosware.library_project.core.utilities.exceptions.problemDetails.ValidationProblemDetails;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BusinessProblemDetails handleBusinessException(BusinessException exception) {
        BusinessProblemDetails businessProblemDetails = new BusinessProblemDetails();
        businessProblemDetails.setDetail(exception.getMessage());

        logger.warn(String.format("Business exception: %s", exception.getMessage()), exception);

        return businessProblemDetails;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationProblemDetails handleValidationException(MethodArgumentNotValidException exception) {
        Map<String,String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().stream().map(error ->
                validationErrors.put(error.getField(),error.getDefaultMessage())
        ).toList();
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setErrors(validationErrors);

        logger.warn(String.format("Validation exception: %s", exception.getMessage()), exception);

        return validationProblemDetails;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UnexpectedProblemDetails handleBusinessException(Exception exception) {
        UnexpectedProblemDetails unexpectedProblemDetails = new UnexpectedProblemDetails();
        unexpectedProblemDetails.setDetail(exception.getMessage());

        logger.warn(String.format("Unexpected exception: %s", exception.getMessage()), exception);

        return unexpectedProblemDetails;
    }
}