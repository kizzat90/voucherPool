package com.boost.voucherpool;

import com.boost.voucherpool.model.VoucherCode;
import com.boost.voucherpool.util.Utilities;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class RedeemVoucherTest {
    /**
     * mvn test -Dtest=RedeemVoucherTest
     * -Dhost= -Dport=
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void redeemVouchers() throws IOException, URISyntaxException {
        String host = System.getProperty("host") != null ? System.getProperty("host") : "localhost";
        String pt = System.getProperty("port") != null ? System.getProperty("port") : "8080";
        int port = Integer.parseInt(pt);

        List<VoucherCode> allVoucherCodes = getAllVoucherCode(host, port);
        for (VoucherCode vc : allVoucherCodes) {
            // Create URI
            CloseableHttpClient client = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder();
            builder.setScheme("http").setHost(host).setPort(port)
                    .setPath("/VoucherPool/main/redeemVoucher")
                    .setParameter("voucherCode", vc.getCode())
                    .setParameter("email", vc.getRecipient().getEmail());
            URI uri = builder.build();

            HttpGet http = new HttpGet(uri);

            CloseableHttpResponse response = client.execute(http);
            String json = Utilities.getInstance().getJsonOutputFromResponse(response);
            Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
            System.out.println(json);

            client.close();
        }
    }

    private List<VoucherCode> getAllVoucherCode(String host, Integer port) throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(host).setPort(port).setPath("/VoucherPool/voucherCode/getAllVoucherCodeList");
        URI uri = builder.build();

        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpResponse response = client.execute(httpGet);

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        JsonNode jsonNode = Utilities.getInstance().retrieveResourceFromResponse(response, JsonNode.class);
        client.close();

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(mapper.treeAsTokens(jsonNode), new TypeReference<List<VoucherCode>>() {});
    }
}
