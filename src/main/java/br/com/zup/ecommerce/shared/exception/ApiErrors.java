package br.com.zup.ecommerce.shared.exception;

import java.util.ArrayList;
import java.util.List;

public class ApiErrors {
    private List<String> globalErrors = new ArrayList<>();
    private List<FieldErrorResponse> fieldErrors = new ArrayList<>();

    public void addGlobalErrors(String message){
        globalErrors.add(message);
    }

    public void addFieldError(String field, String message){
        FieldErrorResponse response = new FieldErrorResponse(field, message);
        fieldErrors.add(response);
    }

    public List<String> getGlobalErrors() {
        return globalErrors;
    }

    public List<FieldErrorResponse> getFieldErrors() {
        return fieldErrors;
    }

    public int getnumberOfErrors(){
        return globalErrors.size() + fieldErrors.size();
    }
}
