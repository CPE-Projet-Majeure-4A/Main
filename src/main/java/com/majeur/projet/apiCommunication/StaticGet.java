package com.majeur.projet.apiCommunication;

import com.majeur.projet.bridge.vehicle.VehicleCrt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

/**
 * Send GET requests from Fire, Facility and Vehicle API's
 * Static class to be used by both Bridge and Updater
 * TODO: Replace with cache in H2 DB
 */
public class StaticGet {

    private static final String facilityId = "664996";

    public static FireObject[] getFires()
    {
        final String url = "http://vps.cpe-sn.fr:8081/fire";

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, FireObject[].class);
    }

    public static FireObject getFire(String id)
    {
        final String url = "http://vps.cpe-sn.fr:8081/fire/"+id;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, FireObject.class);
    }

    public static FacilityObject[] getFacilities()
    {
        final String url = "http://vps.cpe-sn.fr:8081/facility";

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, FacilityObject[].class);
    }

    public static FacilityObject getTeamFacility(){
        final String url = "http://vps.cpe-sn.fr:8081/facility/"+facilityId;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, FacilityObject.class);
    }

    public static VehicleObject getVehicleById(String vehicleId){
        final String url = "http://vps.cpe-sn.fr:8081/vehicle/"+vehicleId;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, VehicleObject.class);
    }

    public static VehicleObject[] getVehicles()
    {
        final String url = "http://vps.cpe-sn.fr:8081/vehicle";

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, VehicleObject[].class);
    }
}
