package com.note11.projectschoolall.common;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Define {

    public String str_SchoolCode = "";
    public String[] SchoolCoderesult;
    public String str_SchoolName = "";
    public String[] SchoolNameresult;
    public String today = "";
    public String LastMonth = "";
    public String[] slunch;

    public String SchoolName = "";

    public String tv_eduscadu1;
    public String tv_eduscadu2;
    public String tv_timetable = "";
    public String tv_lunch = "";

    public Boolean bCheck = true;

    private static com.note11.projectschoolall.common.Define instance;
    public static com.note11.projectschoolall.common.Define ins() {
        if (instance == null) {
            instance = new com.note11.projectschoolall.common.Define();
        }

        return instance;

    }
}
