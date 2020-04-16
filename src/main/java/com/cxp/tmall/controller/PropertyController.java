package com.cxp.tmall.controller;

import com.cxp.tmall.dao.PropertyDoMapper;
import com.cxp.tmall.database.CategoryDo;
import com.cxp.tmall.database.PropertyDo;
import com.cxp.tmall.service.CategoryService;
import com.cxp.tmall.service.PropertyService;
import com.cxp.tmall.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PropertyController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyService propertyService;

    @RequestMapping("admin_property_add")
    public String add(Model model, PropertyDo propertyDo){
        propertyService.add ( propertyDo );
        return "redirect:admin_property_list?cid="+propertyDo.getCid ();
    }

    @RequestMapping("admin_property_delete")
    public String delete(int id){
        PropertyDo propertyDo = propertyService.get ( id );
        propertyService.delete ( id );;
        return "redirect:admin_property_list?cid="+propertyDo.getCid ();
    }
    @RequestMapping("admin_property_edit")
    public String edit(Model model,int id){
        PropertyDo propertyDo = propertyService.get ( id );
        CategoryDo categoryDo = categoryService.get ( propertyDo.getCid () );
        propertyDo.setCategory ( categoryDo );
        model.addAttribute ( "p",propertyDo );
        return "admin/editProperty";
    }
    @RequestMapping("admin_property_update")
    public String update(PropertyDo propertyDo){
        propertyService.update ( propertyDo );
        return "redirect:admin_property_list?cid="+propertyDo.getCid();
    }

        @RequestMapping("admin_property_list")
    public String list(int cid, Model model, Page page){
        CategoryDo c =categoryService.get ( cid );
        PageHelper.offsetPage(page.getStart(),page.getCount());

        List<PropertyDo> ps = propertyService.list(cid);

        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal ( total );
        page.setParam("&cid="+c.getId());
        model.addAttribute("ps", ps);
        model.addAttribute("c", c);
        model.addAttribute("page", page);
        return "admin/listProperty";
    }


}
