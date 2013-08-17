package org.hypnosaur.icfp2013.client;

import com.google.common.base.Preconditions;

import java.util.Arrays;

/**
 * Response for POST /guess.
 */
public class GuessResponse {

    private final String status;
    private final String[] values;
    private final String message;
    private final Boolean lightning;

    public GuessResponse(String status, String[] values, String message, Boolean lightning) {
        Preconditions.checkNotNull(status);
        Preconditions.checkArgument("win".equals(status) || "mismatch".equals(status) || "error".equals(status),
                "Status is either \"win\", \"mismatch\" or \"error\"");

        this.status = status;
        this.values = values;
        this.message = message;
        this.lightning = lightning;
    }

    public String getStatus() {
        return status;
    }

    public String[] getValues() {
        return values;
    }

    public String getMessage() {
        return message;
    }

    public Boolean isLightning() {
        return (lightning == null) ? false : lightning;
    }

    @Override
    public String toString() {
        return "GuessResponse{" +
                "status='" + getStatus() + '\'' +
                ", values=" + Arrays.toString(getValues()) +
                ", message='" + getMessage() + '\'' +
                ", lightning=" + isLightning() +
                '}';
    }
}
