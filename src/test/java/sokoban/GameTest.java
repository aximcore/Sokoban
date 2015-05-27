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
	
	@Test
	public void stepTest2(){
		g.setClickedPos(2,1); // null helyre
		g.setClickedValue(null);
		g.step();	// játékos nem lép mert nem t-re lép

		assertEquals("Játékos marad x = 1-ben", 1, g.getGamerPos().x);
		assertEquals("Játékos marad y = 1-ben", 1, g.getGamerPos().y);
		assertEquals("Nem lepet egyet sem", 0, Integer.valueOf(g.getStepCount()).intValue());
	}
	
	@Test
	public void stepTest3(){
		g.setClickedPos(2,3); // k helyre
		g.setClickedValue("k");
		g.step();	// játékos nem lép mert távol van

		assertEquals("Játékos marad x = 1-ben", 1, g.getGamerPos().x);
		assertEquals("Játékos marad y = 1-ben", 1, g.getGamerPos().y);
		assertEquals("Nem lepet egyet sem", 0, Integer.valueOf(g.getStepCount()).intValue());
	}
	
	@Test
	public void stepTest4(){
		g.setClickedPos(1,2); // k helyre
		g.setClickedValue("k");
		g.step();	// játékos lép k-helyére

		assertEquals("Játékos x = 1-ben", 1, g.getGamerPos().x);
		assertEquals("Játékos y = 2-ben", 2, g.getGamerPos().y);
		assertEquals("Egyet lépet", 1, Integer.valueOf(g.getStepCount()).intValue());
	}
	
	@Test
	public void stepTest5(){
		g.setClickedPos(2,1); // k helyre
		g.setClickedValue("k");
		g.step();	// játékos nem lép k-helyére

		assertEquals("Játékos marad a x = 1-ben", 1, g.getGamerPos().x);
		assertEquals("Játékos marad a y = 1-ben", 1, g.getGamerPos().y);
		assertEquals("Nem lépet", 0, Integer.valueOf(g.getStepCount()).intValue());
	}
	
	@Test(expected=NullPointerException.class)
	public void setTableValueTest(){
		g.setTableValue("t"); // step-ben lesz foglalva actPos-nak memória
	}
	
	@Test
	public void addStepDataTest(){
		int size = Integer.parseInt(g.getStepCount());
		g.addStepData(0, 0);
		assertEquals("Méret eggyel nő", size + 1, Integer.parseInt(g.getStepCount()));
	}
	
	@Test
	public void getWinCountTest(){
		assertEquals("Elején nincs helyén még kocka","0",g.getWinCount());
	}
	
	@Test
	public void getHeaderTest(){
		assertNotNull(g.getHeader());
	}

}
