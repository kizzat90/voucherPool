package com.boost.voucherpool;

import com.boost.voucherpool.model.SpecialOffer;
import com.boost.voucherpool.util.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CreateNewSpecialOfferTest {
    /**
     * mvn test -Dtest=CreateNewSpecialOfferTest
     * -Dhost= -Dport= -Dloop=
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void createNewSpecialOffer() throws URISyntaxException, IOException {
        String host = System.getProperty("host") != null ? System.getProperty("host") : "localhost";
        String pt = System.getProperty("port") != null ? System.getProperty("port") : "8080";
        int port = Integer.parseInt(pt);
        Random random = new Random();
        int loop = System.getProperty("loop") != null ? Integer.parseInt(System.getProperty("loop")) : random.nextInt(20);

        for (int i = 0; i < loop; i++) {
            String email = RandomStringUtils.randomAlphanumeric(10) + randomEmail();
            String name = "[CreateSpecialOffer]" + RandomStringUtils.randomAlphabetic(10);
            String expDate = randomTime();
            String soName = "PROMO" + RandomStringUtils.randomNumeric(3);
            int soDiscount = random.nextInt(10);
            this.createNewRecipient(email, name, host, port);

            SpecialOffer specialOffer = new SpecialOffer();
            specialOffer.setName(soName);
            specialOffer.setFixedPercentageDiscount(soDiscount);

            // Convert Java Objects to JSON
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(specialOffer);

            // Create URI
            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder();
            builder.setScheme("http").setHost(host).setPort(port)
                    .setPath("/VoucherPool/main/newSpecialOffer")
                    .setParameter("email", email)
                    .setParameter("expDate", expDate);
            URI uri = builder.build();

            HttpPost httpPost = new HttpPost(uri);

            // Set HTTP properties
            StringEntity entity = new StringEntity(jsonBody);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // Send to the web service end point
            CloseableHttpResponse response = client.execute(httpPost);
            String json = Utilities.getInstance().getJsonOutputFromResponse(response);
            Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
            System.out.println(json);

            client.close();
        }

    }

    private void createNewRecipient(String email, String name, String host, int port) throws URISyntaxException, IOException {

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
        client.execute(httpPost).close();

    }

    private String randomTime() {
        Random random = new Random();
        int currentInt = random.nextInt(14);
        LocalDateTime newTime = LocalDateTime.now().plusDays(currentInt);
        Date date = newTime.toDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
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
