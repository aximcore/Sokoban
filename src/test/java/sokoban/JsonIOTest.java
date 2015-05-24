package sokoban;

import static org.junit.Assert.*;
import me.aximcore.JsonIO;

import org.junit.Before;
import org.junit.Test;

public class JsonIOTest {
	
	JsonIO js;
	
	@Before
	public void jsonConst(){
		js = new JsonIO();
	}

	@Test
	public void getMapTest() {
		assertNotNull(js.getMap());
	}
	
	@Test
	public void getHeaderTest() {
		assertNotNull(js.getHeader());
	}
	
	@Test
	public void mapRowsTest(){
		assertEquals("11 sora van a map.json matrixnak", 11, js.getMapRowsSize());
	}

}
