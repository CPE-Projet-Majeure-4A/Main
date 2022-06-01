//---------------------------------INITIALISATION DE LA MAP -----------------------------------------------------------------
const mapboxToken = "pk.eyJ1IjoiYXJ0aHVyb2xpdmllciIsImEiOiJjbDN2YTYxZW0wMzdiM21wOGE3eGxyZjNkIn0.TEuLBTV8qSpy8i9zveoxGg";

const zones = [{
    distance: 10,
    color: "#00b798"
}, {
    distance: 30,
    color: "#ffb846"
}, ];

let mymap;


function initMap() {
    mymap = L.map("mapid").setView([45.764043, 4.835659], 10);
    L.tileLayer(

        "https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}", {
            attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
            maxZoom: 18,
            id: "mapbox/streets-v11",
            tileSize: 512,
            zoomOffset: -1,
            accessToken: mapboxToken,
        }
    ).addTo(mymap);

}


initMap();


// ------------------------------------------------------Recu-peration des feux pour l'affichage 
function generate_feu() {

    const URL = "http://vps.cpe-sn.fr:8081/fire";
    let context = {
        method: 'GET'
    };

    fetch(URL, context)
        .then(response => response.json())
        .then(response => callback(response))
        .catch(error => err_callback(error));
}


function err_callback(error) {
    console.log(error);
}

function callback(response) {
    for (feu of response) {

        FireList.push(feu);
        ajout_feu_map(FireList);
    }
}

// ------------------------------------------------------Recu-peration des caserne pour l'affichage 
function generate_caserne() {

    const URL = "http://vps.cpe-sn.fr:8081/facility";
    let context = {
        method: 'GET'
    };

    fetch(URL, context)
        .then(response2 => response2.json())
        .then(response2 => callback2(response2))
        .catch(error => err_callback(error));
}


function callback2(response2) {
    for (caserne of response2) {

        CaserneList.push(caserne);
        ajout_caserne_map(CaserneList);
    }
}





// -------------------------------------------------------------CASERNES ET FEU ----------------------------------
var CASERNES = L.layerGroup().addTo(mymap);
var FEUX = L.layerGroup().addTo(mymap);
var CAMIONS = L.layerGroup().addTo(mymap);

// -----------------------------------------------------------------------AJOUT DES CASERNES------------------------------------------
let CaserneList = []

var Icon_Caserne = L.icon({
    iconUrl: 'station.png',
    iconSize: [50, 50]
});

var Icon_Caserne2 = L.icon({
    iconUrl: 'caserne2.png',
    iconSize: [80, 80]
});


function ajout_caserne_map(CaserneList) {
    for (caserne of CaserneList) {
        var description = "<h2>Caserne N*" + caserne.id + "</h2>  <h3>Nom:" + caserne.name + " </h3>  <h3>MaxVehicleSpace: " + caserne.maxVehicleSpace + "</h3>  <h3>PeopleCapacity: " + caserne.peopleCapacity + " </h3>";

        if (caserne.id == "83") {
            L.marker([caserne.lat, caserne.lon], {
                icon: Icon_Caserne2
            }).addTo(CASERNES).bindPopup(description);
        } else {
            L.marker([caserne.lat, caserne.lon], {
                icon: Icon_Caserne
            }).addTo(CASERNES).bindPopup(description);
        }



    }
}

// -----------------------------------------------------------------------AJOUT DES FEUX------------------------------------------


let FireList = []

var Icon_Feu = L.icon({
    iconUrl: 'fire.png',
    iconSize: [50, 50]
});

function ajout_feu_map(FireList) {
    for (feu of FireList) {
        var description = "<h2>FIRE N*" + feu.id + "</h2>  <h3>Fire Type:" + feu.type + " </h3>  <h3>Intensity: " + feu.intensity + "</h3>  <h3>Range: " + feu.range + " </h3>";
        L.marker([feu.lat, feu.lon], {
            icon: Icon_Feu
        }).addTo(FEUX).bindPopup(description);

    }
}

// -----------------------------------------------------------------------AJOUT DES VOITURES------------------------------------------
var Icon_Camion = L.icon({
    iconUrl: 'voiture.png',
    iconSize: [70, 70]
});

L.marker([45.81, 4.835659], {
    icon: Icon_Camion
}).addTo(CAMIONS).bindPopup("<h2>FIRE N*</h2>  <h3>Fire Type: </h3>  <h3>Intensity: </h3>  <h3>Range: </h3>");



//-----------------------------------------------------------------------QUAND LE DOCUMENT EST FINI------------------------------

$(document).ready(function() {

    var coord_caserne =
        var coord_caserne

    generate_feu();
    generate_caserne();

    var options = {
        units: 'miles'
    }; // units can be degrees, radians, miles, or kilometers, just be sure to change the units in the text box to match. 


    var distance = turf.distance(to, from, options);

    var value = document.getElementById('map-overlay')
    value.innerHTML = "Distance: " + distance.toFixed([2]) + " miles"

});