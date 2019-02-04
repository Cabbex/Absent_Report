package com.casperbjork.absentreport;

import android.provider.CalendarContract;

import java.util.Calendar;
import java.util.Date;

public class DateHandler {

    public DateHandler(){
    }

    public String getMonth(int shift){
        Calendar calendar = Calendar.getInstance();
        String returnString;
        int month;

        if (shift != 0)
            calendar.add(Calendar.DAY_OF_MONTH, shift);

        month = calendar.get(Calendar.MONTH)+1;

        switch (month){
            case 1:
                returnString = "Januari";
                break;
            case 2:
                returnString = "Febrari";
                break;
            case 3:
                returnString = "Mars";
                break;
            case 4:
                returnString = "April";
                break;
            case 5:
                returnString = "Maj";
                break;
            case 6:
                returnString = "Juni";
                break;
            case 7:
                returnString = "Juli";
                break;
            case 8:
                returnString = "Augusti";
                break;
            case 9:
                returnString = "September";
                break;
            case 10:
                returnString = "Oktober";
                break;
            case 11:
                returnString = "November";
                break;
            case 12:
                returnString = "December";
                break;
            default:
                returnString = "";
                break;
        }

        return returnString;
    }

    public String getDay(int shift){
        Calendar calendar = Calendar.getInstance();
        String stringForm;

        if (shift != 0)
            calendar.add(Calendar.DAY_OF_MONTH, shift);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (day < 10){
            stringForm = "0" + String.valueOf(day);
        } else {
            stringForm = String.valueOf(day);
        }

        return stringForm;
    }

    public String getMonthCompact(int shift){
        String currentMonth = this.getMonth(shift);

        return currentMonth.substring(0,3);
    }

    public int convertToInt(String dateString){
        Calendar calendar = Calendar.getInstance();

        String day =  dateString.substring(0,2);
        String month = getMonthFromString(dateString.substring(3));
        int year =  calendar.get(Calendar.YEAR);



        String date = "" + year + month + day;

        return Integer.parseInt(date);
    }

    private String getMonthFromString(String monthInString){
        String monthInInt = "0";

        switch (monthInString){
            case "Jan":
                monthInInt = "01";
                break;
            case "Feb":
                monthInInt = "02";
                break;
            case "Mar":
                monthInInt = "03";
                break;
            case "Apr":
                monthInInt = "04";
                break;
            case "Maj":
                monthInInt = "05";
                break;
            case "Jun":
                monthInInt = "06";
                break;
            case "Jul":
                monthInInt = "07";
                break;
            case "Aug":
                monthInInt = "08";
                break;
            case "Sep":
                monthInInt = "09";
                break;
            case "Okt":
                monthInInt = "10";
                break;
            case "Nov":
                monthInInt = "11";
                break;
            case "Dec":
                monthInInt = "12";
                break;
        }
    return monthInInt;

    }

}
