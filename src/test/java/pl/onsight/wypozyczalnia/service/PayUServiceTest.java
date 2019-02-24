package pl.onsight.wypozyczalnia.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class PayUServiceTest {

    @Autowired
    private PayUService payUService;

    @Test
    public void shouldGetToken(){
        Response response = payUService.getToken();
       // String[] asd = response.readEntity(String.class).split(",");
        String token = response.readEntity(String.class).substring(17, 53);
        System.out.println(token);
        assertThat(response.getStatus()).isEqualTo(200);

    }

    @Test
    public void shouldCreateOrder(){
        payUService.createOrder();
    }

}
