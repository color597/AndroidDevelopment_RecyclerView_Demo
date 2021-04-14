package com.lxp.recyclerview_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mAppRecyclerView;
    private List<Application> mApplicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        PackageManager packageManager = getPackageManager();
        Application application;
        Intent appIntent;
        Intent intent = new Intent().setAction(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);
        ActivityInfo activityInfo;
        ApplicationInfo applicationInfo;
        Drawable appIcon;
        CharSequence appLabel;
        boolean isSystemApp;
        this.mApplicationList = new ArrayList<>();

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfos) {
            activityInfo = resolveInfo.activityInfo;
            applicationInfo = activityInfo.applicationInfo;

            appIcon = activityInfo.loadIcon(packageManager);
            appLabel = activityInfo.loadLabel(packageManager);
            isSystemApp = (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1;
            appIntent = new Intent().setClassName(activityInfo.packageName, activityInfo.name);
            application = new Application(appIcon, appLabel, isSystemApp, appIntent);
            this.mApplicationList.add(application);
        }

        this.mAppRecyclerView = findViewById(R.id.rv);
//      设置布局管理器
        this.mAppRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//      设置适配器
        this.mAppRecyclerView.setAdapter(new ApplicationAdapter(this.mApplicationList, 0));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_as_linear:
                this.mAppRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                this.mAppRecyclerView.setAdapter(new ApplicationAdapter(this.mApplicationList, 0));
                break;
            case R.id.show_as_grid:
                this.mAppRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                this.mAppRecyclerView.setAdapter(new ApplicationAdapter(this.mApplicationList, 1));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}