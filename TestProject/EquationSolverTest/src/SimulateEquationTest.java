import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class SimulateEquationTest extends TestCase{

	public SimulateEquation simulateEquation;
	private String addOperator;
	private String subtractOperator;
	private String divideOperator;
	private String mulitiplicationOperator;
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		initializeSimulateEquation();
		setOperators();
	}
	
	private void initializeSimulateEquation()
	{
		simulateEquation = new SimulateEquation();
		
	}
	
	private void setOperators()
	{
		addOperator = "+";
		subtractOperator = "-";
		divideOperator = "/";
		mulitiplicationOperator = "*";
	}
	
	private String getAddOperator() {
		return addOperator;
	}

	private String getMulitiplicationOperator() {
		return mulitiplicationOperator;
	}

	private String getDivideOperator() {
		return divideOperator;
	}

	private String getSubtractOperator() {
		return subtractOperator;
	}

	@Test
	public void testIsOperatorForAdd() {
		assertEquals(simulateEquation.isOperator("+"), true);
	}

	@Test
	public void testIsOperatorForDivide() {
		assertEquals(simulateEquation.isOperator("/"), true);
	}

	@Test
	public void testIsOperatorForMultiplication() {
		assertEquals(simulateEquation.isOperator("*"),
				true);
	}

	@Test
	public void testIsOperatorForSubtract() {
		assertEquals(simulateEquation.isOperator("-"), true);
	}

}
