package fr.utbm.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController {
    
    @Resource
    AccessPointsController apController;
            
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        apController.lookupAccessPoints();
        return "index";
    }
}
