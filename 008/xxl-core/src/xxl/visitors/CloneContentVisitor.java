package xxl.visitors;

import xxl.content.*;
import xxl.content.binFunctions.*;
import xxl.content.intFunctions.*;

public class CloneContentVisitor implements Visitor{
    
    private Content _newContent;

    public void visit(Content content){}
    public void visit(BinFunction binFunction){}
    public void visit(Add add){
        Add newAdd = new Add(add.getArg1(), add.getArg2());
        _newContent = newAdd;
    }
    public void visit(Div div){
        Div newDiv = new Div(div.getArg1(), div.getArg2());
        _newContent = newDiv;
    }
    public void visit(Mul mul){
        Mul newMul = new Mul(mul.getArg1(), mul.getArg2());
        _newContent = newMul;
    }
    public void visit(Sub sub){
        Sub newSub = new Sub(sub.getArg1(), sub.getArg2());
        _newContent = newSub;
    }
    public void visit(Pow pow){
        Pow newPow = new Pow(pow.getArg1(), pow.getArg2());
        _newContent = newPow;
    }
    public void visit(IntFunction intFunction){}
    public void visit(Average average){
        Average newAverage = new Average(average.getRefList());
        _newContent = newAverage;
    }
    public void visit(Coalesce coalesce){
        Coalesce newCoalesce = new Coalesce(coalesce.getRefList());
        _newContent = newCoalesce;
    }
    public void visit(Concat concat){
        Concat newConcant = new Concat(concat.getRefList());
        _newContent = newConcant;
    }
    public void visit(Product product){
        Product newProduct = new Product(product.getRefList());
        _newContent = newProduct;
    }
    public void visit(LitInt litInt){
        LitInt newLitint = new LitInt(litInt.getIntegerValue());
        _newContent = newLitint;
    }
    public void visit(LitString litString){
        LitString newString = new LitString(litString.getStringValue());
        _newContent = newString;
    }

    public void visit(Ref ref){
        Ref newRef = new Ref(ref.getCell(), ref.getPosition());
        _newContent = newRef;
    }

    public Content getNewContent(){
        return _newContent;
    }

}
