package xxl.content.intFunctions;

import xxl.content.*;
import xxl.visitors.Visitor;

public class IntFunction extends Content {

    private Content[] _refs;

    public IntFunction(Content[] refs){
        _refs = refs;
        setValue(computeValue());
    }

    public IntFunction(){}

    public Content[] getRefsList(){
        return _refs;
    }

    @Override
    public String toString(){
        return "("+_refs[0].show()+":"+_refs[_refs.length-1].show()+")";
    }

    @Override
    public void updateContent(){
        setValue(computeValue());
    }

    public Content getRef(int ix){
        return _refs[ix];
    }

    public Content[] getRefList(){
        return _refs;
    }

    public void setArg(Content[] args){
        _refs = args;
    }

    // this will be overritten
    public Content computeValue(){return null;}

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}


