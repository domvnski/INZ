package pl.wat.domanski.myClinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.database.ClinicViewModel;

public class SettingsFragment extends Fragment {

    TextView textViewDeleteDatabase;
    ClinicViewModel clinicViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);

        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);

        textViewDeleteDatabase = root.findViewById(R.id.textViewSettingsDeleteData);

        textViewDeleteDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDeleteSnackBar();
            }
        });

        return root;
    }

    private void showConfirmDeleteSnackBar() {

        Snackbar snackbar = Snackbar
                .make(getView(), "Czy na pewno chcesz usunąć bazę danych?", Snackbar.LENGTH_LONG)
                .setAction("TAK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clinicViewModel.deleteAllDoctors();
                        clinicViewModel.deleteAllPatients();
                        clinicViewModel.deleteAllVisits();
                        Toast.makeText(getActivity(), "Baza danych została usunięta", Toast.LENGTH_SHORT).show();
                    }
                });
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}
