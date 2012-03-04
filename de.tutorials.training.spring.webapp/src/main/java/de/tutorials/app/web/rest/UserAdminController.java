package de.tutorials.app.web.rest;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.tutorials.app.useradmin.model.User;
import de.tutorials.app.useradmin.service.UserAdminService;

@Controller
@RequestMapping("/useradmin")
public class UserAdminController {
    @Inject
    protected UserAdminService userAdminService;

    @RequestMapping(method = { RequestMethod.POST }, value = "/registeruser", consumes = { "application/json" }, produces = { "application/json" })
    @ResponseBody
    public User registerUser(@RequestBody User user) {
	user.setId(null);
	return userAdminService.register(user);
    }

    @RequestMapping(method = { RequestMethod.GET }, value = "/listusers", produces = { "application/json" })
    public @ResponseBody
    List<User> listUsers() {
	return userAdminService.findAllUsers();
    }

    @RequestMapping(value = "ping")
    public void ping() {
	System.out.println("ping " + new Date());
    }
}
