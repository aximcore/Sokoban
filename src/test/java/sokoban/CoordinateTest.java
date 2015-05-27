package sokoban;

import static org.junit.Assert.*;
import me.aximcore.Coordinate;

import org.junit.Before;
import org.junit.Test;

public class CoordinateTest {
	
	Coordinate cord;
	
	@Before
	public void init(){
		cord = new Coordinate();
	}
	
	@Test
	public void konstruktorTest() {
		assertEquals("Argumentum nélküli konst.-ra 0 az x és y", 0,cord.x);		
		assertEquals("Argumentum nélküli konst.-ra 0 az x és y", 0,cord.y);
	}
	
	@Test
	public void konstruktorTest1() {
		cord = new Coordinate(1,2);
		
		assertEquals("Argumentumos konst.-ra x -> 1", 1,cord.x);		
		assertEquals("Argumentumos konst.-ra y -> 2", 2,cord.y);
	}

}
