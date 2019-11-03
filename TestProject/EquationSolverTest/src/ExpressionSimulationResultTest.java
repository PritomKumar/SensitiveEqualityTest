import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class ExpressionSimulationResultTest extends TestCase{

	private List<String>expressions = new ArrayList<>();
	private Stack<String>stack = new Stack<>();
	private SimulateEquation simulateEquation;
	private String noBracketExpression;
	private String bracketExpression;
	private String parameterExpression;
	private String multiBracketExpression;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		simulateEquation = new SimulateEquation();
		simulateEquation.exp="2+2";
		setExpressions();
	}
	
	private void setExpressions()
	{
		noBracketExpression = "10+10";
		bracketExpression = "(10+10)";
		parameterExpression = "(10+10)/1";
		multiBracketExpression = "((5+5)+(5+5))";
	}
	
	@Test
	public void testNoBracketExpression()
	{
		simulateEquation.exp = noBracketExpression;
		assertEquals(simulateEquation.expressionSimulation(), 20);
	}
	
	@Test
	public void testBracketExpression()
	{
		simulateEquation.exp = bracketExpression;
		assertEquals(simulateEquation.expressionSimulation(), 20);
	}
	
	@Test
	public void testParameterExpression()
	{
		simulateEquation.exp = parameterExpression;
		assertEquals(simulateEquation.expressionSimulation(), 20);
	}
	
	@Test
	public void testMultiBracketExpression()
	{
		simulateEquation.exp = multiBracketExpression;
		assertEquals(simulateEquation.expressionSimulation(), 20);
		
	}

}
