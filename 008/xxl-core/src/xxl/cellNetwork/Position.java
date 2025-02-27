package xxl.cellNetwork;

import java.io.Serializable;

public class Position implements Serializable {
    
    private int _line;
    private int _column;

    /*
     * Creates a new position based on its string format:
     * "int;int"
     */
    public Position(String position){
        String[] fixedPosition = position.split(";");

        // -1 for code flexibility: on display, the first position
        // is 1;1 but it would be easier codewise if it was 0;0
        _line = Integer.parseInt(fixedPosition[0])-1;
        _column = Integer.parseInt(fixedPosition[1])-1;
    }

    public Position(int line, int column){
        _line = line;
        _column = column;
    }

    public int getLine(){
        return _line;
    }

    public int getColumn(){
        return _column;
    }

    public boolean isInsideGrid(int maxLine, int maxColumn){
        return (this.getLine() < maxLine) && (this.getColumn() < maxColumn);
    }

    public boolean inSameLineOrColumn(Position pos){
        return (this.getLine() == pos.getLine()) || (this.getColumn() == pos.getColumn());
    }

    @Override
    public String toString(){
        return (_line+1) + ";" + (_column+1);
    }

}
