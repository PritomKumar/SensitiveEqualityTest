import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class OperationTest extends TestCase{

	SimulateEquation simulateEquation;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		simulateEquation = new SimulateEquation();
	}
	
	@Test
	public void testAddOperation()
	{
		assertEquals(simulateEquation.opration(10, 10, "+"), 20);
	}
	
	@Test
	public void testSubtractOperation()
	{
		assertEquals(simulateEquation.opration(20, 20, "-"), 0);
	}
	
	@Test
	public void testDivisionOperation()
	{
		assertEquals(simulateEquation.opration(100, 100, "/"), 1);
	}
	
	@Test
	public void testMultiplicationOperation()
	{
		assertEquals(simulateEquation.opration(5, 5, "*"), 25);
	}
	

}
