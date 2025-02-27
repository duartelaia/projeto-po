package xxl.storageUnit;

import java.io.Serializable;

import xxl.cellNetwork.Cell;
import xxl.cellNetwork.Position;
import xxl.content.Content;

public abstract class StorageUnit implements Serializable{
    
    private int _nLines;
    private int _nColumns;

    public StorageUnit(int nLines, int nColumns){
        _nLines = nLines;
        _nColumns = nColumns;
    }

    /*
     * Gets a cell
     */
    public abstract Cell getCell(Position pos);

    /*
     * Edits a cell
     */
    public abstract void editCell(Position pos, Content content);

    /*
     * Sets the number of lines 
     */
    public void setLines(int nLines){
        _nLines = nLines;
    }

    /*
     * Sets the number of columns
     */
    public void setColumns(int nColumns){
        _nColumns = nColumns;
    }

    /*
     * Gets the number of lines
     */
    public int getLines(){
        return _nLines;
    }

    /*
     * Gets the number of columns
     */
    public int getColumns(){
        return _nColumns;
    }
    
}
