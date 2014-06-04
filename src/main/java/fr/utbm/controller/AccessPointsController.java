package fr.utbm.controller;

import com.google.gson.JsonObject;
import fr.utbm.dao.exception.AccessPointAlreadyExistsException;
import fr.utbm.model.AccessPoint;
import fr.utbm.service.AccessPointService;
import java.util.Locale;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccessPointsController {

    @Resource
    AccessPointService apService;

    @RequestMapping(value = "/accesspoints/add", method = RequestMethod.GET)
    public @ResponseBody
    String addAccessPoint(@RequestParam String mac, Locale l) {
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
}
