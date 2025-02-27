package xxl.storageUnit;

import xxl.cellNetwork.*;
import xxl.content.Content;

public class ContiguousMemory extends StorageUnit{
    
    private Cell[][] _cells;

    /*
     * Creates a new contiguous memory 
     * It is a simple implementation as it consists on a
     * static memory matrix
     */
    public ContiguousMemory(int nLines, int nColumns){
        super(nLines, nColumns);
        _cells = new Cell[nLines][nColumns];
        for(int i = 0; i < nLines; i++){
            for(int j = 0; j < nColumns; j++){
                _cells[i][j] = new Cell();
            }
        }
    }

    @Override
    public Cell getCell(Position pos){
        return _cells[pos.getLine()][pos.getColumn()];
    }

    @Override
    public void editCell(Position pos, Content content){
        getCell(pos).setContent(content);
    }
    

}
