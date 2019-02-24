package pl.onsight.wypozyczalnia.service;

import javax.ws.rs.core.Response;

public interface PayUService {

    Response getToken();

    Response createOrder();
}
