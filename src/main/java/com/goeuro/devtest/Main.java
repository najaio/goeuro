package com.goeuro.devtest;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String API_ENDPOINT = "http://api.goeuro.com/api/v2/position/suggest/en/";

    public static void main(String[] args) {
        if(args.length == 0){
            LOGGER.info("please inform a city name as argument");
            System.exit(0);
        }
        
        final String cityName = args[0];
        City[] cities = getCityInformation(cityName);
        generateCsv(cities);
        System.exit(0);
    }

    private static City[] getCityInformation(final String cityName) {
        final String url = API_ENDPOINT + cityName;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            String json = response.body().string();
            return new Gson().fromJson(json, City[].class);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static void generateCsv(final City[] cities) {

        if (cities == null) {
            LOGGER.info("nothing to write...");
            return;
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter("cities.csv"))) {

            //write header
            writer.writeNext(new String[]{"_id", "name", "latitude", "longitude"});

            //write content
            Stream.of(cities)
                    .map(c -> new String[]{c.getId(), c.getName(),
                                    c.getLocation().getLatitude(),
                                    c.getLocation().getLongitude()})
                    .forEach(c -> writer.writeNext(c));

            LOGGER.info("file generated!");

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
