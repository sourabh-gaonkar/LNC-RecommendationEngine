package com.lnc;

import com.lnc.controller.HomeController;

import java.io.IOException;

public class App {
  public static void main(String[] args) {
    try {
      System.out.println("WELCOME TO THE RECOMMENDATION SYSTEM");
      HomeController home = new HomeController();
      home.runHomePage();
    } catch (IOException e) {
      System.err.println("IO ERROR.");
    } catch (InterruptedException e) {
      System.err.println("INTERRUPTION ERROR.");
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }
}
