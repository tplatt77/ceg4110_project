/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author student
 */
public class Doctor {
    private final String fingerPrint;
    private final int id;

    public Doctor(String fp, int ID) {
        fingerPrint = fp;
        id = ID;
    }
    
    public String getFilePath(){
        return fingerPrint;
        
        
    }

    public int getId() {
        return id;
    }
    
    
}
