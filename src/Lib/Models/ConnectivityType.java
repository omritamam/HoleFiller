package Lib.Models;

public enum ConnectivityType {
    Four,
    Eight;

    public static ConnectivityType fromString(String x) {
        switch(x) {
            case "4":
                return Four;
            case "8":
                return Eight;
        }
        throw new IllegalArgumentException("Invalid value: " + x);
    }
}
