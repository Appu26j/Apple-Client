package apple26j.events.network;

import apple26j.events.Event;
import net.minecraft.network.Packet;

public class EventPacketReceive extends Event
{
	private Packet packet;
	
	public Packet getPacket()
	{
		return packet;
	}
	
	public EventPacketReceive setPacket(Packet packet)
	{
		this.packet = packet;
		return this;
	}
}
