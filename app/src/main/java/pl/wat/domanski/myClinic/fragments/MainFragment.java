package pl.wat.domanski.myClinic.fragments;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pl.wat.domanski.myClinic.R;
import pl.wat.domanski.myClinic.database.ClinicViewModel;
import pl.wat.domanski.myClinic.database.Visit;

public class MainFragment extends Fragment {

    private Button buttonShowDeviceCalendar;
    private ImageButton imageButtonAddVisit, imageButtonShowVisits;
    private TextView textViewVisitCounter;

    ClinicViewModel clinicViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment, container, false);

        clinicViewModel = ViewModelProviders.of(this).get(ClinicViewModel.class);
        closeKeyboard();

        buttonShowDeviceCalendar = root.findViewById(R.id.buttonMainFragmentShowDeviceCalendar);
        imageButtonAddVisit = root.findViewById(R.id.imageButtonMainFragmentAddVisit);
        imageButtonShowVisits = root.findViewById(R.id.imageButtonMainFragmentShowVisits);
        textViewVisitCounter = root.findViewById(R.id.textViewMainFragmentTodayVisitsCounter);

        updateVisitCounter();

        buttonShowDeviceCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                startActivity(intent);
            }
        });

        imageButtonAddVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditVisitFragment addEditVisitFragment = new AddEditVisitFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, addEditVisitFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        imageButtonShowVisits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisitsFragment visitsFragment = new VisitsFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, visitsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return root;
    }

    private void updateVisitCounter() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth += 1;
        String textCurrentDate = (mDay + "/" + mMonth + "/" + mYear);
        long longCurrentDate = 0;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate;
        try {
            birthDate = format.parse(textCurrentDate);
            longCurrentDate = birthDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        clinicViewModel.getVisitsByDate(longCurrentDate).observe(MainFragment.this, new Observer<List<Visit>>() {
            @Override
            public void onChanged(List<Visit> visits) {
                textViewVisitCounter.setText(String.valueOf(visits.size()));
            }
        });
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
