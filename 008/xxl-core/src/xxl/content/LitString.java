package xxl.content;

import xxl.visitors.Visitor;

public class LitString extends Content{
    
    private String _value;

    public LitString(String str){
        _value = str;
        setValue(this);
    }

    public String getStringValue(){
        return _value;
    }

    public String getQuotlessValue(){
        return _value.substring(1);
    }

    @Override
    public String toString(){
        return _value;
    }

    @Override
    public boolean equalsValue(Content content){
        try{
            LitString intContent = (LitString) content;
            return intContent.getStringValue().equals(_value);
        }catch(ClassCastException e){};
        return false;
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
    
}
