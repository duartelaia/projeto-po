package xxl;

import xxl.content.*;
import xxl.content.binFunctions.*;
import xxl.content.intFunctions.*;
import xxl.visitors.Visitor;

public abstract class Search implements Visitor {
    
    private boolean _ok = false;

    public boolean isOk(){
        return _ok;
    }

    public void setOk(boolean ok){
        _ok = ok;
    }

    public void visit(Content content){
        setOk(false);
    }
    public void visit(BinFunction binFunction){
        setOk(false);
    }
    public void visit(Add add){
        setOk(false);        
    }
    public void visit(Div div){
        setOk(false);        
    }
    public void visit(Mul mul){
        setOk(false);        
    }
    public void visit(Sub sub){
        setOk(false);        
    }
    public void visit(IntFunction intFunction){
        setOk(false);        
    }
    public void visit(Average average){
        setOk(false);        
    }
    public void visit(Coalesce coalesce){
        setOk(false);        
    }
    public void visit(Concat concat){
        setOk(false);        
    }
    public void visit(Product product){
        setOk(false);        
    }
    public void visit(LitInt litInt){
        setOk(false);        
    }
    public void visit(LitString litString){
        setOk(false);        
    }
    public void visit(Ref ref){
        setOk(false);        
    }
    public void visit(Pow pow){
        setOk(false);
    }

}
