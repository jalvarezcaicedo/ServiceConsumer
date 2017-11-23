package com.example.jalvarez.serviceconsumer.data.remote;

class UrlConnection {

    private static String[] ENVIROMENT = {"jsonplaceholder.typicode.com", "coffeeq.eastus.cloudapp.azure.com"};

    static final String TRANSACTIONAL = "http://" + ENVIROMENT[1] + "/";

}