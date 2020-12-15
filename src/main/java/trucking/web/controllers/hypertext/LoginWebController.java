package trucking.web.controllers.hypertext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import trucking.web.datatransfer.UserData;

import java.security.Principal;


@Controller
public class LoginWebController {

    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return "/index";
    }


    @RequestMapping(value = {"/react", })
    public String react() {
        return "/react/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @RequestMapping("/user")
    @ResponseBody
    public UserData user(Authentication authentication) {
         String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().get();
         String username = authentication.getName();
         return new UserData(username, role);
    };

}
