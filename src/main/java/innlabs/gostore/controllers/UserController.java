package innlabs.gostore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import innlabs.gostore.user.User;
import innlabs.gostore.user.UserDAO;

/**
 * Created by mathias on 13/11/16.
 */
@Controller
public class UserController {

    /**
     * GET /create  --> Create a new innlabs.gostore.user and save it in the database.
     */
    @RequestMapping("/create")
    @ResponseBody
    public String create(String email, String name) {
        String userId = "";
        try {
            User user = new User();
            user.setUserName(name);
            user.setMail(email);
            userDao.save(user);
            userId = String.valueOf(user.getUserId());
        }
        catch (Exception ex) {
            return "Error creating the innlabs.gostore.user: " + ex.toString();
        }
        return "User succesfully created with id = " + userId;
    }

    /**
     * GET /delete  --> Delete the innlabs.gostore.user having the passed id.
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            User user = new User();
            user.setUserId(id);
            userDao.delete(user);
        }
        catch (Exception ex) {
            return "Error deleting the innlabs.gostore.user:" + ex.toString();
        }
        return "User succesfully deleted!";
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
            User user = userDao.findByEmail(email);
            userId = String.valueOf(user.getUserId());
        }
        catch (Exception ex) {
            return "User not found";
        }
        return "The innlabs.gostore.user id is: " + userId;
    }

    /**
     * GET /update  --> Update the email and the name for the innlabs.gostore.user in the
     * database having the passed id.
     */
    @RequestMapping("/update")
    @ResponseBody
    public String updateUser(long id, String email, String name) {
        try {
            User user = userDao.findOne(id);
            user.setMail(email);
            user.setUserName(name);
            userDao.save(user);
        }
        catch (Exception ex) {
            return "Error updating the innlabs.gostore.user: " + ex.toString();
        }
        return "User succesfully updated!";
    }


    @Autowired
    private UserDAO userDao;

}
