package fr.utbm.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.utbm.controller.po.BuildingPO;
import fr.utbm.dao.exception.BuildingAlreadyExistsException;
import fr.utbm.dao.exception.BuildingInUseException;
import fr.utbm.dao.exception.BuildingInexistantException;
import fr.utbm.model.Building;
import fr.utbm.service.BuildingService;
import fr.utbm.service.MapService;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for building's management
 *
 * @author Guigeek
 */
@Controller
public class BuildingsController {

    @Resource
    BuildingService buildingService;

    @Resource
    MapService mapService;

    /**
     * Returns the list of all buildings stored in the database
     *
     * @return a JSON array of buildings
     */
    @RequestMapping(value = "/buildings", method = RequestMethod.GET)
    public @ResponseBody
    String getBuildings() {
        ArrayList<BuildingPO> buildings = new ArrayList<BuildingPO>();
        for (Building b : buildingService.getAllBuildings()) {
            buildings.add(new BuildingPO(b));
        }
        return new Gson().toJson(buildings);
    }

    /**
     * Adds building with the given name
     *
     * @param name the name of the new building
     * @return a JSON response
     */
    @RequestMapping(value = "/buildings/add", method = RequestMethod.GET)
    public @ResponseBody
    String addBuilding(@RequestParam String name) {
        JsonObject json = new JsonObject();
        Building newBuilding = new Building();
        newBuilding.setName(name);
        try {
            newBuilding = buildingService.createBuilding(newBuilding);
        } catch (BuildingAlreadyExistsException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Building already exists : " + e.getName());
            return json.toString();
        }
        json.addProperty("success", Boolean.TRUE);
        json.addProperty("data", new Gson().toJson(new BuildingPO(newBuilding, 0)));
        return json.toString();
    }

    /**
     * Deletes the building with the given id
     *
     * @param id the id of the building to delete
     * @return a JSON response
     */
    @RequestMapping(value = "/buildings/delete", method = RequestMethod.GET)
    public @ResponseBody
    String deleteBuilding(@RequestParam Integer id) {
        JsonObject json = new JsonObject();
        try {
            buildingService.deleteBuildingById(id);
            json.addProperty("success", Boolean.TRUE);
        } catch (BuildingInexistantException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Inexistant building : " + e.getId());
        } catch (BuildingInUseException e) {
            json.addProperty("success", Boolean.FALSE);
            json.addProperty("exception", "Building in use : " + e.getId());
        }
        return json.toString();
    }
}
