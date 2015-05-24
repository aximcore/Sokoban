/**
 * 
 */
package me.aximcore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aximcore
 *
 */
public class Game {
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
		this.clickedPos = new Coordinate(x,y);
	}
	
	/**
	 * Új lépés hozzáadása @see stepData -hoz.
	 * @param x
	 * @param y
	 */
	public void addStepData(int x, int y){
		stepData.add(new Coordinate(x,y));
	}
	
	/**
	 * Klikkelt pozíció értékének beállítása.
	 * @param o érték
	 */
	public void setClickedValue(Object o){
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
	 * Visszatér az alap objektum mátrixxal tábla számára.
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
	private Coordinate getGamerPos(){
		int x = 0, y = 0;
		for(int i = 0; i < o.length; i++){
			for(int ii = 0; ii < o[i].length; ii++){
				if("jatekos".equals(o[i][ii])){
					x = i;
					y = ii;
				}
			}
		}
		
		return new Coordinate(x,y);
	}
	
	/**
	 * Lépéssek lekezelése.
	 */
	public void step(){
		actPos = new Coordinate(stepData.get(stepData.size()-1).x, stepData.get(stepData.size()-1).y);
		
		if(Math.abs(clickedPos.x - actPos.x) == 1 && Math.abs(clickedPos.y - actPos.y) == 0 ||
				Math.abs(clickedPos.x - actPos.x) == 0 && Math.abs(clickedPos.y - actPos.y) == 1) { // csak ha egyel mozgunk el

			if("t".equals(clickedPosValue)){ // csak ha sima területre lépünk
				setTableValue(null);
			} else if ( "k".equals(clickedPosValue)){
				pushedPos = new Coordinate();
				if(clickedPos.x > actPos.x) { // ha x nőt akkor
					pushedPos.x = clickedPos.x + 1;
				} else if (clickedPos.x < actPos.x) {
					pushedPos.x = clickedPos.x - 1;
				} else {
					pushedPos.x = clickedPos.x;
				}

				if(clickedPos.y > actPos.y) { // ha y nőt akkor
					pushedPos.y = clickedPos.y + 1;
				} else if (clickedPos.y < actPos.y) {
					pushedPos.y = clickedPos.y - 1;
				} else {
					pushedPos.y = clickedPos.y;
				}
				
				if("t".equals(o[pushedPos.x][pushedPos.y])){ // a kocka amerre mozog ott szabad e
					setTableValue("k");
				} else if ("c".equals(o[pushedPos.x][pushedPos.y])){
					winCount++;
					setTableValue("c");
				}
			}
		}
		
	}
	
	/**
	 * @see {@link #o} mátrixban lévő adatok átírása @see {@link #step()} megfelelően.
	 * @see {@link #stepData} újlépés rögzítése 
	 */
	public void setTableValue(String c){
		if (c == null){
			o[actPos.x][actPos.y] = "t"; // t a jatekos helyére
			o[clickedPos.x][clickedPos.y] = "jatekos"; // jatekost t helyére
			
		} else {
			o[actPos.x][actPos.y] = "t";
			o[clickedPos.x][clickedPos.y] = "jatekos";
			o[pushedPos.x][pushedPos.y] = c; // ahol t volt oda megy a kocka/cél
		}
		
		stepData.add(new Coordinate(clickedPos.x,clickedPos.y));
	}

}
