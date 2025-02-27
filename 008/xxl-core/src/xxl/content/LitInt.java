package xxl.content;

import xxl.visitors.Visitor;

public class LitInt extends Content{

    private int _value;

    public LitInt(int value){
        _value = value;
        setValue(this);
    }

    public int getIntegerValue(){
        return _value;
    }

    @Override
    public String toString(){
        return Integer.toString(_value);
    }

    @Override
    public boolean equalsValue(Content content){
        try{
            LitInt intContent = (LitInt) content;
            return intContent.getIntegerValue() == _value;
        }catch(ClassCastException e){};
        return false;
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
