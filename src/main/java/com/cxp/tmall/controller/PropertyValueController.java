package com.cxp.tmall.controller;

import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.PropertyValueDo;
import com.cxp.tmall.service.ProductService;
import com.cxp.tmall.service.PropertyService;
import com.cxp.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PropertyValueController {
//    @Autowired
//    PropertyValueService propertyValueService;
//    @Autowired
//    ProductService productService;
//    @RequestMapping("admin_propertyValue_edit")
//    public String edit(Model model,int pid){
//        ProductDo productDo = productService.get ( pid );
//        propertyValueService.init ( productDo );
//        List<PropertyValueDo> pvs =propertyValueService.list( productDo.getId ());
//        model.addAttribute ( "p",productDo);
//        model.addAttribute ( "pvs",pvs );
//        return "admin/editPropertyValue";
//    }
//    @RequestMapping("admin_propertyValue_update")
//    public String update(PropertyValueDo propertyValueDo){
//        propertyValueService.update ( propertyValueDo );
//        return "success";
//    }
@Autowired
PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model,int pid) {
        ProductDo p = productService.get(pid);
        propertyValueService.init(p);
        List<PropertyValueDo> pvs = propertyValueService.list(p.getId());

        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "admin/editPropertyValue";
    }
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValueDo pv) {
        propertyValueService.update(pv);
        return "success";
    }
}
