package xxl.content.binFunctions;

import xxl.content.Content;
import xxl.content.LitInt;
import xxl.visitors.Visitor;

public class Mul extends BinFunction {

    public Mul(Content arg1,Content arg2){
        super(arg1,arg2);
    }

    public Mul(){}

    @Override
    public Integer doOperation(LitInt arg1, LitInt arg2){
        return arg1.getIntegerValue()*arg2.getIntegerValue();
    }

    @Override
    public String toString(){
        if(getValue() != null){
            return getValue() + "=MUL" + super.toString();
        }
        else{
           return "#VALUE=MUL" + super.toString();
        }
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }

}
