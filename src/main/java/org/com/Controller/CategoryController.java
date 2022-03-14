package org.com.Controller;

import org.com.Entity.Category;
import org.com.Entity.QueryInfo;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.CategoryService;
import org.com.util.BuildTreeUtil;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @ResponseBody
    @RequestMapping("/searchallcategory")
    public MyResponse SearchAllCategory(@RequestBody QueryInfo queryInfo){
        List<Category> categories = categoryService.SearchAllCategory(queryInfo);
        List<Category> categories1 = new LinkedList<>();
        int count=0;
        if (!categories.isEmpty()){
            List<Category> categoryList = BuildTreeUtil.BuildTreeForCategory(categories);

            for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize() && i < categoryList.size(); i++) {
                categories1.add(categoryList.get(i));
                count++;
            }
            return  new MyResponse("200","查询成功",String.valueOf(categoryList.size()),categories1,"");
        }else return new MyResponse("201","查询失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/update_status")
    public MyResponse UpdateStatus(@RequestParam int category_id, @RequestParam String category_status  , @RequestHeader("Authorization") String token){

        if (JwtUtil.VerifyToken(token)){
            int i = categoryService.UpdateStatusById(category_status, category_id);
            return new MyResponse("200","修改成功","",null,"");
        }
        return new MyResponse("201","修改失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/addcategory")
    public MyResponse AddCategory(@RequestBody Category category){
        Integer integer = categoryService.AddCategory(category);
        if (integer>0) return new MyResponse("200","添加成功","",null,"");
        else if (integer==-1)return new MyResponse("201","该分类名已存在","",null,"");
        else return new MyResponse("202","添加失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/getparent")
    public MyResponse GetParent(@RequestParam String category_level){
        List<Category> categoryList = categoryService.GetAllParent(category_level);
        List<Category> categoryList1 = BuildTreeUtil.BuildTreeForCategory(categoryList);

        return new MyResponse("200","","",categoryList1,"");

    }
}
