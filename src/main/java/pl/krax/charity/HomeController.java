package pl.krax.charity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @RequestMapping("/")
    public String homeAction(){
        return "/views/index";
    }
}
