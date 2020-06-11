package com.example.hotelnfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toogle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String login_id;

    Fragment Frag_login, Frag_nfc, frag_reserve, frag_reserve_room, Frag_singin;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Frag_login = new Frag_login();
        Frag_nfc = new Frag_nfc();
        frag_reserve = new frag_reserve();
        frag_reserve_room = new frag_reserve_room();
        Frag_singin = new Frag_signin();

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationview);

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

//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 위에 뜨는 점 3개 없애는것


        //NFC 화면을 첫화면으로 고정
        getSupportFragmentManager().beginTransaction().add(R.id.content_fragment, new Frag_nfc()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.reserve) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new frag_reserve(), null).addToBackStack(null).commit();
        }
        else if (id == R.id.check_reserve) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new frag_check_reserve(), null).addToBackStack(null).commit();
        }
        else if (id == R.id.menu_login) {
            if (login_id == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new Frag_login(), null).addToBackStack(null).commit();
            } else {
                menuItem.setVisible(false);
            }
        }
        else if (id == R.id.menu_signin) {
            if (login_id == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, new Frag_signin()).commit();
            } else {
                //menuItem.setVisible(false);
            }
        }
        else if (id == R.id.facility_guide) {
            Intent intent = new Intent(this, NfcIssueKey.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public interface onKeyBackPressedListener {
        void onBackKey();
    }

    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if(mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
                Toast.makeText(this, "씨발아 끄고싶은 한번 더눌러", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }
}
