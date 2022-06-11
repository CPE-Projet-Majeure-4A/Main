//---------------------------------INITIALISATION DE LA MAP -----------------------------------------------------------------

var id_groupe=662859;
var markers = [];
var a=0;


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
    mymap = L.map("mapid").setView([45.734043, 4.845659], 12.3);
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
lgMarkers = new L.LayerGroup();



//-----------------------------------------------------------------------------Itinéraire------------------------------------------------------------
// L.Routing.control({
//     waypoints: [
//         L.latLng(45.764043, 4.835659),
//         L.latLng(45.774043, 4.845659)
//     ],
//     routeWhileDragging: true,
//     router: new L.Routing.osrmv1({
//         language: 'fr',
//         profile: 'satelite',
//         show: true,
//         routeWhileDragging: true
//     })
// }).addTo(mymap);



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
        ajout_feu_map(feu);
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
        ajout_caserne_map(caserne);
    }
    generate_feu();
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


function ajout_caserne_map(caserne) {
    var description = "<h2>Caserne N*" + caserne.id + "</h2>  <h3>Nom:" + caserne.name + " </h3>  <h3>MaxVehicleSpace: " + caserne.maxVehicleSpace + "</h3>  <h3>PeopleCapacity: " + caserne.peopleCapacity + " </h3>";

    if (caserne.id == id_groupe) {
        cas_lon = caserne.lon;
        cas_lat = caserne.lat;
        markers += L.marker([caserne.lat, caserne.lon], {
            icon: Icon_Caserne2
        }).addTo(CASERNES).bindPopup(description);
    } else {
        markers += L.marker([caserne.lat, caserne.lon], {
            icon: Icon_Caserne
        }).addTo(CASERNES).bindPopup(description);
    }





}

// -----------------------------------------------------------------------AJOUT DES FEUX------------------------------------------


let FireList = []

var Icon_Feu = L.icon({
    iconUrl: 'fire.png',
    iconSize: [50, 50]
});

function ajout_feu_map(feu) {

    var latlng1 = L.latLng(cas_lat, cas_lon);
    var latlng2 = L.latLng(feu.lat, feu.lon);

    var distance = Math.round(latlng1.distanceTo(latlng2) / 1000);

    var description = "<h2>FIRE N*" + feu.id + "</h2>  <h3>Fire Type:" + feu.type + " </h3>  <h3>Intensity: " + feu.intensity + "</h3>  <h3>Range: " + feu.range + " </h3>  <h3>Distance à la caserne : " + distance + " km";
    markers += L.marker([feu.lat, feu.lon], {
        icon: Icon_Feu
    }).addTo(FEUX).bindPopup(description);


}



//-------------------------------------------------------------------------------AJOUT VEHICULE-------------------------------------------------
let VehiculeList = []

var Icon_Camion_Autre_Equipe = L.icon({
    iconUrl: 'voiture2.png',
    iconSize: [70, 70]
});

var Icon_Camion = L.icon({
    iconUrl: 'voiture.png',
    iconSize: [70, 70]
});

var Icon_Camion2 = L.icon({
    iconUrl: 'camion.png',
    iconSize: [90, 90]
});


async function send() 
{
    json_obj=JSON.stringify({
        crewMember: Number(document.getElementById("CrewMember").value),
        fuel: Number(document.getElementById("fuel").value),
        lon: parseFloat(document.getElementById("lon").value),
        lat: parseFloat(document.getElementById("lat").value),
        type : document.getElementById("Type").value,
        facilityRefID : Number(id_groupe) ,
        liquidQuantity: Number(document.getElementById("liquidQuantity").value)});



    const rawResponse = await fetch("http://vps.cpe-sn.fr:8081/vehicle/a1cc702e-de17-4796-8886-0b937c406ad1", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
        body: json_obj
    });



    generate_vehicle_new(parseFloat(document.getElementById("lon").value),parseFloat(document.getElementById("lat").value));

    

    
    
}

function generate_vehicle_new(lon, lat) {

    const GET_CHUCK_URL="http://vps.cpe-sn.fr:8081/vehicle"; 
    let context =   {
                        method: 'GET'
                    };
        
    fetch(GET_CHUCK_URL,context)
        .then(response4 => response4.json())
        .then(response4 => callback4(response4,lon,lat))
        .catch(error => err_callback(error));
}

function callback4(response4,lon,lat){
    for (vehicle of response4) {

        if (vehicle.lon==lon && vehicle.lat==lat){
            ajout_vehicle_map_new(vehicle);
            VehiculeList.push(vehicle);
        }
    }
}

function ajout_vehicle_map_new(vehicle) {
    var description = "<h2>VEHICULE N*" + vehicle.id + "</h2>  <h3>crewMember:" + vehicle.crewMember + " </h3>  <h3>LiquidType: " + vehicle.liquidType + "</h3>  <h3>fuel: " + vehicle.fuel + " </h3>  <h3>Type : " + vehicle.type ;
        if(vehicle.type=="CAR"){
            markers += L.marker([vehicle.lat, vehicle.lon], {
                icon: Icon_Camion
            }).addTo(CAMIONS).bindPopup(description);
        }
        else{
            markers += L.marker([vehicle.lat, vehicle.lon], {
                icon: Icon_Camion2
            }).addTo(CAMIONS).bindPopup(description);
        }
}


function generate_vehicle() {

    const GET_CHUCK_URL="http://vps.cpe-sn.fr:8081/vehicle"; 
    let context =   {
                        method: 'GET'
                    };
        
    fetch(GET_CHUCK_URL,context)
        .then(response3 => response3.json())
        .then(response3 => callback3(response3))
        .catch(error => err_callback(error));
}

function callback3(response3){
    for (vehicle of response3) {
        
        for (vehicle2 of VehiculeList){
            if (vehicle.id == vehicle2.id){
                a=1;
            }
        }
        if (a!=1){
            ajout_vehicle_map(vehicle);
            VehiculeList.push(vehicle);
        }
        else{
            a=0;
        }

        
    }
}







function ajout_vehicle_map(vehicle) {
    var description = "<h2>VEHICULE N*" + vehicle.id + "</h2>  <h3>crewMember:" + vehicle.crewMember + " </h3>  <h3>LiquidType: " + vehicle.liquidType + "</h3>  <h3>fuel: " + vehicle.fuel + " </h3>  <h3>Type : " + vehicle.type ;
    if (vehicle.facilityRefID===id_groupe){
        if(vehicle.type=="CAR"){
            markers += L.marker([vehicle.lat, vehicle.lon], {
                icon: Icon_Camion
            }).addTo(CAMIONS).bindPopup(description);
        }
        else{
            markers += L.marker([vehicle.lat, vehicle.lon], {
                icon: Icon_Camion2
            }).addTo(CAMIONS).bindPopup(description);
        }
        
    }
    else{
        markers += L.marker([vehicle.lat, vehicle.lon], {
            icon: Icon_Camion_Autre_Equipe
        }).addTo(CAMIONS).bindPopup(description);
    }
    

}

//------------------------------------------------------------------------LEGENDE----------------------------------------
/*Legend specific*/
var legend = L.control({ position: "bottomleft" });

legend.onAdd = function(mymap) {
  var div = L.DomUtil.create("div", "legend");
  div.innerHTML += "<h4>Legend</h4>";
  div.innerHTML += '<i class="icon" style="background-image: url(station.png);background-repeat: no-repeat; "></i> <span> Casernes Ennemis </span> <br>';
  div.innerHTML += '<i class="icon" style="background-image: url(caserne2.png);background-repeat: no-repeat; "></i> <span> Casernes personnels </span><br>';
  div.innerHTML += '<i class="icon" style="background-image: url(voiture2.png);background-repeat: no-repeat;"></i> <span> Vehicules ennemis </span><br>';
  div.innerHTML += '<i class="icon" style="background-image: url(voiture.png);background-repeat: no-repeat;"></i> <span> Voitures personnelles  </span><br>';
  div.innerHTML += '<i class="icon" style="background-image: url(camion.png);background-repeat: no-repeat;"></i> <span> Camions personnels </span><br>';
  div.innerHTML += '<i class="icon" style="background-image: url(fire.png);background-repeat: no-repeat;"></i><span> Feu </span><br>';
  
  

  return div;
};

legend.addTo(mymap);
//-----------------------------------------------------------------------QUAND LE DOCUMENT EST FINI------------------------------

function layerRemoveMarkers() {
    lgMarkers.clearLayers();
}

$(document).ready(function() {

    function main(){
        generate_caserne();
        generate_vehicle();
        layerRemoveMarkers();
        setTimeout(main,1000);
    }

    main();


    

});

var cas_lon = [];
var cas_lat = [];


