package org.tus.icfp2013.client;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Request for POST /guess.
 */
public class GuessRequest {

    private final String id;
    private final String program;

    public GuessRequest(String id, String program) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(program);

        this.id = id;
        this.program = program;
    }

    public String toJson() {
        JsonObject guess = new JsonObject();
        guess.addProperty("id", id);
        guess.addProperty("program", program);

        Gson gson = new Gson();
        return gson.toJson(guess);
    }
}
