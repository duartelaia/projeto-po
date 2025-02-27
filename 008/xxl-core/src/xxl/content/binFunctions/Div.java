package xxl.content.binFunctions;

import xxl.content.Content;
import xxl.content.LitInt;
import xxl.visitors.Visitor;

public class Div extends BinFunction{

    public Div(Content arg1,Content arg2){
        super(arg1,arg2);
    }

    public Div(){}

    @Override
    public Integer doOperation(LitInt arg1, LitInt arg2){
        if(arg2.getIntegerValue() == 0)
            return null;
        return arg1.getIntegerValue()/arg2.getIntegerValue();
    }
    
    @Override
    public String toString(){
        if(getValue() != null){
            return getValue() + "=DIV" + super.toString();
        }
        else{
           return "#VALUE=DIV" + super.toString();
        }
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}
