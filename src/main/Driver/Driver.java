package main.Driver;

import java.util.HashMap;
import java.util.Map;

public class Driver
{
	private Map<Operation, Boolean> mapping = new HashMap<Operation, Boolean>();

    /**
     * Get a boolean indicating whether the current digital operation is enabled
     * @param digitalOperation to get
     * @return the current value of the digital operation
     */
    public boolean getDigital(Operation digitalOperation)
    {
        synchronized (this.mapping)
        {
        	Boolean currentValue = this.mapping.containsKey(digitalOperation) && this.mapping.get(digitalOperation);
        	if (currentValue)
        	{
        		this.mapping.put(digitalOperation, false);
        		return true;
        	}

    	    return false;
        }
    }

    /**
     * Press a button, changing its value to true
     * @param digitalOperation
     */
    public void pressButton(Operation digitalOperation)
    {
        synchronized (this.mapping)
        {
            this.mapping.put(digitalOperation, true);
        }
    }
}
