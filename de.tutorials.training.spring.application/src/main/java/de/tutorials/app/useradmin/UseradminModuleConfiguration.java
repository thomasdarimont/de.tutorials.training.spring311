package de.tutorials.app.useradmin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.tutorials.app.useradmin.service.DefaultUserAdminService;
import de.tutorials.app.useradmin.service.UserAdminService;

@Configuration
public class UseradminModuleConfiguration {

    @Bean
    public UserAdminService userAdminService() {
       /*
	* we don't need a bean definition for this service since it is
	* automatically picked up via component scanning... we leave this bean definition just for demo purposes        
	*/
	DefaultUserAdminService userAdminService = new DefaultUserAdminService();
	return userAdminService;
    }
}