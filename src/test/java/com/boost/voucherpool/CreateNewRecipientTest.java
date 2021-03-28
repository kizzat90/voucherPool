package com.boost.voucherpool;

import com.boost.voucherpool.util.Utilities;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

public class CreateNewRecipientTest {
    /**
     * mvn test -Dtest=CreateNewRecipientTest -Dloop=
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void createNewRecipient() throws URISyntaxException, IOException {
        String host = "localhost";
        int port = 8080;
        Random random = new Random();
        int loop = System.getProperty("loop") != null ? Integer.parseInt(System.getProperty("loop")) : random.nextInt(20);

        for (int i = 0; i < loop; i++) {
            String email = RandomStringUtils.randomAlphanumeric(10) + randomEmail();
            String name = "[CreateRecipient]" + RandomStringUtils.randomAlphabetic(10);

            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder();
            uriBuilder.setScheme("http").setHost(host).setPort(port).setPath("/VoucherPool/recipient/newRecipient")
                    .setParameter("email", email).setParameter("name", name);
            URI uri = uriBuilder.build();

            HttpPost httpPost = new HttpPost(uri);

            // Set HTTP properties
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // Send to the web service end point
            CloseableHttpResponse response = client.execute(httpPost);
            String json = Utilities.getInstance().getJsonOutputFromResponse(response);
            Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

            client.close();
        }
    }

    private String randomEmail() {
        Random random = new Random();
        int number = random.nextInt(3);

        if (number == 1) {
            return "@gmail.com";
        } else if (number == 2) {
            return "@yahoo.com";
        } else {
            return "@hotmail.com";
        }
    }

}
