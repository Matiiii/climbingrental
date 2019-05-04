package pl.onsight.wypozyczalnia.pdfgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class PdfGeneratorUtil {

    //TODO need to be autowired??, if yes then better autowired in constructor
    @Autowired
    private TemplateEngine templateEngine;

    //TODO it need to be finished
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

        List<String> listOfNames = new ArrayList<>();
        File folder = new File("C://orders");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                listOfNames.add(file.getName().substring(0, 10));
            }
        }

        String processedHtml = templateEngine.process(templateName, ctx);
        FileOutputStream os = null;
        String fileName = "orderNR" + orderId + "ID_";

        if (listOfNames.contains(fileName.substring(0, 10))) {
            System.out.println("There is a file with that name!");
        } else {
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
                }
            }
        }
    }
}



