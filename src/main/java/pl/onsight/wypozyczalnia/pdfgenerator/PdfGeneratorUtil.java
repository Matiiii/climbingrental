package pl.onsight.wypozyczalnia.pdfgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

@Component
public class PdfGeneratorUtil {

  @Autowired
  private TemplateEngine templateEngine;

  public void createPdf(String templateName, Map<String, Object> map, Long orderId) throws Exception {
    File directory = new File("C://orders");

    if (!directory.exists()) {
      new File("C://orders").mkdir();
    }

    Context ctx = new Context();
    Iterator itMap = map.entrySet().iterator();
    while (itMap.hasNext()) {
      Map.Entry pair = (Map.Entry) itMap.next();
      ctx.setVariable(pair.getKey().toString(), pair.getValue());
    }

    String processedHtml = templateEngine.process(templateName, ctx);
    FileOutputStream os = null;
    String fileName = "orderNR"+ orderId+ "ID_" ;

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


