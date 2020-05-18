package com.example.hotelnfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toogle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Fragment frag_reserve, frag_reserve_room;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationview);

        frag_reserve = (frag_reserve) getSupportFragmentManager().findFragmentById(R.id.frag_reserve)

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        //햄버거 모양의 아이콘 - 메뉴창 띄우는 버튼
        toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);

        drawerLayout.addDrawerListener(toogle); //드로어에 여러 기능을 붙일수있도록 해줌

        toogle.syncState(); //햄버거 아이콘 클릭하면 돌아가고 하는 기능이 다 담겨있음

        navigationView.setNavigationItemSelectedListener(this);

        // load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        //NFC 화면을 첫화면으로 고정
        getSupportFragmentManager().beginTransaction().add(R.id.content_fragment, new Frag_nfc()).commit();

    }


    //뒤로가기 버튼을 눌렀을 때 이전화면으로 가도록 이게 없다면 그냥 앱 종료되버림
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.reserve) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new frag_reserve()).commit();
        } else if (id == R.id.check_reserve) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new frag_check_reserve()).commit();
        } else if (id == R.id.menu_login) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        } else if (id == R.id.menu_signin) {
            Intent intent = new Intent(getApplicationContext(), Sign_In.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onChangeFragment(int index) {
        if(index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, frag_reserve_room).commit();
        }
    }
}
