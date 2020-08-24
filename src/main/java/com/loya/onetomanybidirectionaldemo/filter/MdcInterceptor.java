package com.loya.onetomanybidirectionaldemo.filter;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;


public class MdcInterceptor implements Filter {

	final Logger LOGGER = (Logger) LoggerFactory.getLogger(MdcInterceptor.class);
	


public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
	// TODO Auto-generated method stub
	try {
	if(request instanceof ServletRequest) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		MDC.put("correlationID", httpServletRequest.getHeader("x-correlation-id"));
	
		chain.doFilter(request, response);
	}
	}

	finally {
		MDC.clear();
	}
}
}

