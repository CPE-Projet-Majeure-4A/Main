package com.majeur.projet.bridge.facility;

import com.majeur.projet.apiCommunication.FacilityObject;
import com.majeur.projet.apiCommunication.StaticGet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Map:
 * GET /facility
 * Return:
 * List of facilities from StaticGet
 */
@RestController
public class FacilityCrt {

    @RequestMapping(method = RequestMethod.GET, value = "/facility")
    public FacilityObject[] facilities() {
        return StaticGet.getFacilities();
    }
}
