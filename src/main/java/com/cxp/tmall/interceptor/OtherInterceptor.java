package com.cxp.tmall.interceptor;

import com.cxp.tmall.database.CategoryDo;
import com.cxp.tmall.database.OrderItemDo;
import com.cxp.tmall.database.UserDo;
import com.cxp.tmall.service.CategoryService;
import com.cxp.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OtherInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        return true;
//        在业务处理器处理请求之前被调用
    }


    public void postHandle(HttpServletRequest request,
                            HttpServletResponse response, Object handler,
                            ModelAndView modelAndView)throws Exception{
        List<CategoryDo> cs = categoryService.list ();
        request.getSession ().setAttribute ( "cs",cs );

        HttpSession session = request.getSession ();
        String contextPath  =session.getServletContext ().getContextPath ();
        request.getSession ().setAttribute ( "contextPath",contextPath );

        UserDo userDo = (UserDo)session.getAttribute ( "user" );
        int cartTotalItemNumber=0;
        if(null!=userDo){
            List<OrderItemDo> ois = orderItemService.listByUser ( userDo.getId () );
            for(OrderItemDo oi :ois){
                cartTotalItemNumber+=oi.getNumber ();
            }
        }
        request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

//        System.out.println("afterCompletion(), 在访问视图之后被调用");
    }

}
