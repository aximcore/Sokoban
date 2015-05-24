package me.aximcore;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonIO {
	private List<String> mapRows = new ArrayList<>();
	private String jsonPath;
	private static Logger logger = LoggerFactory.getLogger(JsonIO.class);

	
	public int getMapRowsSize(){
		return mapRows.size();
	}
	
	public void setJsonPath(String path){
		this.jsonPath = path;
	}
	
	public void read(){
		logger.trace("Json read");
		
		try{
			JSONParser parser = new JSONParser();
			ClassLoader classLoader = getClass().getClassLoader();
			
			logger.trace("Json parser");
			
            Object obj = parser.parse(new FileReader(
                    classLoader.getResource(jsonPath).getFile()));
 
            JSONObject jsonObject = (JSONObject) obj;
            Iterator<?> it = jsonObject.keySet().iterator();
            
            while(it.hasNext()){
            	String key = it.next().toString();
            	
            	logger.debug("Iterat this key {}", key);
            	
            	mapRows.add(jsonObject.get(key).toString());
            }
            logger.debug("mapRows size {}", mapRows.size());
            
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public Object[][] getMap(){
		Object[][] o = new Object[mapRows.size()][mapRows.size()];
		int i = 0, ii = 0;
		for(String row : mapRows){
			ii = 0;
			for(String cell : row.split(",")){
				if(cell.contains("null")){
					o[i][ii] = null;
					ii++;
				} else if ( cell.contains("jatekos")){
					o[i][ii] = "jatekos";
					ii++;
				} else if (cell.contains("t")){
					o[i][ii] = "t";
					ii++;
				} else if (cell.contains("k")){
					o[i][ii] = "k";
					ii++;
				} else if (cell.contains("c")){
					o[i][ii] = "c";
					ii++;
				}
				
				logger.debug("Matrix [i][ii] value {}", o[i][ii]);
			}
			i++;
		}
		
		return o;
	}
	
	public String[] getHeader(){
		String [] header = new String[mapRows.size()];
		
		return header;
	}
	
	public JsonIO(){
		this.setJsonPath("map.json");
		this.read();
	}
}
