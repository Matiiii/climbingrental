package pl.onsight.wypozyczalnia.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.service.PdfService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfServiceImpl implements PdfService {

  @Override
  public void generateHtmlToPdfOrder(Long orderId) throws IOException {
    Document document = new Document();
    PdfWriter writer = null;
    try {
      writer = PdfWriter.getInstance(document,
        new FileOutputStream("src/main/resources/ordersPDF/orderNr" + orderId + ".pdf"));
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    }
    document.open();
    XMLWorkerHelper.getInstance().parseXHtml(writer, document,
      new FileInputStream("src/main/resources/templates/order.html"));
    document.close();
  }
}
