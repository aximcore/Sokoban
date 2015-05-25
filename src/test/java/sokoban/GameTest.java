package sokoban;

import static org.junit.Assert.*;
import me.aximcore.Game;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	
	Game g;

	@Before
	public void initTestGame(){
		g = new Game();
	}
	
	@Test
	public void getMapTest() {	
		assertNotNull(g.getMap());
	}
	
	@Test(expected=NullPointerException.class)
	public void getGamerPosTest(){	
		g.setObject(new Object[][] {null});
		g.getGamerPos(); // túl indexel
	}
	
	@Test
	public void getGamerPosTest1(){
		g.setObject(new Object[][] {
				{null, null, null, null, null, null, null, null, null, null},
				{null, "jatekos", "t", "t", null, null, null, null, null, null},
				{null, null, "t", "k", "t", "t", null, null, null, null},
				{null, null, "t", "t", "t", "t", null, null, null, null},
				{null, null, null, null, "t", "t", "t", "t", null, null},
				{null, null, null, null, "t", "t", "k", "t", "c", null},
				{null, null, null, null, null, "t", "t", "t", "c", null},
				{null, null, null, null, null, null, null, "t", "t", null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
		});

		assertEquals("Jatekos koordinátája x = 1", 1, g.getGamerPos().x);
		assertEquals("Jatekos koordinátája y = 1", 1, g.getGamerPos().y);
	}
	
	@Test
	public void getGamerPosTest2(){	
		g.setObject(new Object[][] {
				{null, null, null, null, null, null, null, null, null, null},
				{null, "t", "t", "t", null, null, null, null, null, null},
				{null, null, "t", "k", "t", "t", null, null, null, null},
				{null, null, "t", "t", "t", "t", null, null, null, null},
				{null, null, null, null, "t", "t", "t", "t", null, null},
				{null, null, null, null, "t", "t", "k", "t", "c", null},
				{null, null, null, null, null, "t", "t", "t", "c", null},
				{null, null, null, null, null, null, null, "t", "t", null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
		});
		
		assertEquals("Ha nincs jatekos akkor x = 0", 0, g.getGamerPos().x);
		assertEquals("Ha nincs jatekos akkor y = 0", 0, g.getGamerPos().y);
	}
	
	@Test
	public void stepTest(){
		g.setClickedPos(1, 2); // t helyre
		g.setClickedValue("t");
		g.step();	// játékos átkerül 1 sor 2 oszlopba
		
		assertEquals("Jatekos átkerül x = 1-be", 1, g.getGamerPos().x);
		assertEquals("Jatekos átkerül y = 2-be", 2, g.getGamerPos().y);
		assertEquals("Egyet lépet", 1, Integer.valueOf(g.getStepCount()).intValue());

	}
	
	@Test
	public void stepTest1(){
		g.setClickedPos(1, 3); // t helyre
		g.setClickedValue("t");
		g.step();	// játékos nem lép mert nem egyet lépne
		
		assertEquals("Játékos marad x = 1-ben", 1, g.getGamerPos().x);
		assertEquals("Játékos marad y = 1-ben", 1, g.getGamerPos().y);
		assertEquals("Nem lepet egyet sem", 0, Integer.valueOf(g.getStepCount()).intValue());
	}
	
	@Test(expected=NullPointerException.class)
	public void setTableValueTest(){
		g.setTableValue("t"); // step-ben lesz foglalva actPos-nak memória
	}

}
