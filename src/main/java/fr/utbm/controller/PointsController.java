package fr.utbm.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.utbm.utils.NetworkUtils;
import fr.utbm.controller.po.AccessPointPO;
import fr.utbm.controller.po.LocationPO;
import fr.utbm.controller.po.RssiPO;
import fr.utbm.dao.exception.AccessPointInexistantException;
import fr.utbm.dao.exception.LocationAlreadyExistsException;
import fr.utbm.dao.exception.MapInexistantException;
import fr.utbm.dao.exception.RssiAlreadyExistsException;
import fr.utbm.model.AccessPoint;
import fr.utbm.model.Location;
import fr.utbm.model.Rssi;
import fr.utbm.service.AccessPointService;
import fr.utbm.service.LocationService;
import fr.utbm.service.MapService;
import fr.utbm.service.RssiService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PointsController {

    private HashMap<String, String> arpEntries = new HashMap<String, String>();

    @Resource
    AccessPointService apService;

    @Resource
    LocationService locationService;

    @Resource
    MapService mapService;

    @Resource
    RssiService rssiService;

    @RequestMapping(value = "/points/add", method = RequestMethod.GET)
    public @ResponseBody
    String addPoint(HttpServletRequest request, @RequestParam Double x, @RequestParam Double y, @RequestParam Integer mapId) throws MapInexistantException, LocationAlreadyExistsException, AccessPointInexistantException, RssiAlreadyExistsException {
        JsonObject json = new JsonObject();
        InputStream is = null;

        // Retrieve user mac address
        arpEntries = NetworkUtils.getArpEntries();
        String userIpAddress = request.getRemoteAddr();
        String userMacAddress = null;
        String apIPAddress = null;
        userMacAddress = arpEntries.get(userIpAddress);

        Location newLocation = new Location();
        newLocation.setX(x);
        newLocation.setY(y);
        newLocation.setMap(mapService.getMapByID(mapId));
        try {
            locationService.createLocation(newLocation);
        } catch (LocationAlreadyExistsException e) {
            System.out.println("Location already exists : " + newLocation.getX() + " " + newLocation.getY());
        }

        // Send request to all registered access points
        for (AccessPoint ap : apService.getAllAccessPoints()) {
            if ((apIPAddress = getIPforMac(ap.getMacAddr())) != null) {
                String response = NetworkUtils.sendRequest("http://" + apIPAddress + ":8080/getRssi?mac=" + userMacAddress, 10000, 15000);
                System.out.println(response);

                Gson gson = new Gson();
                AccessPointPO answerJSON = gson.fromJson(response, AccessPointPO.class);
                for (RssiPO rssiPO : answerJSON.getRssis()) {
                    Rssi rssi = new Rssi();
                    rssi.setAccessPoint(ap);
                    rssi.setAverageValue(rssiPO.getValue());
                    rssi.setLocation(newLocation);
                    try {
                        rssiService.createRssi(rssi);
                    } catch (RssiAlreadyExistsException e) {
                        json.addProperty("success", Boolean.FALSE);
                        json.addProperty("exception", "Rssi value for this location already exists");
                        return json.toString();
                    }
                }
            }
        }

        json.addProperty("success", Boolean.TRUE);
        return json.toString();
    }
    
    @RequestMapping(value = "/points/getSavedPoints", method = RequestMethod.GET)
    public @ResponseBody
    String getSavedPoints(@RequestParam Integer mapId) throws MapInexistantException, LocationAlreadyExistsException, AccessPointInexistantException, RssiAlreadyExistsException {
            ArrayList<LocationPO> locations = new ArrayList<LocationPO>();
            for (Location l : locationService.getLocationByMap(mapId)) {
                locations.add(new LocationPO(l));
            }
            return new Gson().toJson(locations);
    }

    @RequestMapping(value = "/locateMe", method = RequestMethod.GET)
    public @ResponseBody
    String locateMe(HttpServletRequest request) {
        JsonObject json = new JsonObject();
        arpEntries = NetworkUtils.getArpEntries();
        String userIpAddress = request.getRemoteAddr();
        String userMacAddress = null;
        String apIPAddress = null;
        userMacAddress = arpEntries.get(userIpAddress);

        List<Location> closestLocations = new ArrayList<Location>();
        for (AccessPoint ap : apService.getAllAccessPoints()) {
            if ((apIPAddress = getIPforMac(ap.getMacAddr())) != null) {
                String response = NetworkUtils.sendRequest("http://" + apIPAddress + ":8080/getRssi?mac=" + userMacAddress, 10000, 15000);
                System.out.println(response);

                List<Rssi> listRssi = rssiService.getAllRssisByAP(ap.getId());

                Gson gson = new Gson();
                AccessPointPO answerJSON = gson.fromJson(response, AccessPointPO.class);
                for (RssiPO rssiPO : answerJSON.getRssis()) {
                    Double rssiValue = rssiPO.getValue();
                    Location closestLocation = null;
                    Double diff = 0.;
                    for (Rssi rssiSample : listRssi) {
                        if (closestLocation == null || Math.abs(rssiSample.getAverageValue() - rssiValue) < diff) {
                            closestLocation = rssiSample.getLocation();
                            diff = Math.abs(rssiSample.getAverageValue() - rssiValue);
                        }
                    }
                    closestLocations.add(closestLocation);
                }
            }
        }
        if (closestLocations.isEmpty()) {
            json.addProperty("success", Boolean.FALSE);
            return json.toString();
        }

        Double avgX = 0.;
        Double avgY = 0.;
        for (Location currentLocation : closestLocations) {
            avgX += currentLocation.getX();
            avgY += currentLocation.getY();
        }
        avgX /= closestLocations.size();
        avgY /= closestLocations.size();

        json.addProperty("success", Boolean.TRUE);
        json.addProperty("x", avgX);
        json.addProperty("y", avgY);
        return json.toString();
    }

    private String getIPforMac(String macAddress) {
        String ipAddress = null;
        for (Entry<String, String> arpEntry : arpEntries.entrySet()) {
            if (arpEntry.getValue().equals(macAddress)) {
                ipAddress = arpEntry.getKey();
                break;
            }
        }
        return ipAddress;
    }
}
