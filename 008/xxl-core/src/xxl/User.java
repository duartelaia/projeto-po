package xxl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable{
    
    private String _name;
    private Map<String, Spreadsheet> _spreadsheets = new HashMap<String, Spreadsheet>();


    public User(String name){
        _name = name;
    }

    /*
     * Adds a new spreadsheet
     */
    public void putSpreadsheet(Spreadsheet spreadsheet){
        _spreadsheets.put(spreadsheet.getFileName(), spreadsheet);
    }

    /*
     * Gets a spreadsheet
     */
    public Spreadsheet getSpreadsheet(String fileName){
        return _spreadsheets.get(fileName);
    }

    /*
     * Gets the name of the user
     */
    public String getUserName(){
        return _name;
    }

    /*
     * Sets the name of the user
     */
    public void setUserName(String name){
        _name = name;
    }

}
