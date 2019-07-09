package com.revomatico.internship2019.demo1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @RequestMapping("/")
  public String home() {
    return "Events:<br/>" + table();
  }

  private String table() {
    String result = "";
    for (int i = 0; i < 10; i++) {
      result += row();
    }
    return tag("table", result);
  }

  private String tag(String tagName, String result) {
    return "<" + tagName + ">" + result + "</" + tagName + ">";
  }

  private String row() {
    return tag("tr",tag("td","rand2"));
  }
}
