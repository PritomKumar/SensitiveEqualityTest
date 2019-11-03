import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

//import com.rifat.eqn.ExpressionFormatter;


public class ExpressionFormatterTest extends TestCase{

	public List<String>list;	
	public ExpressionFormatter expressionFormatter;
	public String expression;
	
	Main main;
	Main main1 = new Main();
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		setExpression();
		setExpressionFormatter();
		main = new Main();
	}
	
	private void setExpressionFormatter()
	{
		expressionFormatter = new ExpressionFormatter(expression);
	}
	
	private void setExpression()
	{
		expression = "2 + 2";
	}
	
	@Test
	public void testGetRightExp()
	{
		assertEquals("2+2", expressionFormatter.getRightExp());
	}
	
	@Test
	public void testModifyString()
	{
		assertEquals("2+2", expressionFormatter.modifyString());
	}
	
	@Test
	public void testRemove()
	{
		assertEquals("2", expressionFormatter.remove(0, 1));
	}
	

}
