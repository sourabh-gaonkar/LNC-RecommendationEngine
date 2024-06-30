package com.lnc.model;

public class EmployeeProfile {
    private String employeeId;
    private DietPreference dietPreference;
    private SpiceLevel spiceLevel;
    private RegionalPreference regionalPreference;
    private boolean sweetTooth;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public DietPreference getDietPreference() {
        return dietPreference;
    }

    public void setDietPreference(int dietPreferenceCode) {
        this.dietPreference = DietPreference.fromInt(dietPreferenceCode);
    }

    public SpiceLevel getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(int spiceLevelCode) {
        this.spiceLevel = SpiceLevel.fromInt(spiceLevelCode);
    }

    public RegionalPreference getRegionalPreference() {
        return regionalPreference;
    }

    public void setRegionalPreference(int regionalPreferenceCode) {
        this.regionalPreference = RegionalPreference.fromInt(regionalPreferenceCode);
    }

    public boolean isSweetTooth() {
        return sweetTooth;
    }

    public void setSweetTooth(boolean sweetTooth) {
        this.sweetTooth = sweetTooth;
    }

    public enum DietPreference {
        VEG,
        EGG,
        NON_VEG;

        public static DietPreference fromInt(int dietPreferenceCode) {
            switch (dietPreferenceCode) {
                case 1:
                    return VEG;
                case 2:
                    return EGG;
                case 3:
                    return NON_VEG;
                default:
                    throw new IllegalArgumentException("Invalid diet preference code: " + dietPreferenceCode);
            }
        }
    }

    public enum SpiceLevel {
        HIGH,
        MEDIUM,
        LOW;

        public static SpiceLevel fromInt(int spiceLevelCode) {
            switch (spiceLevelCode) {
                case 1:
                    return HIGH;
                case 2:
                    return MEDIUM;
                case 3:
                    return LOW;
                default:
                    throw new IllegalArgumentException("Invalid spice level code: " + spiceLevelCode);
            }
        }
    }

    public enum RegionalPreference {
        SI,
        NI,
        OTHER;

        public static RegionalPreference fromInt(int regionalPreferenceCode) {
            switch (regionalPreferenceCode) {
                case 1:
                    return SI;
                case 2:
                    return NI;
                case 3:
                    return OTHER;
                default:
                    throw new IllegalArgumentException("Invalid regional preference code: " + regionalPreferenceCode);
            }
        }
    }
}