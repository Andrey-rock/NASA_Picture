package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/apod?api_key=xsmBGnabciJxMdUcakuMoTjgCgeO7tIWADezj9UP";

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        ObjectMapper mapper = new ObjectMapper();


//        Scanner scanner = new Scanner(response.getEntity().getContent());
//        String result = scanner.nextLine();
//        System.out.println(result);

        NasaAnswear answear = mapper.readValue(response.getEntity().getContent(), NasaAnswear.class);
        String[] result = answear.hdurl.split("/");
        String fileName = result[result.length-1];

        request = new HttpGet(answear.hdurl);
        response = httpClient.execute(request);

        FileOutputStream fos = new FileOutputStream("D:\\JAVA\\NASA_Picture\\Image\\" + fileName);
        response.getEntity().writeTo(fos);
    }
}