package org.dev.productservice.domain.Base;

import java.util.List;

public class ResponseDTO<T> {
    private String Error;
    private T Data;
    private boolean Success;
    private List<String> Messages;

    public ResponseDTO(String error, T data, boolean success, List<String> messages) {
        this.Error = error;
        this.Data = data;
        this.Success = success;
        this.Messages = messages;
    }

    public T getData() {
        return Data;
    }

    public String getError() {
        return Error;
    }

    public boolean getSuccess() {
        return Success;
    }

    public List<String> getMessages() {
        return Messages;
    }
}
