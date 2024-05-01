// https://jsfiddle.net/gh/get/library/pure/googlemaps/js-samples/tree/master/dist/samples/distance-matrix/jsfiddle

const globalResults = [];
var globalMerged;

function initMap() {
    const bounds = new google.maps.LatLngBounds();
    const markersArray = [];
    const map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 55.53, lng: 9.4 },
        zoom: 10,
    });
    // initialize services
    const geocoder = new google.maps.Geocoder();
    const service = new google.maps.DistanceMatrixService();
    // build request
    const destinations = [
        "Goethesingel 47, 7207 PK Zutphen",
        "Neerwoldstraat 15, 8331 JX Steenwijk",
        "Karwijhof 2, 8308 AJ Nagele",
        "Weidehof 17, 8256 AT Biddinghuizen",
        "Picardstraat 11, 3882 VA Putten",
        "Wijnkanspad 8, 1506 EM Zaandam",
        "Terschelling 2, 1967 EC Heemskerk",
        "Jonkheer van de Pollstraat 65, 2151 AE Nieuw-Vennep",
        "Houstondreef 27, 3564 KL Utrecht",
        "Arnhemseweg 214, 7335 EH Apeldoorn",
        "Ceintuurbaan 20, 7415 AM Deventer",
        "De Genestetlaan 8, 7901 HN Hoogeveen",
        "Gerard Ter Borchstraat 1, 7944 GL Meppel",
        "Oostervoor 7, 8316 BM Marknesse",
        "Wanmolen 5, 1771 PJ Wieringerwerf",
        "Kamperfoeliestraat 34, 4461 NL Goes",
        "Poortdijkstraat 18, 4318 AN Brouwershaven",
        "Garst 51, 9673 AB Winschoten",
        "Overburen 1, 9636 HA Zuidbroek",
        "Murlinckstraat 1, 9843 ED Grijpskerk"
    ];
    const requestPromises = [];

    // Iterate over each address as an origin and make requests
    destinations.forEach(origin => {
        const request = {
            origins: [origin],
            destinations: destinations,
            travelMode: google.maps.TravelMode.DRIVING,
            unitSystem: google.maps.UnitSystem.METRIC,
            avoidHighways: false,
            avoidTolls: false,
        };
        requestPromises.push(service.getDistanceMatrix(request));
    });

    // Execute all requests concurrently
    Promise.all(requestPromises)
        .then(responses => {
            // Process the responses to get all edges in the distance matrix
            processDistanceMatrix(responses);

            console.log("globalResults");
            console.log(globalResults);

            globalMerged = mergeData(globalResults);
            console.log("global merged")
            console.log(globalMerged);
        })
        .catch(error => {
            console.error("Error fetching distance matrix:", error);
        })
        .finally(() => {
        });

    function processDistanceMatrix(responses) {
        // Process the responses to get all edges in the distance matrix
        // For demonstration, let's just log the responses
        responses.forEach((response, index) => {
            console.log(`Distance matrix from ${destinations[index]}:`);
            // console.log(response);
            globalResults.push(response);
        });
    }

    function deleteMarkers(markersArray) {
        for (let i = 0; i < markersArray.length; i++) {
            markersArray[i].setMap(null);
        }

        markersArray = [];
    }

    function mergeData(input) {
        const mergedData = {
            "destinationAddresses": [
                ...input[0].destinationAddresses
            ],
            "originAddresses": [

            ],
            "rows": [

            ]
        }

        input.forEach(item => {
            mergedData.originAddresses.push(item.originAddresses[0]);

            mergedData.rows.push(item.rows[0]);
        });

        return mergedData;
    }
}

window.initMap = initMap;