package org.com;

import org.com.Entity.Article;
import org.com.Mapper.ArticleMapper;
import org.com.Service.Interface.ArticleService;
import org.com.util.CreatFileUtil;
import org.com.util.PythonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class creatFileTest {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;
    @Test
    public void creatFileTest1() throws IOException {

        List<Article> articleList = articleService.GetSimilarArticle(3);
        System.out.println(articleList.toString());
    }
}
