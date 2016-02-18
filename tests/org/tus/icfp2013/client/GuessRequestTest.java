package org.tus.icfp2013.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GuessRequestTest {

    @Test
    public void create() {
        GuessRequest guessRequest = new GuessRequest("u6ay1ACc7plUfwsaBWhqTkko", "(lambda (x) (not x))");

        assertEquals("{" +
                "\"id\":\"afa696afglajf696af\"," +
                "\"program\":\"(lambda (x) (plus x 1))\"}",
                guessRequest.toJson());
    }
}
