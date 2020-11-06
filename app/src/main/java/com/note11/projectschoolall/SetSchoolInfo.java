package com.note11.projectschoolall;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.note11.projectschoolall.activity.FindSchoolActivity;
import com.note11.projectschoolall.common.Define;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetSchoolInfo {

    AppCompatActivity aSetSchoolInfo;

    public SetSchoolInfo(AppCompatActivity appCompatActivity){
        aSetSchoolInfo = appCompatActivity;
        Define.ins().today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        System.out.println("여기에요 여기" + Define.ins().today);
        Calendar calendar = Calendar.getInstance();
        Define.ins().LastMonth = new SimpleDateFormat("yyyyMM"+calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).format(new Date());
        System.out.println("여기는 아닌듯"+Define.ins().LastMonth);
    }

    private class Description2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc3 = Jsoup.connect("https://open.neis.go.kr/hub/SchoolSchedule?KEY=d730e4bed53645b7a755434fa274a3a5&Type=&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+ Define.ins().str_SchoolCode +"&AA_FROM_YMD="+Define.ins().today+"&AA_TO_YMD="+Define.ins().LastMonth).get();
                Document doc2 = Jsoup.connect("https://open.neis.go.kr/hub/hisTimetable?KEY=d730e4bed53645b7a755434fa274a3a5&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+Define.ins().str_SchoolCode+"&SEM=2&ALL_TI_YMD="+Define.ins().today+"&GRADE=1&CLASS_NM=5").get();
                Document doc = Jsoup.connect("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=d730e4bed53645b7a755434fa274a3a5&Type=&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+Define.ins().str_SchoolCode+"&MLSV_YMD="+Define.ins().today).get();

                Elements mElementDataSize4_day = doc3.select("SchoolSchedule").select("row").select("AA_YMD");
                Elements mElementDataSize4_name = doc3.select("SchoolSchedule").select("row").select("EVENT_NM");
                Elements mElementDataSize2 = doc2.select("hisTimetable").select("row").select("ITRT_CNTNT");
                Elements mElementDataSize = doc.select("mealServiceDietInfo").select("row").select("DDISH_NM");

                int i = 0;
                int l = 0;
                int m = 0;

                for(Element element : mElementDataSize4_day){
                    String confirmed = element.text();
                    System.out.println("result SchoolScheduleDay" +(l+1) + " : " + confirmed);
                    l++;
                }
                for(Element element : mElementDataSize4_name){
                    String confirmed = element.text();
                    System.out.println("result SchoolScheduleName" +(m+1) + " : " + confirmed);
                    m++;
                }
                int j = 0;
                for (Element element : mElementDataSize){
                    String confirmed = element.text();
                    //TODO String 배열로 저장
                    System.out.println("result SchoolLunch : " + confirmed);
                    Define.ins().slunch = confirmed.split("<br/>");
                }
                for(j = 0; j < Define.ins().slunch.length; j++){
                    Define.ins().slunch[j].replace(Define.ins().slunch[j].substring(Define.ins().slunch[j].lastIndexOf(".")), "");
                    System.out.println("result SchoolLunch" + j + Define.ins().slunch[j]);
//                    for(int n = 0; n < 19; n++){
//                        if(Define.ins().slunch[j].contains(n+".")) {
//                            Define.ins().slunch[j].replace((n+"."), "");
//                        }
//                    }
                }
                for (Element element : mElementDataSize2){
                    String confirmed = element.text();
                    System.out.println("result OurSubject" +(i+1) + " : " + confirmed);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }

}
