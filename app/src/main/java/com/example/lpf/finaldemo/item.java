package com.example.lpf.finaldemo;

/**
 * Created by lpf on 2018/1/7.
 **/

public class item {
    private String dorm_name;
    private String date;
    private String details;
    item(String dorm_name, String date,String details)
    {
        this.dorm_name = dorm_name;
        this.date = date;
        this.details =details;
    }

    public String getDorm_name()
    {
        return dorm_name;
    }
    public String getDate()
    {
        return date;
    }
    public String getDetails()
    {
        return details;
    }

    public void setDorm_name(String dorm_name)
    {
        this.dorm_name = dorm_name;
    }

    public void setDate(String date)
    {
        this.date = date ;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }
}
