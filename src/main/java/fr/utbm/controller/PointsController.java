package fr.utbm.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.utbm.calibration.NetworkUtils;
import fr.utbm.controller.po.AccessPointPO;
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
import java.util.HashMap;
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
    public @ResponseBody String addPoint(HttpServletRequest request, @RequestParam Double x, @RequestParam Double y, @RequestParam Integer mapId) throws MapInexistantException, LocationAlreadyExistsException, AccessPointInexistantException, RssiAlreadyExistsException {
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
        locationService.createLocation(newLocation);
        
        // Send request to all registered access points
        for (AccessPoint ap : apService.getAllAccessPoints()) {
            if ((apIPAddress = getIPforMac(ap.getMacAddr())) != null) {
                //String response = NetworkUtils.sendRequest("http://" + apIPAddress + ":8888/getRssi?mac="+userMacAddress, 10000, 15000);
                String response = "{\"ap\": \"" + ap.getMacAddr() + "\",\"rssi\":[{\"macAddr\":\"00:00:00:00:00:00\",\"value\":-54.2,\"samples\":5}]}";
                
                Gson gson = new Gson();
                AccessPointPO answerJSON = gson.fromJson(response, AccessPointPO.class);
                for (RssiPO rssiPO : answerJSON.getRssis()) {
                    Rssi rssi = new Rssi();
                    rssi.setAccessPoint(ap);
                    rssi.setAverageValue(rssiPO.getValue());
                    rssi.setLocation(newLocation);
                    rssiService.createRssi(rssi);
                }
            }
        }

        json.addProperty("success", Boolean.TRUE);
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
