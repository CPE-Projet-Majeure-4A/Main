package com.majeur.projet.Bridge.Vehicle;

import com.majeur.projet.ApiCommunication.FireObject;
import com.majeur.projet.ApiCommunication.StaticGet;
import com.majeur.projet.ApiCommunication.StaticVehicle;
import com.majeur.projet.ApiCommunication.VehicleObject;
import org.springframework.web.bind.annotation.*;

/**
 * Map:
 * GET /vehicle
 * Return:
 * List of vehicles from StaticGet
 */
@RestController
public class VehicleCrt {

    @RequestMapping(method = RequestMethod.GET, value = "/vehicle")
    public VehicleObject[] vehicleCrtGet() {
        return StaticGet.getVehicles();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/vehicle/{teamUuid}/{id}")
    public boolean vehicleCrtPut(@PathVariable String id, @PathVariable String teamUuid, @RequestBody Object body) {
        return StaticVehicle.updateVehicle(id, body, teamUuid);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/vehicle/{teamUuid}/{id}")
    public boolean vehicleCrtDelete(@PathVariable String id, @PathVariable String teamUuid) {
        return StaticVehicle.delVehicle(id, teamUuid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/vehicle/{teamUuid}")
    public VehicleObject vehicleCrtPost(@PathVariable String teamUuid, @RequestBody Object body) {
        VehicleObject vehicle = StaticVehicle.addVehicle(body, teamUuid);
        // TODO: Peut être null si erreur dans la requête (à gérer)
        return vehicle;
    }
}
