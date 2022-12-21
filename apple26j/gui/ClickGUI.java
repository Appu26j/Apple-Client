package apple26j.gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import apple26j.Apple;
import apple26j.mods.*;
import apple26j.utils.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ClickGUI extends GuiScreen
{
	private long refreshRate = (long) (1000F / GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate());
	private ArrayList<ClickGUIsModGUI> clickGUIsModGUIs = new ArrayList<>();
	private TimeUtil timeUtil = new TimeUtil();
	private boolean isGuiClosing = false;
	private int index1 = 0;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		if (this.mc.gameSettings.enableVsync || this.timeUtil.hasTimePassed(this.refreshRate))
		{
			if (this.isGuiClosing)
			{
				if (this.index1 > 0)
				{
					this.index1 -= 16;
				}
			}
			
			else
			{
				if (this.index1 < 128)
				{
					this.index1 += 16;
				}
			}
		}
		
		RenderUtil.drawRect(0, 0, this.width, this.height, new Color(0, 0, 0, this.isGuiClosing ? this.index1 : 128).getRGB());
		
		if (this.isGuiClosing && this.index1 == 0)
		{
			try
			{
				super.keyTyped('0', 1);
				this.isGuiClosing = false;
			}
			
			catch (Exception e)
			{
				;
			}
		}
		
		RenderUtil.drawRect((this.width / 2) - 225, (this.height / 2) - 150, (this.width / 2) + 208, (this.height / 2) + 150, new Color(0, 0, 0, this.index1 / 2).getRGB());
		RenderUtil.drawRect((this.width / 2) + 208, (this.height / 2) - 133, (this.width / 2) + 225, (this.height / 2) + 150, new Color(0, 0, 0, this.index1 / 2).getRGB());
		RenderUtil.drawRect((this.width / 2) + 208, (this.height / 2) - 150, (this.width / 2) + 225, (this.height / 2) - 133, this.isInside(mouseX, mouseY, (this.width / 2) + 208, (this.height / 2) - 150, (this.width / 2) + 225, (this.height / 2) - 133) ? new Color(0, 0, 0, this.index1).getRGB() : new Color(0, 0, 0, this.index1 / 2).getRGB());
		GlStateManager.color(1, 1, 1, this.index1 / 128F);
		RenderUtil.drawImage(new ResourceLocation("icons/cross.png"), (this.width / 2) + 210.5F, (this.height / 2) - 147.5F, 12, 12);
		
		for (ClickGUIsModGUI clickGUIsModGUI : this.clickGUIsModGUIs)
		{
			clickGUIsModGUI.drawScreen(mouseX, mouseY, partialTicks);
		}
    }
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
		if (keyCode == 1)
        {
            this.isGuiClosing = true;
        }
    }
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if (this.isInside(mouseX, mouseY, (this.width / 2) + 208, (this.height / 2) - 150, (this.width / 2) + 225, (this.height / 2) - 133) && mouseButton == 0)
		{
			SoundUtil.playClickSound();
			this.keyTyped('0', 1);
		}
		
		for (ClickGUIsModGUI clickGUIsModGUI : this.clickGUIsModGUIs)
		{
			clickGUIsModGUI.mouseClicked(mouseX, mouseY, mouseButton);
		}
    }
	
	@Override
	public void initGui()
	{
		super.initGui();
		this.clickGUIsModGUIs.clear();
		int xOffset = 17;
		int yOffset = 34;
		
		for (Mod mod : Apple.CLIENT.getModsManager().getMods(Category.ALL))
		{
			if (xOffset == 422)
			{
				xOffset = 17;
				yOffset += 135;
			}
			
			this.clickGUIsModGUIs.add(new ClickGUIsModGUI(mod, this, xOffset, yOffset, this.width, this.height));
			xOffset += 135;
		}
	}
	
	public boolean isInside(int mouseX, int mouseY, float x, float y, float width, float height)
	{
		return mouseX > x && mouseX < width && mouseY > y && mouseY < height;
	}
	
	public int getIndex1()
	{
		return this.index1;
	}
	
	@Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
}
