package com.lnc.model;

public class MenuItemProfile {
    private String itemName;
    private DietType dietType;
    private SpiceLevel spiceLevel;
    private Region region;
    private boolean sweet;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public DietType getDietType() {
        return dietType;
    }

    public void setDietType(int dietTypeCode) {
        this.dietType = DietType.fromInt(dietTypeCode);
    }

    public SpiceLevel getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(int spiceLevelCode) {
        this.spiceLevel = SpiceLevel.fromInt(spiceLevelCode);
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(int regionCode) {
        this.region = Region.fromInt(regionCode);
    }

    public boolean isSweet() {
        return sweet;
    }

    public void setSweet(boolean sweet) {
        this.sweet = sweet;
    }

    public enum DietType {
        VEG,
        EGG,
        NON_VEG;

        public static DietType fromInt(int dietTypeCode) {
            switch (dietTypeCode) {
                case 1:
                    return VEG;
                case 2:
                    return EGG;
                case 3:
                    return NON_VEG;
                default:
                    throw new IllegalArgumentException("Invalid diet type code: " + dietTypeCode);
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

    public enum Region {
        SI,
        NI,
        OTHER;

        public static Region fromInt(int regionCode) {
            switch (regionCode) {
                case 1:
                    return SI;
                case 2:
                    return NI;
                case 3:
                    return OTHER;
                default:
                    throw new IllegalArgumentException("Invalid region code: " + regionCode);
            }
        }
    }
}
