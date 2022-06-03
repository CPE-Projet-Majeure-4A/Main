package com.majeur.projet.Bridge.Fire;

import com.majeur.projet.ApiCommunication.FireObject;
import com.majeur.projet.ApiCommunication.StaticGet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Map:
 * GET /fire
 * Return:
 * List of fires from StaticGet
 */
@RestController
public class FireCrt {

    @RequestMapping(method = RequestMethod.GET, value = "/fire")
    public FireObject[] fires() {
        return StaticGet.getFires();
    }

}
