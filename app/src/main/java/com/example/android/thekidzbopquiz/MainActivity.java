package com.example.android.thekidzbopquiz;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void radioAnswerCheck(RadioButton correctAns, RadioGroup questionGroup) {
        if (correctAns.isChecked()) {
            correctAns.setTextColor(getResources().getColor(R.color.colorCorrect));
            score += 1;
        } else {
            RadioButton selected = findViewById(questionGroup.getCheckedRadioButtonId());
            selected.setTextColor(getResources().getColor(R.color.colorWrong));
        }
    }

    public void checkBoxAnswerCheck() {
        CheckBox lyricsCorrect1 = findViewById(R.id.checkbox_correct1);
        CheckBox lyricsCorrect2 = findViewById(R.id.checkbox_correct2);
        CheckBox lyricsFalse1 = findViewById(R.id.checkbox_wrong1);
        CheckBox lyricsFalse2 = findViewById(R.id.checkbox_wrong2);
        CheckBox lyricsFalse3 = findViewById(R.id.checkbox_wrong3);
        TextView questionPrompt = findViewById(R.id.multiple_answers_group);
        if (lyricsCorrect1.isChecked() && lyricsCorrect2.isChecked()) {
            CheckBox wrongAns[] = {lyricsFalse1, lyricsFalse2, lyricsFalse3};
            for (CheckBox lyrics : wrongAns) {
                if (lyrics.isChecked()) {
                    questionPrompt.setTextColor(getResources().getColor(R.color.colorWrong));
                    break;
                } else {
                    lyricsCorrect1.setTextColor(getResources().getColor(R.color.colorCorrect));
                    lyricsCorrect2.setTextColor(getResources().getColor(R.color.colorCorrect));
                    score += 1;
                }
            }
        } else {
            questionPrompt.setTextColor(getResources().getColor(R.color.colorWrong));
        }

    }


    public void textAnswerCheck() {
        EditText youtuberQuestion = (EditText) findViewById(R.id.youtuber_question);
        String youtuberAnswer = youtuberQuestion.getText().toString();
        if (youtuberAnswer.equals("jacksfilms")) {
            score += 1;
            youtuberQuestion.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorCorrect), PorterDuff.Mode.SRC_ATOP);
        } else {
            youtuberQuestion.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorWrong), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void errorMessage() {
        Toast warningMessage = Toast.makeText(this, "Please answer all the questions", Toast.LENGTH_SHORT);
        warningMessage.setGravity(Gravity.CENTER, 0, 0);
        warningMessage.show();
    }

    public void submitScore(View v) {
        RadioGroup question1 = (RadioGroup) findViewById(R.id.question_1_radiogroup);
        RadioButton question1Ans = (RadioButton) findViewById(R.id.question_1_correct);
        RadioGroup question2 = (RadioGroup) findViewById(R.id.question_2_radiogroup);
        RadioButton question2Ans = (RadioButton) findViewById(R.id.question_2_correct);
        RadioGroup question3 = (RadioGroup) findViewById(R.id.question_3_radiogroup);
        RadioButton question3Ans = (RadioButton) findViewById(R.id.question_3_correct);
        RadioGroup question4 = (RadioGroup) findViewById(R.id.question_4_radiogroup);
        RadioButton question4Ans = (RadioButton) findViewById(R.id.question_4_correct);
        RadioGroup question5 = (RadioGroup) findViewById(R.id.question_5_radiogroup);
        RadioButton question5Ans = (RadioButton) findViewById(R.id.question_5_correct);
        RadioGroup question6 = (RadioGroup) findViewById(R.id.question_6_radiogroup);
        RadioButton question6Ans = (RadioButton) findViewById(R.id.question_6_correct);
        CheckBox lyricsCorrect1 = findViewById(R.id.checkbox_correct1);
        CheckBox lyricsCorrect2 = findViewById(R.id.checkbox_correct2);
        CheckBox lyricsFalse1 = findViewById(R.id.checkbox_wrong1);
        CheckBox lyricsFalse2 = findViewById(R.id.checkbox_wrong2);
        CheckBox lyricsFalse3 = findViewById(R.id.checkbox_wrong3);

        score = 0;
        CheckBox lyricsQuestion[] = {lyricsCorrect1, lyricsCorrect2, lyricsFalse1, lyricsFalse2, lyricsFalse3};
        RadioGroup radiogroups[] = {question1, question2, question3, question4, question5, question6};
        for (RadioGroup question : radiogroups) {
            if (question.getCheckedRadioButtonId() == -1) {
                errorMessage();
                return;
            }
        }

        int answered = 0;
        for (CheckBox lyrics : lyricsQuestion) {
            if (lyrics.isChecked()) {
                answered += 1;
            }
        }
        if (answered < 1) {
            errorMessage();
            return;
        }

        radioAnswerCheck(question1Ans, question1);
        radioAnswerCheck(question2Ans, question2);
        radioAnswerCheck(question3Ans, question3);
        radioAnswerCheck(question4Ans, question4);
        radioAnswerCheck(question5Ans, question5);
        radioAnswerCheck(question6Ans, question6);
        checkBoxAnswerCheck();
        textAnswerCheck();
        TextView finalMessage = findViewById(R.id.answer_text);
        LinearLayout answerReveal = findViewById(R.id.answer_reveal);
        String scoreText = "Your Final score is:\n" + score + " out of 7";
        Toast toast = Toast.makeText(this, "You got " + score + " correct. See more info below", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        EditText youtuberQuestion = (EditText) findViewById(R.id.youtuber_question);
        String youtuberAnswer = youtuberQuestion.getText().toString();
        if (youtuberAnswer.equals("jacksfilms")) {
            scoreText += "\nYou even got the bonus correct!";
        }
        if (score >= 7) {
            scoreText += "\nCongrats!";
        }
        finalMessage.setText(scoreText);
        answerReveal.setVisibility(View.VISIBLE);
        ((ScrollView) findViewById(R.id.scrollView)).requestChildFocus(answerReveal, answerReveal);
    }

    public void reset(View v) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void rightAnswersReveal(TextView textString, TextView infodump) {
        textString.setTextColor(getResources().getColor(R.color.colorCorrect));
        infodump.setVisibility(View.VISIBLE);
    }

    public void viewAnswers(View v) {
        rightAnswersReveal((RadioButton) findViewById(R.id.question_1_correct), (TextView) findViewById(R.id.question_1_infodump));
        rightAnswersReveal((RadioButton) findViewById(R.id.question_2_correct), (TextView) findViewById(R.id.question_2_infodump));
        rightAnswersReveal((RadioButton) findViewById(R.id.question_3_correct), (TextView) findViewById(R.id.question_3_infodump));
        rightAnswersReveal((RadioButton) findViewById(R.id.question_4_correct), (TextView) findViewById(R.id.question_4_infodump));
        rightAnswersReveal((RadioButton) findViewById(R.id.question_5_correct), (TextView) findViewById(R.id.question_5_infodump));
        rightAnswersReveal((RadioButton) findViewById(R.id.question_6_correct), (TextView) findViewById(R.id.question_6_infodump));
        ((CheckBox) findViewById(R.id.checkbox_correct1)).setTextColor(getResources().getColor(R.color.colorCorrect));
        rightAnswersReveal(((CheckBox) findViewById(R.id.checkbox_correct2)), (TextView) findViewById(R.id.question_7_infodump));
        EditText question8 = findViewById(R.id.youtuber_question);
        question8.setText("jacksfilms");
        question8.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorCorrect), PorterDuff.Mode.SRC_ATOP);
        findViewById(R.id.question_8_infodump).setVisibility(View.VISIBLE);
        LinearLayout theTop = findViewById(R.id.the_top);
        ((ScrollView) findViewById(R.id.scrollView)).requestChildFocus(theTop, theTop);
    }

}
