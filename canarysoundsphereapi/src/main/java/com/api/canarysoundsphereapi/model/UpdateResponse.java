package com.api.canarysoundsphereapi.model;

// Clase UpdateResponse sirve para enviar una respuesta con una string y un object q le pasemos por parametros en los endpoints de update
public class UpdateResponse<T> {
    private String message;
    private T object;

    public UpdateResponse(String message, T object) {
        this.message = message;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
