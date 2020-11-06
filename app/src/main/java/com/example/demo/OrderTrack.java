package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.baoyachi.stepview.VerticalStepView;

import java.util.ArrayList;
import java.util.List;

public class OrderTrack extends AppCompatActivity {
    VerticalStepView step_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_track);

        step_view=findViewById(R.id.stepview);
        setStepView();
    }

    private void setStepView()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            step_view.setStepsViewIndicatorComplectingPosition(getList()
                    .size())
                    .reverseDraw(false)
                    .setStepViewTexts(getList())
                    .setLinePaddingProportion(0.85f)
                    .setStepsViewIndicatorCompletedLineColor(getColor(R.color.colorPrimaryDark))
                    .setStepViewComplectedTextColor(getColor(R.color.ordertext))
                    .setStepViewUnComplectedTextColor(getColor(R.color.colorPrimaryDark))
                    .setStepsViewIndicatorCompleteIcon(getDrawable(R.drawable.complted))
                    .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.attention))
                    .setStepsViewIndicatorDefaultIcon(getDrawable(R.drawable.default_icon));
            step_view.setStepsViewIndicatorComplectingPosition(3);
        }
    }
    private List<String>getList(){
        List<String> list=new ArrayList<>();
        list.add("Order Placed");
        list.add("Order Dispatch");
        list.add("Order En Route");
        list.add("Order Arrived");
        return list;
    }
}