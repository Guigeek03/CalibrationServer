package fr.utbm.test.service;

import fr.utbm.dao.exception.BuildingAlreadyExistsException;
import fr.utbm.dao.exception.BuildingInUseException;
import fr.utbm.dao.exception.BuildingInexistantException;
import fr.utbm.dao.exception.MapAlreadyExistsException;
import fr.utbm.dao.exception.MapInUseException;
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
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class TestBuildingService {

    @Resource
    SessionFactory sessionFactory;
    
    @Resource
    BuildingService buildingService;

    @Resource
    MapService mapService;
    
    @Test
    public void crudTest() throws BuildingInexistantException, BuildingInUseException, BuildingAlreadyExistsException, MapInUseException {
        for (Map m : mapService.getAllMaps()) {
            mapService.deleteMap(m);
        }
        for (Building b : buildingService.getAllBuildings()) {
            buildingService.deleteBuilding(b);
        }

        Assert.assertEquals(0, buildingService.getAllBuildings().size());

        Building batimentA = new Building();
        batimentA.setName("Batiment A");
        batimentA = buildingService.createBuilding(batimentA);
        
        Building batimentB = new Building();
        batimentB.setName("Batiment B");
        batimentB = buildingService.createBuilding(batimentB);
       
        Assert.assertEquals(2, buildingService.getAllBuildings().size());
        Assert.assertEquals("Batiment A", buildingService.getBuildingByName("Batiment A").getName());
        Assert.assertEquals(batimentB, buildingService.getBuildingByID(batimentB.getId()));
        
        buildingService.deleteBuilding(batimentA);
        buildingService.deleteBuilding(batimentB);
        
        Assert.assertEquals(0, buildingService.getAllBuildings().size());
    }
    
    @Test(expected = BuildingAlreadyExistsException.class)
    public void duplicateBuilding() throws BuildingAlreadyExistsException {
        Building batimentA = new Building();
        batimentA.setName("Batiment A");
        buildingService.createBuilding(batimentA);
        
        Building batimentB = new Building();
        batimentB.setName("Batiment A");
        buildingService.createBuilding(batimentB);      
    }
    
    @Test(expected = DataIntegrityViolationException.class)
    public void deleteBuildingInUse() throws BuildingAlreadyExistsException, BuildingInUseException, MapAlreadyExistsException, BuildingInexistantException {
        Building batimentC = new Building();
        batimentC.setName("Batiment C");
        buildingService.createBuilding(batimentC);
        
        mapService.createMap(new Map("Etage 1", 0, 0, 0., 0., "", batimentC));
        
        buildingService.deleteBuilding(batimentC);
    }
    
    @Test(expected = BuildingInexistantException.class)
    public void getInexistantBuildingById() throws BuildingInexistantException {
        buildingService.getBuildingByID(99);
    }
    
    @Test(expected = BuildingInexistantException.class)
    public void getInexistantBuildingByName() throws BuildingInexistantException {
        buildingService.getBuildingByName("XXX");
    }
}
