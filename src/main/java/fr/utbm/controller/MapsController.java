package fr.utbm.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.utbm.controller.po.MapPO;
import fr.utbm.dao.exception.BuildingInexistantException;
import fr.utbm.dao.exception.MapAlreadyExistsException;
import fr.utbm.dao.exception.MapInUseException;
import fr.utbm.dao.exception.MapInexistantException;
import fr.utbm.model.Map;
import fr.utbm.service.BuildingService;
import fr.utbm.service.MapService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
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
        try {
            newMap.setDescription(name);
            newMap.setBuilding(buildingService.getBuildingByID(id));
            newMap.setPxHeight(pxHeight);
            newMap.setPxWidth(pxWidth);

            newMap = mapService.createMap(newMap);
        } catch (MapAlreadyExistsException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Map already exists : " + e.getName());
            return json.toString();
        } catch (BuildingInexistantException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Building inexistant : " + e.getId());
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
        } catch (DataIntegrityViolationException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Map in use : " + id);
        }
        return json.toString();
    }

    @RequestMapping(value = "/buildings/{idBuilding}/addMap", method = RequestMethod.POST)
    public @ResponseBody
    String storeimages(@RequestPart("image") MultipartFile file, @PathVariable Integer idBuilding, @RequestParam String name) {
        JsonObject json = new JsonObject();

        Map newMap = new Map();
        try {
            newMap.setDescription(name);
            newMap.setBuilding(buildingService.getBuildingByID(idBuilding));
            newMap.setPxHeight(0);
            newMap.setPxWidth(0);

            newMap = mapService.createMap(newMap);
        } catch (MapAlreadyExistsException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Map already exists : " + e.getName());
            return json.toString();
        } catch (BuildingInexistantException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Building inexistant : " + e.getId());
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

    @RequestMapping(value = "/buildings/{idBuilding}/retrieveMap", method = RequestMethod.GET)
    public void retrieveMap(HttpServletResponse response, @PathVariable Integer idBuilding, @RequestParam Integer idFloor) {
        byte[] bytes = null;
        try {
            File file = new File("C:/images/" + idBuilding + "_" + idFloor + ".jpg");
            bytes = FileCopyUtils.copyToByteArray(file);
            System.out.println(bytes.toString());
            //response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            response.setContentLength(bytes.length);
            System.out.println(bytes.length);
            response.setContentType("image/jpeg");
            ServletOutputStream sos = response.getOutputStream();
            sos.write(bytes);
            //FileCopyUtils.copy(bytes, sos);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //return null;
    }
}
