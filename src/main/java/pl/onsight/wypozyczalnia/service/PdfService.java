package pl.onsight.wypozyczalnia.service;

import java.io.IOException;

public interface PdfService {

  void generateHtmlToPdfOrder(Long orderId) throws IOException;
}
