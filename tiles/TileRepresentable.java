package tiles;

/*
 *  Author: Alex Thomas
 *  Creation Date: 11/2/2020
 *  Purpose: an interface defining functionality necessary to represent an object on a tile
 */

public interface TileRepresentable {
    public char represent();
    public void move(Tile destination);
}
