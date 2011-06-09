/*
 * IdToColor.java
 * 
 * Version 3.2
 *
 * Last Edited
 * 07/06/2011
 * 
 * written by codename_B
 * 
 */

package com.ubempire.render;

import java.awt.Color;

import org.bukkit.ChunkSnapshot;
import org.bukkit.util.Vector;

public class IdToColor {

    protected static BananaMapRender plugin; 
    
	public static Color getColor(int id, int damage, Vector vec, ChunkSnapshot chunk) {

	    //Fetch color from default table or config
	    Color color = plugin.varColor(id, damage);

	    //Grass/ground depth
		if((id == 2 || id == 31 || id == 37 || id == 38 || id == 39 || id == 40 || id == 59) && plugin.varDepthGround()) {
		    /* Future: Load biome-specific color here (from config) */
		    int mxp = 255-((128-vec.getBlockY())*2-1);
			if(mxp>255)mxp=255;
			color = new Color(color.getRed(), mxp, color.getBlue());
		}
		
		//Water depth
		if((id == 8 || id == 9) && plugin.varDepthWater()) {
    		Vector proc = vec;
    		int Y = vec.getBlockY();
    		int procID = id;
    		while(proc.getY()>1 && (procID == 8 || procID == 9)) {
    			proc.setY(proc.getY()-1);
    			procID = chunk.getBlockTypeId(proc.getBlockX(), proc.getBlockY(), proc.getBlockZ());
    		}
            int newR = (18-(Y-proc.getBlockY()))*4;
            int newG = (18-(Y-proc.getBlockY()))*7;
            int newB = (18-(Y-proc.getBlockY()))*16;
            if(newR<0)newR=0;
            if(newG<0)newG=0;
            if(newB<0)newB=0;
            if(newR>255)newR=255;
            if(newG>255)newG=255;
            if(newB>255)newB=255;
    		color = new Color(newR,newG,newB);
		}
		
		//Lava depth
		if((id == 10 || id == 11) && plugin.varDepthLava()) {
    		Vector proc = vec;
    		int Y = vec.getBlockY();
    		int procID = id;
    		while(proc.getY()>3 && (procID == 10 || procID == 11)) {
    			proc.setY(proc.getY()-3);
    			procID = chunk.getBlockTypeId(proc.getBlockX(), proc.getBlockY(), proc.getBlockZ());
    		}
			int newR = (54-(Y-proc.getBlockY()))*4;
			int newG = (54-(Y-proc.getBlockY()))*1;
			int newB = (54-(Y-proc.getBlockY()))*0;
			if(newR<0)newR=0;
			if(newG<0)newG=0;
			if(newB<0)newB=0;
			if(newR>color.getRed())newR=color.getRed();
			if(newG>color.getGreen())newG=color.getGreen();
			if(newB>color.getBlue())newB=color.getBlue();
    		color = new Color(newR,newG,newB);
		}
		
		//Sand depth
		if(id == 12 && plugin.varDepthGround()) {   
			int xiG=230;
			int multiplier = vec.getBlockY();
			int mxp = 255-((128-multiplier)*2-1)+70;
			int mxR = mxp;
			if(mxR>255)mxR=255;
			xiG = mxR-70;
			color = new Color(xiG+70, xiG+50, xiG+30);
		}

		//Ice variation
		if(id == 79) {
			int variator = (int) ((Math.random()*30));
			if (variator < 16)
    		color = new Color(color.getRed()+variator,color.getGreen()+variator,color.getBlue()+variator);
    	}
		
		//Netherrack depth
		if(id == 87 && plugin.varDepthGround()) {
   			int mxp = (255-((color.getRed()-vec.getBlockY())*2))/2;
			color = new Color(mxp,8,8);
		}
		return color;
	}

}
