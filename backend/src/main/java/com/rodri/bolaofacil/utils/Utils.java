package com.rodri.bolaofacil.utils;

public class Utils {

	public static <T> T nullCoalescence(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
