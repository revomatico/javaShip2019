package com.revomatico.internship2019.demo1;

import javax.annotation.PostConstruct;

import io.vavr.collection.Map;
import io.vavr.collection.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Demo1Application {
  private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Demo1Application.class);
  @Autowired
  Environment env;

  public static void main(String[] args) {
    SpringApplication.run(Demo1Application.class, args);
  }

  @PostConstruct
  public void init() throws Exception {
    logger.info("*********************\nProperties:\n" + getAllKnownProperties(env).map(x -> "    " + x._1() + "=" + x._2 + "\n").mkString()
        + "*********************\n");
  }

  public static Map<String, Object> getAllKnownProperties(Environment env) {
    Map<String, Object> rtn = TreeMap.empty();
    if (env instanceof ConfigurableEnvironment) {
      for (org.springframework.core.env.PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
        if (propertySource instanceof EnumerablePropertySource) {
          for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
            rtn = rtn.put(key + "@" + propertySource.getName(), propertySource.getProperty(key));
          }
        }
      }
    }
    return rtn;
  }
}
