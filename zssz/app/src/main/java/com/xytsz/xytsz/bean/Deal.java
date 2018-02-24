package com.xytsz.xytsz.bean;


import java.util.ArrayList;

/**
 * Created by admin on 2017/2/15.
 *
 */
public class Deal {

    public  String[] roadS;
    public  ArrayList<String> dealType = new ArrayList<>();
    public  ArrayList<ArrayList<String>> facilityTypes = new ArrayList<>();
    public  ArrayList<ArrayList<ArrayList<String>>> problemTypes = new ArrayList<>();
    public  ArrayList<ArrayList<ArrayList<String>>> facilityNames = new ArrayList<>();
    public  ArrayList<ArrayList<ArrayList<ArrayList<String>>>> facilitySizes = new ArrayList<>();

    public  ArrayList<String> selectFatype = new ArrayList<>();
    public  ArrayList<String> selectPbtype = new ArrayList<>();
    public  ArrayList<String> selectFaNametype = new ArrayList<>();
    public  ArrayList<String> selectFaSizetype = new ArrayList<>();

}
