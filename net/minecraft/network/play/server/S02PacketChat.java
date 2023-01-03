package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.*;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.*;

public class S02PacketChat implements Packet<INetHandlerPlayClient>
{
    private IChatComponent chatComponent;
    private byte type;

    public S02PacketChat()
    {
    }

    public S02PacketChat(IChatComponent component)
    {
        this(component, (byte)1);
    }

    public S02PacketChat(IChatComponent message, byte typeIn)
    {
    	String messageIn1 = message.getUnformattedText();
    	String messageIn2 = message.getUnformattedTextForChat();
    	String messageIn3 = message.getFormattedText();

    	if (messageIn1.toLowerCase().contains("lmao") || messageIn1.toLowerCase().contains("lmfao"))
        {
        	messageIn1 = messageIn1.replaceAll("lmao", "lol").replaceAll("lmfao", "lol");
        }

    	if (messageIn2.toLowerCase().contains("lmao") || messageIn2.toLowerCase().contains("lmfao"))
        {
        	messageIn2 = messageIn2.replaceAll("lmao", "lol").replaceAll("lmfao", "lol");
        }

    	if (messageIn3.toLowerCase().contains("lmao") || messageIn3.toLowerCase().contains("lmfao"))
        {
        	messageIn3 = messageIn3.replaceAll("lmao", "lol").replaceAll("lmfao", "lol");
        }

    	String one = messageIn1;
    	String two = messageIn2;
    	String three = messageIn3;

    	IChatComponent message2 = new IChatComponent()
    	{
			@Override
			public Iterator<IChatComponent> iterator()
			{
				return message.iterator();
			}

			@Override
			public IChatComponent setChatStyle(ChatStyle style)
			{
				return message.setChatStyle(style);
			}

			@Override
			public ChatStyle getChatStyle()
			{
				return message.getChatStyle();
			}

			@Override
			public IChatComponent appendText(String text)
			{
				return message.appendText(text);
			}

			@Override
			public IChatComponent appendSibling(IChatComponent component)
			{
				return message.appendSibling(component);
			}

			@Override
			public String getUnformattedTextForChat()
			{
				return two;
			}

			@Override
			public String getUnformattedText()
			{
				return one;
			}

			@Override
			public String getFormattedText()
			{
				return three;
			}

			@Override
			public List<IChatComponent> getSiblings()
			{
				return message.getSiblings();
			}

			@Override
			public IChatComponent createCopy()
			{
				return message.createCopy();
			}
    	};

    	System.out.println(message2.getFormattedText());
        this.chatComponent = message2;
        this.type = typeIn;
    }

    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.chatComponent = buf.readChatComponent();
        this.type = buf.readByte();
    }

    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeChatComponent(this.chatComponent);
        buf.writeByte(this.type);
    }

    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleChat(this);
    }

    public IChatComponent getChatComponent()
    {
        return this.chatComponent;
    }

    public boolean isChat()
    {
        return this.type == 1 || this.type == 2;
    }

    public byte getType()
    {
        return this.type;
    }
}
