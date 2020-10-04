package com.rest1.postdata.imp.pathcomponent;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

public interface CsvPathComponent {

  public Path getCsvDataPath(URI csvUri) throws IOException;

}
