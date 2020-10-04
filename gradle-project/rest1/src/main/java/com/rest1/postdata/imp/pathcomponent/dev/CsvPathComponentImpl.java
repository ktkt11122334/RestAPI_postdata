package com.rest1.postdata.imp.pathcomponent.dev;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.rest1.postdata.imp.pathcomponent.CsvPathComponent;

@Component
@Profile("localhost")
public class CsvPathComponentImpl implements CsvPathComponent {

  @Override
  public Path getCsvDataPath(URI csvPath) throws IOException {
    return Paths.get(csvPath);
  }


}
