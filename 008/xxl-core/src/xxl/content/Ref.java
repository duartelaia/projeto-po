package xxl.content;

import xxl.cellNetwork.Cell;
import xxl.cellNetwork.Position;
import xxl.visitors.Visitor;

public class Ref extends Content{

    private Cell _cell;

    private Position _position;

    public Ref(Cell cell,Position position){ 
        _cell = cell;
        _position = position;

        if(!_cell.hasEmptyContent()){
            setValue(_cell.getContent().getValue());
        }
        
    }

    @Override
    public String toString(){
        if(getValue() != null){
            return getValue() + "=" + _position;
        }
        else {
            return "#VALUE="+_position;
        }
    }

    @Override
    public void updateContent(){
        if (_cell.hasEmptyContent())
            setValue(null);
        else
            setValue(_cell.getContent().getValue());
    }
    
    @Override
    public String show(){
        return _position.toString();
    }

    public Cell getCell(){
        return _cell;
    }

    public Position getPosition(){
        return _position;
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
