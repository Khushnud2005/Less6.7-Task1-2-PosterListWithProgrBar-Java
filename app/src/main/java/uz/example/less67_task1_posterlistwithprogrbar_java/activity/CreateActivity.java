package uz.example.less67_task1_posterlistwithprogrbar_java.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uz.example.less67_task1_posterlistwithprogrbar_java.MainActivity;
import uz.example.less67_task1_posterlistwithprogrbar_java.R;

public class CreateActivity extends AppCompatActivity {
    EditText et_idUser;
    EditText et_title;
    EditText et_post;
    Button btn_create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initViews();
    }
    void initViews(){
        et_idUser = findViewById(R.id.et_userIdCreate);
        et_title = findViewById(R.id.et_titleCreate);
        et_post = findViewById(R.id.et_textCreate);
        btn_create = findViewById(R.id.btn_submitCreate);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String post = et_post.getText().toString().trim();
                String id_user = et_idUser.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("title",title);
                intent.putExtra("post",post);
                intent.putExtra("id_user",id_user);
                setResult(78,intent);
                CreateActivity.super.onBackPressed();
            }
        });
    }


}