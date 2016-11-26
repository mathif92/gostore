package innlabs.gostore.controllers;

import com.google.gson.Gson;
import innlabs.gostore.category.Category;
import innlabs.gostore.category.CategoryDAO;
import innlabs.gostore.list.CategoryList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mathias on 24/11/16.
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryDAO categoryDAO;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getAllCategories() {
        try {
            List<Category> categories = categoryDAO.findAll();
            CategoryList categoryList = new CategoryList();
            categoryList.categories = categoryList.convertCategoriesToCategoriesDTO(categories);
            Gson gson = new Gson();
            return new ResponseEntity<String>(gson.toJson(categoryList), HttpStatus.OK);
        } catch(Exception ex) {

            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
