package apple26j.gui;

import java.awt.Color;
import java.io.IOException;

import apple26j.fontrenderer.FixedFontRenderer;
import apple26j.mods.Mod;
import apple26j.utils.*;

public class ClickGUIsModGUI
{
	private Mod mod;
	private ClickGUI parent;
	private int xOffset, yOffset, width, height;
	
	public ClickGUIsModGUI(Mod mod, ClickGUI parent, int xOffset, int yOffset, int width, int height)
	{
		this.mod = mod;
		this.parent = parent;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.width = width;
		this.height = height;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
		RenderUtil.drawRect(((this.width / 2) - 225) + this.xOffset, ((this.height / 2) - 150) + this.yOffset, ((this.width / 2) - 225) + (this.xOffset + 120), ((this.height / 2) - 150) + (this.yOffset + 120), this.isInside(mouseX, mouseY, ((this.width / 2) - 225) + this.xOffset, ((this.height / 2) - 150) + this.yOffset, ((this.width / 2) - 225) + (this.xOffset + 120), ((this.height / 2) - 150) + (this.yOffset + 120)) ? new Color(0, 0, 0, (int) (this.parent.getIndex1() * 64)).getRGB() : new Color(0, 0, 0, (int) (this.parent.getIndex1() * 32)).getRGB());
		RenderUtil.drawRect(((this.width / 2) - 225) + this.xOffset, ((this.height / 2) - 50) + this.yOffset, ((this.width / 2) - 225) + (this.xOffset + 120), ((this.height / 2) - 150) + (this.yOffset + 120), this.mod.isEnabled() ? new Color(0, 200, 50, (int) (this.parent.getIndex1() * 255)).getRGB() : new Color(200, 50, 50, (int) (this.parent.getIndex1() * 255)).getRGB());
		FixedFontRenderer.drawString(this.mod.getName(), (((this.width / 2) - 165) + this.xOffset) - (FixedFontRenderer.getStringWidth(this.mod.getName(), 12) / 2), ((this.height / 2) - 150) + (this.yOffset + 45), 12, new Color(75, 75, 75, (int) (this.parent.getIndex1() * 255)).getRGB());
		FixedFontRenderer.drawStringWithShadow(this.mod.isEnabled() ? "ENABLED" : "DISABLED", (((this.width / 2) - 165) + this.xOffset) - (FixedFontRenderer.getStringWidth(this.mod.isEnabled() ? "ENABLED" : "DISABLED", 8) / 2), ((this.height / 2) - 150) + (this.yOffset + 106), 8, new Color(255, 255, 255, (int) (this.parent.getIndex1() * 255)).getRGB());
    }
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
		if (this.isInside(mouseX, mouseY, ((this.width / 2) - 225) + this.xOffset, ((this.height / 2) - 150) + this.yOffset, ((this.width / 2) - 225) + (this.xOffset + 120), ((this.height / 2) - 150) + (this.yOffset + 120)))
		{
			if (mouseButton == 0)
			{
				this.mod.toggle();
				
				if (this.mod.isEnabled())
				{
					SoundUtil.playEnableSound();
				}
				
				else
				{
					SoundUtil.playDisableSound();
				}
			}
			
			else
			{
				SoundUtil.playClickSound();
				this.parent.setSelectedMod(this.mod);
			}
		}
    }
	
	public boolean isInside(int mouseX, int mouseY, float x, float y, float width, float height)
	{
		return mouseX > x && mouseX < width && mouseY > y && mouseY < height;
	}
}
