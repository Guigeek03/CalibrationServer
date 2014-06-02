package fr.utbm.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.utbm.controller.po.MapPO;
import fr.utbm.dao.exception.MapAlreadyExistsException;
import fr.utbm.dao.exception.MapInUseException;
import fr.utbm.dao.exception.MapInexistantException;
import fr.utbm.model.Map;
import fr.utbm.service.BuildingService;
import fr.utbm.service.MapService;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MapsController {

    @Resource
    BuildingService buildingService;

    @Resource
    MapService mapService;

    @RequestMapping(value = "/buildings/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String getMaps(@PathVariable Integer id) {
        ArrayList<MapPO> maps = new ArrayList<MapPO>();
        for (Map m : mapService.getMapsForBuildingID(id)) {
            maps.add(new MapPO(m));
        }
        return new Gson().toJson(maps);
    }

    @RequestMapping(value = "/buildings/{id}/add", method = RequestMethod.GET)
    public @ResponseBody
    String addMap(@PathVariable Integer id, @RequestParam String name, @RequestParam Integer pxWidth, @RequestParam Integer pxHeight, Locale l) {
        JsonObject json = new JsonObject();
        Map newMap = new Map();
        newMap.setDescription(name);
        newMap.setBuilding(id);
        newMap.setPxHeight(pxHeight);
        newMap.setPxWidth(pxWidth);

        try {
            newMap = mapService.createMap(newMap);
        } catch (MapAlreadyExistsException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Map already exists : " + e.getName());
            return json.toString();
        }
        json.addProperty("success", Boolean.TRUE);
        json.addProperty("data", new Gson().toJson(new MapPO(newMap)));
        return json.toString();
    }

    @RequestMapping(value = "/buildings/{idBuilding}/delete", method = RequestMethod.GET)
    public @ResponseBody
    String deleteBuilding(@PathVariable Integer idBuilding, @RequestParam Integer id, Locale l) {
        JsonObject json = new JsonObject();
        try {
            mapService.deleteMapById(id);
            json.addProperty("success", Boolean.TRUE);
        } catch (MapInexistantException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Inexistant map : " + e.getId());
        } catch (MapInUseException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Map in use : " + e.getId());
        }
        return json.toString();
    }

    @RequestMapping(value = "/upload/", method = RequestMethod.POST)
    public @ResponseBody
    String upload() {
        JsonObject json = new JsonObject();
        json.addProperty("success", Boolean.TRUE);
        return json.toString();
    }

    @RequestMapping(value = "/buildings/{idBuilding}/addMap", method = RequestMethod.POST)
    public @ResponseBody String storeimages(@RequestPart("image") MultipartFile file, @PathVariable Integer idBuilding, @RequestParam String name) {
        JsonObject json = new JsonObject();
        
        Map newMap = new Map();
        newMap.setDescription(name);
        newMap.setBuilding(idBuilding);
        newMap.setPxHeight(0);
        newMap.setPxWidth(0);

        try {
            newMap = mapService.createMap(newMap);
        } catch (MapAlreadyExistsException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Map already exists : " + e.getName());
            return json.toString();
        }

        try {
            String fullFileName = "C:/images/" + idBuilding + "_" + newMap.getId() + ".jpg";
            FileOutputStream fos = new FileOutputStream(fullFileName);
            fos.write(file.getBytes());
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            json.addProperty("success", Boolean.FALSE);
            return json.toString();
        }
        
        json.addProperty("success", Boolean.TRUE);
        json.addProperty("data", new Gson().toJson(new MapPO(newMap)));
        return json.toString();
    }
}
