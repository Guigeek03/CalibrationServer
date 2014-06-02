package fr.utbm.test.service;

import fr.utbm.dao.exception.BuildingAlreadyExistsException;
import fr.utbm.dao.exception.BuildingInUseException;
import fr.utbm.dao.exception.BuildingInexistantException;
import fr.utbm.model.Building;
import fr.utbm.service.BuildingService;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void crudTest() throws BuildingInexistantException, BuildingInUseException, BuildingAlreadyExistsException {
        
        /**for (Building b : buildingService.getAllBuildings()) {
            buildingService.deleteBuilding(b);
        }
        Assert.assertEquals(0, buildingService.getAllBuildings().size());**/
                
        Building b = new Building();
        b.setName("TEST_BUILDING");
        b = buildingService.createBuilding(b);
        
        buildingService.deleteBuilding(b);

        //Assert.assertEquals(1, buildingService.getAllBuildings().size());
        
       // Assert.assertEquals("Batiment A", buildingService.getBuildingByName("Batiment A").getName());
    }
}
