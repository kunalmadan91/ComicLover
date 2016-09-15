package com.kunalmadan.android.comiclover;

import java.util.ArrayList;

/**
 * Created by KUNAL on 10-Sep-16.
 */
public class DataEvent {

    public ArrayList<Comic> getmList() {
        return mList;
    }

    public void setmList(ArrayList<Comic> mList) {
        this.mList = mList;
    }

    private ArrayList<Comic> mList;

    public DataEvent(ArrayList<Comic> list) {
        mList = list;

    }
}
