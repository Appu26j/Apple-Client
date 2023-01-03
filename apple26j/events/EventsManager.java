package apple26j.events;

import java.util.ArrayList;

import apple26j.events.entity.EventUpdate;
import apple26j.events.gui.EventRender;
import apple26j.events.mc.*;
import apple26j.events.network.EventPacketReceive;

public class EventsManager
{
	private ArrayList<Event> events = new ArrayList<>();
	
	public EventsManager()
	{
		this.events.add(new EventUpdate());
		this.events.add(new EventKey());
		this.events.add(new EventRender());
		this.events.add(new EventWorldChange());
		this.events.add(new EventPacketReceive());
	}
	
	public Event getEvent(Class classs)
	{
		return this.events.stream().filter(event -> event.getClass().equals(classs)).findFirst().orElse(null);
	}
}
