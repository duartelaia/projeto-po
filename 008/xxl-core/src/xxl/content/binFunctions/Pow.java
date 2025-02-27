package xxl.content.binFunctions;

import xxl.content.Content;
import xxl.content.LitInt;
import xxl.visitors.Visitor;

public class Pow extends BinFunction{
    
    public Pow(Content arg1,Content arg2){
        super(arg1,arg2);
    }

    public Pow(){}

    @Override
    public Integer doOperation(LitInt arg1, LitInt arg2){
        int r = 1;
        for (int i = 0; i < arg2.getIntegerValue(); i++){
            r *= arg1.getIntegerValue();
        }
        return r;
    }

    @Override
    public String toString(){
        if(getValue() != null){
            return getValue() + "=POW" + super.toString();
        }
        else{
           return "#VALUE=POW" + super.toString();
        }
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}