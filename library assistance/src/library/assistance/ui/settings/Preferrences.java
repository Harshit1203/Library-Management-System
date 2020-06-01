/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistance.ui.settings;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Preferrences {
    private static final String CONFIG_FILE = "config.txt";
    private int minwithoutFine;
    private int fineAmount;
    private String username;
    private String password;

    public void setMinwithoutFine(int minwithoutFine) {
        this.minwithoutFine = minwithoutFine;
    }

    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Preferrences()
    {
        minwithoutFine = 14;
        fineAmount = 10;
        username = "admin";
        password = "admin";
        
    }

    public int getMinwithoutFine() {
        return minwithoutFine;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public static void initConfig()
    {
        Writer writer = null;
        try {
            Preferrences pref = new Preferrences();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(pref,writer);
        } catch (IOException ex) {
            Logger.getLogger(Preferrences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Preferrences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static Preferrences loadPreferences()
    {
        Gson gson = new Gson();
        Preferrences preferences = new Preferrences();
        
        try {
            preferences = gson.fromJson(new FileReader(CONFIG_FILE),Preferrences.class);
        } catch (FileNotFoundException ex) {
            initConfig();
            Logger.getLogger(Preferrences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preferences;
    }
    
     public static void writeConfig(Preferrences pref)
    {
        Writer writer = null;
        try {
            
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(pref,writer);
        } catch (IOException ex) {
            Logger.getLogger(Preferrences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Preferrences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
