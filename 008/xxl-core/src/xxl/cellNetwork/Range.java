package xxl.cellNetwork;

import java.util.ArrayList;
import java.util.List;

public class Range {
    
    private List<Position> _range = new ArrayList<>();

    public Iterator forwardIterator() {return new ForwardIterator();}

    /*
     * Default constructor. Used to create a range that isnt 
     * necessarily in order/continuous.
     */
    public Range(){}

    /*
     * Creates a new range of positions based on its string format:
     * "position:position"
     */
    public Range(Position firstPosition, Position lastPosition){
        Position[] orderedPositions = orderPositions(firstPosition, lastPosition);
        firstPosition = orderedPositions[0];
        lastPosition = orderedPositions[1];

        for(int l = firstPosition.getLine(); l <= lastPosition.getLine(); l++){
            for(int c = firstPosition.getColumn(); c <= lastPosition.getColumn(); c++){
                _range.add(new Position(l, c));
            }
        }
    }

    private class ForwardIterator implements Iterator{
        private int _ix = 0;
        public boolean hasNext() {return _ix < _range.size();}
        public Position next() {return _range.get(_ix++);}
    }

    private Position[] orderPositions(Position pos1, Position pos2){
        Position[] orderedPositions = new Position[2];
        Position firstPosition = pos1;
        Position lastPosition = pos2;
        
        if(pos1.getLine() == pos2.getLine() && pos1.getColumn()>pos2.getColumn()){
            firstPosition = pos2;
            lastPosition = pos1;
        }
        if(pos1.getColumn() == pos2.getColumn() && pos1.getLine()>pos2.getLine()){
            firstPosition = pos2;
            lastPosition = pos1;
        }
        orderedPositions[0] = firstPosition;
        orderedPositions[1] = lastPosition;

        return orderedPositions;
    }

    public void addPosition(Position position){
        _range.add(position);
    }

    public Position getFirstPosition(){
        return _range.get(0);
    }

    public Position getLastPosition(){
        return _range.get(_range.size()-1);
    }

    public boolean isLineOrColumn(){
        return getFirstPosition().inSameLineOrColumn(getLastPosition());
    }

    public int getLength(){
        return _range.size();
    }

    @Override 
    public String toString(){
        return getFirstPosition()+":"+getLastPosition();
    }

}
