package com.majeur.projet.Bridge.Facility;

import com.majeur.projet.ApiCommunication.FacilityObject;
import com.majeur.projet.ApiCommunication.FireObject;
import com.majeur.projet.ApiCommunication.StaticGet;
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
