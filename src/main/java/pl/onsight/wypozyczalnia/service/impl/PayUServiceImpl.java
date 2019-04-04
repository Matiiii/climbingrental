package pl.onsight.wypozyczalnia.service.impl;

import org.glassfish.jersey.client.ClientProperties;
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
        Client client = ClientBuilder.newClient().property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);

        //testowy sklep na sandboxie
        Entity<String> payload = Entity.text("grant_type=client_credentials&client_id=352643&client_secret=f15e8ffa7a663cee2b910a44ba607f36");

        Response response = client.target("https://secure.snd.payu.com/pl/standard/user/oauth/authorize?grant_type=client_credentials&client_id=352643&client_secret=f15e8ffa7a663cee2b910a44ba607f36")
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        .post(payload);

        System.out.println("status: " + response.getStatus());
        System.out.println("headers: " + response.getHeaders());
        //System.out.println("body:" + response.readEntity(String.class));

        return response;
    }

    @Override
    public Response createOrder() {
        Client client = ClientBuilder.newClient().property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);
        Entity payload = Entity.json("{  \"notifyUrl\": \"https://asd.pl/notify\"," +
                "  \"customerIp\": \"127.0.0.1\"," +
                "  \"merchantPosId\": \"352643\"," +
                "  \"description\": \"RTV market\"," +
                "  \"currencyCode\": \"PLN\"," +
                "  \"totalAmount\": \"21000\"," +
                "  \"buyer\": {" +
                "  \"email\": \"john.doe@example.com\"," +
                "  \"phone\": \"654111654\"," +
                "  \"firstName\": \"John\"," +
                "  \"lastName\": \"Doe\", " +
                "  \"language\": \"pl\"" +
                "  }," +
                "  \"settings\":{" +
                "  \"invoiceDisabled\": \"true\"" +
                "   }," +
                "  \"products\": [    {" +
                "      \"name\": \"Wireless mouse\"," +
                "      \"unitPrice\": \"15000\"," +
                "      \"quantity\": \"1\"    },    {" +
                "      \"name\": \"HDMI cable\"," +
                "      \"unitPrice\": \"6000\"," +
                "      \"quantity\": \"1\"" +
                "    }  ]}");
        Response response = client.target("https://secure.snd.payu.com/api/v2_1/orders")
                        .request(MediaType.APPLICATION_JSON_TYPE)
                        //.header("Authorization", "Bearer 3e5cac39-7e38-4139-8fd6-30adc06a61bd")
                        .header("Authorization", "Bearer " + token)
                        .post(payload);

        System.out.println("status: " + response.getStatus());
        System.out.println("headers: " + response.getHeaders());
        System.out.println("body:" + response.readEntity(String.class));

        return response;
    }


}
