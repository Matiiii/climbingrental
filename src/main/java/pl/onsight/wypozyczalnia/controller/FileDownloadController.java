package pl.onsight.wypozyczalnia.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileDownloadController {


  @GetMapping("download/{id}")
  public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") Long id) throws IOException {

    List<String> listOfNames = new ArrayList<>();
        File folder = new File("C://orders");
        File[] listOfFiles = folder.listFiles();
        for (File fileWithName : listOfFiles) {
          if (fileWithName.isFile()) {
            listOfNames.add(fileWithName.getName());
      }
    }
    String path = "C://orders/" + listOfNames.get(id.intValue() - 1);
    File file = new File(path);
    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment;filename=" + file.getName())
      .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
      .body(resource);
  }

}
