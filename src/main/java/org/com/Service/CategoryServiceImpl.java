package org.com.Service;

import org.com.Entity.Category;
import org.com.Entity.QueryInfo;
import org.com.Mapper.CategoryMapper;
import org.com.Service.Interface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> SearchAllCategory(QueryInfo queryInfo) {
        if (queryInfo.getQuerytext().equals("")){
            List<Category> categories = categoryMapper.SearchAllCategory();
            return categories;
        }else return categoryMapper.SearchByName(queryInfo.getQuerytext());

    }

    @Override
    public int UpdateStatusById(String category_status, int category_id  ) {
        List<Category> categoryList = categoryMapper.SearchAllCategory();
        List<Integer> IDlist = new LinkedList<Integer>( ){{add(category_id);}};
        int i=0,j=0,count=0;
        for (i=0;i<categoryList.size();i++){
            for (j=0;j<IDlist.size();j++){
                if (categoryList.get(i).getCategory_parent()==IDlist.get(j)) {
                    IDlist.add(categoryList.get(i).getCategory_id());
                    categoryList.remove(categoryList.get(i));
                    i=0;
                    j=0;
                }
            }
        }
        for (Integer integer:IDlist){
            Integer k = categoryMapper.UpdateStatusById(category_status, integer);
            count=count+k;
        }
        return count;
    }

    @Override
    public List<Category> GetAllParent(String category_level) {
        return categoryMapper.GetALlParent(category_level);
    }

    @Override
    public Integer AddCategory(Category category) {
        int flag=0;
        List<Category> categoryList = categoryMapper.SearchAllCategory();
        for (Category category1 : categoryList){
            if (category1.getCategory_name().equals(category.getCategory_name())){
                flag=1;
            }
        }
        if (flag==0) return categoryMapper.Insert(category);
        else return -1;
    }


}
