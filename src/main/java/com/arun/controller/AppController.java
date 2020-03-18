package com.arun.controller;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

@Controller
public class AppController {
	
	
	@Value("${url}")
	String url;
	
	@Value("${server-id}")
	int serverId;

	
	@PostConstruct
	public void init() {
		System.out.println("Server id====="+serverId+"          URL===="+url);
	}
	@GetMapping("/")
	public String index(HttpServletRequest request,HttpServletResponse resp) {
		Cookie cookieArray[]=request.getCookies();
		print(cookieArray);
		Cookie cookies=WebUtils.getCookie(request,"cookie-arun");
		String cookieValue=cookies!=null?cookies.getValue():null;
		System.out.println("Cookie Val at index==========="+cookieValue);
		if (cookieValue == null || cookieValue.isEmpty()) {
			 cookieValue=UUID.randomUUID().toString();
			Cookie cookie=new Cookie("cookie-arun", cookieValue+"#"+serverId);
			cookie.setMaxAge(-1);
			resp.addCookie(cookie);
		}else {
			System.out.println("Cookie is not generated for server id===="+serverId+"  existing cookie value is"+cookieValue);
		}
		
		request.setAttribute("server-name", serverId);
		request.setAttribute("url", url);

		return "index";
	}
	
	private void print(Cookie[] cookieArray) {
		System.out.println("Cookies============================Value       at login");
		Arrays.asList(cookieArray).stream().map(c->c.getValue()).forEach(System.out::println);
		
	}
	@ResponseBody
	@GetMapping("/call")
	public String postData(HttpServletRequest request,HttpServletResponse response) {
		
		Cookie cookies=WebUtils.getCookie(request,"cookie-arun");
		String cookieValue=cookies!=null?cookies.getValue():null;
		
		System.out.println("Cookie Val==========="+cookieValue);
		if (cookieValue == null || cookieValue.isEmpty()) {
			 cookieValue=UUID.randomUUID().toString();
			Cookie cookie=new Cookie("cookie-arun", cookieValue+"#"+serverId);
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
		} 	else {
			System.out.println("Cookie is not generated for server id===="+serverId+"  existing cookie value is"+cookieValue);
		}
		System.out.println("Post Call from Server===="+serverId+" Cookie value======"+cookieValue);
		return "hello ";
		
	}
}
