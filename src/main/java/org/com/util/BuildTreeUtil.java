package org.com.util;

import org.com.Entity.Category;
import org.com.Entity.User;

import java.util.LinkedList;
import java.util.List;

public class BuildTreeUtil {

    public final static List<User> BuildTreeForUser(List<User> Users){
//        List<User> UsersList = new LinkedList<>();
//
//        for (User item: Users) {
//
//            if (item.getUser_parent().equals("0")){
//                UsersList.add(item);
//            }
//        }
//
//        for (User item1 : UsersList){
//            for (User item2: Users){
//                String parent = String.valueOf(item1.getUser_id());
//                if (parent.equals(item2.getUser_parent())){
//                    try{
//                        System.out.println("item1:"+item1+" "+"item2:"+item2);
//                        item1.setChildren(item2 );
//                        //item1.setChildren(item2);
//                        System.out.println("item1:"+item1+" "+"item2:"+item2);
//                    }catch (Exception e){
//                        System.out.println("这里出错啦");
//                    }
//
//                }
//            }
//        }
//        return UsersList;
        return null;

    }
    /**
     *
    public final static  List<Category>  BuildTreeForCategory(List<Category> categories){
        List<Category> categoryList = new LinkedList<>();

        for (Category item: categories) {
            if (item.getCategory_parent()== 0){
                categoryList.add(item);
            }
        }

        for (Category item1 : categoryList){
            for (Category item2: categories){
                if (item1.getCategory_parent()==item2.getCategory_id()){
                    try{
                        item1.setChildren(item2 );
                    }catch (Exception e){
                        System.out.println("这里出错啦");
                    }

                }
            }
        }
        return categoryList;
    }
     */
    public final static  List<Category>  BuildTreeForCategory(List<Category> categories ) {
        List<Category> categoryList = GetRoot(categories);
        for (Category category:categoryList){
            category.setChildren(BuildChildren(categories,category.getCategory_id()));
        }
        return categoryList;
    }
    public final static List<Category> BuildChildren(List<Category> categories,int category_id){
        List<Category> categoryList = new LinkedList<>();
        for (Category category:categories){
            if (category.getCategory_parent()==category_id) {
                categoryList.add(category);
            }
        }
        for (Category category:categoryList){
            category.setChildren(BuildChildren(categories,category.getCategory_id()));;
        }
        if (categoryList.size()==0){
            return new LinkedList<>();
        }
        return categoryList;
    }
    /**
     *
     * @return :返回root
     * */
    public final static List<Category> GetRoot(List<Category> categories  ){
        List<Category> categoryList = new LinkedList<>();
        for (Category category : categories){
            if (category.getCategory_parent()==0) categoryList.add(category);
        }
        return categoryList;
    }
}
