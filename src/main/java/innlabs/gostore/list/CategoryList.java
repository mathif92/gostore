package innlabs.gostore.list;

import innlabs.gostore.category.Category;
import innlabs.gostore.dto.CategoryDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathias on 24/11/16.
 */
public class CategoryList {

    public List<CategoryDTO> categories;

    public List<CategoryDTO> convertCategoriesToCategoriesDTO(List<Category> categories) {
        List<CategoryDTO> categoryDTOList = new ArrayList<CategoryDTO>();

        for (Category category : categories) {
            categoryDTOList.add(new CategoryDTO(category));
        }

        return categoryDTOList;
    }

}
