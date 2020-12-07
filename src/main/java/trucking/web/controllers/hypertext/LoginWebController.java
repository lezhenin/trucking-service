package trucking.web.controllers.hypertext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginWebController {

    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return "/index";
    }


    @RequestMapping(value = {"/react/client/index", })
    public String ReactIndex() {
        return "/react/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

}
