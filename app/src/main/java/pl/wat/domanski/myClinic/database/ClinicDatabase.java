package pl.wat.domanski.myClinic.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

@Database(entities = {Patient.class, Doctor.class, Visit.class, PatientAddress.class, PatientContact.class}, version = 6)
public abstract class ClinicDatabase extends RoomDatabase {

    public abstract ClinicDao clinicDao();

    private static ClinicDatabase INSTANCE;

    public static synchronized ClinicDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.
                    databaseBuilder(context.getApplicationContext(), ClinicDatabase.class, "clinic_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ClinicDao clinicDao;

        private PopulateDbAsyncTask(ClinicDatabase db) {
            clinicDao = db.clinicDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            clinicDao.insertVisit(new Visit(1, 1, new Date().getTime(), new Date().getTime(), new Date().getTime(), "test IQ"));
            clinicDao.insertVisit(new Visit(2, 1, new Date().getTime(), new Date().getTime(), new Date().getTime(), "kolonoskopia"));
            clinicDao.insertVisit(new Visit(3, 2, new Date().getTime(), new Date().getTime(), new Date().getTime(), "Wycinanie paznokcia"));
            clinicDao.insertDoctor(new Doctor("Hubert", "Danilczuk", "535990151", "danilczuk@example.com", "Podolog", "1234567"));
            clinicDao.insertDoctor(new Doctor("Kamil", "Olichwirowski", "511536444", "oli@example.com", "Ginekolog", "1234567"));
            clinicDao.insertDoctor(new Doctor("Aleksandra", "Golonka", "511536444", "ola@example.com", "Psychiatra", "1234567"));
            clinicDao.insertPatient(new Patient("Patryk", "Strzechowski", new Date().getTime(), "96010285736", ""));
            clinicDao.insertPatient(new Patient("Bartłomiej", "Kichor", new Date().getTime(), "96010285736", "Wysokie ciśnienie"));
            clinicDao.insertPatient(new Patient("Karol", "Sobala", new Date().getTime(), "96010285736", ""));
            clinicDao.insertPatient(new Patient("Hubert", "Karakan", new Date().getTime(), "96010285736", "Szpotawe kolano"));
            clinicDao.insertPatientAddress(new PatientAddress(1, "Wroclawska 72/1", "01-231", "Warszawa"));
            clinicDao.insertPatientAddress(new PatientAddress(2, "Kowala 69", "01-231", "Radom"));
            clinicDao.insertPatientAddress(new PatientAddress(3, "Narwik 13", "01-231", "Warszawa"));
            clinicDao.insertPatientAddress(new PatientAddress(4, "Roztworowskiego 999", "01-231", "Warszawa"));
            clinicDao.insertPatientContact(new PatientContact(1, "516709566", "macho88@wp.pl"));
            clinicDao.insertPatientContact(new PatientContact(2, "607775536", "kici69@onet.pl"));
            clinicDao.insertPatientContact(new PatientContact(3, "500680023", "kkarol91@gmail.com"));
            clinicDao.insertPatientContact(new PatientContact(4, "500680023", "karak@gmail.com"));

            return null;
        }
    }


}
