package blockmaker;

import blockmaker.GFXFrame;
import java.util.ArrayList;

public final class TypeGFX
{
	public TypeGFX() {};

	public enum Type
	{
		NULL,
		SPRITE,
		ANIMATED,
		FADING
	};

	public int getX()
	{
		if ( frames.size() < 1 || type == Type.NULL )
		{
			return -1;
		}
		else
		{
			return frames.get( 0 ).x;
		}
	};

	public int getY()
	{
		if ( frames.size() < 1 || type == Type.NULL )
		{
			return -1;
		}
		else
		{
			return frames.get( 0 ).y;
		}
	};
	
	public void setFrame( int x, int y )
	{
		if ( frames.size() < 1 )
		{
			frames.add( new GFXFrame( x, y ) );
		}
		else
		{
			frames.get( 0 ).x = x;
			frames.get( 0 ).y = y;
		}
		
		if ( type == Type.NULL )
		{
			type = Type.SPRITE;
		}
	}
	
	public String getTypeString()
	{
		return type.name();
	};
	
	public boolean isSet()
	{
		return type != Type.NULL;
	};

	public Type type = Type.NULL;
	public ArrayList<GFXFrame> frames = new ArrayList<GFXFrame>();
	public boolean flip_x = false;
	public boolean flip_y = false;
	public int rotation = 0;
	public boolean priority = false;
	public int animation_speed = 8;
	public int alpha = 255;
	public int selected_frame = 0;
};