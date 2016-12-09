package com.pes.enfaixapp.Controllers;

import org.json.JSONObject;

/**
 * Created by eduard on 5/11/16.
 */
public interface AsyncResult {
    void processFinish(JSONObject output);
}
