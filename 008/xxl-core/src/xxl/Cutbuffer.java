package xxl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xxl.cellNetwork.Position;
import xxl.cellNetwork.Range;
import xxl.content.Content;

public class Cutbuffer implements Serializable {
    
    private List<Content> _contents = new ArrayList<>();
    private Range _range;

    /**
     * Parses a general content in input type to its
     * independent class type
     *
     * @param contents list of contents to insert
     * @param spreadsheetRange range of positions that the contents were on the spreadsheet
     */
    public void setContents(List<Content> contents, Range spreadsheetRange){
        _contents = contents;
        
        Position firstPosition = new Position(0, 0);

        int newLine = spreadsheetRange.getLastPosition().getLine() - spreadsheetRange.getFirstPosition().getLine();
        int newColumn = spreadsheetRange.getLastPosition().getColumn() - spreadsheetRange.getFirstPosition().getColumn();

        Position lastPosition = new Position(newLine, newColumn);

        _range = new Range(firstPosition, lastPosition);
    }

    /*
     * Gets the contents
     */
    public List<Content> getContents(){
        return _contents;
    }
    
    /*
     * Gets the range
     */
    public Range getRange(){
        return _range;
    }

}
