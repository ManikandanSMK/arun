package com.arun.controller;

import java.io.IOException;
import java.net.CookieStore;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController
public class AppController {

	@Value("${server_name}")
	String serverName;

	@Value("${cookie-value}")
	String cookieValue;

	@PostConstruct
	public void init() {
		System.out.println("Server Name===============" + serverName);
	}

	@GetMapping("/test")
	public void index(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Cookie c = new Cookie("demo", cookieValue);
		resp.addCookie(c);

		resp.getOutputStream().write(serverName.getBytes());
	}

	@GetMapping("/ping")
	public void ping(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		resp.getOutputStream().write("pong".getBytes());
	}

}
