package com.majeur.projet.apiCommunication;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Sends UPDATE, PUT and DELETE requests to Vehicle API
 */
public class StaticVehicle {
    static final private String url = "http://vps.cpe-sn.fr:8081/vehicle";

    //@Value("com.majeur.projet.teamUuid")
    private static String teamUuid = "a1cc702e-de17-4796-8886-0b937c406ad1";

    public static void updateVehicle(VehicleObject vehicle){
        String vehicleId = Integer.toString(vehicle.getId());
        //PUT peut ne pas fonctionner à cause d'un bug de l'API
        // Si problème utiliser POST en précisant l'id dans body
        RestTemplate restTemplate = new RestTemplate();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("crewMember", vehicle.getCrewMember());
        jsonObject.put("facilityRefID", vehicle.getFacilityRefID());
        jsonObject.put("fuel", vehicle.getFuel());
        jsonObject.put("id", vehicle.getId());
        jsonObject.put("lat", vehicle.getLat());
        jsonObject.put("liquidQuantity", vehicle.getLiquidQuantity());
        jsonObject.put("liquidType", vehicle.getLiquidType());
        jsonObject.put("lon", vehicle.getLon());
        jsonObject.put("type", vehicle.getType());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);
         restTemplate.put(url+"/"+teamUuid+"/"+vehicleId, request);
    }

    public static boolean delVehicle(String vehicleId, String teamUuid){
        RestTemplate restTemplate = new RestTemplate();
        try{ //Nécessaire?
            restTemplate.delete(url+"/"+teamUuid+"/"+vehicleId);
        }catch(HttpClientErrorException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static VehicleObject addVehicle(VehicleObject vehicle){
        RestTemplate restTemplate = new RestTemplate();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("crewMember", vehicle.getCrewMember());
        jsonObject.put("facilityRefID", vehicle.getFacilityRefID());
        jsonObject.put("fuel", vehicle.getFuel());
        jsonObject.put("id", vehicle.getId());
        jsonObject.put("lat", vehicle.getLat());
        jsonObject.put("liquidQuantity", vehicle.getLiquidQuantity());
        jsonObject.put("liquidType", vehicle.getLiquidType());
        jsonObject.put("lon", vehicle.getLon());
        jsonObject.put("type", vehicle.getType());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(jsonObject.toString(), headers);

        System.out.println("POST: " + request);
        return restTemplate.postForObject(url+"/"+teamUuid, request, VehicleObject.class);
    }
}
