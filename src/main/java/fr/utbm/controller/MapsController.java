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

/**
 * Controller for map's management
 *
 * @author Guigeek
 */
@Controller
public class MapsController {

    @Resource
    BuildingService buildingService;

    @Resource
    MapService mapService;

    private static final String imageDirectory = "C:/images/";
    
    /**
     * Retrieves maps for the given building id
     *
     * @param id the building id
     * @return a JSON array of maps
     */
    @RequestMapping(value = "/buildings/{id}", method = RequestMethod.GET)
    public @ResponseBody
    String getMaps(@PathVariable Integer id) {
        ArrayList<MapPO> maps = new ArrayList<MapPO>();
        for (Map m : mapService.getMapsForBuildingID(id)) {
            maps.add(new MapPO(m));
        }
        return new Gson().toJson(maps);
    }

    
    /**
     * Deletes a map with given building id and id
     *
     * @param idBuilding the building id
     * @param id the map id
     * @return a JSON response
     */
    @RequestMapping(value = "/buildings/{idBuilding}/delete", method = RequestMethod.GET)
    public @ResponseBody
    String deleteBuilding(@PathVariable Integer idBuilding, @RequestParam Integer id) {
        JsonObject json = new JsonObject();
        try {
            mapService.deleteMapById(id);
            File file = new File(imageDirectory + idBuilding + "_" + id + ".jpg");
            if (file.exists()) {
                file.delete();
            }
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

    /**
     * Receives a new map (with image file) and stores it in the database
     * @param file the image file
     * @param idBuilding the building id
     * @param name the name of the map
     * @return a JSON response
     */
    @RequestMapping(value = "/buildings/{idBuilding}/addMap", method = RequestMethod.POST)
    public @ResponseBody
    String addMap(@RequestPart("image") MultipartFile file, @PathVariable Integer idBuilding, @RequestParam String name, @RequestParam Integer pxWidth, @RequestParam Integer pxHeight) {
        JsonObject json = new JsonObject();

        Map newMap = new Map();
        try {
            newMap.setDescription(name);
            newMap.setBuilding(buildingService.getBuildingByID(idBuilding));
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

        try {
            String fullFileName = imageDirectory + idBuilding + "_" + newMap.getId() + ".jpg";
            FileOutputStream fos = new FileOutputStream(fullFileName);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException ex) {
            json.addProperty("success", Boolean.FALSE);
            return json.toString();
        }

        json.addProperty("success", Boolean.TRUE);
        json.addProperty("data", new Gson().toJson(new MapPO(newMap)));
        return json.toString();
    }

    /**
     * Sends a map
     * @param response the HTTP response
     * @param idBuilding the building id
     * @param idFloor the floor id
     */
    @RequestMapping(value = "/buildings/{idBuilding}/retrieveMap", method = RequestMethod.GET)
    public void retrieveMap(HttpServletResponse response, @PathVariable Integer idBuilding, @RequestParam Integer idFloor) {
        byte[] bytes = null;
        try {
            File file = new File(imageDirectory + idBuilding + "_" + idFloor + ".jpg");
            bytes = FileCopyUtils.copyToByteArray(file);
            response.setContentLength(bytes.length);
            response.setContentType("image/jpeg");
            ServletOutputStream sos = response.getOutputStream();
            sos.write(bytes);
        } catch (IOException ex) {
        }
    }
}
