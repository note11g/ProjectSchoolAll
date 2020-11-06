package com.note11.projectschoolall;

import android.os.AsyncTask;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.note11.projectschoolall.common.Define;
import com.note11.projectschoolall.listview.MyAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

//public class SetSchools {
//
//    AppCompatActivity aSetSchools;
//    MyAdapter mMyAdapter;
//    ListView mListView;
//
//    public SetSchools(AppCompatActivity appCompatActivity){
//        aSetSchools = appCompatActivity;
//        mListView = (ListView)aSetSchools.findViewById(R.id.lv_schools);
//        mMyAdapter = new MyAdapter();
//        new SetSchools.Description().execute();
//    }
//
//    private class Description extends AsyncTask<Void, Void, Void>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Document doc = Jsoup.connect("https://open.neis.go.kr/hub/schoolInfo?ATPT_OFCDC_SC_CODE=B10&Type=&pIndex=1&pSize=100&KEY=d730e4bed53645b7a755434fa274a3a5").get();
//                Elements mElementDataSize = doc.select("schoolInfo").select("row").select("SCHUL_NM");
//                int i = 0;
//                for (Element element : mElementDataSize){
//                    String confirmed = element.text();
//                    System.out.println("result Schools "+(i+1)+": " + confirmed);
//                    Define.ins().mList.add(confirmed);
//                    i++;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            mListView.setAdapter(mMyAdapter);
//        }
//    }

//}
