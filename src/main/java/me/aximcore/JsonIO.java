package me.aximcore;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonIO {
	private List<String> mapRows = new ArrayList<>();
	private String jsonPath;
	
	public int getMapRowsSize(){
		return mapRows.size();
	}
	
	public void setJsonPath(String path){
		this.jsonPath = path;
	}
	
	public void read(){
		try{
			JSONParser parser = new JSONParser();
			ClassLoader classLoader = getClass().getClassLoader();
            Object obj = parser.parse(new FileReader(
                    classLoader.getResource(jsonPath).getFile()));
 
            JSONObject jsonObject = (JSONObject) obj;
            Iterator<?> it = jsonObject.keySet().iterator();
            
            while(it.hasNext()){
            	mapRows.add(jsonObject.get(it.next().toString()).toString());
            }
            
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
