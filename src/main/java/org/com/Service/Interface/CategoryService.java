package org.com.Service.Interface;

import org.com.Entity.Category;
import org.com.Entity.QueryInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public List<Category> SearchAllCategory(QueryInfo queryInfo);
    public int UpdateStatusById(String category_status,int category_id  );
    public List<Category> GetAllParent(String category_level);
    public Integer AddCategory(Category category);
}
