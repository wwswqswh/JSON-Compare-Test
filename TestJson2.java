package com.xy2401.example.json;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 
import com.google.gson.Gson;

public class TestJson2 {
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Gson gson = new Gson();
	private static JSONObject jsonObject = new JSONObject();

	public static void main(String[] args) throws JsonProcessingException {

		Map<String,Map> map = new HashMap<>();
		
		map.put("1", null);
		map.put("2", new HashMap<>());
		
		System.out.println(gson.toJson(map));
		System.out.println(objectMapper.writeValueAsString(map));
		System.out.println(JSONObject.toJSONString(map,SerializerFeature.WriteMapNullValue));
 
	}
}
