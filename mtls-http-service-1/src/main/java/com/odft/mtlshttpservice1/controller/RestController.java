package com.odft.mtlshttpservice1.controller;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.ssl.PrivateKeyStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Map;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    Logger logger = LoggerFactory.getLogger(RestController.class);
    @Value("${server.ssl.mtls-key-store}")
    private Resource keyStore;

    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${server.ssl.key-password}")
    private String keyPassword;
    @Value("${server.ssl.mtls-trust-store}")
    private Resource trustStore;

    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;
    private String serverName = "https://localhost:8081";

    @GetMapping("/to-service-2")
    public ResponseEntity<String> sendARequestToService()  {
        SSLContext sslContext = null;
        ResponseEntity<String> responseEntity;
        try {
            sslContext = new SSLContextBuilder()
                    .loadKeyMaterial(keyStore.getFile(), keyStorePassword.toCharArray(), keyPassword.toCharArray())
                    .loadTrustMaterial(trustStore.getFile(), trustStorePassword.toCharArray()).build();
        }  catch (Exception e) {
            logger.info("bad request or properties");
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConFactory).build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(serverName, String.class);
            logger.info(response.getBody());
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (ResourceAccessException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.error(e.getMessage());
            return responseEntity;
        }
    }

    @Scheduled(fixedDelay = 15000, initialDelay = 15000)
    public void serviceCall() {
        sendARequestToService();
    }

    @GetMapping
    public String getHello() {
        return "Hello from service 1";
    }
}
