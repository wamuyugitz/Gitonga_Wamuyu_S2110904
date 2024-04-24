package com.app.gitongawamuyus2110904.Models;

// Class representing a university with its name and location
public class UniListModel {

    // Fields representing university name and location
    String NameUni; // Name of the university
    String UniLoctaion; // Location of the university

    // Constructor with parameters
    public UniListModel(String nameUni, String uniLocation) {
        NameUni = nameUni;
        UniLoctaion = uniLocation;
    }

    // Getter for university name
    public String getNameUni() {
        return NameUni;
    }

    // Setter for university name
    public void setNameUni(String nameUni) {
        NameUni = nameUni;
    }

    // Getter for university location
    public String getUniLoctaion() {
        return UniLoctaion;
    }

    // Setter for university location
    public void setUniLoctaion(String uniLocation) {
        UniLoctaion = uniLocation;
    }
}
