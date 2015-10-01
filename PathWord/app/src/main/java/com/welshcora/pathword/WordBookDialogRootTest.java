package com.welshcora.pathword;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class WordBookDialogRootTest extends BaseDialog {

    DbAdapter mDbAdapter;
    int count = 0;
    TextView example1;
    TextView example2;
    TextView example3;
    TextView example4;
    TextView example5;

    TextView totalCount; //전체 단어 표현하는 뷰
    TextView currentCount;//현재 스테이지 표현하는 뷰
    ArrayList<TextView> examples;
    TextView sampleWord;

    JSONObject returnJson;
    ToServer ts;


    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();


    int totalCountInt;
    boolean answerCheckMode = false;//결과확인 모드
    ArrayList<resultQuestion> resultQuestions = new ArrayList<resultQuestion>();//각 문제 스테이지의 객체가 담겨있음.

    public Dialog onCreateDialog(Bundle savedInstanceState){
        mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mLaytoutInflater = getActivity().getLayoutInflater();
        view = mLaytoutInflater.inflate(R.layout.dialog_test_root, null);
        fontChanger = new FontChangeCrawler(getActivity().getAssets());
        mBuilder.setView(view);

        return mBuilder.create();
    }

    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        ImageButton previous = (ImageButton) view.findViewById(R.id.previousQuestion);
        ImageButton next = (ImageButton) view.findViewById(R.id.nextQuestion);
        ImageButton stopTest = (ImageButton) view.findViewById(R.id.stopTest);
        final ImageView correct = (ImageView) view.findViewById(R.id.test_correct);
        final ImageView incorrect = (ImageView) view.findViewById(R.id.test_incorrect);

        example1 = (TextView) view.findViewById(R.id.example1_root);
        example2 = (TextView) view.findViewById(R.id.example2_root);
        example3 = (TextView) view.findViewById(R.id.example3_root);
        example4 = (TextView) view.findViewById(R.id.example4_root);
        example5 = (TextView) view.findViewById(R.id.example5_root);

        totalCount = (TextView)view.findViewById(R.id.totalcount);
        currentCount = (TextView)view.findViewById(R.id.currentcount);
        currentCount.setTypeface(fontChanger.typeface);
        totalCount.setTypeface(fontChanger.typeface);
        TextView text = (TextView) view.findViewById(R.id.textView6);
        text.setTypeface(fontChanger.typeface);
        sampleWord = (TextView) view.findViewById(R.id.sampleword);
        sampleWord.setTypeface(fontChanger.typeface);


        mDbAdapter = new DbAdapter(getActivity()); //디비를 사용할것이다!
        mDbAdapter.open();//디비는 쓸대만 열어두고 아닐땐 닫아야 함. 커서의 버퍼가 차기 때문



        examples = new ArrayList<TextView>();
        examples.add(example1);
        examples.add(example2);
        examples.add(example3);
        examples.add(example4);
        examples.add(example5);

        for(int i =0; i < examples.size(); i++) examples.get(i).setTypeface(fontChanger.typeface);

        ts = new ToServer();
        key.add("");
        value.add("");
        returnJson = ts.sendToServer("fetchReminded.php", key, value);

        final ArrayList<getFromServer> getFromServers = new ArrayList<getFromServer>();
        String temp1 = "";
        getFromServer rootObject = new getFromServer();
        for(int i = 0; i < returnJson.length(); i++){
            try {
                temp1 = returnJson.getString(String.valueOf(i));
                if(temp1.equals("")) continue;
                Log.v("템프값 받아옴", temp1);
                if('0' <= temp1.charAt(0) && temp1.charAt(0) <= '9'){ //첫번째 문자가 숫자면
                    if(i != 0){
                        getFromServers.add(rootObject);
                        Log.v("객체 추가", temp1);
                    }
                    rootObject = new getFromServer();
                    Log.v("객체 생성", temp1);
                    rootObject.date = temp1.substring(0, temp1.length()-4);
                    Log.v("날짜", temp1.substring(0, temp1.length()-4));
                } else {
                    rootObject.wordRemind.add(temp1);
                    Log.v("연관단어", temp1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        createQuestion(getFromServers);

        restoreQuestion(resultQuestions.get(0));

        class CustomDismissListener1 extends DialogDismissListener {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(getValueForBool(WordBookDialog.KEY_RESULT, true)) {
                    count = 0;
                    restoreQuestion(resultQuestions.get(0));//처음으로 돌아가고 정답확인모드를 On 시킨다.
                    answerCheckMode = true;
                    Log.v("사용자 입력", String.valueOf(resultQuestions.get(0).userChoice));
                    Log.v("정답", String.valueOf(resultQuestions.get(0).answerCount));
                    for(int i = 0; i < examples.size(); i++){
                        examples.get(i).setBackgroundResource(R.drawable.test_root_circle1);
                    }
                    if(resultQuestions.get(0).userChoice == resultQuestions.get(0).answerCount){
                        correct.setVisibility(View.VISIBLE);
                        incorrect.setVisibility(View.GONE);
                    } else {
                        incorrect.setVisibility(View.VISIBLE);
                        correct.setVisibility(View.GONE);
                        examples.get(resultQuestions.get(0).answerCount).setTypeface(Typeface.create(fontChanger.typeface, Typeface.BOLD));
                        examples.get(resultQuestions.get(0).answerCount).setTextColor(Color.RED);
                    }
                    if(resultQuestions.get(0).userChoice != -1) examples.get(resultQuestions.get(0).userChoice).setBackgroundResource(R.drawable.test_root_circle2);

                } else {
                    dismiss();
                }
            }
        }
        class CustomDismissListener2 extends DialogDismissListener {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (getValueForBool(WordBookDialog.KEY_RESULT, true)) {
                    dismiss();
                }
            }
        }

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    Toast.makeText(getActivity(), "처음 문제입니다", Toast.LENGTH_SHORT).show();
                } else {
                    count--;
                    Log.v("사용자 입력", String.valueOf(resultQuestions.get(count).userChoice));
                    Log.v("정답", String.valueOf(resultQuestions.get(count).answerCount));
                    for(int i = 0; i < examples.size(); i++){
                        examples.get(i).setBackgroundResource(R.drawable.test_root_circle1);
                    }
                    if (resultQuestions.get(count).userChoice != -1){
                        examples.get(resultQuestions.get(count).userChoice).setBackgroundResource(R.drawable.test_root_circle2);
                        examples.get(resultQuestions.get(count).userChoice).invalidate();
                    }

                    if(answerCheckMode) {
                        for(int i = 0; i < 5; i++){//정답단어 빨간색된거 일단 원상태 복귀
                            examples.get(i).setTypeface(fontChanger.typeface);
                            examples.get(i).setTextColor(Color.BLACK);
                        }
                        if (resultQuestions.get(count).userChoice == resultQuestions.get(count).answerCount) {
                            correct.setVisibility(View.VISIBLE);
                            incorrect.setVisibility(View.GONE);
                        } else {
                            incorrect.setVisibility(View.VISIBLE);
                            correct.setVisibility(View.GONE);
                            examples.get(resultQuestions.get(count).answerCount).setTypeface(Typeface.create(fontChanger.typeface, Typeface.BOLD));
                            examples.get(resultQuestions.get(count).answerCount).setTextColor(Color.RED);
                        }
                    }
                    restoreQuestion(resultQuestions.get(count));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == totalCountInt-1) {
                    if(answerCheckMode){
                        dismiss();
                    } else {
                        WordBookTestConfirmDialog fragment = new WordBookTestConfirmDialog();
                        fragment.setDismissListener(new CustomDismissListener1()); //리스너 넣기

                        int correctCount = 0;
                        int incorrectCount = 0;
                        ArrayList<String> wrongWord = new ArrayList<java.lang.String>();
                        for(int i = 0; i < resultQuestions.size(); i++){
                            if(resultQuestions.get(i).userChoice == resultQuestions.get(i).answerCount){
                                correctCount++;
                            } else {
                                incorrectCount++;
                                wrongWord.add(resultQuestions.get(i).sampleWord);
                            }
                        }
                        int rank = correctCount / (correctCount + incorrectCount);

                        Date today = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
                        mDbAdapter.reflectTest(formatter.format(today), resultQuestions.size(), correctCount, incorrectCount, "root", rank, wrongWord);


                        Bundle args = new Bundle();
                        args.putString("title", "테스트 결과를 보시겠습니까?");
                        fragment.setArguments(args);
                        fragment.show(getFragmentManager(), "dialog");
                    }

                } else {
                    count++;
                    Log.v("사용자 입력", String.valueOf(resultQuestions.get(count).userChoice));
                    Log.v("정답", String.valueOf(resultQuestions.get(count).answerCount));
                    for(int i = 0; i < examples.size(); i++){
                        examples.get(i).setBackgroundResource(R.drawable.test_root_circle1);
                    }
                    if (resultQuestions.get(count).userChoice != -1){
                        examples.get(resultQuestions.get(count).userChoice).setBackgroundResource(R.drawable.test_root_circle2);
                        examples.get(resultQuestions.get(count).userChoice).invalidate();
                    }


                    if(answerCheckMode){
                        for(int i = 0; i < 5; i++){//정답단어 빨간색된거 일단 원상태 복귀
                            examples.get(i).setTypeface(fontChanger.typeface);
                            examples.get(i).setTextColor(Color.BLACK);
                        }
                        if(resultQuestions.get(count).userChoice == resultQuestions.get(count).answerCount){
                            correct.setVisibility(View.VISIBLE);
                            incorrect.setVisibility(View.GONE);
                        } else {
                            incorrect.setVisibility(View.VISIBLE);
                            correct.setVisibility(View.GONE);
                            examples.get(resultQuestions.get(count).answerCount).setTypeface(Typeface.create(fontChanger.typeface, Typeface.BOLD));
                            examples.get(resultQuestions.get(count).answerCount).setTextColor(Color.RED);
                        }
                    }
                    restoreQuestion(resultQuestions.get(count));
                }
            }
        });
        stopTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalCountInt = count + 1;
                WordBookTestConfirmDialog fragment = new WordBookTestConfirmDialog();
                fragment.setDismissListener(new CustomDismissListener2()); //리스너 넣기
                Bundle args = new Bundle();
                args.putString("title", "테스트를 종료 하시겠습니까?");
                fragment.setArguments(args);
                fragment.show(getFragmentManager(), "dialog");
            }
        });
        class onQuestionClicked implements View.OnClickListener{
            boolean isBlue = false;
            int number;
            public onQuestionClicked(int number){
                this.number = number;
            }
            @Override
            public void onClick(View v) {
                Log.v("루트버튼눌림", String.valueOf(number));
                if(!isBlue){
                    examples.get(number).setBackgroundResource(R.drawable.test_root_circle2);
                    isBlue = true;
                } else {
                    examples.get(number).setBackgroundResource(R.drawable.test_root_circle1);
                    isBlue = false;
                }
                resultQuestions.get(count).userChoice = number;
            }
        }
        for(int i = 0; i < examples.size(); i++){
            examples.get(i).setOnClickListener(new onQuestionClicked(i));
        }
    }
    public void createQuestion(ArrayList<getFromServer> getFromServers){
        Random rand = new Random();
        int chooseRoot = rand.nextInt(getFromServers.size());//서버로부터 가져온 루트 중에서 무작위로 하나를 선택.
        totalCountInt = getFromServers.get(chooseRoot).wordRemind.size();
        for(int k = 0; k < totalCountInt; k++){
            resultQuestion resultQuestionTemp = new resultQuestion();//결과항목 표시를위한 객체를 생성


            String sampleWord = getFromServers.get(chooseRoot).wordRemind.get(k); //그 루트의 단어들을 순차적으로 선택



            resultQuestionTemp.sampleWord = sampleWord; //그 문제의 해당 단어를 넣어준다.

            int answerCount = rand.nextInt(5);
            resultQuestionTemp.answerCount = answerCount;//정답이었던 항목을 넣어준다.
            Log.v(k+1 +"번 정답", String.valueOf(answerCount));

            String resultDescription = "";
            String description[] = mDbAdapter.SearchWord2(sampleWord).get(0).split(", "); //설명 부분을 토큰으로 나눈다.
            for (int j = 0; j < description.length; j++) { // 몇번째까지가 단어 부호냐?
                if (0 <= j && j < 4) {//4번까지만 form 검사
                    char first = description[j].charAt(0);
                    char last = description[j].charAt(description[j].length() - 1);
                    if ((('A' <= first && first <= 'Z' || 'a' <= first && first <= 'z') ||
                            ('A' <= last && last <= 'Z' || 'a' <= last && last <= 'z') && description[j].length() <= 2) || first == '[') {//토큰의 첫번째 글자와 마지막 글자가 영어거나 글자수가 4이하라면
                    } else {
                        resultDescription += description[j] + ", ";
                    }
                } else {
                    resultDescription += description[j] + ", ";
                }
            }
            resultQuestionTemp.descripton1[answerCount] = resultDescription.substring(0, resultDescription.length()-2);//정답자리에 설명을 넣어준다.


            int randomCountList[] = new int[5];
            int answerid = mDbAdapter.SerchId(sampleWord);
            for(int i = 0; i<5;i++){
                randomCountList[i] = rand.nextInt(mDbAdapter.totalCount()) + 1;//1부터 디비전체 영어단어 갯수 까지
                for(int j = 0; j<i; j++){
                    while(randomCountList[i] == randomCountList[j] || randomCountList[i] == answerid) randomCountList[i] = rand.nextInt(mDbAdapter.totalCount()) + 1;
                }
            }

            for(int i = 0; i < 5; i++){
                if(i == answerCount) continue;//정답 보기는 냅둠.
                resultDescription = "";
                ArrayList<String> randomList = mDbAdapter.SearchWord3(randomCountList, "roottest");
                String temp[] = randomList.get(i).split(", "); //랜덤으로 선택된 5개의 설명 부분을 토큰으로 나눈다.
                for (int j = 0; j < temp.length; j++) { // 몇번째까지가 단어 부호냐?
                    if (0 <= j && j < 4) {//4번까지만 form 검사
                        char first = temp[j].charAt(0);
                        char last = temp[j].charAt(temp[j].length() - 1);
                        if ((('A' <= first && first <= 'Z' || 'a' <= first && first <= 'z') ||
                                ('A' <= last && last <= 'Z' || 'a' <= last && last <= 'z') && temp[j].length() <= 2) || first == '[') {//토큰의 첫번째 글자와 마지막 글자가 영어거나 글자수가 4이하라면
                        } else {
                            resultDescription += temp[j] + ", ";
                        }
                    } else {
                        resultDescription += temp[j] + ", ";
                    }
                }
                resultQuestionTemp.descripton1[i] = resultDescription.substring(0, resultDescription.length() - 2);//나머지 자리에 설명을 넣어준다.
            }
            resultQuestions.add(resultQuestionTemp); //총 문제들이 담긴 array list에 현재 문제의정보를 담아준다.
        }

    }
    public void restoreQuestion(resultQuestion previousData){
        currentCount.setText(String.valueOf(count+1));
        sampleWord.setText(previousData.sampleWord);

        int answerCount = previousData.answerCount;

        examples.get(answerCount).setText(previousData.descripton1[answerCount]);

        for(int i = 0; i < 5; i++) {
            if (i == answerCount) continue;//정답 보기는 냅둠.
            examples.get(i).setText(previousData.descripton1[i]);
        }
    }

}

/*class resultQuestion{
    String descripton[] = new String[4];//버튼 1,2,3,4의 설명
    String sampleWord;//문제로 냈던 단어
    int answerCount;//1,2,3,4 중 정답이었던 항목
    int userChoice = -1;
}*/

