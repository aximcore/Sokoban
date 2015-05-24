package me.aximcore;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonIO {
	List<String> mapRows = new ArrayList<>();
	private void read(){
		try{
			JSONParser parser = new JSONParser();
			ClassLoader classLoader = getClass().getClassLoader();
            Object obj = parser.parse(new FileReader(
                    classLoader.getResource("map.json").getFile()));
 
            JSONObject jsonObject = (JSONObject) obj;
	
			for(int i = 0; i < 10; i++){
				mapRows.add(jsonObject.get(String.valueOf(i)).toString());
			}
		} catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public Object[][] getMap(){
		Object[][] o = new Object[10][10];
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
	
	public JsonIO(){
		this.read();
	}
}
