package apple26j.mods.hypixel;

import com.google.common.eventbus.Subscribe;

import apple26j.events.mc.EventWorldChange;
import apple26j.events.network.EventPacketReceive;
import apple26j.interfaces.ModInterface;
import apple26j.mods.*;
import apple26j.settings.Setting;
import apple26j.utils.GGUtil;
import net.minecraft.network.play.server.S02PacketChat;

@ModInterface(name = "Anti GG", description = "Hides \"GG\" messages at the end of a game.", category = Category.HYPIXEL)
public class AntiGG extends Mod
{
	private boolean aBoolean = true;
	
	public AntiGG()
	{
		this.addSetting(new Setting("Hide self GG message", this, true));
	}
	
	@Subscribe
	public void onPacketReceive(EventPacketReceive e)
	{
		if (e.getPacket() instanceof S02PacketChat)
		{
			if (mc.getCurrentServerData() != null)
			{
				String message = ((S02PacketChat) e.getPacket()).getChatComponent().getUnformattedText();
				
				if (!this.aBoolean)
				{
					this.aBoolean = GGUtil.hasGameEnded(message);
				}
				
				if (this.aBoolean)
				{
					if (GGUtil.containsGG(message))
					{
						if (message.contains(mc.thePlayer.getName()))
						{
							if (this.getSetting("Hide self GG message").getCheckBoxValue())
							{
								e.setCancelled(true);
							}
						}
						
						else
						{
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	@Subscribe
	public void onWorldChange(EventWorldChange e)
	{
		this.aBoolean = false;
	}
}
