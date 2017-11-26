package com.example.capgemini.mybankapp.data.remote;

class UrlConnection {

    private static String[] ENVIROMENT = {"10.0.2.2", "192.168.0.11"};

    static final String TRANSACTIONAL = "https://" + ENVIROMENT[1] + ":4443/";

}