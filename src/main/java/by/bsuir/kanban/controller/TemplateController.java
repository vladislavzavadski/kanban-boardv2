package by.bsuir.kanban.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by vladislav on 07.04.17.
 */
@Controller
public class TemplateController {

    @RequestMapping(value = "/{page}")
    public String page(@PathVariable("page") String page){
        return page;
    }

    @RequestMapping(value = "/")
    public String start(){
        return "start";
    }

//    @RequestMapping(value = "/main", method = RequestMethod.GET)
//    public String main(){
//        return "main";
//    }

}
