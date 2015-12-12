package com.allega.nomad.service.rest.app.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by imnotpro on 7/8/15.
 */
public class Errors {

    private Map<String, List<String>> errors = new HashMap<>();

    public String getFirstError() {
        for (Map.Entry<String, List<String>> entry : errors.entrySet()) {
            return entry.getValue().get(0);
        }
        return null;
    }
}
