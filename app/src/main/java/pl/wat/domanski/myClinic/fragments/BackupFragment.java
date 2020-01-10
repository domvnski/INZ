package pl.wat.domanski.myClinic.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.codekidlabs.storagechooser.StorageChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.wat.domanski.myClinic.R;

public class BackupFragment extends Fragment {

    private Button buttonImportData, buttonExportData, buttonPickData;
    private TextView textViewPattern;
    private String backupPath;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.backup_fragment, container, false);

        buttonImportData = root.findViewById(R.id.buttonImportData);
        buttonExportData = root.findViewById(R.id.buttonExportData);
        buttonPickData = root.findViewById(R.id.buttonPickData);
        textViewPattern = root.findViewById(R.id.textViewPattern);

        buttonPickData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFolderPicker();
            }
        });

        buttonExportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDB();
            }
        });

        buttonImportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreDB();
            }
        });

        return root;
    }

    private void showFolderPicker() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123); //todo inny request?
        } else {
            final StorageChooser chooser = new StorageChooser.Builder()
                    .withActivity(getActivity())
                    .withFragmentManager(getActivity().getFragmentManager())
                    .withMemoryBar(true)
                    .allowCustomPath(true)
                    .setType(StorageChooser.DIRECTORY_CHOOSER)
                    .build();

            chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
                @Override
                public void onSelect(String path) {
                    backupPath = path.substring(path.indexOf("/0") + 2);
                    if (backupPath.length() == 0) {
                        Toast.makeText(getActivity(), "Nie wybrano folderu", Toast.LENGTH_SHORT).show();
                    } else {
                        textViewPattern.setText("Wybrany folder: " + backupPath);
                        buttonImportData.setEnabled(true);
                    }
                }
            });
            chooser.show();
        }
    }

    private void restoreDB() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);
        } else {
            try {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if (sd.canWrite()) {
                    String currentDBPath = "/data/" + "pl.wat.domanski.myClinic" + "/databases/" + "clinic_database";
                    String currentDBshmPath = "/data/" + "pl.wat.domanski.myClinic" + "/databases/" + "clinic_database-shm";
                    String currentDBwalPath = "/data/" + "pl.wat.domanski.myClinic" + "/databases/" + "clinic_database-wal";

                    String backupDBPath = backupPath + "/clinic_database";
                    String backupDBshmPath = backupPath + "/clinic_database-shm";
                    String backupDBwalPath = backupPath + "/clinic_database-wal";

                    File currentDB = new File(data, currentDBPath);
                    File currentDBshm = new File(data, currentDBshmPath);
                    File currentDBwal = new File(data, currentDBwalPath);

                    File backupDB = new File(sd, backupDBPath);
                    File backupDBshm = new File(sd, backupDBshmPath);
                    File backupDBwal = new File(sd, backupDBwalPath);


                    FileChannel DBsrc = new FileInputStream(backupDB).getChannel();
                    FileChannel DBdst = new FileOutputStream(currentDB).getChannel();
                    DBdst.transferFrom(DBsrc, 0, DBsrc.size());
                    DBsrc.close();
                    DBdst.close();

                    FileChannel DBshmSrc = new FileInputStream(backupDBshm).getChannel();
                    FileChannel DBshmDst = new FileOutputStream(currentDBshm).getChannel();
                    DBshmDst.transferFrom(DBshmSrc, 0, DBshmSrc.size());
                    DBshmSrc.close();
                    DBshmDst.close();

                    FileChannel DBwalSrc = new FileInputStream(backupDBwal).getChannel();
                    FileChannel DBwalDst = new FileOutputStream(currentDBwal).getChannel();
                    DBwalDst.transferFrom(DBwalSrc, 0, DBwalSrc.size());
                    DBwalSrc.close();
                    DBwalDst.close();

                    Toast.makeText(getActivity(), "Baza danych została przywrócona z " + backupDBPath, Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Nie znaleziono plików bazy danych", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void exportDB() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);
        } else {
            try {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String currentDate = simpleDateFormat.format(new Date());

                File backupDirectory = new File("/sdcard/myClinic-Backup/" + currentDate);
                backupDirectory.mkdirs();

                if (sd.canWrite()) {
                    String currentDBPath = "/data/" + "pl.wat.domanski.myClinic" + "/databases/" + "clinic_database";
                    String currentDBshmPath = "/data/" + "pl.wat.domanski.myClinic" + "/databases/" + "clinic_database-shm";
                    String currentDBwalPath = "/data/" + "pl.wat.domanski.myClinic" + "/databases/" + "clinic_database-wal";

                    String backupDBPath = "myClinic-Backup/" + currentDate + "/clinic_database";
                    String backupDBshmPath = "myClinic-Backup/" + currentDate + "/clinic_database-shm";
                    String backupDBwalPath = "myClinic-Backup/" + currentDate + "/clinic_database-wal";

                    File currentDB = new File(data, currentDBPath);
                    File currentDBshm = new File(data, currentDBshmPath);
                    File currentDBwal = new File(data, currentDBwalPath);

                    File backupDB = new File(sd, backupDBPath);
                    File backupDBshm = new File(sd, backupDBshmPath);
                    File backupDBwal = new File(sd, backupDBwalPath);

                    FileChannel DBsrc = new FileInputStream(currentDB).getChannel();
                    FileChannel DBdst = new FileOutputStream(backupDB).getChannel();
                    DBdst.transferFrom(DBsrc, 0, DBsrc.size());
                    DBsrc.close();
                    DBdst.close();

                    FileChannel DBshmSrc = new FileInputStream(currentDBshm).getChannel();
                    FileChannel DBshmDst = new FileOutputStream(backupDBshm).getChannel();
                    DBshmDst.transferFrom(DBshmSrc, 0, DBshmSrc.size());
                    DBshmSrc.close();
                    DBshmDst.close();

                    FileChannel DBwalSrc = new FileInputStream(currentDBwal).getChannel();
                    FileChannel DBwalDst = new FileOutputStream(backupDBwal).getChannel();
                    DBwalDst.transferFrom(DBwalSrc, 0, DBwalSrc.size());
                    DBwalSrc.close();
                    DBwalDst.close();

                    Toast.makeText(getActivity(), "Pliki bazy danych zostały pomyślnie zapisane w " + backupDBPath, Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Nie udało się zapisać bazy danych", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
