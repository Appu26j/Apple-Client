package apple26j.events.mc;

import apple26j.events.Event;

public class EventKey extends Event
{
	private int key;
	
	public int getKey()
	{
		return this.key;
	}
	
	public EventKey setKey(int key)
	{
		this.key = key;
		return this;
	}
}
