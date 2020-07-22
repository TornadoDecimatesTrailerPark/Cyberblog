package com.cyberblogger.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by foxi.chen on 8/02/20.
 *
 * @author foxi.chen
 */
public class PropertyUtils {
  public static String getPropertyFromClasspath(String propsName, String propsKey) throws IOException{

    // The getResourceAsStream method can be used to load any files on a program's classpath.
    // Any files you put into the src folder, or within a resources folder,  will end up on
    // the classpath at runtime.
    try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propsName)) {

      Properties props = new Properties();
      props.load(in);

      return props.getProperty(propsKey);
    }
  }
}
