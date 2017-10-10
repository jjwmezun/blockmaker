package blockmaker;

public final class BlockType
{
	public TypeGFX getGFX()
	{
		return gfx;
	};
	
	public void setFrame( int x, int y )
	{
		gfx.setFrame( x, y );
		set = true;
	};
	
	public void switchFlipX( boolean value )
	{
		gfx.flip_x = value;
		set = true;
	};
	
	public void switchFlipY( boolean value )
	{
		gfx.flip_y = value;
		set = true;
	};
	
	public void setRotation( int value )
	{
		assert( value == 0 || value == 90 || value == 180 || value == 270 );
		gfx.rotation = value;
		set = true;
	};

	private boolean set = false;
	private TypeGFX gfx = new TypeGFX();
};