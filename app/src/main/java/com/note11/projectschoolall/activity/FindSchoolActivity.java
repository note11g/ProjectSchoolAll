package com.note11.projectschoolall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.note11.projectschoolall.R;
import com.note11.projectschoolall.SetSchoolInfo;
import com.note11.projectschoolall.common.Define;
import com.note11.projectschoolall.databinding.ActivityFindSchoolBinding;
import com.note11.projectschoolall.listview.MyAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FindSchoolActivity extends AppCompatActivity {

    private ActivityFindSchoolBinding binding;
    MyAdapter cMyAdapter;
    SetSchoolInfo cSetSchoolInfo;

    ListView mlistView;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_school);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_school);

        if(!getIntent().getStringExtra("schoolName").isEmpty()){
            String scName = getIntent().getStringExtra("schoolName");
            binding.setSchoolName(scName);
            search(scName);
        }else binding.setSchoolName("");

        binding.btnSearch.setOnClickListener(v->{
            search(binding.getSchoolName());
        });

        cMyAdapter = new MyAdapter();

        mlistView = (ListView)findViewById(R.id.lv_schools);
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Define.ins().SchoolNameresult = Define.ins().str_SchoolName.split("/");
                Define.ins().SchoolCoderesult = Define.ins().str_SchoolCode.split("/");
                selectSucceed(Define.ins().SchoolCoderesult[position], Define.ins().SchoolNameresult[position]);
            }
        });
        mEditText = (EditText)findViewById(R.id.edt_find_school);
//        mEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String text = mEditText.getText().toString();
//                searchAdapter(text);
//            }
//        });
    }

    private void search(String schoolName){
        //이 함수에서, schoolName으로 학교를 찾아, 리사이클러뷰에 표시.
        //리사이클러뷰에 (학교이름)항목을 표시하고,
        // 클릭시 해당 학교로
        // selectSucceed(학교코드, 학교이름)실행

        Define.ins().SchoolName = schoolName;
        new FindSchoolActivity.Description().execute();
        Toast.makeText(this, "검색 : "+schoolName, Toast.LENGTH_SHORT).show();
//        selectSucceed("", schoolName);
    }

//    public void searchAdapter(String charText) {
//
//        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
//        Define.ins().mList.clear();
//
//        // 문자 입력이 없을때는 모든 데이터를 보여준다.
//        if (charText.length() == 0) {
//            Define.ins().mList.addAll(Define.ins().mArrList);
//        }
//        // 문자 입력을 할때..
//        else
//        {
//            // 리스트의 모든 데이터를 검색한다.
//            for(int i = 0;i < Define.ins().mArrList.size(); i++)
//            {
//                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
//                if (Define.ins().mArrList.get(i).toLowerCase().contains(charText))
//                {
//                    // 검색된 데이터를 리스트에 추가한다.
//                    Define.ins().mList.add(Define.ins().mArrList.get(i));
//                }
//            }
//        }
//        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
//        cMyAdapter.notifyDataSetChanged();
//    }

    private void selectSucceed(String schoolCode, String schoolName){
        Intent intent = new Intent();
        intent.putExtra("schoolCodeCallback", schoolCode);
        intent.putExtra("schoolNameCallback", schoolName);
        setResult(RESULT_OK, intent);
        finish();
    }


    private class Description extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //TODO URL의
                //      SD_SCHUL_CODE : 사용자가 입력한 자신의 학교 고유코드
                Document doc = Jsoup.connect("https://open.neis.go.kr/hub/schoolInfo?SCHUL_NM="+Define.ins().SchoolName+"&Type=&pIndex=1&pSize=100&KEY=d730e4bed53645b7a755434fa274a3a5").get();
                Elements mElementDataSize = doc.select("schoolInfo").select("row").select("SD_SCHUL_CODE");
                Elements mElementDataSize2 = doc.select("schoolInfo").select("row").select("SCHUL_NM");
                int i = 0;
                for (Element element : mElementDataSize){
                    String confirmed = element.text();
                    System.out.println("result SchoolCode : " + confirmed);
                    Define.ins().str_SchoolCode += confirmed + "/";
                    i++;
                }
                System.out.println("final result : " + Define.ins().str_SchoolCode);
                int j = 0;
                for (Element element : mElementDataSize2){
                    String confirmed = element.text();
                    System.out.println("result SchoolCode : " + confirmed);
                    Define.ins().str_SchoolName += confirmed + "/";
                    j++;
                }
                System.out.println("final result : " + Define.ins().str_SchoolName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Define.ins().bCheck = false;
            lstSet(Define.ins().str_SchoolName, Define.ins().str_SchoolCode);
//            new Description2().execute();
        }

        public void lstSet(String all_Schoolname, String all_SchoolCode){
            String[] str = all_Schoolname.split("/");
            String[] str2 = all_SchoolCode.split("/");
            for(int i = 0; i < str.length; i++){
                cMyAdapter.addItem(str[i]);
            }
            mlistView.setAdapter(cMyAdapter);
        }

    }



}