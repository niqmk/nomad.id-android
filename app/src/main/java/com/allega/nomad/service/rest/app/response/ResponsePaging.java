package com.allega.nomad.service.rest.app.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by imnotpro on 5/30/15.
 */
public class ResponsePaging<T> {

    public static final int STATUS_OK = 1;

    @Expose
    private String name;
    @Expose
    private String message;
    @SerializedName("system_message")
    @Expose
    private String systemMessage;
    @SerializedName("debug_message")
    @Expose
    private String debugMessage;
    @Expose
    private int code;
    @Expose
    private long status;
    @Expose
    private Paging<T> results;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public Paging<T> getResults() {
        return results;
    }

    public void setResults(Paging<T> results) {
        this.results = results;
    }
}
