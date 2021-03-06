
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


import com.alibaba.fastjson.JSON;


/**
 * 创建样本
 * @author accountwcx@qq.com
 *
 */
public class SampleBuilder {

	/**
	 * 创建样本，并把样本保存到文件中。
	 * 
	 * @param sampleSize 样本数量
	 * @param listSize 样本List长度
	 * @param mapKeyNum 样本Map中Key的数量
	 */
	public void sampleBuilderInitial(int sampleSize, int listSize, int mapKeyNum){
		
		String jsonDataPath = "d:\\samples_json.dat";
		buildJsonSamples(sampleSize, listSize, mapKeyNum, jsonDataPath);
	}

public List<String> loadJSONSamples(String filePath) {
	List<String> list = new LinkedList<String>();

	File file = new File(filePath);
	if (!file.exists()) {
		return list;
	}

	BufferedReader br = null;
	try {
		br = new BufferedReader(new FileReader(file));
		String line = br.readLine();            
		while(line != null){
			list.add(line);
			line = br.readLine();
		}           
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (null != br) {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}
	return list;
}

/**
 * 创建样本
 * 
 * @param sampleSize 样本数量
 * @param listSize 样本List长度
 * @param mapKeyNum 样本Map的Key数量
 * @return 样本List
 */
public List<SampleEntity> buildSamples(int sampleSize, int listSize, int mapKeyNum) {
	List<SampleEntity> list = new LinkedList<SampleEntity>();
	for (int i = 0; i < sampleSize; i++) {
		list.add(new SampleEntity(listSize, mapKeyNum));
	}
	return list;
}

/**
 * 创建样本，并把样本JSON序列化，保存到文件中。
 * 
 * @param sampleSize 样本数量
 * @param listSize 样本List长度
 * @param mapKeyNum 样本Map中Key的数量
 * @param filePath 样本输出的文件路径 
 */
public void buildJsonSamples(int sampleSize, int listSize, int mapKeyNum, String filePath) {
	File file = new File(filePath);
	File parent = file.getParentFile();
	if (!parent.exists()) {
		parent.mkdirs();
	}

	if (file.exists()) {
		file.delete();
	}

	List<SampleEntity> list = buildSamples(sampleSize, listSize, mapKeyNum);

	StringBuilder sb = new StringBuilder();
	for (SampleEntity item : list) {
		sb.append(JSON.toJSONString(item));
		sb.append("\n");
	}

	BufferedWriter bw = null;
	try {
		file.createNewFile();

		bw = new BufferedWriter(new FileWriter(file));
		bw.write(sb.toString());
		bw.flush();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (null != bw) {
			try {
				bw.close();
			} catch (IOException e) {
			}
		}
	}
}


}
