package trucking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/client/**").hasAnyRole("CLIENT")
                .antMatchers("/api/client/**").hasAnyRole("CLIENT")
                .antMatchers("/driver/**").hasAnyRole("DRIVER")
                .antMatchers("/api/driver/**").hasAnyRole("DRIVER")
                .antMatchers("/manager/**").hasAnyRole("MANAGER")
                .antMatchers("/api/manager/**").hasAnyRole("MANAGER")
                .antMatchers("/react/**").hasAnyRole("CLIENT", "DRIVER", "MANAGER")
                .antMatchers("/user/**").hasAnyRole("CLIENT", "DRIVER", "MANAGER")
            .and()
                .httpBasic()
            .and()
                .formLogin()
                .loginPage("/login");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("manager").password("{noop}manager123").roles("MANAGER");
        auth.inMemoryAuthentication().withUser("driver").password("{noop}driver123").roles("DRIVER");
        auth.inMemoryAuthentication().withUser("client").password("{noop}client123").roles("CLIENT");
    }

}
