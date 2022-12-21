package apple26j.events;

import java.util.ArrayList;

import apple26j.events.entity.EventUpdate;
import apple26j.events.gui.EventRender;
import apple26j.events.mc.EventKey;

public class EventsManager
{
	private ArrayList<Event> events = new ArrayList<>();
	
	public EventsManager()
	{
		this.events.add(new EventUpdate());
		this.events.add(new EventKey());
		this.events.add(new EventRender());
	}
	
	public Event getEvent(Class classs)
	{
		return this.events.stream().filter(event -> event.getClass().equals(classs)).findFirst().orElse(null);
	}
}
