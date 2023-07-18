package com.example.springsecuritydemo.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.springsecuritydemo.entity.HttpResult;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class HttpUtils {

	/**
	 * 获取HttpServletRequest对象
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 输出信息到浏览器
	 * @param response
	 * @param
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, Object data,String msg) throws IOException {
		response.setContentType("application/json; charset=utf-8");
        HttpResult result = HttpResult.ok(data);
        result.setMsg(msg);
        String json = JSONObject.toJSONString(result);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
	}

	/**
	 * 输出信息到浏览器
	 * @param response
	 * @param
	 * @throws IOException
	 */
	public static void error(HttpServletResponse response, String data) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		HttpResult result = HttpResult.error(data);
		String json = JSONObject.toJSONString(result);
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 输出信息到浏览器
	 * @param response
	 * @param
	 * @throws IOException
	 */
	public static void auth(HttpServletResponse response, String data) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		HttpResult result = HttpResult.auth(403,data);
		String json = JSONObject.toJSONString(result);
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 输出信息到浏览器
	 * @param response
	 * @param
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, int code,String msg) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		HttpResult result = HttpResult.error(code,msg);
		String json = JSONObject.toJSONString(result);
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}

	public static void jwt(HttpServletResponse response, String data) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		HttpResult result = HttpResult.auth(401,data);
		String json = JSONObject.toJSONString(result);
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * get请求接口，返回json
	 * @param url 接口地址
	 * @return
	 */
	public static String getJson(String url){
// RequestBody body=RequestBody.create(MediaType.parse("application/json"),"");
		Request requestOk = new Request.Builder()
				.url(url)
				.get()
				.build();

		Response response;
		try {
			response = new OkHttpClient().newCall(requestOk).execute();
			String jsonString = response.body().string();
			if(response.isSuccessful()){
				return jsonString;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "";
	}
	
}
