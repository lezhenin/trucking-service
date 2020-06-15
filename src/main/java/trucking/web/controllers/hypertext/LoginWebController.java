package trucking.web.controllers.hypertext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class LoginWebController {

    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return "/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

}
