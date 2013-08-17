package org.hypnosaur.icfp2013.client;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Request for POST /train.
 */
public class TrainRequest {

    private final int size;
    private final String[] operators;

    public TrainRequest(int size, String[] operators) {
        Preconditions.checkNotNull(size);
        Preconditions.checkArgument(size >= 3 && size <= 30,
                "The size of the problem requested should be in the range [3,30]");

        Preconditions.checkNotNull(operators);
        Preconditions.checkArgument(operators.length == 1);
        Preconditions.checkArgument(
                Strings.isNullOrEmpty(operators[0]) ||
                        "tfold".equals(operators[0]) ||
                        "fold".equals(operators[0]), "operators is either [], [\"tfold\"], or [\"fold\"]");

        this.size = size;
        this.operators = operators;
    }

    public String toJson() {
        Gson gson = new Gson();

        JsonObject train = new JsonObject();
        train.addProperty("size", size);

        if (operators[0] == null)
            train.add("operators", new JsonArray());
        else
            train.add("operators", gson.toJsonTree(operators));

        return gson.toJson(train);

    }
}
