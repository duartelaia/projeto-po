package xxl.content;

import java.io.Serializable;

import xxl.visitors.Visitor;

public abstract class Content implements Serializable{

    private Content _value = null;

    public String show(){
        return this.toString();
    }

    public void updateContent(){}

    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    public Content getValue(){
        return _value;
    }

    public void setValue(Content value){
        _value = value;
    }

    // this will be overriden in litstring and litint
    public boolean equalsValue(Content content){return false;}
}
