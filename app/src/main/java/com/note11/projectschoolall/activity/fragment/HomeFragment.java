package com.note11.projectschoolall.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.note11.projectschoolall.R;
import com.note11.projectschoolall.common.Define;
import com.note11.projectschoolall.databinding.FragmentHomeBinding;
import com.note11.projectschoolall.util.UserCache;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private Context mContext;
    private FragmentHomeBinding binding;

    TextView tv_timetable;
    TextView tv_lunch;
    TextView tv_EduScadu;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        tv_timetable = binding.tvTimetable;
        tv_timetable.setText("");
        tv_lunch = binding.tvLunch;
        tv_lunch.setText("");
        tv_EduScadu = binding.tvEduScadu;
        tv_EduScadu.setText("");

        Define.ins().today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        System.out.println("여기에요 여기" + Define.ins().today);
        Calendar calendar = Calendar.getInstance();
        Define.ins().LastMonth = new SimpleDateFormat("yyyyMM"+calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).format(new Date());

        if(UserCache.getUser(mContext).getSchoolName().substring(UserCache.getUser(mContext).getSchoolName().length()-4, UserCache.getUser(mContext).getSchoolName().length()).equals("고등학교")){
            new Description_High().execute();
        } else if (UserCache.getUser(mContext).getSchoolName().substring(UserCache.getUser(mContext).getSchoolName().length()-3, UserCache.getUser(mContext).getSchoolName().length()).equals("등학교")){
            new Description_Elementry().execute();
        } else {
            new Description_Middle().execute();
        }

//        UserCache.getUser(mContext).get
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        //업데이트
    }

    private class Description_High extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc3 = Jsoup.connect("https://open.neis.go.kr/hub/SchoolSchedule?KEY=d730e4bed53645b7a755434fa274a3a5&Type=&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+ UserCache.getUser(mContext).getSchoolCode() +"&AA_FROM_YMD="+Define.ins().today+"&AA_TO_YMD="+Define.ins().LastMonth).get();
                Document doc2 = Jsoup.connect("https://open.neis.go.kr/hub/hisTimetable?KEY=d730e4bed53645b7a755434fa274a3a5&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+Define.ins().str_SchoolCode+"&SEM=2&ALL_TI_YMD="+Define.ins().today+"&GRADE="+UserCache.getUser(mContext).getGrade()+"&CLASS_NM="+UserCache.getUser(mContext).getClassNum()).get();
                Document doc = Jsoup.connect("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=d730e4bed53645b7a755434fa274a3a5&Type=&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+UserCache.getUser(mContext).getSchoolCode()+"&MLSV_YMD="+Define.ins().today).get();

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
                    Define.ins().tv_eduscadu1 += confirmed +"/";
                    l++;
                }
                for(Element element : mElementDataSize4_name){
                    String confirmed = element.text();
                    System.out.println("result SchoolScheduleName" +(m+1) + " : " + confirmed);
                    Define.ins().tv_eduscadu2 += confirmed + "/";
                    m++;
                }
                int j = 0;
                for (Element element : mElementDataSize){
                    String confirmed = element.text();
                    //TODO String 배열로 저장
                    System.out.println("result SchoolLunch : " + confirmed);
                    Define.ins().slunch = confirmed.split("<br/>");
                }
//                for(j = 0; j < Define.ins().slunch.length; j++){
//                    Define.ins().slunch[j].replace(Define.ins().slunch[j].substring(Define.ins().slunch[j].lastIndexOf(".")), "");
//                    System.out.println("result SchoolLunch" + j + Define.ins().slunch[j]);
//                    Define.ins().slunch[j] = Define.ins().slunch[j];
//                    System.out.println(Define.ins().slunch[j]);
//                }
                for (Element element : mElementDataSize2){
                    String confirmed = element.text();
                    System.out.println("result OurSubject" +(i+1) + " : " + confirmed);
                    Define.ins().tv_timetable += confirmed + "/";
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            SetPage(Define.ins().tv_timetable,Define.ins().tv_eduscadu1,Define.ins().tv_eduscadu2, Define.ins().slunch);
        }

        public void SetPage(String tv_timetable_, String tv_eduscadu1,String tv_eduscadu2, String[] lunch){
            String[] timetable = tv_timetable_.split("/");
            String[] EduScadu1 = tv_eduscadu1.split("/");
            String[] EduScadu2 = tv_eduscadu2.split("/");
            for(int i = 0; i< timetable.length; i++){
//                tv_timetable.append(timetable[i]+"\n");
                tv_timetable.setText(tv_timetable.getText() + timetable[i]+"\n");
            }
            for(int i = 0; i< EduScadu1.length; i++){
//                tv_EduScadu.append(EduScadu1[i]+"  " +EduScadu2[i]+"\n");
                tv_EduScadu.setText(tv_EduScadu.getText() + EduScadu1[i]+EduScadu2[i]+"\n");
            }
            for(int i = 0; i< lunch.length; i++){
//                tv_lunch.append(lunch[i]+"\n");
                tv_lunch.setText(tv_lunch.getText() + lunch[i]+"\n");
            }
        }

    }

    private class Description_Middle extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc3 = Jsoup.connect("https://open.neis.go.kr/hub/SchoolSchedule?KEY=d730e4bed53645b7a755434fa274a3a5&Type=&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+ UserCache.getUser(mContext).getSchoolCode() +"&AA_FROM_YMD="+Define.ins().today+"&AA_TO_YMD="+Define.ins().LastMonth).get();
                Document doc2 = Jsoup.connect("https://open.neis.go.kr/hub/elsTimetable?KEY=d730e4bed53645b7a755434fa274a3a5&Type=xml&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+UserCache.getUser(mContext).getSchoolCode()+"&SEM=2&ALL_TI_YMD="+Define.ins().today+"&GRADE="+UserCache.getUser(mContext).getGrade()+"&CLASS_NM="+UserCache.getUser(mContext).getClassNum()).get();
                Document doc = Jsoup.connect("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=d730e4bed53645b7a755434fa274a3a5&Type=&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+UserCache.getUser(mContext).getSchoolCode()+"&MLSV_YMD="+Define.ins().today).get();

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
                    Define.ins().tv_eduscadu1 += confirmed +"/";
                    l++;
                }
                for(Element element : mElementDataSize4_name){
                    String confirmed = element.text();
                    System.out.println("result SchoolScheduleName" +(m+1) + " : " + confirmed);
                    Define.ins().tv_eduscadu2 += confirmed + "/";
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
                    Define.ins().slunch[j] = Define.ins().slunch[j].replaceAll("^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣\\\\s]*$", "");
                    System.out.println(Define.ins().slunch[j]);
                }
                for (Element element : mElementDataSize2){
                    String confirmed = element.text();
                    System.out.println("result OurSubject" +(i+1) + " : " + confirmed);
                    Define.ins().tv_timetable += confirmed + "/";
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

    private class Description_Elementry extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc3 = Jsoup.connect("https://open.neis.go.kr/hub/SchoolSchedule?KEY=d730e4bed53645b7a755434fa274a3a5&Type=&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+ UserCache.getUser(mContext).getSchoolCode() +"&AA_FROM_YMD="+Define.ins().today+"&AA_TO_YMD="+Define.ins().LastMonth).get();
                Document doc2 = Jsoup.connect("https://open.neis.go.kr/hub/misTimetable?KEY=d730e4bed53645b7a755434fa274a3a5&Type=xml&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+UserCache.getUser(mContext).getSchoolCode()+"&SEM=2&ALL_TI_YMD="+Define.ins().today+"&GRADE="+UserCache.getUser(mContext).getGrade()+"&CLASS_NM="+UserCache.getUser(mContext).getClassNum()).get();
                Document doc = Jsoup.connect("https://open.neis.go.kr/hub/mealServiceDietInfo?KEY=d730e4bed53645b7a755434fa274a3a5&Type=&pIndex=1&pSize=100&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE="+UserCache.getUser(mContext).getSchoolCode()+"&MLSV_YMD="+Define.ins().today).get();

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
                    Define.ins().tv_eduscadu1 += confirmed +"/";
                    l++;
                }
                for(Element element : mElementDataSize4_name){
                    String confirmed = element.text();
                    System.out.println("result SchoolScheduleName" +(m+1) + " : " + confirmed);
                    Define.ins().tv_eduscadu2 += confirmed + "/";
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
                    Define.ins().slunch[j] = Define.ins().slunch[j].replaceAll("^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣\\\\s]*$", "");
                    System.out.println(Define.ins().slunch[j]);
                }
                for (Element element : mElementDataSize2){
                    String confirmed = element.text();
                    System.out.println("result OurSubject" +(i+1) + " : " + confirmed);
                    Define.ins().tv_timetable += confirmed + "/";
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