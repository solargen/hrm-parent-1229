package cn.itsource.hrm.filter;

import cn.itsource.hrm.client.CacheClient;
import cn.itsource.hrm.util.AjaxResult;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-30
 */
@Component
public class LoginFilter extends ZuulFilter{

    @Autowired
    private CacheClient cacheClient;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        //获取请求对象
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String uri = request.getRequestURI();
        //设置放行的请求
        if(uri.contains("/register")||uri.contains("login")||uri.contains("/v2/api-docs")){
            return null;//放行
        }

        //解析请求头
        String accessToken = request.getHeader("ACCESS-TOKEN");
        if(accessToken!=null){
            AjaxResult ajaxResult = cacheClient.get("USER:" + accessToken);
            if(ajaxResult.isSuccess()&&ajaxResult.getResultObj()!=null){
                return null;
            }
        }

        //要么没有请求头ACCESS-TOKEN,要么token不存在,响应
        AjaxResult ajaxResult = AjaxResult.me().setSuccess(false).setMessage("请先登录!!!");

        //直接响应，不会再通过过滤器调用其他服务
        ctx.setSendZuulResponse(false);
        HttpServletResponse response = ctx.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().write(JSONObject.toJSONString(ajaxResult));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
