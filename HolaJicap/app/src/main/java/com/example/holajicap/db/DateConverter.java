package com.example.holajicap.db;

import androidx.room.TypeConverter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @TypeConverter
    public static Date fromString(String value) {
        try {
            return value == null ? null : sdf.parse(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String fromDate(Date date) {
        return date == null ? null : sdf.format(date);
    }
}
