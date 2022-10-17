package uz.example.less67_task1_posterlistwithprogrbar_java.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import uz.example.less67_task1_posterlistwithprogrbar_java.MainActivity;
import uz.example.less67_task1_posterlistwithprogrbar_java.R;
import uz.example.less67_task1_posterlistwithprogrbar_java.activity.CreateActivity;
import uz.example.less67_task1_posterlistwithprogrbar_java.activity.EditActivity;
import uz.example.less67_task1_posterlistwithprogrbar_java.model.Poster;

public class PosterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MainActivity activity;
    ArrayList<Poster> items;

    public PosterAdapter(MainActivity activity, ArrayList<Poster> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_list, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof PosterViewHolder) {
            Poster poster = items.get(position);

            SwipeLayout sl_swipe = ((PosterViewHolder) holder).sl_swipe;

            LinearLayout linear_left = ((PosterViewHolder) holder).linear_left;
            LinearLayout linear_right = ((PosterViewHolder) holder).linear_right;
            sl_swipe.setShowMode(SwipeLayout.ShowMode.PullOut);
            sl_swipe.addDrag(SwipeLayout.DragEdge.Left,linear_left);
            sl_swipe.addDrag(SwipeLayout.DragEdge.Right,linear_right);


            /*ll_poster.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    activity.dialogPoster(poster);
                    return false;
                }
            });*/
            TextView tv_title = ((PosterViewHolder) holder).tv_title;
            TextView tv_body = ((PosterViewHolder) holder).tv_body;
            TextView tv_delete = ((PosterViewHolder) holder).tv_delete;
            TextView tv_edit = ((PosterViewHolder) holder).tv_edit;
            tv_title.setText(poster.getTitle().toUpperCase());
            tv_body.setText(poster.getBody());

            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.dialogPoster(poster);

                }
            });
            tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity.getBaseContext(), EditActivity.class);
                    intent.putExtra("id",String.valueOf(poster.getId()));
                    intent.putExtra("user_id",String.valueOf(poster.getUserId()));
                    intent.putExtra("title",poster.getTitle());
                    intent.putExtra("body",poster.getBody());
                    //activity.setResult(88,intent);
                    activity.startActivity(intent);

                }
            });
        }
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView tv_title;
        public TextView tv_body;
        public LinearLayout linear_left;
        public LinearLayout linear_right;
        public SwipeLayout sl_swipe;
        TextView tv_delete;
        TextView tv_edit;

        public PosterViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            tv_title = view.findViewById(R.id.tv_title);
            tv_body = view.findViewById(R.id.tv_body);
            linear_right = view.findViewById(R.id.ll_linear_right);
            linear_left = view.findViewById(R.id.ll_linear_left);
            sl_swipe = view.findViewById(R.id.sl_swipe);
            tv_delete = view.findViewById(R.id.tv_delete);
            tv_edit = view.findViewById(R.id.tv_edit);
        }
    }
}
