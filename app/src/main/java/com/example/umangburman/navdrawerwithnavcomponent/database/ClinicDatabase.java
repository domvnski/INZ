package com.example.umangburman.navdrawerwithnavcomponent.database;

        import android.content.Context;
        import android.os.AsyncTask;

        import androidx.annotation.NonNull;
        import androidx.room.Database;
        import androidx.room.Room;
        import androidx.room.RoomDatabase;
        import androidx.sqlite.db.SupportSQLiteDatabase;

        import java.sql.Time;
        import java.util.Date;

@Database(entities = {Patient.class, Doctor.class, Visit.class}, version = 1)
public abstract class ClinicDatabase extends RoomDatabase {

    public abstract ClinicDao clinicDao();

    private static ClinicDatabase INSTANCE;

    public static synchronized ClinicDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                INSTANCE = Room.
                        databaseBuilder(context.getApplicationContext(),
                                ClinicDatabase.class, "clinic_database")
                        .fallbackToDestructiveMigration() //https://www.youtube.com/watch?v=0cg09tlAAQ0 - 10 min
                        .addCallback(roomCalback)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCalback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private ClinicDao clinicDao;

        private PopulateDbAsyncTask(ClinicDatabase db){
            clinicDao=db.clinicDao();

        }
        @Override
        protected Void doInBackground(Void... voids) {

            clinicDao.insertVisit(new Visit(1,1,new Date().getTime(),new Date().getTime(),new Date().getTime(), "pierwsza"));
            clinicDao.insertVisit(new Visit(2,1,new Date().getTime(),new Date().getTime(),new Date().getTime(), "druga"));
            clinicDao.insertDoctor(new Doctor("Hubert", "Danilczuk", new Date().getTime(), "555444333", "marczuk@sex.pl"));
            clinicDao.insertPatient(new Patient( "Patryk", "Strzechowski", new Date().getTime(), "note"));
            clinicDao.insertPatient(new Patient( "Bartłomiej", "Kichor", new Date().getTime(), "note"));
            clinicDao.insertPatient(new Patient( "Karol", "Sobala", new Date().getTime(), "note"));
//            clinicDao.insertPatient(new Patient(4, "Patryk", "Strzechowski", "data uro", "note"));
//            clinicDao.insertPatient(new Patient(5, "Bartłomiej", "Kichor", "data uasdzxcxzro", "note"));
//            clinicDao.insertPatient(new Patient(6, "Karol", "Sobala", "daasdro", "note"));
//            clinicDao.insertPatient(new Patient(7, "Patryk", "Strzechowski", "data uro", "note"));
//            clinicDao.insertPatient(new Patient(8, "Bartłomiej", "Kichor", "data uasdzxcxzro", "note"));
//            clinicDao.insertPatient(new Patient(9, "Karol", "Sobala", "daasdro", "note"));
//            clinicDao.insertPatient(new Patient(11, "Patryk", "Strzechowski", "data uro", "note"));
//            clinicDao.insertPatient(new Patient(21, "Bartłomiej", "Kichor", "data uasdzxcxzro", "note"));
//            clinicDao.insertPatient(new Patient(31, "Karol", "Sobala", "daasdro", "note"));
//            clinicDao.insertPatient(new Patient(41, "Patryk", "Strzechowski", "data uro", "note"));
//            clinicDao.insertPatient(new Patient(51, "Bartłomiej", "Kichor", "data uasdzxcxzro", "note"));
//            clinicDao.insertPatient(new Patient(61, "Karol", "Sobala", "daasdro", "note"));
//            clinicDao.insertPatient(new Patient(71, "Patryk", "Strzechowski", "data uro", "note"));
//            clinicDao.insertPatient(new Patient(81, "Bartłomiej", "Kichor", "data uasdzxcxzro", "note"));
//            clinicDao.insertPatient(new Patient(91, "Karol", "Sobala", "daasdro", "note"));

            return null;
        }
    }


}
