package innlabs.gostore.controllers;

import com.google.gson.Gson;
import innlabs.gostore.category.CategoryDAO;
import innlabs.gostore.category.SubCategory;
import innlabs.gostore.category.SubCategoryDAO;
import innlabs.gostore.list.ProductList;
import innlabs.gostore.paging.PagingCategoryProductsDAO;
import innlabs.gostore.paging.PagingSubCategoryProductsDAO;
import innlabs.gostore.product.Product;
import innlabs.gostore.product.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mathias on 24/11/16.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final int PAGE_SIZE = 20;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    SubCategoryDAO subCategoryDAO;

    @Autowired
    PagingSubCategoryProductsDAO pagingSubCategoryProductsDAO;

    @Autowired
    PagingCategoryProductsDAO pagingCategoryProductsDAO;

    @Autowired
    ProductDAO productDAO;

    @RequestMapping(value = "/category/{categoryId}/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getProductsByCategory(@PathVariable("categoryId") Long categoryId, @PathVariable("pageNumber") Integer pageNumber) {
        try {
            PageRequest pageRequest =
                    new PageRequest(pageNumber - 1, PAGE_SIZE,
                            Sort.Direction.DESC, "productId");
            Page<Product> products = pagingCategoryProductsDAO.findAllCategoryProducts(pageRequest, categoryId);
            ProductList productList = new ProductList();
            productList.products = products.getContent();
            Gson gson = new Gson();
            return new ResponseEntity<String>(gson.toJson(productList), HttpStatus.OK);
        } catch(Exception ex) {

            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/subcategory/{subCategoryId}/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getProductsBySubCategory(@PathVariable("subCategoryId") Long subCategoryId, @PathVariable("pageNumber") Integer pageNumber) {
        try {
            PageRequest pageRequest =
                    new PageRequest(pageNumber - 1, PAGE_SIZE,
                            Sort.Direction.DESC, "productId");
            Page<Product> products = pagingSubCategoryProductsDAO.findAllSubCategoryProducts(pageRequest, subCategoryId);
            ProductList productList = new ProductList();
            productList.products = products.getContent();
            Gson gson = new Gson();
            return new ResponseEntity<String>(gson.toJson(productList), HttpStatus.OK);
        } catch(Exception ex) {

            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
