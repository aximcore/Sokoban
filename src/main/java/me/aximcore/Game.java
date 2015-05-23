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
	
	private Coordinate actPos;
	private Coordinate pushedPos; // kocka új koordinátája tollás következtében
	private Coordinate clickedPos;
	private Object clickedPosValue;
	private List<Coordinate> stepData = new ArrayList<>();
	private Coordinate kocka = new Coordinate();
	private int score;
	
	private Object o[][] = new Object[][] {
			{null, null, null, null, null, null, null, null, null, null},
			{null, "jatekos", "t", "t", null, null, null, null, null, null},
			{null, null, "t", "k", "t", "t", null, null, null, null},
			{null, null, "t", "t", "t", "t", null, null, null, null},
			{null, null, null, null, "t", "t", "t", "t", null, null},
			{null, null, null, null, "t", "t", "t", "t", "t", null},
			{null, null, null, null, null, "t", "t", "t", "c", null},
			{null, null, null, null, null, null, null, "t", "t", null},
			{null, null, null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null, null, null},
	};

	class Coordinate {
		public int x;
		public int y;

		public Coordinate(int x, int y){this.x = x; this.y = y;}
		public Coordinate(){this.x = 0; this.y = 0;}
	}

	public void setClickedPos(int x, int y){
		this.clickedPos = new Coordinate(x,y);
	}
	
	public void addStepData(int x, int y){
		stepData.add(new Coordinate(x,y));
	}
	
	public void setClickedValue(Object o){
		clickedPosValue = o;
	}
	
	public Object[][] getMap(){ return o; }
	
	public void step(){
		System.out.println("step-ba belépve");
		actPos = new Coordinate(stepData.get(stepData.size()-1).x, stepData.get(stepData.size()-1).y);
		
		if(Math.abs(clickedPos.x - actPos.x) == 1 && Math.abs(clickedPos.y - actPos.y) == 0 ||
				Math.abs(clickedPos.x - actPos.x) == 0 && Math.abs(clickedPos.y - actPos.y) == 1) { // csak ha egyel mozgunk el

			System.out.println("1-ba belépve");
			if("t".equals(clickedPosValue)){ // csak ha sima területre lépünk
				System.out.println("t-ba belépve");
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
					System.out.println("k-ba belépve");
					setTableValue("k");
				} else if ("c".equals(o[pushedPos.x][pushedPos.y])){
					System.out.println("c-ba belépve");
					setTableValue("c");
				}
			}
		}
		
		if(o[clickedPos.x][clickedPos.y] != null)
		System.out.println("game v: " + o[clickedPos.x][clickedPos.y].toString());
		
	}
	
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
