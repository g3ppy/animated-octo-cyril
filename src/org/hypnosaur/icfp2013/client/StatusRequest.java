/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hypnosaur.icfp2013.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
public class StatusRequest {

    public StatusRequest() {
    }

    public String toJson() {
        JsonObject status = new JsonObject();
        Gson gson = new Gson();
        return gson.toJson(status);
    }

}
