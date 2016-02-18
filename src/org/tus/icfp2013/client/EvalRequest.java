package org.tus.icfp2013.client;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * An evaluation request for POST /eval.
 */
public class EvalRequest {

    private final Optional<String> id;
    private final Optional<String> program;
    private final String[] arguments;

    private EvalRequest(Optional<String> id, Optional<String> program, String[] arguments) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(program);
        Preconditions.checkNotNull(arguments);

        if (id.isPresent())
            Preconditions.checkArgument(!program.isPresent(),
                    "If programID is present then program should be absent");

        if (program.isPresent())
            Preconditions.checkArgument(!id.isPresent(),
                    "If program is present then programID should be absent");

        this.id = id;
        this.program = program;
        this.arguments = arguments;
    }

    public String toJson() {
        Gson gson = new Gson();
        JsonObject request = new JsonObject();

        if (id.isPresent())
            request.addProperty("id", id.get());
        else
            request.addProperty("program", program.get());

        request.add("arguments", gson.toJsonTree(arguments));

        return gson.toJson(request);
    }


    public static EvalRequest evalProgramId(String id, String[] arguments) {
        return new EvalRequest(Optional.of(id), Optional.<String>absent(), arguments);
    }

    public static EvalRequest evalProgramCode(String program, String[] arguments) {
        return new EvalRequest(Optional.<String>absent(), Optional.of(program), arguments);
    }
}
