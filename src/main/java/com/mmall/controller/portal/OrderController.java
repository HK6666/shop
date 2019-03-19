package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    private IOrderService iOrderService;
    @RequestMapping("pay.do")
    @ResponseBody
    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request)
    {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        {
            if(user == null)
            {
                return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
            }
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(orderNo,user.getId(),path);
    }
    @RequestMapping("alipay_callback.do")
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request)
    {
       Map requestParams =  request.getParameterMap();
       for(Iterable iter = (Iterable) requestParams.keySet().iterator(); ((Iterator) iter).hasNext();)
       {
           String name = (String)((Iterator) iter).next();
           String[] values = (String[]) requestParams.get(name);
           String valueStr = "";
           for(int i = 0;i<values.length;i++)
           {
               valueStr = (i == values.length -1)?valueStr + values[i]:valueStr+values[i]+",";
           }
       }
    }


}
