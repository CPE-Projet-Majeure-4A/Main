package com.majeur.projet.bridge.Fire;

import com.majeur.projet.apiCommunication.FireObject;
import com.majeur.projet.apiCommunication.StaticGet;
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
