package com.example.kms.new_moms;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<ImageDtO> imageDtOs = new ArrayList<>();
    private List<String> uidLists = new ArrayList<>();
    private FirebaseDatabase database;
    private List<inform> informs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        database = FirebaseDatabase.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);


        database.getReference().child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageDtOs.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ImageDtO imageDtO = snapshot.getValue(ImageDtO.class);
                    imageDtOs.add(imageDtO);
                }

                boardRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.getReference().child("informs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                informs.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    inform inform = snapshot.getValue(inform.class);
                    informs.add(inform);
                }

                boardRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
//        database.getReference().child("informs")
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board,parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((CustomViewHolder)holder).textView.setText(imageDtOs.get(position).title);
            ((CustomViewHolder)holder).textView2.setText(imageDtOs.get(position).description);
            ((CustomViewHolder)holder).textView5.setText("우리 아이는 태어난 지 "+imageDtOs.get(position).month + " 개월 째 입니다\n");

            Glide.with(holder.itemView.getContext()).load(imageDtOs.get(position).imageUrl).into(((CustomViewHolder)holder).imageView);

            int tmep_month = Integer.parseInt(imageDtOs.get(position).month);
            float temp_height = Float.parseFloat(imageDtOs.get(position).height);
            float temp_weight = Float.parseFloat(imageDtOs.get(position).weight);

            for(int i=0; i< informs.size(); i++){
                int temp_avg_month = Integer.parseInt(informs.get(i).month);
                float temp_avg_height = Float.parseFloat(informs.get(i).height);
                float temp_avg_weight = Float.parseFloat(informs.get(i).weight);
                float diff_height;
                float diff_weight;

                if(tmep_month == temp_avg_month){
//                    ((CustomViewHolder)holder).temp1.setText(informs.get(i).height);

                    if(temp_height> temp_avg_height){
                        diff_height = temp_height - temp_avg_height;
                        ((CustomViewHolder)holder).temp1.setText(imageDtOs.get(position).month + " 개월 째 유아의 평균 신장은 " + informs.get(i).height+ "cm로\n"+"우리아이는 평균보다" +diff_height+ "cm 큽니다!");
                    }
                    else if(temp_height == temp_avg_height){
                        ((CustomViewHolder)holder).temp1.setText(imageDtOs.get(position).month + " 개월 째 유아의 평균 신장은 " + informs.get(i).height+ "cm로\n"+"우리아이는 평균입니다");
                    }
                    else{
                        diff_height = temp_avg_height - temp_height;
                        ((CustomViewHolder)holder).temp1.setText(imageDtOs.get(position).month + " 개월 째 유아의 평균 신장은 " + informs.get(i).height+ "cm로\n"+"우리아이는 평균보다" +diff_height+ "cm 작습니다..");
                    }

                    if(temp_weight> temp_avg_weight){
                        diff_weight = temp_weight - temp_avg_weight;
                        ((CustomViewHolder)holder).temp2.setText(imageDtOs.get(position).month + " 개월 째 유아의 평균 몸무게는 " + informs.get(i).weight+ "kg로\n"+"우리아이는 평균보다" +diff_weight+ "kg 우량아 입니다!");
                    }
                    else if(temp_weight == temp_avg_weight){
                        ((CustomViewHolder)holder).temp2.setText(imageDtOs.get(position).month + " 개월 째 유아의 평균 몸무게는 " + informs.get(i).weight+ "kg로\n"+"우리아이는 평균입니다");
                    }
                    else{
                        diff_weight = temp_avg_weight - temp_weight;
                        ((CustomViewHolder)holder).temp2.setText(imageDtOs.get(position).month + " 개월 째 유아의 평균 몸무게는 " + informs.get(i).weight+ "kg로\n"+"우리아이는 평균보다" +diff_weight+ "kg 날씬합니다!");
                    }
//                    ((CustomViewHolder)holder).temp2.setText(informs.get(i).weight);
                }
            }


        }

        @Override
        public int getItemCount() {
            return imageDtOs.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            TextView textView2;

            TextView textView5;

            TextView temp1;
            TextView temp2;


            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.item_imageView);
                textView = (TextView) view.findViewById(R.id.item_textView);
                textView2 = (TextView) view.findViewById(R.id.item_textView2);
                textView5 = (TextView) view.findViewById(R.id.item_textView5);

                temp1 = (TextView) view.findViewById(R.id.temp1);
                temp2 = (TextView) view.findViewById(R.id.temp2);

            }
        }
    }
}
