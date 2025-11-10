package net.xzh.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

/**
 * 实现HttpRequestHandler接口的方式（类似Servlet，映射地址在mvc中配置）
 * @author xzh
 *
 */

public class ApiRequestHandler implements HttpRequestHandler {
    
    @Override
    public void handleRequest(HttpServletRequest request, 
                            HttpServletResponse response) 
            throws ServletException, IOException {
        // 设置响应类型
        response.setContentType("application/json;charset=UTF-8");
        // 获取请求参数
        String action = request.getParameter("action");
        
        // 直接操作HttpServletResponse，类似Servlet
        PrintWriter out = response.getWriter();
        
        if ("list".equals(action)) {
            // 返回JSON数据
            out.write("{\"status\":\"success\",\"data\":[{\"id\":1,\"name\":\"张三\"}]}");
        } else if ("info".equals(action)) {
            out.write("{\"status\":\"success\",\"data\":{\"id\":1,\"name\":\"李四\"}}");
        } else {
            out.write("{\"status\":\"error\",\"message\":\"未知操作\"}");
        }
        
        out.flush();
        out.close();
    }
}