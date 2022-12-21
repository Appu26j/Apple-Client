package apple26j.utils;

public class TimeUtil
{
	private long previousTime = 0;
	
	public long getCurrentTime()
	{
		return System.nanoTime() / 1000000;
	}
	
	public long getDifference()
	{
		return this.getCurrentTime() - this.previousTime;
	}
	
	public boolean hasTimePassed(long milliseconds)
	{
		if (milliseconds < this.getDifference())
		{
			this.reset();
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	public void reset()
	{
		this.previousTime = this.getCurrentTime();
	}
}
