package br.com.zup.ecommerce.shared.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = e.getBindingResult().getGlobalErrors();

        return buildApiErrors(fieldErrors, globalErrors);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors bindExceptionHandler(BindException e){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = e.getBindingResult().getGlobalErrors();

        return buildApiErrors(fieldErrors, globalErrors);
    }

    private ApiErrors buildApiErrors(List<FieldError> fieldErrors, List<ObjectError> globalErrors) {
        ApiErrors validationErrors = new ApiErrors();
        globalErrors.forEach(error -> validationErrors.addGlobalErrors(getErrorMessage(error)));
        fieldErrors.forEach(error -> {
            String message = getErrorMessage(error);
            validationErrors.addFieldError(error.getField(), message);
        });

        return validationErrors;
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
