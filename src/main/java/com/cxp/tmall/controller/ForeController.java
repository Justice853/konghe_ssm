package com.cxp.tmall.controller;

import com.cxp.tmall.comparator.*;
import com.cxp.tmall.database.*;
import com.cxp.tmall.service.*;
import com.github.pagehelper.PageHelper;
import com.sun.deploy.net.HttpUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

    @RequestMapping("forehome")
    public String home(Model model){
        List<CategoryDo> cs = categoryService.list ();
        productService.fill ( cs );
        productService.fillByRow ( cs );
        model.addAttribute ( "cs",cs );
        return "fore/home";
    }
    @RequestMapping("foreregister")
    public String register(Model model, UserDo userDo){
        String name = userDo.getName ();
        name = HtmlUtils.htmlEscape ( name );
        userDo.setName ( name );
        boolean exist = userService.isExist ( name );
        if(exist){
            String m = "用户名被使用";
            model.addAttribute ( "msg",m );
            model.addAttribute ( "user",null );
            return "fore/register";
        }
        userService.add ( userDo );
        return "redirect:registerSuccessPage";
    }
    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session){
        name = HtmlUtils.htmlEscape ( name );
        UserDo userDo = userService.get ( name,password );
        if(null == userDo){
            model.addAttribute ( "msg","用户名密码错误" );
            return "fore/login";
        }
        session.setAttribute ( "user",userDo );
        return "redirect:forehome";
    }
    @RequestMapping("forelogout")
    public String logout(HttpSession session){
        session.removeAttribute ( "user" );
        return "redirect:forehome";
    }
    @RequestMapping("foreproduct")
    public String product(int pid,Model model){
        ProductDo p = productService.get ( pid );
        List<ProductImageDo> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
        List<ProductImageDo> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        List<PropertyValueDo>pvs  =propertyValueService.list ( p.getId () );
        List<ReviewDo> reviews = reviewService.list ( p.getId () );
        productService.setSaleAndReviewNumber ( p );
        model.addAttribute ( "reviews",reviews );
        model.addAttribute ( "p",p );
        model.addAttribute ( "pvs",pvs );
        return "fore/product";
    }
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checklogin(HttpSession session){
        UserDo userDo =(UserDo)session.getAttribute ( "user" );
        if(null!=userDo)
            return "success";
        return "fail";
    }
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name")String name,@RequestParam("password")String password,HttpSession session){
        name= HtmlUtils.htmlEscape ( name );
        UserDo userDo=userService.get ( name,password );
        if(null==userDo){
            return "fail";
        }
        session.setAttribute ( "user",userDo );
        return "success";
    }
    @RequestMapping("forecategory")
    public String category(int cid,String sort,Model model){
        CategoryDo c = categoryService.get ( cid );
        productService.fill ( c );
        productService.setSaleAndReviewNumber ( c.getProducts () );

        if(null!=sort){
            switch (sort){
                case "revice":
                    Collections.sort ( c.getProducts (),new ProductReviewComparator () );
                    break;
                case "date":
                    Collections.sort ( c.getProducts (),new ProductDateComparator () );
                    break;
                case "saleCount":
                    Collections.sort ( c.getProducts (),new ProductSaleCountComparator () );
                    break;
                case "price":
                    Collections.sort ( c.getProducts (),new ProductPriceComparator () );
                    break;
                case "all":
                    Collections.sort ( c.getProducts (),new ProductAllComparator () );
                    break;
            }
        }
        model.addAttribute ( "c",c );
        return "fore/category";
    }
    @RequestMapping("foresearch")
    public String search(String keyword,Model model){
        PageHelper.offsetPage ( 0,20 );
        List<ProductDo> ps= productService.search(keyword);
        productService.setSaleAndReviewNumber ( ps );
        model.addAttribute ( "ps",ps );
        return "fore/searchResult";
    }
    @RequestMapping("forebuyone")
    public String buyone(int pid,int num,HttpSession session){
        ProductDo productDo = productService.get ( pid );
        int oiid=0;
        UserDo userDo =(UserDo) session.getAttribute ( "user" );
        boolean found=false;
        List<OrderItemDo>ois = orderItemService.listByUser ( userDo.getId () );
        for(OrderItemDo orderItemDo :ois){
            if(orderItemDo.getProductDo ().getId ().intValue ()==productDo.getId ().intValue ()){
                orderItemDo.setNumber ( orderItemDo.getNumber ()+num );
                orderItemService.update ( orderItemDo );
                found=true;
                oiid=orderItemDo.getId ();
                break;
            }
        }
        if(!found){
            OrderItemDo orderItemDo = new OrderItemDo ();
            orderItemDo.setUid ( userDo.getId () );
            orderItemDo.setNumber ( num );
            orderItemDo.setPid ( pid );
            orderItemService.add ( orderItemDo );
            oiid=orderItemDo.getId ();
        }
        return "redirect:forebuy?oiid="+oiid;
    }
    @RequestMapping("forebuy")
    public String buy(Model model,String []oiid,HttpSession session){
        List<OrderItemDo> ois =new ArrayList<> (  );
        float total =0;

        for(String strid : oiid){
            int id = Integer.parseInt ( strid );
            OrderItemDo orderItemDo =orderItemService.get ( id );
            total+=orderItemDo.getProductDo ().getPromotePrice ()*orderItemDo.getNumber ();
            ois.add ( orderItemDo );
        }
        session.setAttribute ( "ois",ois );
        model.addAttribute ( "total",total );
        return "fore/buy";
    }
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCary(int pid,int num,Model model,HttpSession session){
        ProductDo productDo = productService.get ( pid );
        UserDo userDo = (UserDo)session.getAttribute ( "user" );
        boolean found =false;
        List<OrderItemDo>ois = orderItemService.listByUser ( userDo.getId () );

        for(OrderItemDo orderItemDo :ois){
            if(orderItemDo.getProductDo ().getId ().intValue ()==productDo.getId ().intValue ()){
                orderItemDo.setNumber ( orderItemDo.getNumber ()+num );
                orderItemService.update ( orderItemDo );
                found=true;
                break;
            }
        }
        if(!found){
            OrderItemDo orderItemDo = new OrderItemDo ();
            orderItemDo.setUid ( userDo.getId () );
            orderItemDo.setNumber ( num );
            orderItemDo.setPid ( pid );
            orderItemService.add ( orderItemDo );
        }
        return "success";
    }
    @RequestMapping("forecart")
    public String cart(Model model,HttpSession session){
        UserDo userDo = (UserDo) session.getAttribute ( "user" );
        List<OrderItemDo>ois = orderItemService.listByUser ( userDo.getId () );
        model.addAttribute ( "ois",ois );
        return "fore/cart";
    }
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(Model model ,HttpSession session,int pid,int number){
        UserDo userDo = (UserDo)session.getAttribute ( "user" );
        if(null==userDo){
            return "fail";
        }
        List<OrderItemDo> ois  = new ArrayList<> (  );
        for(OrderItemDo oi :ois){
            if(oi.getProductDo ().getId ().intValue ()==pid){
                oi.setNumber ( number );
                orderItemService.update ( oi );
                break;
            }
        }
        return "success";
    }
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(int oiid,HttpSession session,Model model){
        UserDo userDo = (UserDo)session.getAttribute ( "user" );
        if(null==userDo){
            return "fail";
        }
        orderItemService.delete ( oiid );
        return "success";
    }

    @RequestMapping("forecreateOrder")
    public String createOrder(HttpSession session,OrderDo orderDo,Model model){
        UserDo userDo = (UserDo)session.getAttribute ( "user" );
        String orderCode = new SimpleDateFormat ("yyyyMMddHHmmssSSS").format(new Date ()) + RandomUtils.nextInt(10000);
        orderDo.setOrderCode ( orderCode );
        orderDo.setCreateDate ( new Date (  ) );
        orderDo.setUid ( userDo.getId () );
        orderDo.setStatus ( OrderService.waitPay );
        List<OrderItemDo> ois = (List<OrderItemDo>)session.getAttribute ( "ois" );

        float total = orderService.add ( orderDo,ois );
        return "redirect:forealipay?oid="+orderDo.getId() +"&total="+total;
    }
    @RequestMapping("forepayed")
    public String payed(int oid , float total,Model model){
        OrderDo orderDo = orderService.get ( oid );
        orderDo.setStatus ( OrderService.waitDelivery );
        orderDo.setPayDate ( new Date (  ) );
        orderService.update ( orderDo );
        model.addAttribute ( "o",orderDo );
        return "fore/payed";
    }
    @RequestMapping("forebought")
    public String bought(Model model,HttpSession session){
        UserDo userDo = (UserDo) session.getAttribute ( "user" );
        List<OrderDo> os = orderService.list (userDo.getId (),OrderService.delete);
        orderItemService.fill ( os );
        model.addAttribute ( "os",os );
        return "fore/bought";

    }
    @RequestMapping("foreconfirmPay")
    public String confirmPay(Model model,int oid){
        OrderDo orderDo = orderService.get ( oid );
        orderItemService.fill ( orderDo );
        model.addAttribute ( "o",orderDo );
        return "fore/confirmPay";
    }
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(int oid ,Model model){
        OrderDo orderDo = orderService.get ( oid );
        orderDo.setStatus ( OrderService.waitReview );
        orderDo.setConfirmDate ( new Date (  ) );
        orderService.update ( orderDo );
        return "fore/orderConfirmed";
    }
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(int oid ,Model model){
        OrderDo orderDo = orderService.get ( oid );
        orderDo.setStatus(OrderService.delete);
        orderService.update ( orderDo );
        return "success";

    }
    @RequestMapping("forereview")
    public String review(int oid,Model model){
        OrderDo orderDo = orderService.get ( oid );
        orderItemService.fill ( orderDo );
        ProductDo p = orderDo.getOrderItemDos ().get ( 0 ).getProductDo () ;
        List<ReviewDo> reviews = reviewService.list ( p.getId () );
        productService.setSaleAndReviewNumber ( p );
        model.addAttribute ( "p",p );
        model.addAttribute ( "o",orderDo );
        model.addAttribute ( "reviews",reviews );
        return "fore/review";
    }
    @RequestMapping("foredoreview")
    public String doreview(Model model,HttpSession session,@RequestParam("oid") int oid,@RequestParam("pid") int pid,String content){
        OrderDo o = orderService.get ( oid );
        o.setStatus ( OrderService.finish );
        orderService.update ( o );
        ProductDo p = productService.get ( pid );
        content = HtmlUtils.htmlEscape ( content );

        UserDo userDo = (UserDo)session.getAttribute ( "user" );
        ReviewDo reviewDo = new ReviewDo ();
        reviewDo.setContent ( content );
        reviewDo.setPid ( pid );
        reviewDo.setCreateDate ( new Date (  ) );
        reviewDo.setUid ( userDo.getId () );
        reviewService.add ( reviewDo );

        return "redirect:forereview?oid="+oid+"&showonly=true";

    }

}
