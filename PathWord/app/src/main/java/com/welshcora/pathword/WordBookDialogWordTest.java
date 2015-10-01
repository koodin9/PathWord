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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class WordBookDialogWordTest extends BaseDialog {

    DbAdapter mDbAdapter;
    int count = 0;
    RadioButton example1;
    RadioButton example2;
    RadioButton example3;
    RadioButton example4;
    RadioGroup radioGroup;
    TextView totalCount; //전체 단어 표현하는 뷰
    TextView currentCount;//현재 스테이지 표현하는 뷰
    ArrayList<RadioButton> examples;
    TextView sampleWord;
    int totalCountInt;
    boolean answerCheckMode = false;//결과확인 모드
    ArrayList<resultQuestion> resultQuestions = new ArrayList<resultQuestion>();//각 문제 스테이지의 객체가 담겨있음.

    public Dialog onCreateDialog(Bundle savedInstanceState){
        mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mLaytoutInflater = getActivity().getLayoutInflater();
        view = mLaytoutInflater.inflate(R.layout.dialog_test_word, null);
        fontChanger = new FontChangeCrawler(getActivity().getAssets());
        mBuilder.setView(view);

        return mBuilder.create();
    }
    public void onStart() {
        super.onStart();


        ImageButton previous = (ImageButton) view.findViewById(R.id.previousQuestion);
        ImageButton next = (ImageButton) view.findViewById(R.id.nextQuestion);
        ImageButton stopTest = (ImageButton) view.findViewById(R.id.stopTest);
        final ImageView correct = (ImageView) view.findViewById(R.id.test_correct);
        final ImageView incorrect = (ImageView) view.findViewById(R.id.test_incorrect);
        example1 = (RadioButton) view.findViewById(R.id.example1);
        example2 = (RadioButton) view.findViewById(R.id.example2);
        example3 = (RadioButton) view.findViewById(R.id.example3);
        example4 = (RadioButton) view.findViewById(R.id.example4);
        radioGroup = (RadioGroup) view.findViewById(R.id.radiolayout);
        totalCount = (TextView)view.findViewById(R.id.totalcount);

        mDbAdapter = new DbAdapter(getActivity()); //디비를 사용할것이다!
        mDbAdapter.open();//디비는 쓸대만 열어두고 아닐땐 닫아야 함. 커서의 버퍼가 차기 때문

        currentCount = (TextView)view.findViewById(R.id.currentcount);

        sampleWord = (TextView) view.findViewById(R.id.sampleword);
        TextView text = (TextView) view.findViewById(R.id.textView6);
        text.setTypeface(fontChanger.typeface);
        sampleWord = (TextView) view.findViewById(R.id.sampleword);
        sampleWord.setTypeface(fontChanger.typeface);




        examples = new ArrayList<RadioButton>();
        examples.add(example1);
        examples.add(example2);
        examples.add(example3);
        examples.add(example4);

        for(int i =0; i < examples.size(); i++) examples.get(i).setTypeface(fontChanger.typeface);



        try{ //번들정보가 있으면 단어장에서 뽑아서 테스트하는거고 번들정보가 없다면 테스트 탭에서 테스트
            final ArrayList<String> wordBookData = mDbAdapter.SelectWordBook2(getArguments().getStringArrayList("key")); //시간, 단어, 설명
            totalCount.setText(String.valueOf(wordBookData.size() / 3));
            totalCountInt = wordBookData.size() / 3;
            createQuestion(wordBookData);
        }catch (Exception e){
            final ArrayList<String> wordBookData = mDbAdapter.SelectWordBook("alphabet"); //시간, 단어, 설명
            totalCount.setText(String.valueOf(wordBookData.size() / 3));
            totalCountInt = wordBookData.size() / 3;
            createQuestion(wordBookData);
        }

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
                    if(resultQuestions.get(0).userChoice == resultQuestions.get(0).answerCount){
                        correct.setVisibility(View.VISIBLE);
                        incorrect.setVisibility(View.GONE);
                    } else {
                        incorrect.setVisibility(View.VISIBLE);
                        correct.setVisibility(View.GONE);
                        examples.get(resultQuestions.get(0).answerCount).setTypeface(Typeface.create(fontChanger.typeface, Typeface.BOLD));
                        examples.get(resultQuestions.get(0).answerCount).setTextColor(Color.RED);
                    }
                    if(resultQuestions.get(0).userChoice != -1) examples.get(resultQuestions.get(0).userChoice).setChecked(true);

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
                radioGroup.setClickable(true);
                if (count == 0) {
                    Toast.makeText(getActivity(), "처음 문제입니다", Toast.LENGTH_SHORT).show();
                } else {
                    count--;
                    Log.v("사용자 입력", String.valueOf(resultQuestions.get(count).userChoice));
                    Log.v("정답", String.valueOf(resultQuestions.get(count).answerCount));
                    if (resultQuestions.get(count).userChoice != -1){
                        examples.get(resultQuestions.get(count).userChoice).setChecked(true);
                        examples.get(resultQuestions.get(count).userChoice).invalidate();
                    } else{
                        for(int i = 0; i < examples.size(); i++){
                            examples.get(i).setChecked(false);
                            examples.get(i).setSelected(false);
                            examples.get(i).invalidate();
                            radioGroup.clearCheck();
                            radioGroup.invalidate();
                        }
                    }
                    if(answerCheckMode) {
                        for(int i = 0; i < 4; i++){//정답단어 빨간색된거 일단 원상태 복귀
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
                radioGroup.setClickable(true);
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
                        mDbAdapter.reflectTest(formatter.format(today), resultQuestions.size(), correctCount, incorrectCount, "word", rank, wrongWord);

                        Bundle args = new Bundle();
                        args.putString("title", "테스트 결과를 보시겠습니까?");
                        fragment.setArguments(args);
                        fragment.show(getFragmentManager(), "dialog");
                    }

                } else {
                    count++;
                    Log.v("사용자 입력", String.valueOf(resultQuestions.get(count).userChoice));
                    Log.v("정답", String.valueOf(resultQuestions.get(count).answerCount));
                    if (resultQuestions.get(count).userChoice != -1){
                        examples.get(resultQuestions.get(count).userChoice).setChecked(true);
                        examples.get(resultQuestions.get(count).userChoice).invalidate();
                    } else{
                        for(int i = 0; i < examples.size(); i++){
                            examples.get(i).setChecked(false);
                            examples.get(i).setSelected(false);
                            examples.get(i).invalidate();
                            radioGroup.clearCheck();
                            radioGroup.invalidate();
                        }
                    }
                    if(answerCheckMode){
                        for(int i = 0; i < 4; i++){//정답단어 빨간색된거 일단 원상태 복귀
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
                totalCountInt = count+1;
                WordBookTestConfirmDialog fragment = new WordBookTestConfirmDialog();
                fragment.setDismissListener(new CustomDismissListener2()); //리스너 넣기
                Bundle args = new Bundle();
                args.putString("title", "테스트를 종료 하시겠습니까?");
                fragment.setArguments(args);
                fragment.show(getFragmentManager(), "dialog");
            }
        });
        class onQuestionClicked implements OnCheckedChangeListener{
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.example1){
                    resultQuestions.get(count).userChoice = 0;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.example2){
                    resultQuestions.get(count).userChoice = 1;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.example3){
                    resultQuestions.get(count).userChoice = 2;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.example4){
                    resultQuestions.get(count).userChoice = 3;
                }

            }

        }
        radioGroup.setOnCheckedChangeListener(new onQuestionClicked());
    }
    public void createQuestion(ArrayList<String> wordBookData){
        for(int k = 0; k < totalCountInt; k++){
            resultQuestion resultQuestionTemp = new resultQuestion();//결과항목 표시를위한 객체를 생성
            Random rand = new Random();
            int num = rand.nextInt(wordBookData.size() / 3); //단어장에 있는 단어중 하나를 무작위로 선택
            boolean isSame = true;
            while(isSame){
                if(k==0) break;
                for(int i = 0; i < k; i++){
                    if(wordBookData.get(3 * num + 1).equals(resultQuestions.get(i).sampleWord)){
                        num = rand.nextInt(wordBookData.size() / 3);
                        isSame = true;
                        break;
                    } else isSame = false;
                }
            }


            resultQuestionTemp.sampleWord = wordBookData.get(3 * num + 1); //그 문제의 해당 단어를 넣어준다.

            int answerCount = rand.nextInt(4);
            resultQuestionTemp.answerCount = answerCount;//정답이었던 항목을 넣어준다.
            Log.v(k+1 +"번 정답", String.valueOf(answerCount));

            String resultDescription = "";
            String description[] = wordBookData.get(3 * num + 2).split(", "); //설명 부분을 토큰으로 나눈다.
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
            resultQuestionTemp.descripton[answerCount] = resultDescription.substring(0, resultDescription.length()-2);//정답자리에 설명을 넣어준다.


            int randomCountList[] = new int[4];
            int answerid = mDbAdapter.SerchId(wordBookData.get(3 * num + 1));
            for(int i = 0; i<4;i++){
                randomCountList[i] = rand.nextInt(mDbAdapter.totalCount()) + 1;//1부터 디비전체 영어단어 갯수 까지
                for(int j = 0; j<i; j++){
                    while(randomCountList[i] == randomCountList[j] || randomCountList[i] == answerid) randomCountList[i] = rand.nextInt(mDbAdapter.totalCount()) + 1;
                }
            }

            for(int i = 0; i < 4; i++){
                if(i == answerCount) continue;//정답 보기는 냅둠.
                resultDescription = "";
                ArrayList<String> randomList = mDbAdapter.SearchWord3(randomCountList,"wordtest");
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
                resultQuestionTemp.descripton[i] = resultDescription.substring(0, resultDescription.length() - 2);//나머지 자리에 설명을 넣어준다.
            }
            resultQuestions.add(resultQuestionTemp); //총 문제들이 담긴 array list에 현재 문제의정보를 담아준다.
        }

    }
    public void restoreQuestion(resultQuestion previousData){

        currentCount.setText(String.valueOf(count+1));
;
        sampleWord.setText(previousData.sampleWord);

        int answerCount = previousData.answerCount;

        examples.get(answerCount).setText(previousData.descripton[answerCount]);

        for(int i = 0; i < 4; i++){
            if(i == answerCount) continue;//정답 보기는 냅둠.
            examples.get(i).setText(previousData.descripton[i]);
        }

    }
}

class resultQuestion{
    String descripton[] = new String[4];//버튼 1,2,3,4의 설명
    String descripton1[] = new String[5];//루트 1,2,3,4,5의 설명
    String sampleWord;//문제로 냈던 단어
    int answerCount;//1,2,3,4 중 정답이었던 항목
    int userChoice = -1;
}

