package org.com.Controller;


import org.com.Entity.Book;
import org.com.Entity.QueryInfo;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.BookService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @ResponseBody
    @RequestMapping("/querybooks")
    public MyResponse QueryBooksAll(@RequestBody QueryInfo queryInfo,@RequestHeader("Authorization") String  token){

        int count = 0;
        if (JwtUtil.VerifyToken(token)){//JWT验证
            List<Book> bookList = new LinkedList<>();
            List<Book> books = bookService.QueryBookAll();

            for (int i=((queryInfo.getPagenum()-1)*queryInfo.getPagesize());count<queryInfo.getPagesize()&&i<books.size();i++){
                bookList.add(bookList.get(i));
                count++;
            }
            return new MyResponse("200","查询成功",bookList);
        }else return new MyResponse("201","JWT验证失败",null);

    }
}
