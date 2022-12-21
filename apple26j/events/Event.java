package apple26j.events;

public class Event
{
	private boolean cancelled;
	
	public boolean isCancelled()
	{
		return this.cancelled;
	}
	
	public Event setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
		return this;
	}
}
