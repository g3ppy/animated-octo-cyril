package org.tus.icfp2013.client;

/**
 * Signals an error with communicating with the web service.
 */
public class WebServiceException extends RuntimeException {

    private final int code;

    public WebServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private String getDetailedMessage() {
        String message = getMessage();

        switch (code) {
            case 400:
                message = "400: Bad Request   (some input is not well-formed)";
                break;
            case 401:
                message = "401: Unauthorized  problem was not requested by the current user";
                break;
            case 403:
                message = "403: authorization required";
                break;
            case 404:
                message = "404: Not Found     no such challenge";
                break;
            case 410:
                message = "410: Gone          problem requested more than 5 minutes ago";
                break;
            case 412:
                message = "412:               problem was already solved (by current user)";
                break;
            case 413:
                message = "413:               request too big";
                break;
            case 429:
                message = "429: try again later";
        }

        return message;
    }

    @Override
    public String toString() {
        return "WebServiceException{" +
                "code=" + getCode() +
                " message=\"" + getDetailedMessage() + "\"" +
                '}';
    }
}
