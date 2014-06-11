package fr.utbm.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.utbm.utils.NetworkUtils;
import fr.utbm.dao.exception.AccessPointAlreadyExistsException;
import fr.utbm.model.AccessPoint;
import fr.utbm.service.AccessPointService;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for access point's management
 *
 * @author Guigeek
 */
@Controller
public class AccessPointsController {

    @Resource
    AccessPointService apService;

    /**
     * Adds access point with the given name
     *
     * @param mac the mac address given in the request
     * @return a JSON response
     */
    @RequestMapping(value = "/accesspoints/add", method = RequestMethod.GET)
    public @ResponseBody
    String addAccessPoint(@RequestParam String mac) {
        JsonObject json = new JsonObject();
        AccessPoint newAP = new AccessPoint();
        newAP.setMacAddr(mac);
        try {
            apService.createAccessPoint(newAP);
        } catch (AccessPointAlreadyExistsException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Access point already exists : " + e.getMacAddr());
            return json.toString();
        }
        json.addProperty("success", Boolean.TRUE);
        return json.toString();
    }

    /**
     * Send a request to every ARP entry in the ARP table to check if it is an
     * access point
     */
    public void lookupAccessPoints() {
        String response = null;
        Gson gson = new Gson();

        for (Entry<String, String> arpEntry : NetworkUtils.getArpEntries().entrySet()) {
            response = NetworkUtils.sendRequest("http://" + arpEntry.getKey() + ":8888/checkRouter", 2000, 4000);
            JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);
            if (!response.equals("") && jsonResponse.get("success").getAsBoolean()) {
                System.out.println(arpEntry.getKey() + " is a router");
                AccessPoint newAP = new AccessPoint();
                newAP.setMacAddr(arpEntry.getValue());
                try {
                    apService.createAccessPoint(newAP);
                } catch (AccessPointAlreadyExistsException ex) {

                }
            } else {
                System.out.println(arpEntry.getKey());
            }
        }
    }

}
