package innlabs.gostore.controllers;

import innlabs.gostore.category.Category;
import innlabs.gostore.category.CategoryDAO;
import innlabs.gostore.enums.CategoryEnum;
import innlabs.gostore.product.Product;
import innlabs.gostore.product.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by mathias on 03/12/16.
 */
@RestController
@RequestMapping("/dataloader")
public class DataLoaderController {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CategoryDAO categoryDAO;

    private static String[] carBrands = {"Volkswagen", "Fiat", "Chevrolet", "Nissan", "Toyota", "Mitsubishi", "Suzuki", "Peugeot", "Renault", "Citröen", "Ford", "Hyundai", "Kia"};

    private static String[] itemDescriptionsArray = {"Producto en exelentes condiciones", "Impecable producto a la venta", "Oferte antes de agotar stock por este producto",
                                                "Gran oferta para aprovechar ya!", "Oferta de lanzamiento para este producto"};

    private static List<String> itemDescriptionsList = new ArrayList<String>();

    private static Map<String, List<String>> carModelsForEachBrand = new HashMap<String, List<String>>();

    static {

        for (String carBrand: carBrands) {

            List<String> volkswagenModels = new ArrayList<String>();
            volkswagenModels.add("Gol");
            volkswagenModels.add("Golf");
            volkswagenModels.add("Suran");
            volkswagenModels.add("Beetle");
            volkswagenModels.add("Up");
            volkswagenModels.add("Bora");
            volkswagenModels.add("Vento");
            volkswagenModels.add("Amarok");
            carModelsForEachBrand.put("Volkswagen", volkswagenModels);

            List<String> fiatModels = new ArrayList<String>();
            fiatModels.add("Uno");
            fiatModels.add("Palio");
            fiatModels.add("Siena");
            fiatModels.add("Punto");
            fiatModels.add("Strada");
            fiatModels.add("Idea");
            carModelsForEachBrand.put("Fiat", fiatModels);

            List<String> chevroletModels = new ArrayList<String>();
            chevroletModels.add("Onix");
            chevroletModels.add("Corsa");
            chevroletModels.add("Corsa Classic");
            chevroletModels.add("Prisma");
            chevroletModels.add("Sail");
            chevroletModels.add("S10");

            itemDescriptionsList = Arrays.asList(itemDescriptionsArray);

        }

    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> insertProducts() {
        try {

            List<Category> categories = categoryDAO.findAll();
            for (Category category : categories) {
                createProductsForCategory(category);
            }

            return new ResponseEntity<String>(HttpStatus.OK);
        } catch(Exception ex) {

            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void createProductsForCategory(Category category) throws Exception {
        int categoryId = (int) category.getCategoryId();
        switch(categoryId) {
            case CategoryEnum.CARS_CATEGORY :
                createCars();
                break;


        }

    }

    private void createCars() throws Exception{
        for (String carBrand : carBrands) {
            List<String> carModels = carModelsForEachBrand.get(carBrand);
            //create 250.000 cars by model which will make 250.000 * 20 = 5.000.000 cars = 5 GB hard drive
            for (int i = 0; i < 250000; i++) {
                for (String carModel : carModels) {
                    Product car = new Product();
                    //shuffle a collection to set the product's name
                    car.setName(carModel);
                    //shuffle a description collection to set the product's description
                    car.setCreateDate(new Date());
                    car.setModifyDate(new Date());
                    car.setQuantityOnHand(1);
                    //set some price from a price collection created
                    int price = ThreadLocalRandom.current().nextInt(10000, 40000 + 1);
                    car.setPrice(new BigDecimal(price));

                    Collections.shuffle(itemDescriptionsList);
                    car.setDescription(itemDescriptionsList.get(0));
                    productDAO.save(car);

                    //de-reference de object to make it elegible for Garbage Collection
                    car = null;
                }
                System.gc();
            }
        }
    }

}
