package xxl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

import xxl.exceptions.ImportFileException;
import xxl.exceptions.MissingFileAssociationException;
import xxl.exceptions.UnavailableFileException;
import xxl.exceptions.UnrecognizedEntryException;
import xxl.exceptions.UnrecognizedFunctionException;

/**
 * Class representing a spreadsheet application.
 */
public class Calculator {

    /** The current spreadsheet. */
    private Spreadsheet _spreadsheet = null;

    // The current user
    private User _user = new User("root");

    /**
     * Creates a new spreadsheet
     *
     * @param nLines number of lines.
     * @param nColumns number of columns.
     */
    public void newSpreadsheet(int nLines, int nColumns){
        _spreadsheet = new Spreadsheet(nLines, nColumns);
        _spreadsheet.putUser(_user);
    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        
        if((_spreadsheet==null) || (_spreadsheet.hasNoName()))
            throw new MissingFileAssociationException();

        try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_spreadsheet.getFileName())))){
            oos.writeObject(_spreadsheet);
        }

        _user.putSpreadsheet(_spreadsheet);

        _spreadsheet.setChanged(false);
    }

    /**
     * Saves the serialized application's state into the specified file. The current network is
     * associated to this file.
     *
     * @param filename the name of the file.
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        _spreadsheet.setFileName(filename);
        save();
    }

    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException{
        try(ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){
            _spreadsheet = (Spreadsheet) inputStream.readObject();
        } 
        catch(FileNotFoundException e){
            throw new UnavailableFileException(filename);
        }
        
    }

    /**
     * Read text input file and create domain entities..
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        try (BufferedReader input = new BufferedReader(new FileReader(filename))){
                
            // Number of lines
            String line = input.readLine();
            int nLines = Integer.parseInt(line.split("=")[1]);

            // Number of columns
            line = input.readLine();
            int nColumns = Integer.parseInt(line.split("=")[1]);
            newSpreadsheet(nLines, nColumns);
            
            // Read cells
            while ((line = input.readLine()) != null){
                String[] parsedLine = line.split("\\|");
                // If there is no content, there is no need to insert
                if (parsedLine.length > 1)
                    _spreadsheet.insertContents(parsedLine[0], parsedLine[1]);
            }
        }
	        
        catch (UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        }
        catch (UnrecognizedFunctionException e){}
        catch(IOException e){}
            
    }

    /**
     * Gets the current spreadsheet
     */
    public Spreadsheet getSpreadsheet(){
        return this._spreadsheet;
    }

    /*
     * Checks if the spreadsheet has changed
     */
    public boolean hasChanged(){
        return (_spreadsheet != null) && _spreadsheet.hasChanged();
    }

}
