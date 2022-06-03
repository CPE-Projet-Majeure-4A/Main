package com.majeur.projet.ApiCommunication;

import com.majeur.projet.ApiCommunication.FireObject;
import org.springframework.web.client.RestTemplate;

/**
 * Send GET requests from Fire, Facility and Vehicle API's
 * Static class to be used by both Bridge and Updater
 * TODO: Replace with cache in H2 DB
 */
public class StaticGet {


    public static FireObject[] getFires()
    {
        final String url = "http://vps.cpe-sn.fr:8081/fire";

        RestTemplate restTemplate = new RestTemplate();
        FireObject[] result = restTemplate.getForObject(url, FireObject[].class);

        return result;
    }

    public static FacilityObject[] getFacilities()
    {
        final String url = "http://vps.cpe-sn.fr:8081/facility";

        RestTemplate restTemplate = new RestTemplate();
        FacilityObject[] result = restTemplate.getForObject(url, FacilityObject[].class);

        return result;
    }

    public static VehicleObject[] getVehicles()
    {
        //TODO Adapter API pour ne pas avoir besoin de l'uuid
        final String url = "http://vps.cpe-sn.fr:8081/vehicle/a1cc702e-de17-4796-8886-0b937c406ad1";

        RestTemplate restTemplate = new RestTemplate();
        VehicleObject[] result = restTemplate.getForObject(url, VehicleObject[].class);

        return result;
    }
}
