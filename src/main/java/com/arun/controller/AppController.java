package com.arun.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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
	
	@Value("${tier2.response}")
	String response;

	@PostConstruct
	public void init() {
		System.out.println("Server id=====" + serverId + "          URL====" + url);
	}

	@GetMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse resp) {
		Cookie cookieArray[] = request.getCookies();
		//print(cookieArray);
		Cookie cookies = WebUtils.getCookie(request, "cookie-arun");
		String cookieValue = cookies != null ? cookies.getValue() : null;
		System.out.println("Cookie Val at index===========" + cookieValue);
		if (cookieValue == null || cookieValue.isEmpty()) {
			cookieValue = UUID.randomUUID().toString();
			Cookie cookie = new Cookie("cookie-arun", cookieValue + "#" + serverId);
			cookie.setMaxAge(-1);
			resp.addCookie(cookie);
		} else {
			System.out.println("Cookie is not generated for server id====" + serverId + "  existing cookie value is"
					+ cookieValue);
		}

		request.setAttribute("server-name", serverId);
		request.setAttribute("url", url);

		return "index";
	}

	/*
	 * private void print(Cookie[] cookieArray) {
	 * System.out.println("Cookies============================Value       at login"
	 * ); Arrays.asList(cookieArray).stream().map(c ->
	 * c.getValue()).forEach(System.out::println);
	 * 
	 * }
	 */

	@ResponseBody
	@GetMapping("/call")
	public void postData(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Cookie cookies = WebUtils.getCookie(request, "cookie-arun");
		String cookieValue = cookies != null ? cookies.getValue() : null;

		System.out.println("Cookie Val===========" + cookieValue);
		if (cookieValue == null || cookieValue.isEmpty()) {
			cookieValue = UUID.randomUUID().toString();
			Cookie cookie = new Cookie("cookie-arun", cookieValue + "#" + serverId);
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
		} else {
			System.out.println("Cookie is not generated for server id====" + serverId + "  existing cookie value is"
					+ cookieValue);
		}
		System.out.println("Post Call from Server====" + serverId + " Cookie value======" + cookieValue);
		response.sendRedirect("/call-backend");

	}

	@GetMapping(value = "/call-backend")
	@ResponseBody
	public void callBackend(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	if(req.getLocalPort()==8081) {
		backEndCall(resp);
		return;
	}
	
	String val=req.getRequestURL().toString();
	val=val.replace("8080","8081");
	HttpClient client=HttpClientBuilder.create().build();
	HttpGet get=new HttpGet(val);
	HttpResponse response=client.execute(get);
	String ans=EntityUtils.toString(response.getEntity());
	resp.getOutputStream().write(ans.getBytes());
	resp.setHeader("Content-Type", "text/html");
	}

	private void backEndCall(HttpServletResponse resp) throws Exception {
		resp.setHeader("Content-Type","text/html");
		resp.getOutputStream().write(response.getBytes());
		
	}

	
}
