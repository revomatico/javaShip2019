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
    String tagName = "table";
    return "<" + tagName + ">" + result + "</" + tagName + ">";
  }

  private String row() {
    // TODO d
    // return null;
    // throw new RuntimeException("Not implemented yet!!!");
    String tagName = "tr";
    String tagName2 = "td";
    return "<" + tagName + ">" + "<" + tagName2 + ">" 
        + "rand" + "</" + tagName2 + ">" + "</" + tagName + ">";
    // "<tr><td>rand</td></tr>";
  }
}
