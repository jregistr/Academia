/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212lab02;

/**
 *
 * @author Jeffrey R
 */
public class Cone 
{
    public int radius;
	public int height;
	public double volume;
	
	public Cone ()
	{
		radius = 0;
		height = 0;
	}
	
	public Cone (int r, int h)
	{
		radius = r;
		height = h;
		calcVolume(r, h);
	}
	
	public double getVolume () 
	{
		// This method returns the current volume
		return volume;
	}
	
	public void calcVolume ()
	{
		float oneThird = (1.0f/3);
		float pie = 3.14f;
		float rSquared = (float)(Math.pow(radius, 2));
		float vl = oneThird * pie * rSquared * height;
		volume = vl;
	}
	
	public void calcVolume (int r, int h)
	{
		float oneThird = (1.0f/3);
		float pie = 3.14f;
		float rSquared = (float)(Math.pow(r, 2));
		float vl = oneThird * pie * rSquared * h;
		volume = vl;
	}
	
	public void scale (int scaleFactor)
	{
		radius *= scaleFactor;
		height *= scaleFactor;
		calcVolume ();
	}
	
	public void shrink (double shrinkFactor)
	{
		radius /= shrinkFactor;
		height /= shrinkFactor;
		calcVolume ();
	}
}
