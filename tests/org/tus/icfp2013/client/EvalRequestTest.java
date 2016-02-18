package org.tus.icfp2013.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EvalRequestTest {

    @Test
    public void RequestWithId() {
        EvalRequest evalRequest = EvalRequest.evalProgramId(
                "3", new String[] {"0x00000000000001", "0xEFFFFFFFFFFFFF"});

        assertEquals("{" +
                "\"id\":\"3\"," +
                "\"arguments\":[\"0x00000000000001\",\"0xEFFFFFFFFFFFFF\"]}",
                evalRequest.toJson());
    }

    @Test
    public void RequestWithProgram() {
        EvalRequest evalRequest = EvalRequest.evalProgramCode(
                "(lambda (x) (shl1 x))", new String[] {"0x00000000000001", "0xEFFFFFFFFFFFFF"});

        assertEquals("{" +
                "\"program\":\"(lambda (x) (shl1 x))\"," +
                "\"arguments\":[\"0x00000000000001\",\"0xEFFFFFFFFFFFFF\"]}",
                evalRequest.toJson());
    }
}
