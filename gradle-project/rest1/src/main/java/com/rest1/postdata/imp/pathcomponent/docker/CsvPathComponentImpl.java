package com.rest1.postdata.imp.pathcomponent.docker;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.rest1.postdata.imp.pathcomponent.CsvPathComponent;

@Component
@Profile("docker")
public class CsvPathComponentImpl implements CsvPathComponent {

  /**
   * for jar project
   */
  @Override
  public Path getCsvDataPath(URI csvPath) throws IOException {

    final String[] pathArray = csvPath.toString().split("!");
    final FileSystem fs = FileSystems.newFileSystem(URI.create(pathArray[0]), new HashMap<>());

    return fs.getPath(pathArray[1] + pathArray[2]);
  }


}
