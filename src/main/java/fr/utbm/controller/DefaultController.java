package fr.utbm.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Default controller of the server
 * 
 * @author Guigeek
 */
@Controller
public class DefaultController {
    
    @Resource
    AccessPointsController apController;
           
    /**
     * Returns the index webpage and checks for access points
     * @return the index.jsp webpage
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        apController.lookupAccessPoints();
        return "index";
    }
}
