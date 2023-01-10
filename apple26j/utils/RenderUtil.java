package apple26j.utils;

import static org.lwjgl.opengl.GL11.*;

import apple26j.interfaces.MinecraftInterface;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderUtil implements MinecraftInterface
{
	private static ResourceLocation storedResourceLocation;
	
	public static void drawRect(float x, float y, float width, float height, int color)
	{
		if (x < width)
        {
            float f = x;
            x = width;
            width = f;
        }
		
        if (y < height)
        {
            float g = y;
            y = height;
            height = g;
        }
        
        float red = (float) (color >> 16 & 255) / 255;
        float green = (float) (color >> 8 & 255) / 255;
        float blue = (float) (color & 255) / 255;
        float alpha = (float) (color >> 24 & 255) / 255;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        worldrenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, height, 0).endVertex();
        worldrenderer.pos(width, height, 0).endVertex();
        worldrenderer.pos(width, y, 0).endVertex();
        worldrenderer.pos(x, y, 0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
	}
	
	private static void drawRectNoColor(float x, float y, float width, float height, int color)
	{
		if (x < width)
        {
            float f = x;
            x = width;
            width = f;
        }
		
        if (y < height)
        {
            float g = y;
            y = height;
            height = g;
        }
        
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        worldrenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(x, height, 0).endVertex();
        worldrenderer.pos(width, height, 0).endVertex();
        worldrenderer.pos(width, y, 0).endVertex();
        worldrenderer.pos(x, y, 0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
	}
	
	public static void drawOutline(float x, float y, float width, float height, int color)
	{
		drawRect(x, y, width, y + 1, color);
		drawRect(x, height - 1, width, height, color);
		drawRect(x, y, x + 1, height, color);
		drawRect(width - 1, y, width, height, color);
	}
	
	public static void drawImage(ResourceLocation image, float x, float y, float width, float height)
	{
		if (storedResourceLocation == null || !storedResourceLocation.equals(image))
		{
			storedResourceLocation = image;
		}
		
		mc.getTextureManager().bindTexture(storedResourceLocation);
		float f = 1 / width;
        float f1 = 1 / height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        worldrenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0).tex(0, height * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0).tex(width * f, height * f1).endVertex();
        worldrenderer.pos(x + width, y, 0).tex(width * f, 0).endVertex();
        worldrenderer.pos(x, y, 0).tex(0, 0).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
	}
	
	public static void drawImage(ResourceLocation image, float x, float y, float imageX, float imageY, float renderWidth, float renderHeight, float width, float height, float imageWidth, float imageHeight)
	{
		if (storedResourceLocation == null || !storedResourceLocation.equals(image))
		{
			storedResourceLocation = image;
		}
		
		mc.getTextureManager().bindTexture(storedResourceLocation);
		float f = 1 / width;
        float f1 = 1 / height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        worldrenderer.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + imageHeight, 0).tex(imageX * f, (imageY + renderHeight) * f1).endVertex();
        worldrenderer.pos(x + imageWidth, y + imageHeight, 0).tex((imageX + renderWidth) * f, (imageY + renderHeight) * f1).endVertex();
        worldrenderer.pos(x + imageWidth, y, 0).tex((imageX + renderWidth) * f, imageY * f).endVertex();
        worldrenderer.pos(x, y, 0).tex(imageX * f, imageY * f1).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
	}
	
	public static void drawRectWithRoundCorners(float x, float y, float width, float height, float radius, int color)
	{
        float red = (float) (color >> 16 & 255) / 255;
        float green = (float) (color >> 8 & 255) / 255;
        float blue = (float) (color & 255) / 255;
        float alpha = (float) (color >> 24 & 255) / 255;
        GlStateManager.color(red, green, blue, alpha);
		drawRect(x + radius, y, width - radius, y + radius, color);
		drawRect(x + radius, height - radius, width - radius, height, color);
		drawRect(x, y + radius, width - radius, height - radius, color);
		drawRect(width - radius, y + radius, width, height - radius, color);
		glEnable(GL_SCISSOR_TEST);
		scissor(x, y, x + radius, y + radius);
		drawCircle(x + radius, y + radius, radius, color);
		glDisable(GL_SCISSOR_TEST);
		glEnable(GL_SCISSOR_TEST);
		scissor(x, height - radius, x + radius, height);
		drawCircle(x + radius, height - radius, radius, color);
		glDisable(GL_SCISSOR_TEST);
		glEnable(GL_SCISSOR_TEST);
		scissor(width - radius, y, width, y + radius);
		drawCircle(width - radius, y + radius, radius, color);
		glDisable(GL_SCISSOR_TEST);
		glEnable(GL_SCISSOR_TEST);
		scissor(width - radius, height - radius, width, height);
		drawCircle(width - radius, height - radius, radius, color);
		glDisable(GL_SCISSOR_TEST);
	}
	
	public static void drawCircle(float x, float y, float size, int color)
	{
        float red = (float) (color >> 16 & 255) / 255;
        float green = (float) (color >> 8 & 255) / 255;
        float blue = (float) (color & 255) / 255;
        float alpha = (float) (color >> 24 & 255) / 255;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        glBegin(GL_TRIANGLE_FAN);
        double degree = Math.PI / 180;
        
        for (double d = 0; d < 360; d++)
        {
            glVertex2d(x + Math.sin(d * degree) * size, y + Math.cos(d * degree) * size);
        }
        
        glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
	
	public static void scissor(float x, float y, float width, float height)
    {
    	ScaledResolution scaledResolution = new ScaledResolution(mc);
    	x *= scaledResolution.getScaleFactor();
    	y *= scaledResolution.getScaleFactor();
    	width *= scaledResolution.getScaleFactor();
    	height *= scaledResolution.getScaleFactor();
        glScissor((int) x, mc.displayHeight - (int) height, (int) (width - x), (int) (height - y));
    }
}
