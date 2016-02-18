package org.tus.icfp2013.client;

import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Makes POST requests to the web service.
 */
public class WebServiceClient {

    public static final String URL = "http://icfpc2013.cloudapp.net/%s?auth=%svpsH1H";
    public static final int WAIT = 20000;

    private final String secret;

    public WebServiceClient(String secret) {
        this.secret = secret;
    }

    public String call(String method, String data) throws IOException {
        Preconditions.checkNotNull(method);
        Preconditions.checkNotNull(data);

        String urlString = String.format(URL, method, secret);
        HttpPost post = new HttpPost(urlString);
        post.setEntity(new StringEntity(data));

        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(post);
        int code = response.getStatusLine().getStatusCode();

        while (code == 429) {
            // wait and retry
            System.out.println(String.format("Received 429 error code. " +
                    "Will wait %d sec than retry the http request ...", WAIT));
            try {
                Thread.sleep(WAIT);
            } catch (InterruptedException e) {
                // ignored
            }

            httpClient = new DefaultHttpClient();
            response = httpClient.execute(post);
            code = response.getStatusLine().getStatusCode();
        }

        if (code == 200) {
            InputStream is = null;
            try {
                is = response.getEntity().getContent();
                return CharStreams.toString(new InputStreamReader(is));
            } finally {
                Closeables.close(is, false);
            }
        }

        throw new WebServiceException(code, response.getStatusLine().getReasonPhrase());
    }
}

