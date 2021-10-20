package com.warehousemanagementsystem.warehousemanagementsystem.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public  class RestTemplateExchange {
    public static RestTemplate restTemplate = new RestTemplate();
    public static String url = "http://3b56-27-71-108-244.ngrok.io";


    public static <T> ResponseEntity<T> useExchangeMethodsOfRestTemplate
            (String url1, HttpMethod method, Class<T> type) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url+url1,
                method,
                requestEntity,
                type);
        return responseEntity;
    }

}
