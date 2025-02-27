package xxl.content.binFunctions;

import xxl.content.Content;
import xxl.content.LitInt;
import xxl.visitors.Visitor;

public class Add extends BinFunction{
    
    public Add(Content arg1,Content arg2){
        super(arg1,arg2);
    }

    public Add(){}

    @Override
    public Integer doOperation(LitInt arg1, LitInt arg2){
        return arg1.getIntegerValue()+arg2.getIntegerValue();
    }

    @Override
    public String toString(){
        if(getValue() != null){
            return getValue() + "=ADD" + super.toString();
        }
        else{
           return "#VALUE=ADD" + super.toString();
        }
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
