package uz.example.less67_task1_posterlistwithprogrbar_java.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uz.example.less67_task1_posterlistwithprogrbar_java.MainActivity;
import uz.example.less67_task1_posterlistwithprogrbar_java.R;

public class EditActivity extends AppCompatActivity {
    EditText et_idUser;
    EditText et_title;
    EditText et_post;
    Button btn_edit;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initViews();
    }

    void initViews(){
        Bundle extras = getIntent().getExtras();
        et_idUser = findViewById(R.id.et_userIdEdit);
        et_title = findViewById(R.id.et_titleEdit);
        et_post = findViewById(R.id.et_textEdit);
        btn_edit = findViewById(R.id.btn_submitEdit);
        if (extras !=null){
            Log.d("###","extras not NULL - ");
            et_idUser.setText(extras.getString("user_id"));
            et_title.setText(extras.getString("title"));
            et_post.setText(extras.getString("body"));
            id = extras.getString("id");
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String post = et_post.getText().toString().trim();
                String id_user = et_idUser.getText().toString().trim();
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("post",post);
                intent.putExtra("id_user",id_user);
                intent.putExtra("id",id);
                setResult(88,intent);
                startActivity(intent);
            }
        });

    }
}