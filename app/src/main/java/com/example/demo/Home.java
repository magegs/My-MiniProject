package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Model.Products;
import Privalent.Privalent;
import ViewHolder.ProductViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);







        Paper.init(this);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview=navigationView.getHeaderView(0);
        TextView navusername=headerview.findViewById(R.id.nav_username);
        CircleImageView profileimage=headerview.findViewById(R.id.profile_image);
        navusername.setText(Privalent.currentuser.getName());


        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment,new Product_Items());
        fragmentTransaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
         getMenuInflater().inflate(R.menu.toolbarmenu,menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.app_bar_cart)
        {
            final DatabaseReference cartref = FirebaseDatabase.getInstance().getReference();
            ValueEventListener valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.child("cart").child("UserView")
                            .child(Privalent.currentuser.getPhoneno()).child("Products").exists())
                    {
                        if (dataSnapshot.child("CartOrders").child(Privalent.currentuser.getPhoneno()).exists())
                        {
                            Toast.makeText(Home.this, "Cart Order is under processing !", Toast.LENGTH_LONG).show();
                        }
                    }
                    if(dataSnapshot.child("cart").child("UserView")
                            .child(Privalent.currentuser.getPhoneno()).child("Products").exists())
                    {
                        if (!dataSnapshot.child("CartOrders").child(Privalent.currentuser.getPhoneno()).exists())
                        {
                            Intent intent=new Intent(Home.this,Cart.class);
                            startActivity(intent);
                        }
                    }
                    if(!dataSnapshot.child("cart").child("UserView")
                            .child(Privalent.currentuser.getPhoneno()).child("Products").exists())
                {
                    if (dataSnapshot.child("CartOrders").child(Privalent.currentuser.getPhoneno()).exists())
                    {
                        Toast.makeText(Home.this,"Cart was Under Processing!",Toast.LENGTH_LONG).show();
                    }
                }
                    else
                    {
                        Toast.makeText(Home.this,"Cart was Empty!",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            cartref.addListenerForSingleValueEvent(valueEventListener);
            cartref.removeEventListener(valueEventListener);

        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId()==R.id.nav_home){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Product_Items());
            fragmentTransaction.commit();
            Toast.makeText(Home.this,"You tap home!",Toast.LENGTH_LONG).show();
        }
        if(menuItem.getItemId()==R.id.categoery){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new CategoryFragment());
            fragmentTransaction.commit();
            Toast.makeText(Home.this,"You tap Categoery!",Toast.LENGTH_LONG).show();
        }
        if(menuItem.getItemId()==R.id.yourorders){
            Toast.makeText(Home.this,"You tap yourorders!",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(Home.this,Custbilling.class);
            startActivity(intent);
        }
        if(menuItem.getItemId()==R.id.logout){
            Paper.book().destroy();

            Intent intent=new Intent(Home.this, MainActivity.class);
            startActivity(intent);
        }
        if(menuItem.getItemId()==R.id.app_bar_cart){
            final DatabaseReference cartref = FirebaseDatabase.getInstance().getReference();
            ValueEventListener valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.child("cart").child("UserView")
                            .child(Privalent.currentuser.getPhoneno()).child("Products").exists())
                    {
                        if (dataSnapshot.child("CartOrders").child(Privalent.currentuser.getPhoneno()).exists())
                        {
                            Toast.makeText(Home.this, "Cart Order is under processing !", Toast.LENGTH_LONG).show();
                        }
                    }
                    if(dataSnapshot.child("cart").child("UserView")
                            .child(Privalent.currentuser.getPhoneno()).child("Products").exists())
                    {
                        if (!dataSnapshot.child("CartOrders").child(Privalent.currentuser.getPhoneno()).exists())
                        {
                            Intent intent=new Intent(Home.this,Cart.class);
                            startActivity(intent);
                        }
                    }
                    if(!dataSnapshot.child("cart").child("UserView")
                            .child(Privalent.currentuser.getPhoneno()).child("Products").exists())
                    {
                        if (dataSnapshot.child("CartOrders").child(Privalent.currentuser.getPhoneno()).exists())
                        {
                            Toast.makeText(Home.this,"Cart was Under Processing!",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        {
                        Toast.makeText(Home.this,"Cart was Empty!",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            cartref.addListenerForSingleValueEvent(valueEventListener);
            cartref.removeEventListener(valueEventListener);


        }
        if(menuItem.getItemId()==R.id.track_order){
            Intent intent=new Intent(Home.this, OrderTrack.class);
            startActivity(intent);
        }

        return true;
    }
}


