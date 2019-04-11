package pl.onsight.wypozyczalnia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Component
public class PdfGeneratorUtil {

  @Autowired
  private TemplateEngine templateEngine;

  public void createPdf(String templateName, Map<String, String> map, Long orderId) throws Exception {
    Context ctx = new Context();
    Iterator itMap = map.entrySet().iterator();
    while (itMap.hasNext()) {
      Map.Entry pair = (Map.Entry) itMap.next();
      ctx.setVariable(pair.getKey().toString(), pair.getValue());
    }

    String processedHtml = templateEngine.process(templateName, ctx);
    FileOutputStream os = null;
    String fileName = "ordernumber";
    File directory = new File("C:/Users/aszaniaw/Desktop/ordersPDF");
    try {
      final File outputFile = File.createTempFile(fileName, ".pdf", directory);
      os = new FileOutputStream(outputFile);

      ITextRenderer renderer = new ITextRenderer();
      renderer.setDocumentFromString(processedHtml);
      renderer.layout();
      renderer.createPDF(os, false);
      renderer.finishPDF();

    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (IOException e) { /*ignore*/ }
      }
    }
  }

}
