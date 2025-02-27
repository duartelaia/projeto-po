package xxl.content.binFunctions;

import xxl.content.Content;
import xxl.content.LitInt;
import xxl.content.Ref;
import xxl.visitors.Visitor;

public class BinFunction extends Content{

    private Content _arg1;

    private Content _arg2;

    public BinFunction(){}
    
    public BinFunction(Content arg1,Content arg2){
        _arg1 = arg1;
        _arg2 = arg2;
        setValue(computeValue());
    }

    public Content computeValue(){

        Content computedArg1 = null,computedArg2 = null;
        
        try{
            Ref arg1Ref = (Ref) _arg1;
            if(!arg1Ref.getCell().hasEmptyContent())
                computedArg1 = arg1Ref.getCell().getContent().getValue();
        }
        catch(ClassCastException e){
            LitInt arg1Lit = (LitInt) _arg1;
            computedArg1 = arg1Lit.getValue();
        }

        try{
            Ref arg2Ref = (Ref) _arg2;
            if(!arg2Ref.getCell().hasEmptyContent())
                computedArg2 = arg2Ref.getCell().getContent().getValue();
        }
        catch(ClassCastException e){
            LitInt arg2Lit = (LitInt) _arg2;
            computedArg2 = arg2Lit.getValue();
        }

        if ((computedArg1 == null) || (computedArg2 == null))
            return null;

        LitInt parsedArg1, parsedArg2;
        
        try{
            parsedArg1 = (LitInt) computedArg1;
            parsedArg2 = (LitInt) computedArg2;
        }
        catch(ClassCastException e){
            return null;
        }
        
        Integer result = doOperation(parsedArg1, parsedArg2);

        if(result != null)
            return new LitInt(result);
        else
            return null;
    }

    public Integer doOperation(LitInt arg1, LitInt arg2){
        return 0; // This will be overriden
    }

    @Override
    public String toString(){
        return "("+_arg1.show()+","+_arg2.show()+")";
    }

    public Content getArg1(){
        return _arg1;
    }

    public Content getArg2(){
        return _arg2;
    }

    public void setArg1(Content arg1){
        _arg1 = arg1;
    }

    public void setArg2(Content arg2){
        _arg2 = arg2;
    }

    @Override
    public void updateContent(){
        setValue(computeValue());
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
