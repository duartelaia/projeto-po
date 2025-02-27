package xxl.visitors;

import xxl.content.*;
import xxl.content.intFunctions.*;
import xxl.content.binFunctions.*;

public interface Visitor {
    void visit(Content content);
    void visit(BinFunction binFunction);
    void visit(Add add);
    void visit(Div div);
    void visit(Mul mul);
    void visit(Sub sub);
    void visit(IntFunction intFunction);
    void visit(Average average);
    void visit(Coalesce coalesce);
    void visit(Concat concat);
    void visit(Product product);
    void visit(LitInt litInt);
    void visit(LitString litString);
    void visit(Ref ref);
    void visit(Pow pow);
}
