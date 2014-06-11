package fr.utbm.test.service;

import fr.utbm.dao.exception.BuildingAlreadyExistsException;
import fr.utbm.dao.exception.BuildingInUseException;
import fr.utbm.dao.exception.BuildingInexistantException;
import fr.utbm.dao.exception.MapAlreadyExistsException;
import fr.utbm.dao.exception.MapInUseException;
import fr.utbm.dao.exception.MapInexistantException;
import fr.utbm.model.Building;
import fr.utbm.model.Map;
import fr.utbm.service.BuildingService;
import fr.utbm.service.MapService;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/testContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
public class TestMapService {

    @Resource
    SessionFactory sessionFactory;
    
    @Resource
    BuildingService buildingService;
    
    @Resource
    MapService mapService;

    @Test
    public void crudTest() throws BuildingInexistantException, BuildingInUseException, BuildingAlreadyExistsException, MapAlreadyExistsException, MapInUseException, MapInexistantException {
        for (Map m : mapService.getAllMaps()) {
            mapService.deleteMap(m);
        }
        for (Building b : buildingService.getAllBuildings()) {
            buildingService.deleteBuilding(b);
        }
        Assert.assertEquals(0, buildingService.getAllBuildings().size());
        Assert.assertEquals(0, mapService.getAllMaps().size());
                
        Building b = new Building();
        b.setName("TEST1");
        b = buildingService.createBuilding(b);

        Map m1 = new Map();
        m1.setBuilding(b);
        m1.setDescription("TEST1");
        
        m1 = mapService.createMap(m1);
        
        Map m2 = new Map();
        m2.setBuilding(b);
        m2.setDescription("TEST2");
        
        m2 = mapService.createMap(m2);
        
        b = buildingService.getBuildingByID(b.getId());
        
        Assert.assertEquals(2, mapService.getAllMaps().size());
        Assert.assertEquals(2, mapService.getMapsForBuildingID(b.getId()).size());
        Assert.assertEquals(1, buildingService.getAllBuildings().size());
        Assert.assertEquals(2, b.getMaps().size());
        
        mapService.deleteMapById(m1.getId());
        mapService.deleteMapById(m2.getId());
        buildingService.deleteBuilding(b);
    }
    
    @Test(expected = MapAlreadyExistsException.class)
    public void duplicateMap() throws MapAlreadyExistsException, BuildingAlreadyExistsException {
        Building batimentA = new Building();
        batimentA.setName("Batiment A");
        batimentA = buildingService.createBuilding(batimentA);
        
        Map m1 = new Map();
        m1.setBuilding(batimentA);
        m1.setDescription("TEST1");
        
        m1 = mapService.createMap(m1);
        
        Map m2 = new Map();
        m2.setBuilding(batimentA);
        m2.setDescription("TEST1");
        
        m2 = mapService.createMap(m2);  
    }
        
    @Test(expected = MapInexistantException.class)
    public void getInexistantMapById() throws MapInexistantException {
        mapService.getMapByID(99);
    }
}
