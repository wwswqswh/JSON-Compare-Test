package com.xy2401.example.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestJson {

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Gson gson = new Gson();
	private static JSONObject jsonObject = new JSONObject();
	// private static long count = 10000;

	public static void main(String[] args) throws IOException, InterruptedException {

		int num = 1000000;
		List<Demo> demos = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			Demo demo = new Demo();
			demo.setD(Math.random());
			demo.setContent(String.valueOf(Math.random()));
			demos.add(demo);
		}

		long endTime1, endTime2;

		endTime1 = System.currentTimeMillis();
		for (int i = 0; i < demos.size(); i++) {
			gson.toJson(demos.get(i));
		}
		endTime2 = System.currentTimeMillis();
		System.out.println("gson--obj--json:" + (endTime2 - endTime1));

		System.gc();
		Thread.sleep(2000);
		
		
		endTime1 = System.currentTimeMillis();
		for (int i = 0; i < demos.size(); i++) {
			objectMapper.writeValueAsString(demos.get(i));
		}
		endTime2 = System.currentTimeMillis();
		System.out.println("jackJson--obj--json:" + (endTime2 - endTime1));
		System.gc();
		Thread.sleep(2000);
		
		endTime1 = System.currentTimeMillis();
		for (int i = 0; i < demos.size(); i++) {
			JSON.toJSON(demos.get(i));
		}
		endTime2 = System.currentTimeMillis();
		System.out.println("fastJson--obj--json:" + (endTime2 - endTime1));
		System.gc();
		Thread.sleep(2000);
		
//		System.out.println(gson.toJson(demos.get(0)));
//		System.out.println(objectMapper.writeValueAsString(demos.get(0)));
//		System.out.println(JSON.toJSON(demos.get(0)));

		System.out.println("list");

		endTime1 = System.currentTimeMillis();

		gson.toJson(demos);

		endTime2 = System.currentTimeMillis();
		System.out.println("gson--obj--json:" + (endTime2 - endTime1));
		System.gc();
		Thread.sleep(2000);
		
		endTime1 = System.currentTimeMillis(); 
		objectMapper.writeValueAsString(demos); 
		endTime2 = System.currentTimeMillis();
		System.out.println("jackJson--obj--json:" + (endTime2 - endTime1));
		System.gc();
		Thread.sleep(2000);
		
		
		endTime1 = System.currentTimeMillis(); 
		JSON.toJSON(demos); 
		endTime2 = System.currentTimeMillis();
		System.out.println("fastJson--obj--json:" + (endTime2 - endTime1));
		 
		System.out.println("");
		System.out.println("JSON --> OBJ");
		String demosJson = objectMapper.writeValueAsString(demos);
		;
		List<String> demoJsons = new ArrayList<>();
		for (int i = 0; i < demos.size(); i++) {
			demoJsons.add(objectMapper.writeValueAsString(demos.get(i)));
		}

		endTime1 = System.currentTimeMillis();

		for (int i = 0; i < demos.size(); i++) {
			gson.fromJson(demoJsons.get(i), Demo.class);
		}
		endTime2 = System.currentTimeMillis();
		System.out.println("gson--json--obj:" + (endTime2 - endTime1));

		endTime1 = System.currentTimeMillis();
		for (int i = 0; i < demos.size(); i++) {
			objectMapper.readValue(demoJsons.get(i), Demo.class);
		}
		endTime2 = System.currentTimeMillis();
		System.out.println("jackJson--json--obj:" + (endTime2 - endTime1));

		endTime1 = System.currentTimeMillis();
		for (int i = 0; i < demos.size(); i++) {
			JSON.parseObject(demoJsons.get(i), Demo.class);
		}
		endTime2 = System.currentTimeMillis();
		System.out.println("fastJson--json--obj:" + (endTime2 - endTime1));

		System.out.println("list");
		endTime1 = System.currentTimeMillis();

		gson.fromJson(demosJson, new TypeToken<List<Demo>>() {
		}.getType());

		endTime2 = System.currentTimeMillis();
		System.out.println("gson--json--obj:" + (endTime2 - endTime1));

		endTime1 = System.currentTimeMillis();

		objectMapper.readValue(demosJson, new com.fasterxml.jackson.core.type.TypeReference<List<Demo>>() {
		});

		endTime2 = System.currentTimeMillis();
		System.out.println("jackJson--json--obj:" + (endTime2 - endTime1));

		endTime1 = System.currentTimeMillis();

		JSON.parseObject(demosJson, new TypeToken<List<Demo>>() {
		}.getType());

		endTime2 = System.currentTimeMillis();
		System.out.println("fastJson--json--obj:" + (endTime2 - endTime1));
	}

	public static class Demo {
		private Double d;
		private String content;

		public Double getD() {
			return d;
		}

		public void setD(Double d) {
			this.d = d;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String toString() {
			System.out.println("super:" + super.toString() + "d:" + d + "content" + content);
			return super.toString();
		}
	}
}
