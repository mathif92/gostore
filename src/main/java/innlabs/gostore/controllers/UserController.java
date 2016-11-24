package innlabs.gostore.controllers;

import innlabs.gostore.user.User;
import innlabs.gostore.user.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Created by mathias on 15/11/16.
 */

@RestController
@RequestMapping("/users")
public class UserController {


    @RequestMapping("/login/{userNameOrEmail}/{password}")
    @ResponseBody
    public ResponseEntity<String> login(@PathVariable("userNameOrEmail") String userNameOrEmail, @PathVariable(value = "password") String password) {
        try {
            User user = !userDao.findByMail(userNameOrEmail).isEmpty() ? userDao.findByMail(userNameOrEmail).get(0) : null;
            if(user == null) {
                user = !userDao.findByUserName(userNameOrEmail).isEmpty() ? userDao.findByUserName(userNameOrEmail).get(0) : null;
                if(user == null) {
                    return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    //we have to hash the received password to properly match the password in the database
                    if(user.getPassword().equals(password)) {
                        return new ResponseEntity<String>(HttpStatus.OK);
                    } else {
                        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            } else {
                //we have to hash the received password to properly match the password in the database
                if(user.getPassword().equals(password)) {
                    return new ResponseEntity<String>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch(Exception ex) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * POST /users  --> Creates a new user in the database
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userDao.save(user);
        }
        catch (Exception ex) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * GET /getByEmail  --> Return the id for the innlabs.gostore.user having the passed
     * email.
     */
    @RequestMapping("/getByEmail")
    @ResponseBody
    public String getByEmail(String email) {
        String userId = "";
        try {
            User user = !userDao.findByMail(email).isEmpty() ? userDao.findByMail(email).get(0) : null;
            userId = String.valueOf(user.getUserId());
        }
        catch (Exception ex) {
            return "User not found";
        }
        return "The innlabs.gostore.user id is: " + userId;
    }


    @Autowired
    UserDAO userDao;

}
