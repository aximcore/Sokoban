package me.aximcore;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.LogManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Játék mappa beolvasása Json-nal.
 * @author aximcore
 *
 */
public class JsonIO {
	/**
	 * Játék map sorai.
	 */
	private List<String> mapRows = new ArrayList<>();
	
	/**
	 * Json fájl elérhetősége, .jar állományban.
	 */
	private String jsonPath;
	
	//private static InputStream	in = JsonIO.class.getResourceAsStream("/logging.properties");
	/**
	 * Logger beállítása az osztályra.
	 */
	private static Logger logger = LoggerFactory.getLogger(JsonIO.class);

	/*private static void setLogger(){
		if (in != null) {
			try {
				LogManager.getLogManager().readConfiguration(in);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	/**
	 * Hány sorom van.
	 * @return sorok száma.
	 */
	public int getMapRowsSize(){
		return mapRows.size();
	}
	
	/**
	 * Json fájl elérhetőségének beálítása.
	 * @param path elérési út.
	 */
	public void setJsonPath(String path){
		this.jsonPath = path;
	}
	
	/**
	 * Json fájl beolvasás resource-ből. 
	 */
	public void read(){
		logger.trace("Json read");
		
		try{
			InputStream inputStream = this.getClass().getResourceAsStream(jsonPath);
			DataInputStream in = new DataInputStream(inputStream);
			String dataIn = new String();
			String a = new String();
			while((a = in.readLine()) != null){
				dataIn += a;
			}
			
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(dataIn.toString());
					
			logger.trace("Json parser");

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
	
	/**
	 * ArrayList-ből egy Object mátrixot készít a {@link me.aximcore.GameGui#table JTable}-nek.
	 * @return Object Mátrix.
	 */
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
	
	/**
	 * {@link me.aximcore.GameGui#table JTable}-nek készít fejlécet.
	 * @return string tömb ami mérete megegyezik a beolvasott sorok számával.
	 */
	public String[] getHeader(){
		String [] header = new String[mapRows.size()];
		
		return header;
	}
	
	/**
	 * Konstruktor, json elérhetősége és olvasás.
	 */
	public JsonIO(){

		this.setJsonPath("/map.json");
		this.read();
	}
}
