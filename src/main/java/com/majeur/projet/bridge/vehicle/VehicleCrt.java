package com.majeur.projet.bridge.vehicle;

import com.majeur.projet.apiCommunication.StaticGet;
import com.majeur.projet.apiCommunication.StaticVehicle;
import com.majeur.projet.apiCommunication.VehicleObject;
import org.springframework.http.ResponseEntity;
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
    public void vehicleCrtPut(@PathVariable String id, @PathVariable String teamUuid, @RequestBody VehicleObject body) {
        StaticVehicle.updateVehicle(body);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/vehicle/{teamUuid}/{id}")
    public boolean vehicleCrtDelete(@PathVariable String id, @PathVariable String teamUuid) {
        return StaticVehicle.delVehicle(id, teamUuid);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/vehicle/{teamUuid}")
    public VehicleObject vehicleCrtPost(@PathVariable String teamUuid, @RequestBody VehicleObject body) {
        return StaticVehicle.addVehicle(body);
    }
}
