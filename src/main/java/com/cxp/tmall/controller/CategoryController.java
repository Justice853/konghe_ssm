package com.cxp.tmall.controller;

import com.cxp.tmall.database.CategoryDo;
import com.cxp.tmall.service.CategoryService;
import com.cxp.tmall.util.ImageUtil;
import com.cxp.tmall.util.Page;
import com.cxp.tmall.util.UploadedImageFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController  {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String List(Model model,Page page){
        PageHelper.offsetPage ( page.getStart (),page.getCount () );
        List<CategoryDo> cs = categoryService.list ();

        int total = (int) new PageInfo<> ( cs ).getTotal ();
        page.setTotal ( total );
        model.addAttribute ( "page",page );
        model.addAttribute ( "cs",cs );
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(CategoryDo c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.add ( c );
        File imageFolder = new File ( session.getServletContext ().getRealPath ("img/category"  ) );
        File file = new File ( imageFolder,c.getId ()+".jpg" );
        if(!file.getParentFile ().exists ())
        {
            file.getParentFile ().mkdirs ();
        }
        uploadedImageFile.getImage ().transferTo ( file );
        BufferedImage img = ImageUtil.change2jpg ( file );
        ImageIO.write ( img,"jpg",file );
        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_delete")
    public String delete(int id,HttpSession session){
        categoryService.delete ( id );
        File imageFolder = new File ( session.getServletContext ().getRealPath ( "img/category" ) );
        File file = new File ( imageFolder,id+".jpg" );
        file.delete ();
        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_edit")
    public String edit(int id,Model model){
        CategoryDo c = categoryService.get ( id );
        model.addAttribute ( "c",c );
        return "admin/editCategory";
    }
    @RequestMapping("admin_category_update")
    public String update(CategoryDo c,HttpSession session,UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update ( c );
        MultipartFile image = uploadedImageFile.getImage ();
        if(null!=image &&!image.isEmpty ()){
            File imageFolder = new File ( session.getServletContext ().getRealPath ( "img/category" ) );
            File file = new File ( imageFolder,c.getId ()+".jpg" );
            image.transferTo ( file );
            BufferedImage img =ImageUtil.change2jpg ( file );
            ImageIO.write ( img,"jpg",file );
        }
        return "redirect:/admin_category_list";
    }

}
