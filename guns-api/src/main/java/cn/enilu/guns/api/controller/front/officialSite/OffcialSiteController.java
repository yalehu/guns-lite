package cn.enilu.guns.api.controller.front.officialSite;

import cn.enilu.guns.api.controller.BaseController;
import cn.enilu.guns.bean.entity.cms.Article;
import cn.enilu.guns.bean.enumeration.cms.ChannelEnum;
import cn.enilu.guns.bean.vo.front.Rets;
import cn.enilu.guns.bean.vo.offcialSite.Banner;
import cn.enilu.guns.bean.vo.offcialSite.News;
import cn.enilu.guns.bean.vo.offcialSite.Product;
import cn.enilu.guns.bean.vo.offcialSite.Solution;
import cn.enilu.guns.service.cms.ArticleService;
import cn.enilu.guns.service.cms.BannerService;
import cn.enilu.guns.utils.factory.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offcialSite")
public class OffcialSiteController extends BaseController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;
    @RequestMapping(method = RequestMethod.GET)
    public Object index(){
        Map<String,Boolean> showMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        showMap.put("banner",true);
        showMap.put("menu",true);
        showMap.put("newsList",true);
        showMap.put("productList",true);
        showMap.put("solutionList",true);
        showMap.put("footMenu",true);

        if(showMap.get("banner")==true){
            Banner banner = bannerService.queryIndexBanner();
            dataMap.put("banner",banner);
        }
        if(showMap.get("newsList") ==true){
            List<News> newsList = new ArrayList<>();
            List<cn.enilu.guns.bean.entity.cms.Article> articles = articleService.queryIndexNews();
            for(cn.enilu.guns.bean.entity.cms.Article article:articles){
                News news = new News();
                news.setDesc(article.getTitle());
                news.setUrl("/article?id="+article.getId());
                news.setSrc("https://nutz.cn/yvr/u/enilu/avatar");
                newsList.add(news);
            }
            dataMap.put("newsList",newsList);
        }
        if(showMap.get("productList") == true){
            List<Product> products = new ArrayList<>();
            cn.enilu.guns.utils.factory.Page<Article> articles = articleService.query(1,4, ChannelEnum.PRODUCT.getId());
            for(Article article:articles.getRecords()){
                Product product = new Product();
                product.setId(article.getId());
                product.setName(article.getTitle());
                product.setImg(article.getImg());
                products.add(product);
            }
            dataMap.put("productList",products);
        }

        if(showMap.get("solutionList") == true){
            List<Solution> solutions = new ArrayList<>();
            Page<Article> articles = articleService.query(1,4, ChannelEnum.SOLUTION.getId());
            for(Article article:articles.getRecords()){
                Solution solution = new Solution();
                solution.setId(article.getId());
                solution.setName(article.getTitle());
                solution.setImg(article.getImg());
                solutions.add(solution);
            }
            dataMap.put("solutionList",solutions);
        }
        Map map = new HashMap();
        map.put("show",showMap);
        map.put("data",dataMap);
        return Rets.success(map);

    }
}