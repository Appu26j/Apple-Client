package apple26j.mods.hypixel;

import com.google.common.eventbus.Subscribe;

import apple26j.events.mc.EventWorldChange;
import apple26j.events.network.EventPacketReceive;
import apple26j.interfaces.ModInterface;
import apple26j.mods.*;
import apple26j.settings.Setting;
import apple26j.utils.GGUtil;
import net.minecraft.network.play.server.S02PacketChat;

@ModInterface(name = "Auto GG", description = "Automatically says \"GG\" at the end of a game.", category = Category.HYPIXEL)
public class AutoGG extends Mod
{
	private boolean aBoolean = true;
	
	public AutoGG()
	{
		this.addSetting(new Setting("GG Message", this, "gg", "gg", "gf", "GG", "Good Game", "Well Played! <3"));
	}
	
	@Subscribe
	public void onPacketReceive(EventPacketReceive e)
	{
		if (e.getPacket() instanceof S02PacketChat)
		{
			if (mc.getCurrentServerData() != null)
			{
				String message = ((S02PacketChat) e.getPacket()).getChatComponent().getUnformattedText();
				
				if (GGUtil.hasGameEnded(message))
				{
					if (this.aBoolean)
					{
						mc.thePlayer.sendChatMessage(mc.getCurrentServerData().serverIP.toLowerCase().endsWith("hypixel.net") ? ("/ac " + this.getSetting("GG Message").getModeValue()) : this.getSetting("GG Message").getModeValue());
						this.aBoolean = false;
					}
				}
			}
		}
	}
	
	@Subscribe
	public void onWorldChange(EventWorldChange e)
	{
		this.aBoolean = true;
	}
}
