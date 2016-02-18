package org.tus.icfp2013.client;

import org.junit.Test;

/**
 *
 */
public class TrainRequestTest {

    @Test
    public void trainRequest() {
        TrainRequest smallSizeReq = new TrainRequest(3, new String[] { null });
        System.out.println(smallSizeReq.toJson());

        smallSizeReq = new TrainRequest(4, new String[] { "fold" });
        System.out.println(smallSizeReq.toJson());
    }
}
