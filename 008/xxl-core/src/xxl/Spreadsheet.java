package xxl;

// FIXME import classes
import xxl.exceptions.UnrecognizedEntryException;
import xxl.exceptions.UnrecognizedFunctionException;
import xxl.storageUnit.*;
import xxl.visitors.*;
import xxl.visitors.Visitor;
import xxl.cellNetwork.*;
import xxl.content.*;
import xxl.content.binFunctions.*;
import xxl.content.intFunctions.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a spreadsheet.
 */
public class Spreadsheet implements Serializable {

    @Serial
    private static final long serialVersionUID = 202308312359L;

    private StorageUnit _storageUnit;

    private Cutbuffer _cutBuffer = new Cutbuffer();

    private String _fileName = "";

    private boolean _hasChanged = true;

    private Map<String, User> _users = new HashMap<String, User>();

    public Spreadsheet(int nLines, int nColumns){
        _storageUnit = new ContiguousMemory(nLines, nColumns);
    }

    /**
     * Edits a range of cells with their new content
     *
     * @param range range to change
     * @param newContent content to insert
     * @throws UnrecognizedEntryException if the range is not inside the grid
     */
    public void editCells(Range range, Content newContent) throws UnrecognizedEntryException{
        
        Iterator forwardIterator = range.forwardIterator();

        while(forwardIterator.hasNext()){
            Position currentPosition = forwardIterator.next();
            
            Visitor removeObserverVisitor = new RemoveObserverVisitor(_storageUnit.getCell(currentPosition));
            if (_storageUnit.getCell(currentPosition).getContent() != null)
                _storageUnit.getCell(currentPosition).getContent().accept(removeObserverVisitor);
            
            Visitor addObserverVisitor = new AddObserverVisitor(_storageUnit.getCell(currentPosition));
            if (newContent != null)
                newContent.accept(addObserverVisitor);

            _storageUnit.editCell(currentPosition, newContent);
        }
    }

    /**
     * Gets content from a specific range
     *
     * @param range range to get content from
     */
    public List<Content> getContents(Range range) {
        List<Content> values = new ArrayList<>();

        Iterator forwardIterator = range.forwardIterator();

        while(forwardIterator.hasNext()){
            Position currentPosition = forwardIterator.next();
            values.add(_storageUnit.getCell(currentPosition).getContent());
        }

        return values;
    }

    /**
     * Insert specified content in specified range.
     *
     * @param rangeSpecification range to insert new content to
     * @param contentSpecification content to insert
     */
    public void insertContents(String rangeSpecification, String contentSpecification) throws UnrecognizedEntryException,UnrecognizedFunctionException{
        _hasChanged = true;
        Content content = parseContent(contentSpecification);

        Position[] positions = parseAndVerifyPositions(rangeSpecification);
        Range range = new Range(positions[0], positions[1]);

        editCells(range, content);
    }

    /**
     * Delete specified content in specified range.
     *
     * @param rangeSpecification range to delete
     * @throws UnrecognizedEntryException if the range is not inside the grid
     */
    public void deleteContents(String rangeSpecification) throws UnrecognizedEntryException{
        _hasChanged = true;

        Position[] positions = parseAndVerifyPositions(rangeSpecification);
        Range range = new Range(positions[0], positions[1]);

        editCells(range, null);
    }

    /**
     * Copies a range to the cutbuffer
     *
     * @param rangeString range to copy
     * @throws UnrecognizedEntryException if the range is not inside the grid
     */
    public void copyContents(String rangeString) throws UnrecognizedEntryException{
        _hasChanged = true;
        
        Position[] positions = parseAndVerifyPositions(rangeString);
        Range range = new Range(positions[0], positions[1]);

        List<Content> spreadsheetContents = getContents(range);
        List<Content> clonedContent = new ArrayList<>();
        CloneContentVisitor cloneContentVisitor = new CloneContentVisitor();

        for(Content content : spreadsheetContents){
            if (content != null){
                content.accept(cloneContentVisitor);
                clonedContent.add(cloneContentVisitor.getNewContent());
            }
            else
                clonedContent.add(null);
        }

        _cutBuffer.setContents(clonedContent, range);

    }

    /**
     * Cuts a range to the cutbuffer
     *
     * @param rangeString range to cut
     * @throws UnrecognizedEntryException if the range is not inside the grid
     */
    public void cutContents(String rangeString) throws UnrecognizedEntryException{
        copyContents(rangeString);
        deleteContents(rangeString);
    }

    /**
     * Pastes to a range
     *
     * @param range range to paste to
     * @throws UnrecognizedEntryException if the range is not inside the grid
     */
    public void pasteContents(String rangeString) throws UnrecognizedEntryException{
        _hasChanged = true;

        Position[] positions = parseAndVerifyPositions(rangeString);
        Range range = new Range(positions[0], positions[1]);

        List<Content> contents = _cutBuffer.getContents();
        Range cutbufferRange = _cutBuffer.getRange();

        if((range.getLength() != 1) && (range.getLength() != cutbufferRange.getLength()))
            return;

        Iterator forwardIterator = cutbufferRange.forwardIterator();
        
        for(int i = 0; i < contents.size(); i++){
            Position currentPosition = forwardIterator.next();
            Position pos = new Position(currentPosition.getLine()+range.getFirstPosition().getLine(),
                                        currentPosition.getColumn()+range.getFirstPosition().getColumn());

            Range posRange = new Range(pos, pos);
            if(pos.isInsideGrid(_storageUnit.getLines(), _storageUnit.getColumns()))
                editCells(posRange, contents.get(i));
            else
                break;
        }

    }

    /*
     * Gets the content of the cutbuffer in string type
     */
    public List<String> showCutbuffer(){
        return showValues(_cutBuffer.getContents(), _cutBuffer.getRange());
    }

    /*
     * Gets the content of a range in string type
     */
    public List<String> showValuesInRange(String rangeString) throws UnrecognizedEntryException{
        
        Position[] positions = parseAndVerifyPositions(rangeString);
        Range range = new Range(positions[0], positions[1]);
    
        return showValues(getContents(range), range);
    }
    
    /**
     * Returns a list of contents in their string format based on a range
     * @param contents contents to parse
     * @param range positions of the contents to parse
     */
    public List<String> showValues(List<Content> contents, Range range){

        Iterator forwardIterator = range.forwardIterator();

        List<String> values = new ArrayList<>();

        for (Content value : contents){
            String line = forwardIterator.next().toString() + "|";
            if (value != null)
                line += value.toString();
            values.add(line);
        }

        return values;
    }

    /**
     * Returns a list of contents in their string format based on filter
     * @param search search condition
     */
    public List<String> search(Search search){

        List<Content> contentResults = new ArrayList<>();
        Range rangeResults = new Range();

        for(int i = 0; i < _storageUnit.getLines(); i++){
            for(int j = 0; j < _storageUnit.getColumns(); j++){
                Position pos = new Position(i, j);
                Content currentContent = _storageUnit.getCell(pos).getContent();
                if (currentContent != null){
                    currentContent.accept(search);
                    if(search.isOk()){                  
                        contentResults.add(currentContent);
                        rangeResults.addPosition(pos);
                    }
                }
            }
        }

        return showValues(contentResults, rangeResults);
    }

    /**
     * Tries to parse the first and last position of a range
     *
     * @param rangeString range to parse
     * @throws UnrecognizedEntryException if the range is not inside the grid
     */
    public Position[] parseAndVerifyPositions(String rangeString) throws UnrecognizedEntryException{

        // Verifies if the positions are inside the grid
        String[] splittedRange = rangeString.split(":");
        Position[] positions = new Position[2];
        
        for(int i = 0; i < splittedRange.length; i++){
            positions[i] = new Position(splittedRange[i]);
            if(!positions[i].isInsideGrid(_storageUnit.getLines(), _storageUnit.getColumns())){
                throw new UnrecognizedEntryException(rangeString);
            }
        }

        if(splittedRange.length == 1){
            positions[1] = positions[0];
        }

        // Verifies if a range is either a column or a line
        if(!positions[0].inSameLineOrColumn(positions[1])){
            throw new UnrecognizedEntryException(rangeString);
        }

        return positions;
    } 

    /**
     * Parses a general content in input type to its
     * independent class type
     *
     * @param content content to parse
     * @throws UnrecognizedEntryException if the range is not inside the grid
     * @throws UnrecognizedFunctionException if an unknown function is trying to get parsed
     */
    public Content parseContent(String content) throws UnrecognizedEntryException, UnrecognizedFunctionException{
        if(content.charAt(0) == '='){
            // Function or reference case
            if(Character.isDigit(content.charAt(1))){
                // Reference
                // substring to remove the '='
                return parseReference(new Position(content.substring(1)));
            }
            else{
                // Function
                // substring to remove the '='
                try{
                    return parseBinFunction(content.substring(1));
                }catch(UnrecognizedFunctionException e){
                    return parseIntFunction(content.substring(1));
                }
            }
        }
        else{
            // Literal case
            return parseLiteral(content);
        }
    }

    /**
     * Parses a literal to its
     * independent class type
     *
     * @param content content to parse
     */
    public Content parseLiteral(String content){
        if(content.charAt(0) == '\'')
            return parseString(content);
        else
            return parseInt(content);
    }

    /**
     * Parses a string literal
     *
     * @param content content to parse
     */
    public LitString parseString(String content){
        return new LitString(content);
    }

    /**
     * Parses an int literal
     *
     * @param content content to parse
     */
    public LitInt parseInt(String content){
        return new LitInt(Integer.parseInt(content));
    }

    /**
     * Parses a reference
     *
     * @param content content to parse
     */
    public Ref parseReference(Position pos){
        return new Ref(_storageUnit.getCell(pos), pos);
    }

    /**
     * Parses a general binary function in input type to its
     * independent class type
     *
     * @param content content to parse
     * @throws UnrecognizedEntryException if the range is not inside the grid
     * @throws UnrecognizedFunctionException if an unknown function is trying to get parsed
     */
    private Content parseBinFunction(String content) throws UnrecognizedEntryException, UnrecognizedFunctionException{
        // splitedContent: [functionName, arguments]
        String[] splitedContent = content.split("\\(");
        Content[] arguments;

        Content newBinFunction = parseBinFunctionName(splitedContent[0]);
        if (newBinFunction != null){
            arguments = parseBinFunctionArguments(splitedContent[1]);
            BinFunction parsed = (BinFunction) newBinFunction;
            parsed.setArg1(arguments[0]);
            parsed.setArg2(arguments[1]);
            return parsed;
        }
            
        // Function not found
        throw new UnrecognizedFunctionException(splitedContent[0]);

    }

    /**
     * Returns a binary function type based on its name
     * @param name name of the function
     */
    public BinFunction parseBinFunctionName(String name){
        switch(name){
            case "ADD":
                return new Add();
            case "DIV":
                return new Div();
            case "SUB":
                return new Sub();
            case "MUL":
                return new Mul();
            case "POW":
                return new Pow();
        }
        return null;
    }

    /**
     * Parses the arguments of binary function
     *
     * @param arguments arguments to parse
     * @throws UnrecognizedEntryException if the range is not inside the grid
     */
    public Content[] parseBinFunctionArguments(String arguments) throws UnrecognizedEntryException{

        String[] splitedArguments = arguments.split(",");
        // Remove the last ')'
        splitedArguments[1] = splitedArguments[1].replace(")", "");

        Content[] parsedArguments = new Content[2];
            
        for(int i = 0; i < 2; i++){
            if (splitedArguments[i].contains(";")){
                // Its a reference

                Position[] positions = parseAndVerifyPositions(splitedArguments[i]);
                Range range = new Range(positions[0], positions[1]);
                

                parsedArguments[i] = parseReference(range.getFirstPosition());
            }else{
                // Its an int
                parsedArguments[i] = parseInt(splitedArguments[i]);
            }
        }
        
        return parsedArguments;
    }

    /**
     * Parses a general interval function in input type to its
     * independent class type
     *
     * @param content content to parse
     * @throws UnrecognizedEntryException if the range is not inside the grid
     * @throws UnrecognizedFunctionException if an unknown function is trying to get parsed
     */
    public Content parseIntFunction(String content) throws UnrecognizedEntryException, UnrecognizedFunctionException{
        // splitedContent: [functionName, arguments]
        String[] splitedContent = content.split("\\(");
        Content[] arguments;

        splitedContent[1] = splitedContent[1].replace(")", "");

        Position[] positions = parseAndVerifyPositions(splitedContent[1]);
        Range range = new Range(positions[0], positions[1]);

        Content newIntFunction = parseIntFunctionName(splitedContent[0]);
        if (newIntFunction != null){
            arguments = parseIntFunctionArguments(range);
            IntFunction parsed = (IntFunction) newIntFunction;
            parsed.setArg(arguments);
            return parsed;
        }
            
        // Function not found
        throw new UnrecognizedFunctionException(splitedContent[0]);
    }

    /**
     * Returns an interval function type based on its name
     * @param name name of the function
     */
    public IntFunction parseIntFunctionName(String name){
        switch(name){
            case "AVERAGE":
                return new Average();
            case "COALESCE":
                return new Coalesce();
            case "CONCAT":
                return new Concat();
            case "PRODUCT":
                return new Product();
        }
        return null;
    }

    public Content parseFunctionName(String name){
        Content newFunction = parseBinFunctionName(name);
        if (newFunction != null)
            return newFunction;
        
        newFunction = parseIntFunctionName(name);
        return newFunction;
    }


    /**
     * Parses the arguments of an interval function
     *
     * @param range range to parse
     * @throws UnrecognizedEntryException if the range is not inside the grid
     */
    public Content[] parseIntFunctionArguments(Range range) throws UnrecognizedEntryException{

        List<Content> refs = new ArrayList<>();

        Iterator forwardIterator = range.forwardIterator();
        while(forwardIterator.hasNext()){
            refs.add(parseReference(forwardIterator.next()));
        }

        return (Content[]) refs.toArray(new Content[0]);
    }

    /*
     * Gets the name of the spreadsheet
     */
    public String getFileName(){
        return _fileName;
    }

    /*
     * Sets the name of the spreadsheet
     */
    public void setFileName(String fileName){
        _fileName = fileName;
    }

    /*
     * Checks if the spread has not got a name
     */
    public boolean hasNoName(){
        return _fileName.equals("");
    }
    
    /*
     * Checks if the spreadsheet has changed
     */
    public boolean hasChanged(){
        return _hasChanged;
    }

    /*
     * Changes the change state of the spreadsheet
     */
    public void setChanged(boolean changed){
        _hasChanged = changed;
    }

    /*
     * Adds a new user to the spreadsheet
     */
    public void putUser(User user){
        _users.put(user.getUserName(), user);
    }

    /*
     * Gets a user from the spreadsheet
     */
    public User getUser(String userName){
        return _users.get(userName);
    }

}
