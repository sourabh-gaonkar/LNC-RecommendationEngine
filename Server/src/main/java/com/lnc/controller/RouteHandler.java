package com.lnc.controller;

public interface RouteHandler {
    String handle(String path, String data) throws Exception;

}
