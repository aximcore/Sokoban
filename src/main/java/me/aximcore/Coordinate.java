package me.aximcore;

/**
 * Pozíciók tárolásához használt adatstruktúra.
 */
public class Coordinate {
	/**
	 * Sor index.
	 */
	public int x;
	
	/**
	 * Oszlop index.
	 */
	public int y;

	/**
	 * Konstruktor két paraméteres példányosításhoz.
	 * @param x sor index
	 * @param y oszlop index
	 */
	public Coordinate(int x, int y){this.x = x; this.y = y;}
	
	/**
	 * Konstruktor paraméter nélküli példányosításhoz.
	 */
	public Coordinate(){this.x = 0; this.y = 0;}
}

