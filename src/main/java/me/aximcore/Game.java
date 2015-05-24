/**
 * 
 */
package me.aximcore;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aximcore
 *
 */
public class Game {
	
	/**
	 * Logger beálítása 
	 */
	private static Logger logger = LoggerFactory.getLogger(Game.class);
	/**
	 * Aktuális pozíció tárolása számoláshoz.
	 */
	private Coordinate actPos;
	
	/**
	 * Eltolt objektum új koordinátái.
	 */
	private Coordinate pushedPos;
	
	/**
	 * A klikelt pozicíó tárolása.
	 */
	private Coordinate clickedPos;
	
	/**
	 * A klikelt pozición lévő cella adatainak tárolására.
	 */
	private Object clickedPosValue;
	
	/**
	 * @see List típusú tömben tároljuk az lépésekket. 
	 */
	private List<Coordinate> stepData;
	
	/**
	 * Helyére tolt kockák száma.
	 */
	private Integer winCount = 0;
	
	/**
	 * Játék adatmodelje, egy pálya milyen elemeket tartalmazon.
	 */
	private Object o[][];
	
	/**
	 * Tábla fejléce
	 */
	private String[] header;

	/**
	 * Konstruktor amely @see {@link JsonIO} példanyosítja
	 */
	public Game(){
		JsonIO j = new JsonIO();
		this.o = j.getMap();
		this.header = j.getHeader();
		this.stepData = new ArrayList<>();
		this.stepData.add(getGamerPos());
	}

	/**
	 * Klikkelt pozíció beállítása.
	 * @param x sor index
	 * @param y oszlop index
	 */
	public void setClickedPos(int x, int y){
		logger.trace("ClickedPos set");
		
		this.clickedPos = new Coordinate(x,y);
	}
	
	/**
	 * Új lépés hozzáadása @see stepData -hoz.
	 * @param x
	 * @param y
	 */
	public void addStepData(int x, int y){
		logger.trace("add StepData");
		
		stepData.add(new Coordinate(x,y));
	}
	
	/**
	 * Klikkelt pozíció értékének beállítása.
	 * @param o érték
	 */
	public void setClickedValue(Object o){
		logger.trace("ClickedValue set");
		
		clickedPosValue = o;
	}
	
	/**
	 * Lépések számával tér vissza.
	 * @return String típussal
	 */
	public String getStepCount(){
		return new Integer(stepData.size() - 1).toString();
	}
	
	/**
	 * Hány kocka van már a helyén.
	 * @return String
	 */
	public String getWinCount(){
		return winCount.toString();
	}
	
	/**
	 * Alap mátrix beálítása
	 * @param o egy Object mátrix
	 */
	public void setObject(Object [][] o){
		this.o = o;
	}
	
	/**
	 * Visszatér az alap objektum mátrix-szal tábla számára.
	 * @return @see {@link #o}
	 */
	public Object[][] getMap(){ return o; }
	
	/**
	 * Tábla fejlécével tér vissza
	 * @return String tömb
	 */
	public String[] getHeader(){ return header; }
	
	/**
	 * Játékos pozíciójának megkeresése
	 * @return @see {@link Coordinate}
	 */
	public Coordinate getGamerPos(){
		
		logger.trace("getGamerPos");
		
		int x = 0, y = 0;
		for(int i = 0; i < o.length; i++){
			for(int ii = 0; ii < o[i].length; ii++){
				if("jatekos".equals(o[i][ii])){
					x = i;
					y = ii;
				}
			}
		}
		
		logger.debug("Gamer pos x - y : {} - {}", x,y);
		
		return new Coordinate(x,y);
	}
	
	/**
	 * Lépéssek lekezelése.
	 */
	public void step(){
		logger.trace("Step begin");
		
		actPos = new Coordinate(stepData.get(stepData.size()-1).x, stepData.get(stepData.size()-1).y);
		
		if(Math.abs(clickedPos.x - actPos.x) == 1 && Math.abs(clickedPos.y - actPos.y) == 0 ||
				Math.abs(clickedPos.x - actPos.x) == 0 && Math.abs(clickedPos.y - actPos.y) == 1) { // csak ha egyel mozgunk el

			if("t".equals(clickedPosValue)){ // csak ha sima területre lépünk
				logger.trace("if t == clickedPosValue");
				
				setTableValue(null);
			} else if ( "k".equals(clickedPosValue)){
				
				logger.trace("if k == clickedPosValue");
				
				pushedPos = new Coordinate();
				if(clickedPos.x > actPos.x) { // ha x nőt akkor
					pushedPos.x = clickedPos.x + 1;
				} else if (clickedPos.x < actPos.x) {
					pushedPos.x = clickedPos.x - 1;
				} else {
					pushedPos.x = clickedPos.x;
				}
				
				logger.debug("pushedPos.x: {} ", pushedPos.x);

				if(clickedPos.y > actPos.y) { // ha y nőt akkor
					pushedPos.y = clickedPos.y + 1;
				} else if (clickedPos.y < actPos.y) {
					pushedPos.y = clickedPos.y - 1;
				} else {
					pushedPos.y = clickedPos.y;
				}
				
				logger.debug("pushedPos.y: {} ", pushedPos.y);
				
				if("t".equals(o[pushedPos.x][pushedPos.y])){ // a kocka amerre mozog ott szabad e
					setTableValue("k");
				} else if ("c".equals(o[pushedPos.x][pushedPos.y])){
					winCount++;
					
					logger.debug("winCount: {}", winCount);
					
					setTableValue("c");
				}
			}
		}
		logger.trace("Step end");
	}
	
	/**
	 * @see {@link #o} mátrixban lévő adatok átírása @see {@link #step()} megfelelően.
	 * @see {@link #stepData} újlépés rögzítése 
	 */
	public void setTableValue(String c){
		if (c == null){
			logger.trace("setTableValue(null)");
			o[actPos.x][actPos.y] = "t"; // t a jatekos helyére
			o[clickedPos.x][clickedPos.y] = "jatekos"; // jatekost t helyére
			logger.debug("o -> actPos value: {} o -> clickedPos value: {}", 
					o[actPos.x][actPos.y],o[clickedPos.x][clickedPos.y] );
		} else {
			logger.trace("setTableValue else");
			o[actPos.x][actPos.y] = "t";
			o[clickedPos.x][clickedPos.y] = "jatekos";
			o[pushedPos.x][pushedPos.y] = c; // ahol t volt oda megy a kocka/cél
			logger.debug("o -> actPos value: {} ; o -> clickedPos value: {} ; o -> pushedPos value: {}", 
					o[actPos.x][actPos.y],o[clickedPos.x][clickedPos.y], o[pushedPos.x][pushedPos.y] );
		}
		
		stepData.add(new Coordinate(clickedPos.x,clickedPos.y));
	}

}
