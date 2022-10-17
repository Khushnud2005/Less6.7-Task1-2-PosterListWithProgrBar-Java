package uz.example.less67_task1_posterlistwithprogrbar_java;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import uz.example.less67_task1_posterlistwithprogrbar_java.activity.CreateActivity;
import uz.example.less67_task1_posterlistwithprogrbar_java.adapter.PosterAdapter;
import uz.example.less67_task1_posterlistwithprogrbar_java.model.Poster;
import uz.example.less67_task1_posterlistwithprogrbar_java.network.VolleyHandler;
import uz.example.less67_task1_posterlistwithprogrbar_java.network.VolleyHttp;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Poster> posters = new ArrayList<>();
    ProgressBar pb_loading;
    FloatingActionButton floating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    void initViews(){
        pb_loading = findViewById(R.id.pb_loading);
        floating = findViewById(R.id.floating);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        apiPosterList();
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateActivity();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            Log.d("###","extras not NULL - ");
            String edit_title = extras.getString("title");
            String edit_post = extras.getString("post");
            String edit_userId = extras.getString("id_user");
            String edit_id = extras.getString("id");
            Poster poster = new Poster(Integer.parseInt(edit_id),Integer.parseInt(edit_userId),edit_title,edit_post);
            Toast.makeText(MainActivity.this, "Post Prepared to Edit", Toast.LENGTH_LONG).show();

            apiPosterEdit(poster);

        }

    }


    public ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == 78) {
                        Intent data = result.getData();

                        if (data !=null){
                            String new_title = data.getStringExtra("title");
                            String new_post = data.getStringExtra("post");
                            String new_userId = data.getStringExtra("id_user");
                            Poster poster = new Poster(Integer.parseInt(new_userId),new_title,new_post);
                            Toast.makeText(MainActivity.this, "Title modified", Toast.LENGTH_LONG).show();

                            apiPosterCreate(poster);
                        }
                        // your operation....
                    }else {
                        Toast.makeText(MainActivity.this, "Operation canceled", Toast.LENGTH_LONG).show();
                    }

                }
            });
    void refreshAdapter(ArrayList<Poster> posters) {
        PosterAdapter adapter = new PosterAdapter(this, posters);
        recyclerView.setAdapter(adapter);
    }

    void openCreateActivity(){
        Intent intent = new Intent(MainActivity.this, CreateActivity.class);
        launchSomeActivity.launch(intent);
    }

    public void dialogPoster(Poster poster) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Poster")
                .setMessage("Are you sure you want to delete this poster?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        apiPosterDelete(poster);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void apiPosterList() {
        pb_loading.setVisibility(View.VISIBLE);
        VolleyHttp.get(VolleyHttp.API_LIST_POST, VolleyHttp.paramsEmpty(), new VolleyHandler() {
            @Override
            public void onSuccess(String response) {
                Poster[] postArray = new Gson().fromJson(response, Poster[].class);
                posters.clear();
                for (Poster poster : postArray) {
                    posters.add(poster);
                }
                refreshAdapter(posters);
                pb_loading.setVisibility(View.GONE);
                Log.d("@@@onResponse ", "" + posters.size());
            }

            @Override
            public void onError(String error) {
                Log.d("@@@onErrorResponse ", error);
            }
        });
    }
    private void apiPosterCreate(Poster poster) {
        pb_loading.setVisibility(View.VISIBLE);
        VolleyHttp.post(VolleyHttp.API_CREATE_POST, VolleyHttp.paramsCreate(poster), new VolleyHandler() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(MainActivity.this, poster.getTitle()+" Created", Toast.LENGTH_LONG).show();
                apiPosterList();
            }

            @Override
            public void onError(String error) {
                Log.d("@@@onErrorResponse ", error);
            }
        });
    }
    private void apiPosterEdit(Poster poster) {
        pb_loading.setVisibility(View.VISIBLE);
        VolleyHttp.put(VolleyHttp.API_UPDATE_POST+poster.getId(), VolleyHttp.paramsUpdate(poster), new VolleyHandler() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(MainActivity.this, poster.getTitle()+" Edited", Toast.LENGTH_LONG).show();
                Log.d("@@@onResponse ", response);
                apiPosterList();
            }

            @Override
            public void onError(String error) {
                Log.d("@@@onErrorResponse ", error);
            }
        });
    }
    private void apiPosterDelete(Poster poster) {
        pb_loading.setVisibility(View.VISIBLE);
        VolleyHttp.del(VolleyHttp.API_DELETE_POST + poster.getId(), new VolleyHandler() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(MainActivity.this, poster.getTitle()+" Deleted", Toast.LENGTH_LONG).show();
                apiPosterList();
                //pb_loading.setVisibility(View.GONE);
            }

            @Override
            public void onError(String error) {
                Log.d("@@@onErrorResponse ", error);
            }
        });
    }

}