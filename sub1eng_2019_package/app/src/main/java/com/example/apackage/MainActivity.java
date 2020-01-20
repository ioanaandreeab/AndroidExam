package com.example.apackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<DataPackage> packages = new ArrayList<>();
    private final int CODE_SEND_PACKAGE = 333;
    private final int CODE_EDIT_PACKAGE = 444;
    int selectedPosition;
    ListView LVPackages;
    PackageDatabase database;
    private final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(),PackageDatabase.class,"packagesDB").allowMainThreadQueries().build();
        LVPackages = findViewById(R.id.LVPackages);
        LVPackages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                Intent intentEdit = new Intent(getApplicationContext(),SendPackage.class);
                intentEdit.putExtra("toBeEdited",packages.get(position));
                startActivityForResult(intentEdit,CODE_EDIT_PACKAGE);
            }
        });
        addPackagesInLV();
    }

    private void addPackagesInLV() {
        PackageAdapter adapter = new PackageAdapter(getApplicationContext(),R.layout.package_adapter,packages);
        LVPackages.invalidate();
        LVPackages.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_package,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_backup:
                for(DataPackage dataPackage : packages) {
                    database.getPackageDAO().insertPackageInBD(dataPackage);
                }
                List<DataPackage> packagesFromDB = database.getPackageDAO().selectAllPackagesFromDB();
                for(DataPackage dataPackage: packagesFromDB){
                    Log.d(TAG,dataPackage.toString());
                }
                break;
            case R.id.menu_list:
                break;
            case R.id.menu_rate:
                break;
            case R.id.menu_send:
                Intent intent = new Intent(getApplicationContext(),SendPackage.class);
                startActivityForResult(intent, CODE_SEND_PACKAGE);
                break;
            case R.id.menu_update:
                GetPackagesFromJSON getPackagesFromJSON = new GetPackagesFromJSON(){
                    @Override
                    protected void onPostExecute(List<DataPackage> dataPackages) {
                        super.onPostExecute(dataPackages);
                        for(DataPackage dataPackage: dataPackages){
                            packages.add(dataPackage);
                        }
                        addPackagesInLV();
                    }
                };
                getPackagesFromJSON.execute("http://pdm.ase.ro/examen/packages.json.txt");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_SEND_PACKAGE){
            if(resultCode == RESULT_OK){
                DataPackage myPackage = data.getParcelableExtra("savedPackage");
                packages.add(myPackage);
                addPackagesInLV();
            }
        }

        if(requestCode == CODE_EDIT_PACKAGE){
            if(resultCode == RESULT_OK) {
                DataPackage editedPackage = data.getParcelableExtra("savedPackage");
                packages.get(selectedPosition).setLatitude(editedPackage.getLatitude());
                packages.get(selectedPosition).setLongitude(editedPackage.getLongitude());
                packages.get(selectedPosition).setPackageId(editedPackage.getPackageId());
                packages.get(selectedPosition).setPackageType(editedPackage.getPackageType());
                packages.get(selectedPosition).setTimestamp(editedPackage.getTimestamp());
                addPackagesInLV();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for(int i = 0;i<packages.size();i++){
            outState.putParcelable("key"+i,packages.get(i));
        }
        outState.putInt("size",packages.size());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int n = savedInstanceState.getInt("size");
        for(int i =0;i<n;i++) {
            packages.add((DataPackage)savedInstanceState.getParcelable("key"+i));
        }
        addPackagesInLV();
    }
}
