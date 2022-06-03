package com.majeur.projet.Bridge.Vehicle;

import com.majeur.projet.ApiCommunication.FireObject;
import com.majeur.projet.ApiCommunication.StaticGet;
import com.majeur.projet.ApiCommunication.VehicleObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Map:
 * GET /vehicle
 * Return:
 * List of vehicles from StaticGet
 */
@RestController
public class VehicleCrt {

    @RequestMapping(method = RequestMethod.GET, value = "/vehicle")
    public VehicleObject[] fires() {
        return StaticGet.getVehicles();
    }
}
