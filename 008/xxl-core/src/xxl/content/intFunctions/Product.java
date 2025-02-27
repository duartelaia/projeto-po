package xxl.content.intFunctions;

import xxl.content.Content;
import xxl.content.LitInt;
import xxl.content.Ref;
import xxl.visitors.Visitor;

public class Product extends IntFunction{

    public Product(Content[] refs){
        super(refs);
    }

    public Product(){}

    /* verificação?  */
    @Override
    public Content computeValue(){

        int product=1;

        for(Content content: getRefsList()){
            Content computedContent = null;
            try{
                Ref ref = (Ref) content;
                if(!ref.getCell().hasEmptyContent())
                    computedContent = ref.getCell().getContent().getValue();
            }catch(ClassCastException e){
                return null;
            }

            if (computedContent == null)
                return null;

            LitInt convertedContent;
            try{
                convertedContent = (LitInt) computedContent;
            }
            catch(ClassCastException e){
                return null;
            }

            product *= convertedContent.getIntegerValue();
        }
        return new LitInt(product);
    }

    @Override
    public String toString(){
        if(getValue() != null){
            return this.computeValue().toString() + "=PRODUCT"+super.toString();
        }
        else{
            return "#VALUE=PRODUCT" +super.toString();
        }
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
    
}
