package com.cxp.tmall.controller;

import com.cxp.tmall.database.CategoryDo;
import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.service.CategoryService;
import com.cxp.tmall.service.ProductService;
import com.cxp.tmall.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_list")
   public String list(int cid, Model model, Page page){
        CategoryDo categoryDo = categoryService.get ( cid );
        PageHelper.offsetPage ( page.getStart (),page.getCount () );

        List<ProductDo> ps = productService.list ( cid );

        int total = (int) new PageInfo<> ( ps ).getTotal ();

        page.setTotal ( total );
        page.setParam ( "&cid="+categoryDo.getId () );
        model.addAttribute ( "ps",ps );
        model.addAttribute ( "c",categoryDo );
        model.addAttribute ( "page",page );
        return "admin/listProduct";
    }
    @RequestMapping("admin_product_add")
    public String add(Model model,ProductDo productDo){
        productService.add ( productDo );
        return "redirect:admin_product_list?cid="+productDo.getCid ();
    }
    @RequestMapping("admin_product_delete")
    public String delete(int id){
        ProductDo productDo = productService.get ( id );
        productService.delete ( id );
        return "redirect:admin_product_list?cid="+productDo.getCid ();
    }
    @RequestMapping("admin_product_update")
    public String update(ProductDo productDo){
        productService.update ( productDo );
        return "redirect:admin_product_list?cid="+productDo.getCid ();
    }
    @RequestMapping("admin_product_edit")
    public String edit(Model model,int id){
        ProductDo productDo = productService.get ( id );
        CategoryDo  categoryDo = categoryService.get ( productDo.getCid () );
        productDo.setCategoryDo (categoryDo);
        model.addAttribute ( "p",productDo );
        return "admin/editProduct";
    }
}
