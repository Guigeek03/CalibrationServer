package fr.utbm.controller;

import com.google.gson.JsonObject;
import fr.utbm.calibration.NetworkUtils;
import fr.utbm.model.AccessPoint;
import fr.utbm.service.AccessPointService;
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

    @RequestMapping(value = "/points/add", method = RequestMethod.GET)
    public @ResponseBody String addPoint(HttpServletRequest request, @RequestParam Double x, @RequestParam Double y, @RequestParam Integer mapId) {
        JsonObject json = new JsonObject();
        InputStream is = null;
        
        // Retrieve user mac address
        arpEntries = NetworkUtils.getArpEntries();
        String userIpAddress = request.getRemoteAddr();
        String userMacAddress = null;
        String apIPAddress = null;
        userMacAddress = arpEntries.get(userIpAddress);
        
        // Send request to all registered access points
        for (AccessPoint ap : apService.getAllAccessPoints()) {
            if ((apIPAddress = getIPforMac(ap.getMacAddr())) != null) {
                String response = NetworkUtils.sendRequest("http://" + apIPAddress + ":8888/getRssi?mac="+userMacAddress, 10000, 15000);
                
                //TODO: Parse response
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
