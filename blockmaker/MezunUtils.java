package blockmaker;

import java.lang.Math;

final public class MezunUtils
{
	public static final int BLOCK_SIZE = 16;
	public static final int DEFAULT_TILESET_WIDTH = 16;
	public static final int MAX_BLOCK_TYPES = 336;
	public static final int MAX_UNIVERSAL_TYPES = 64;

	public static int pixelsToBlocks( int n )
	{
		return ( int )( Math.floor( n / BLOCK_SIZE ) );
	};
	
	public static int blocksToPixels( int n )
	{
		return n * BLOCK_SIZE;
	};

	public static int nFromXAndY( int x, int y, int w )
	{
		return ( y * w ) + x;
	};
	
	public static int xFromN( int n, int w )
	{
		return n % w;
	};
	
	public static int yFromN( int n, int w )
	{
		return ( int )( Math.floor( n / w ) );
	};
};