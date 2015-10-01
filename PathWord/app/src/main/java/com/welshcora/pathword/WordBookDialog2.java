package com.welshcora.pathword;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class WordBookDialog2 extends BaseDialog {
    ArrayList<String> wordList;
    String currentWord;
    TextView wordView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mLaytoutInflater = getActivity().getLayoutInflater();
        view = mLaytoutInflater.inflate(R.layout.dialog_wordbook_wordselect, null);

        mBuilder.setView(view);

        return mBuilder.create();
    }

    public void onStart()
    {
        super.onStart();

        // safety check
        if (getDialog() == null)
            return;


        getDialog().getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // ... other stuff you want to do in your onStart() method
        fontChanger = new FontChangeCrawler(getActivity().getAssets());
        wordList = getArguments().getStringArrayList("key");
        currentWord = getArguments().getString("key2");


        wordView = (TextView) view.findViewById(R.id.dialogword);

        getDescription(currentWord);

        wordView.setText(currentWord);
        wordView.setTypeface(fontChanger.typeface);

        TextView remind = (TextView) view.findViewById(R.id.remind);
        remind.setTypeface(fontChanger.typeface);
        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        cancle.setTypeface(fontChanger.typeface);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ImageView previous = (ImageView) view.findViewById(R.id.previous);
        ImageView next = (ImageView) view.findViewById(R.id.next);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < wordList.size(); i++) {
                    if (currentWord.equals(wordList.get(i))) {
                        try{
                            getDescription(wordList.get(i-1));
                            currentWord = wordList.get(i-1);
                        } catch (Exception e){
                            Toast.makeText(getActivity().getBaseContext(), "이전 단어가 없습니다.", Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < wordList.size(); i++) {
                    if (currentWord.equals(wordList.get(i))) {
                        try{
                            getDescription(wordList.get(i+1));
                            currentWord = wordList.get(i+1);
                        } catch (Exception e){
                            Toast.makeText(getActivity().getBaseContext(), "다음 단어가 없습니다.", Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                }
            }
        });

    }
    public void getDescription(String word){
        DbAdapter  mDbAdapter = new DbAdapter(getActivity());
        mDbAdapter.open();
        ArrayList<String> resultQuery = mDbAdapter.SearchWord2(word);

        String description[] = resultQuery.get(0).split(", "); //설명 부분을 토큰으로 나눈다.
        String pronoun = "";
        String resultDescription = "";
        int count = 1;
        for (int j = 0; j < description.length; j++) { // 몇번째까지가 단어 부호냐?
            if(0 <= j && j < 4) {//4번까지만 form 검사
                char first = description[j].charAt(0); char last = description[j].charAt(description[j].length()-1);
                if (('A' <= first && first <= 'Z' || 'a' <= first && first <= 'z') &&
                        ('A' <= last && last <= 'Z' || 'a' <= last && last <= 'z') && description[j].length() <= 4) {//토큰의 첫번째 글자와 마지막 글자가 영어거나 글자수가 4이하라면
                } else {
                    if(String.valueOf(description[j].charAt(0)).equals("[")){
                        pronoun = description[j];
                    }
                    if(j+1 < description.length - 1) {
                        resultDescription += count + ". " +description[j+1]+ "\n";
                        count++;
                    } else if(j+1 < description.length) resultDescription += count + ". " +description[j+1];
                }
            } else {
                if(String.valueOf(description[j].charAt(0)).equals("[")){
                    pronoun = description[j];
                }
                if(j+1 < description.length - 1) {
                    resultDescription += count + ". " +description[j+1]+ "\n";
                    count++;
                } else if(j+1 < description.length) resultDescription += count + ". " +description[j+1];
            }

        }
        Log.v("다이얼로그 설명", resultDescription);
        Log.v("다이얼로그 발음", pronoun);
        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        descriptionView.setText(resultDescription);
        TextView pronounVew = (TextView) view.findViewById(R.id.pronoun);
        pronounVew.setText(pronoun);
        pronounVew.setTypeface(fontChanger.typeface);
        descriptionView.setTypeface(fontChanger.typeface);
        wordView.setText(word);
        wordView.setTypeface(fontChanger.typeface);

    }
    public void onStop(){
        super.onStop();
    }
}