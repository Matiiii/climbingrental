package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.service.PayUService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class PayUServiceImpl implements PayUService {

    private String token = getToken().readEntity(String.class).substring(17, 53);

    @Override
    public Response getToken() {
        Client client = ClientBuilder.newClient();
        //Entity<String> payload = Entity.text("grant_type=trusted_merchant&#38;client_id=145227&#38;client_secret=12f071174cb7eb79d4aac5bc2f07563f&#38;email=testaccount1%40devil.allegro.pl&#38;ext_customer_id=extCustId6667");
        //Entity<String> payload = Entity.text("grant_type=client_credentials&#38;client_id=145227&#38;client_secret=12f071174cb7eb79d4aac5bc2f07563f&#38;email=testaccount1%40devil.allegro.pl&#38;ext_customer_id=extCustId6667");
        Entity<String> payload = Entity.text("grant_type=client_credentials&client_id=145227&client_secret=12f071174cb7eb79d4aac5bc2f07563f");

        Response response = client.target("https://secure.payu.com/pl/standard/user/oauth/authorize?grant_type=client_credentials&client_id=145227&client_secret=12f071174cb7eb79d4aac5bc2f07563f")
                        .request(MediaType.APPLICATION_JSON)
                        .post(payload);


      /*  Response response = client.target("https://private-anon-c8d7694ad3-payu21.apiary-mock.com/pl/standard/user/oauth/authorize ")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .post(payload);*/

        System.out.println("status: " + response.getStatus());
        System.out.println("headers: " + response.getHeaders());
        //System.out.println("body:" + response.readEntity(String.class));

        return response;
    }

    @Override
    public Response createOrder() {
        Client client = ClientBuilder.newClient();
        Entity payload = Entity.json("{  \"notifyUrl\": \"https://your.eshop.com/notify\",  \"customerIp\": \"127.0.0.1\",  \"merchantPosId\": \"145227\",  \"description\": \"RTV market\",  \"currencyCode\": \"PLN\",  \"totalAmount\": \"21000\",  \"products\": [    {      \"name\": \"Wireless mouse\",      \"unitPrice\": \"15000\",      \"quantity\": \"1\"    },    {      \"name\": \"HDMI cable\",      \"unitPrice\": \"6000\",      \"quantity\": \"1\"    }  ]}");
        Response response = client.target("https://secure.snd.payu.com/api/v2_1/orders ")
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .header("Authorization", "Bearer 3e5cac39-7e38-4139-8fd6-30adc06a61bd")
                        .post(payload);

        System.out.println("status: " + response.getStatus());
        System.out.println("headers: " + response.getHeaders());
        System.out.println("body:" + response.readEntity(String.class));

        return response;
    }


}
