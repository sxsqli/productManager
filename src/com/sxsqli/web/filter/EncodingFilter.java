package com.sxsqli.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class EncodingFilter
 */
public class EncodingFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public EncodingFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/*
		 * tomcat服务器配置文件server.xml中的Connector需要添加URIEncoding="UTF-8"使服务器支持中文路径
		 * 上述设置已经使tomcat对URI进行UTF-8解码，此处就不另外对HttpServletRequest进行包装了
		 */
		
		//设置post方式的解码
		request.setCharacterEncoding("UTF-8");
		//设置响应头，随便设置响应编码与解码方式
		response.setContentType("text/html;charset=UTF-8");
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
