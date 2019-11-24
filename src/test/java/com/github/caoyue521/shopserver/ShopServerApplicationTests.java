package com.github.caoyue521.shopserver;

import com.github.caoyue521.shopserver.dao.GoodDao;
import com.github.caoyue521.shopserver.dao.GoodDetailDao;
import com.github.caoyue521.shopserver.dao.SellerDao;
import com.github.caoyue521.shopserver.entity.*;
import com.github.caoyue521.shopserver.service.GoodService;
import com.github.caoyue521.shopserver.service.SellerService;
import com.github.caoyue521.shopserver.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest
class ShopServerApplicationTests {
    @Autowired
    SellerService sellerService;
    @Autowired
    GoodService goodService;
    @Autowired
    UserService userService;
    @Autowired
    GoodDetailDao goodDetailDao;
    @Autowired
    GoodDao goodDao;
    @Test
    void contextLoads() {
        for(int i=0;i<10;i++){
            User user = new User();
            user.setMobile("13111112222");
            user.setUid("caoyue"+i);
            user.setName("曹悦"+i);
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            user.setPhoto("http://");
            user.setRemark("");
            userService.save(user);
        }

    }
    @Test
    void initGood(){
        for(int i =0;i<10;i++){
            Seller seller = new Seller();
            seller.setName("first seller"+i);
            seller.setOwner("caoyue_"+i);
            sellerService.save(seller);
            Good good = new Good();
            List<String> banners = Arrays.asList("http://new-img3.ol-img.com/moudlepic/199_module_images/201706/59426699b17f2_944.jpg","http://new-img3.ol-img.com/moudlepic/199_module_images/201706/59426699b17f2_944.jpg","http://new-img3.ol-img.com/moudlepic/199_module_images/201706/59426699b17f2_944.jpg","http://new-img3.ol-img.com/moudlepic/199_module_images/201706/59426699b17f2_944.jpg");
            good.setBanners(banners);
            good.setSeller_id(seller.getId());
            good.setName("good_"+i);
            good.setPrice(10000);
            good.setPic("http://new-img3.ol-img.com/moudlepic/199_module_images/201706/59426699b17f2_944.jpg");
            good.setSummary("abcdefg_"+i);
            good.setType("pop");
            goodService.save(good);
            List<Comment> list = new ArrayList<>(10);
            for(int j=0;j<10;j++){
                Comment comment = new Comment();
                comment.setId(0L);
                comment.setMessage("comment_ "+j);
                comment.setUid("caoyue_"+j);
                comment.setGood_id(good.getId());
                list.add(comment);
            }
            good.setComments(list);

            goodService.save(good);
//            GoodDetail goodDetail = new GoodDetail();
//            goodDetail.setGood(good);
//            goodDetail.setId(0L);
//            goodDetail.setContent("content"+i);
//            goodDetailDao.save(goodDetail);
        }






    }
    public void initComment(){
        List<Good> list = goodDao.findAll();
        for(Good good:list){

        }
    }
    @Test
    public void initDetail(){
        List<Good> list = goodDao.findAll();
        for(Good good:list){
            GoodDetail goodDetail = new GoodDetail();
            goodDetail.setGood_id(good.getId());
            goodDetail.setContent("content"+ new Random().nextInt(100));
            goodDetailDao.save(goodDetail);
        }

    }

}
