
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;


import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.ceair.observersdk.utils.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.travelsky.phoenix.jacksonUtilTest.JMapper;


public class JsonCompareTest {

	private SampleBuilder samplebuilder = new SampleBuilder();
	private static String[] jsonName ={"Jackson","fastJSON","GSON"};
	@Test 
	public void testInitial(){
		samplebuilder.sampleBuilderInitial(10,10,10);
	}
	@Test
	public void testJson2Object(){
		//Jackson类型
		ObjectMapper mapper = new ObjectMapper();
		//Gson时间需要单独设置
		GsonBuilder builder = new GsonBuilder();
		// Register an adapter to manage the date types as long values
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		Gson gson = builder.create();

		long[][] timecost = new long[10][3];
		//设置开始时间
		long starttime = 0;
		//从文件读取数据
		List<String> jsonList = samplebuilder.loadJSONSamples("d:\\samples_json.dat");

		//执行10次大循环
		for(int i=0;i<10;i++){
			//Jackson	
			starttime=System.currentTimeMillis();
			for(String str:jsonList){	
				try {
					mapper.readValue(str, SampleEntity.class); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			timecost[i][0]=System.currentTimeMillis()-starttime;

			//fastJson
//			starttime=System.currentTimeMillis();
//			for(String str:jsonList){	
//				JSON.parseObject(str, SampleEntity.class);
//			}
//			timecost[i][1]=System.currentTimeMillis()-starttime;

			//GSON
//			starttime=System.currentTimeMillis();
//			for(String str:jsonList){	
//				gson.fromJson(str, SampleEntity.class);
//			}
//			timecost[i][2]=System.currentTimeMillis()-starttime;
		}
		for(int j=0;j<3;j++){
			System.out.println(jsonName[j]);
			for(int i=0;i<10;i++){
				System.out.println(timecost[i][j]+" ");
			}
			System.out.println("\n");
		}

	}

	@Test
	public void testObject2JSON(){

		ObjectMapper mapper = new ObjectMapper(); 
		Gson gson = new Gson();

		long[][] timecost = new long[10][3];
		//设置开始时间
		long starttime = 0;

		//生成需要转换的数据
		List<SampleEntity> list = samplebuilder.buildSamples(10000, 10, 10);
		//执行10次大循环
		for(int i=0;i<10;i++){
			//Jackson	
			starttime=System.currentTimeMillis();
			for(SampleEntity entity:list){	
				try {
					mapper.writeValueAsString(entity);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			timecost[i][0]=System.currentTimeMillis()-starttime;

			//fastJson
			starttime=System.currentTimeMillis();
			for(SampleEntity entity:list){	
				JSON.toJSONString(entity);
			}
			timecost[i][1]=System.currentTimeMillis()-starttime;

			//GSON
			starttime=System.currentTimeMillis();
			for(SampleEntity entity:list){	
				gson.toJson(entity);
			}
			timecost[i][2]=System.currentTimeMillis()-starttime;
		}
		for(int j=0;j<3;j++){
			System.out.println(jsonName[j]);
			for(int i=0;i<10;i++){
				System.out.println(timecost[i][j]+" ");
			}
			System.out.println("\n");
		}
	}
}
