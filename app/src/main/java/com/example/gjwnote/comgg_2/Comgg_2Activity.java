package com.example.gjwnote.comgg_2;

import java.util.Random;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Comgg_2Activity extends AppCompatActivity {

    private Button startBtn;
    private Button addBtn;
    private TextView tvStatus;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);

        LayoutParams lp; // lp라는 레이아웃 파라미터를 생성한다
        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // width,height를 match로 한다.

        RelativeLayout gameView = new RelativeLayout(this); // gameView를 RelativeLayout로 선언한다
        gameView.setLayoutParams(lp); // 위에서 만든 lp를 받아온다
        setContentView(gameView); // 액티비티에 gameView 레이아웃을 설정


        LinearLayout cv = new LinearLayout(this); // cv를 LinearLayout로 선언한다
        cv.setLayoutParams(lp); // cv에 width,height를 match로 설정한다
        cv.setOrientation(LinearLayout.VERTICAL); // cv레이아웃의 방향을 수직으로 설정
        cv.setBackgroundColor(Color.rgb(255, 255, 255)); // 맨 뒷벼경의 색.
        gameView.addView(cv); // 게임뷰안에 cv레이아웃을 추가한다

        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); // width를 match로 하고 height를 wrap로 한다
        TextView tvLabel = new TextView(this); // tvLabel를 TextView로 만든다.
        tvLabel.setLayoutParams(lp); // tvLabel에 width,height를 match로 설정
        tvLabel.setBackgroundColor(Color.WHITE); // 첫번째 글씨의 뒷 배경 색
        tvLabel.setTextColor(Color.BLACK); // 첫번째 글씨의 색
        tvLabel.setHeight(200); // 숫자가 커질수록 첫번째 글씨의 공간이 넙어진다.
        tvLabel.setTextSize(20f); // 첫번째 글씨의 사이즈
        tvLabel.setGravity(Gravity.CENTER); // 글씨를 중앙으로 오게 한다
        tvLabel.setText("핵폭탄을 누르는 사람이 벌칙 받는 게임입니다.");
        cv.addView(tvLabel); // 글씨를 추가한다

        tvLabel = new TextView(this); // tvLabel를 새로운 TextView로 만든다
        tvLabel.setLayoutParams(lp); // tvLabel에 width,height를 match로 설정
        tvLabel.setBackgroundColor(Color.WHITE); // 두번째 글씨의 뒷 배경 색
        tvLabel.setTextColor(Color.RED); // 두번째 글씨의 색
        tvLabel.setHeight(100); // 숫자가 커질수록 두번째 글씨의 공간이 넓어진다
        tvLabel.setTextSize(15f); // 두번째 글씨의 사이즈
        tvLabel.setGravity(Gravity.CENTER); // 글씨를 중앙으로 오게 한다.
        tvLabel.setText("핵폭탄 버튼을 누르면 핵폭탄 이미지가 나옵니다.");
        cv.addView(tvLabel); // 글씨를 추가한다

        startBtn = new Button(this); // startBtn을 버튼으로 만든다
        startBtn.setLayoutParams(lp); // startBtn 버튼의 width,height를 match로 한다
        startBtn.setTextSize(30f); // 버튼안의 글씨 크기를 정한다
        startBtn.setText("START GAME"); // 버튼안에다 글씨를 넣는다
        startBtn.setOnClickListener(BtnClick);
        cv.addView(startBtn); // 버튼을 추가한다

        gv = new RelativeLayout(this); // gv를 RelativeLayout 으로 만든다
        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT); // lp의 width,height를 match로 선언한다
        gv.setLayoutParams(lp); // gv의 width,height를 match로 선언한다
        cv.addView(gv);

        int btnId = 0;
        int nb = 5; // 버튼의 가로,세로의 갯수
        for(int ny=0; ny<nb; ny++){
            for(int nx=0; nx<nb; nx++){
                gv.addView( CreateButton(ny, nx, String.valueOf(btnId++)) );
            }
        }
    }
//    private final int gr = 3;
    private RelativeLayout gv;
    private Button CreateButton(int ny, int nx, String label){
        int sw = getWindow().getWindowManager().getDefaultDisplay().getWidth(); // 스크린의 가로 사이즈르 얻어온다
        int nb = 5; // 버튼 가로,세로의 공간의 크기를 설정
        int BtnWidth = sw / nb;

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(BtnWidth-4, BtnWidth-4); // 버튼의 width의 크기를 -4만큼 한다
        lp.topMargin = 2+ny * BtnWidth; // 버튼의 탑마진을 조정한다
        lp.leftMargin = 2+nx * BtnWidth; // 버튼의 레프트마진을 조정한다
        Button btn = new Button(this);
        btn.setLayoutParams(lp); // btn에 width,height를 match로 설정
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundColor(Color.WHITE);
        btn.setTextSize(35);
        btn.setText(label);
        btn.setOnClickListener(BtnClick);
        btn.setEnabled(false); // 버튼을 안눌리는 상태로 만들겠다. true면 눌리는 상태.
        return btn;
    }
    private int touchCount = 0;
    private int bombCount = 0;
    private View.OnClickListener BtnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v == startBtn){ // 스타트 버튼을 눌렀을때
                shuffleArray(); // 지뢰를 섞는다
                ResetButtons(); // 버튼을 초기화 한다
                touchCount = 0; // 버튼을 클릭한 횟수를 초기화한다
                bombCount = 0; // 터진 핵폭탄의 개수를 초기화한다
                startBtn.setVisibility(View.INVISIBLE); // 스타트 버튼을 안보이게 한다. 옵션이 INVISIBLE이면 자리는 그대로 차지하고 GONE면 차지하지 않는다
                for(Button b : btn){
                    b.setClickable(true);
                }
            }
            else { // 스타트 버튼이 아닌 다른 버튼이 눌렸을 때
                int btnId = Integer.valueOf( ((Button)v).getText().toString() ); // 눌린 버튼이 몇 번인지 알아온다
                int btnColor; // 버튼 색을 저장 할 변수를 만든다
                if(bombData[btnId]==1){

                    ((Button)v).setBackgroundResource(R.drawable.hack_1); // 핵폭탄 버튼을 누르면 버튼이 핵폭탄으로 바뀜
                    if( ++bombCount == 1) startBtn.setVisibility(View.VISIBLE); //bombCount 변수가 1일때 폭탄이 터지는데 그때 startBtn(Button)객체의
                    //VISIBLE속성을 사용해 스타트버튼이 나오도록 함.
                    Dialog dialog = new Dialog(Comgg_2Activity.this);//다이얼로그 객체를 만듬
                    ImageView img = new ImageView(Comgg_2Activity.this); // 이미지뷰 객체를 만듬
                    img.setImageDrawable(getResources().getDrawable(R.drawable.hack_1)); // img객체에 담길 그림파일을 설정함.
                    img.setAdjustViewBounds(true);
                    TextView title =  new TextView(Comgg_2Activity.this);
                    title.setText(touchCount+1 + "번만에 핵폭탄!!!!");
                    title.setGravity(Gravity.CENTER);
                    title.setTextSize(20);
                    title.setBackgroundColor(Color.GRAY);
                    title.setTextColor(Color.WHITE);
                    AlertDialog d = new AlertDialog.Builder(Comgg_2Activity.this).setCustomTitle(title).setView(img).show();
                    for(Button b : btn){
                        b.setClickable(false);
                    }// 버튼을 안눌리는 상태로 만들겠다. true면 눌리는 상태.

//                    dialog.setTitle(touchCount+ "번 만에 찾았습니다람쥐~~~"); // 핵폭탄의 뜬 화면의 제목
//                    dialog.setTextSize(30);
//                    dialog.setContentView(img); // 다이얼로그에 설정해놓은 img객체를 넣음.
//                    dialog.show(); // 다이얼로그 출력
                }
                else {
                    touchCount++;
                    btnColor = Color.WHITE; // 버튼이 눌렸을때의 바뀌는 색.
                    ((Button)v).setBackgroundColor(btnColor);
                    ((Button)v).setClickable(false); // 버튼을 안눌리는 상태로 만들겠다. true면 눌리는 상태.
                }
            }
        }
    };

    Button[] btn = new Button[25];
    private void ResetButtons(){
        for(int i=gv.getChildCount()-1; i>-1; --i){
            btn[i] = (Button)gv.getChildAt(i);
            btn[i].setBackgroundColor(Color.RED);
            btn[i].setEnabled(true);
        }
    }
    private int[] bombData = {1,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0,0}; // 폭탄의 갯수 - 숫자1을 늘리면 폭탄의 갯수도 증가
    private void shuffleArray(){
        //섞어버리기~
        Random rnd = new Random();
        for(int i=gv.getChildCount()-1; i>0; i--){
            int index = rnd.nextInt(i+1);
            int first = bombData[index];
            int tmp = bombData[i];
            bombData[index] = tmp;
            bombData[i] = first;
        }
    }
}