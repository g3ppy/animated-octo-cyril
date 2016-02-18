package org.tus.icfp2013.client;

import com.google.common.base.Preconditions;

import java.util.Arrays;

/**
 * The response from POST /eval.
 */
public class EvalResponse {

    private final String status;
    private final String[] outputs;
    private final String message;

    public EvalResponse(String status, String[] outputs, String message) {
        Preconditions.checkNotNull(status);
        Preconditions.checkArgument("ok".equals(status) || "error".equals(status),
                "Status is either \"ok\" or \"error\"");

        this.status = status;

        if ("error".equals(status)) {
            this.outputs = null;
            this.message = message;
        } else {
            this.outputs = outputs;
            this.message = "";
        }
    }

    public String getStatus() {
        return status;
    }

    public String[] getOutputs() {
        return outputs;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "EvalResponse{" +
                "status='" + getStatus() + '\'' +
                ", outputs=" + Arrays.toString(getOutputs()) +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
