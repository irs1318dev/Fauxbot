package main;

public class FauxbotRunner implements Runnable
{
	private ControllerManager controllers;
	private boolean stop;

	public FauxbotRunner(ControllerManager controllers)
	{
		this.controllers = controllers;
		this.stop = false;
	}

	@Override
	public void run()
	{
		while (!this.stop)
		{
			this.controllers.update();

			try
			{
				Thread.sleep(20);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void stop()
	{
		this.stop = true;
	}
}
