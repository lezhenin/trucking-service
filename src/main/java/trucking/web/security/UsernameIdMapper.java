package trucking.web.security;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Service
public class UsernameIdMapper {

    private final Map<String, Long> map = new HashMap<>();

    public void put(String username, Long id) {
        map.put(username, id);
    }

    public Long map(String username) {
        return map.get(username);
    }

    public Long map(Principal principal) {
        return map.get(principal.getName());
    }


}
