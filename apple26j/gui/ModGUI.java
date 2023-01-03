package apple26j.gui;

import static apple26j.utils.RenderUtil.drawOutline;

import java.awt.Color;

import apple26j.mods.Mod;

public class ModGUI
{
	private Mod mod;
	private DragGUI parent;
	
	public ModGUI(Mod mod, DragGUI parent)
	{
		this.mod = mod;
		this.parent = parent;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
		this.mod.onRender();
		
		if (this.isInside(mouseX, mouseY, this.mod.getX(), this.mod.getY(), this.mod.getWidth(), this.mod.getHeight()))
		{
			drawOutline(this.mod.getX(), this.mod.getY(), this.mod.getWidth(), this.mod.getHeight(), new Color(255, 255, 255, (int) (this.parent.getIndex1() * 255)).getRGB());
		}
    }
	
	public boolean isInside(int mouseX, int mouseY, float x, float y, float width, float height)
	{
		return mouseX > x && mouseX < width && mouseY > y && mouseY < height;
	}
	
	public Mod getMod()
	{
		return this.mod;
	}
}
