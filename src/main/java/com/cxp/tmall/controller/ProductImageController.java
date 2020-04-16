package com.cxp.tmall.controller;

import com.cxp.tmall.database.ProductDo;
import com.cxp.tmall.database.ProductImageDo;
import com.cxp.tmall.service.ProductImageService;
import com.cxp.tmall.service.ProductService;
import com.cxp.tmall.util.ImageUtil;
import com.cxp.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;

    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model){
        ProductDo productDo =productService.get ( pid );
        List<ProductImageDo> pisSingle = productImageService.list ( pid,ProductImageService.type_single );
        List<ProductImageDo> pisDetail = productImageService.list ( pid,ProductImageService.type_detail );
        model.addAttribute ( "p",productDo );
        model.addAttribute ( "pisSingle",pisSingle );
        model.addAttribute ("pisDetail",pisDetail  );
        return "admin/listProductImage";
    }
    @RequestMapping("admin_productImage_add")
    public String add(ProductImageDo pi, HttpSession session, UploadedImageFile uploadedImageFile){
        productImageService.add ( pi );
        String fileName = pi.getId ()+".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;
        if(ProductImageService.type_single.equals ( pi.getType () )){
            imageFolder= session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small= session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
        }
        else {
            imageFolder= session.getServletContext().getRealPath("img/productDetail");
        }
        File f = new File ( imageFolder,fileName );
        f.getParentFile ().mkdirs ();
        try{
            uploadedImageFile.getImage ().transferTo ( f );
            BufferedImage img = ImageUtil.change2jpg ( f );
            ImageIO.write ( img,"jpg",f );
            if (ProductImageService.type_single.equals ( pi.getType () )){
                File f_small = new File ( imageFolder_small,fileName );
                File f_middle = new File ( imageFolder_middle,fileName );
                ImageUtil.resizeImage ( f,56,56,f_small );
                ImageUtil.resizeImage ( f,217,190,f_middle );
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return "redirect:admin_productImage_list?pid="+pi.getPid ();
    }
    @RequestMapping("admin_productImage_delete")
    public String delete(int id,HttpSession session){
        ProductImageDo pi = productImageService.get ( id );
        String fileName = pi.getId ()+".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;

        if(ProductImageService.type_single.equals ( pi.getType () )){
            imageFolder=session.getServletContext ().getRealPath ( "img/productSingle" );
            imageFolder_small=session.getServletContext ().getRealPath ( "img/productSingle_small" );
            imageFolder_middle=session.getServletContext ().getRealPath ( "img/productSingle_middle" );
            File imageFile =new File ( imageFolder,fileName );
            File f_small = new File (imageFolder_small,fileName );
            File f_middle = new File ( imageFolder_middle,fileName );
            imageFile.delete ();
            f_small.delete ();
            f_middle.delete ();
        }
        else {
            imageFolder=session.getServletContext ().getRealPath ( "img/productDetail" );
            File imageFile = new File ( imageFolder,fileName );
            imageFile.delete ();
        }
        productImageService.delete ( id );

        return "redirect:admin_productImage_list?pid="+pi.getPid ();
    }


}
