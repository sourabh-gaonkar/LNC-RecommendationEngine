package com.lnc.model;

public class EmployeeProfile {
    private String employeeId;
    private String dietPreference;
    private String spiceLevel;
    private String regionalPreference;
    private boolean sweetTooth;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDietPreference() {
        return dietPreference;
    }

    public void setDietPreference(String dietPreference) {
        this.dietPreference = dietPreference;
    }

    public String getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public String getRegionalPreference() {
        return regionalPreference;
    }

    public void setRegionalPreference(String regionalPreference) {
        this.regionalPreference = regionalPreference;
    }

    public boolean isSweetTooth() {
        return sweetTooth;
    }

    public void setSweetTooth(boolean sweetTooth) {
        this.sweetTooth = sweetTooth;
    }
}
