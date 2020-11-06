package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Custbilling extends AppCompatActivity {
    private Button bill_btn;
    private ImageView order_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custbilling);
        bill_btn=(Button)findViewById(R.id.bill_download);
        order_img=(ImageView)findViewById(R.id.order_productimage) ;
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        createPDF();
    }

    private void createPDF()
    {
        bill_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfDocument pdfDocument=new PdfDocument();
                Paint paint=new Paint();
                Paint titlepaint=new Paint();

                PdfDocument.PageInfo mypageinfol=new PdfDocument.PageInfo.Builder(1200,2010,1).create();
                PdfDocument.Page mypage1=pdfDocument.startPage(mypageinfol);
                Canvas canvas=mypage1.getCanvas();
                titlepaint.setTextAlign(Paint.Align.CENTER);
                titlepaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                titlepaint.setTextSize(70);
                canvas.drawText("Purchase Invoice",1200/2,270,titlepaint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(35f);
                paint.setColor(Color.BLACK);
                canvas.drawText("Customer_Name:G.S.MAGENDRAN",20,590,paint);
                canvas.drawText("Customer_Number:8072543321",20,640,paint);

               paint.setTextAlign(Paint.Align.RIGHT);
               canvas.drawText("InvoiceNo:20201234",1200-20,590,paint);
               canvas.drawText("Date:09-10-2020",1200-20,640,paint);
               canvas.drawText("Time:14:10:51",1200-20,690,paint);

               paint.setStyle(Paint.Style.STROKE);
               paint.setStrokeWidth(2);
               canvas.drawRect(20,780,1200-20,860,paint);

               paint.setTextAlign(Paint.Align.LEFT);
               paint.setStyle(Paint.Style.FILL);
               canvas.drawText("SI.NO",40,830,paint);
                canvas.drawText("ITEM",200,830,paint);
                canvas.drawText("PRICE",700,830,paint);
                canvas.drawText("QTY",900,830,paint);
                canvas.drawText("TOTAL",1050,830,paint);
                canvas.drawLine(180,790,180,840,paint);
                canvas.drawLine(680,790,680,840,paint);
                canvas.drawLine(880,790,880,840,paint);
                canvas.drawLine(1030,790,1030,840,paint);

                canvas.drawText("1)",40,950,paint);
                canvas.drawText("Capsicum Mix Color",200,950,paint);
                canvas.drawText("135",700,950,paint);
                canvas.drawText("4",900,950,paint);
                canvas.drawText("540",1050,950,paint);

                canvas.drawText("2)",40,1050,paint);
                canvas.drawText("Cherry Tomato",200,1050,paint);
                canvas.drawText("135",700,1050,paint);
                canvas.drawText("4",900,1050,paint);
                canvas.drawText("540",1050,1050,paint);

                canvas.drawLine(680,1200,1200-20,1200,paint);
                canvas.drawText("Sub Total",700,1250,paint);
                canvas.drawText(":",900,1250,paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("1080",1200-40,1250,paint);

                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Delivery Charge",700,1300,paint);
                canvas.drawText(":",900,1300,paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("100",1200-40,1300,paint);

                canvas.drawLine(680,1350,1200-20,1350,paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(50f);
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("TOTAL",700,1415,paint);
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("1180",1200-40,1415,paint);





                pdfDocument.finishPage(mypage1);

                File file=new File(Environment.getExternalStorageDirectory(),"/Billing.pdf");

                try{
                    pdfDocument.writeTo(new FileOutputStream(file));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                pdfDocument.close();
            }
        });
    }
}