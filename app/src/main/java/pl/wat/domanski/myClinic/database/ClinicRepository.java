package pl.wat.domanski.myClinic.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ClinicRepository {

    private ClinicDao clinicDao;
    private LiveData<List<Patient>> allPatients;
    private LiveData<List<Doctor>> allDoctors;
    private LiveData<List<Visit>> allVisits;
    private Patient patient;
    private PatientAddress patientAddress;
    private PatientContact patientContact;
    private Doctor doctor;
    private Visit visit;


    public ClinicRepository(@NonNull Application application) {
        ClinicDatabase clinicDatabase = ClinicDatabase.getInstance(application);
        clinicDao = clinicDatabase.clinicDao();
        allPatients = clinicDao.getAllPatients();
        allDoctors = clinicDao.getAllDoctors();
        allVisits = clinicDao.getAllVisits();

    }

    public void insertPatient(Patient patient, PatientAddress address, PatientContact contact) {
        new Thread(() -> {
            long patientId = clinicDao.insertPatient(patient);
            address.setPatientId((int) patientId);
            contact.setPatientId((int) patientId);
            clinicDao.insertPatientAddress(address);
            clinicDao.insertPatientContact(contact);
        }).start();
    }

    public void updatePatient(Patient patient) {
        new UpdatePatientAsyncTask(clinicDao).execute(patient);
    }

    public void deletePatient(Patient patient) {
        new DeletePatientAsyncTask(clinicDao).execute(patient);
    }

    public void deleteAllPatients() {
        new DeleteAllPatientsAsyncTask(clinicDao).execute();
    }

    public LiveData<List<Patient>> getAllPatients() {
        return allPatients;
    }

    public void getPatientById(final int id, OnPatientLoaded callback) {
        new Thread(() -> {
            patient = clinicDao.getPatientById(id);
            callback.loaded(patient);
        }).start();
    }

    public interface OnPatientLoaded {
        void loaded(Patient patient);
    }


    public void updatePatientAddress(PatientAddress patientAddress) {
        new UpdatePatientAddressAsyncTask(clinicDao).execute(patientAddress);
    }

    public void deletePatientAddress(PatientAddress patientAddress) {
        new DeletePatientAddressAsyncTask(clinicDao).execute(patientAddress);
    }

    public void getPatientAddressByPatientId(final int id, OnPatientAddressLoaded callback) {
        new Thread(() -> {
            patientAddress = clinicDao.getPatientAddressByPatientId(id);
            callback.loaded(patientAddress);
        }).start();
    }

    public interface OnPatientAddressLoaded {
        void loaded(PatientAddress patientAddress);
    }


    public void updatePatientContact(PatientContact patientContact) {
        new UpdatePatientContactAsyncTask(clinicDao).execute(patientContact);
    }

    public void deletePatientContact(PatientContact patientContact) {
        new DeletePatientContactAsyncTask(clinicDao).execute(patientContact);
    }

    public void getPatientContactByPatientId(final int id, OnPatientContactLoaded callback) {
        new Thread(() -> {
            patientContact = clinicDao.getPatientContactByPatientId(id);
            callback.loaded(patientContact);
        }).start();
    }

    public interface OnPatientContactLoaded {
        void loaded(PatientContact patientContact);
    }


    public void insertDoctor(Doctor doctor) {
        new InsertDoctorAsyncTask(clinicDao).execute(doctor);
    }

    public void updateDoctor(Doctor doctor) {
        new UpdateDoctorAsyncTask(clinicDao).execute(doctor);
    }

    public void deleteDoctor(Doctor doctor) {
        new DeleteDoctorAsyncTask(clinicDao).execute(doctor);
    }

    public void deleteAllDoctors() {
        new DeleteAllDoctorsAsyncTask(clinicDao).execute();
    }

    public LiveData<List<Doctor>> getAllDoctors() {
        return allDoctors;
    }

    public void getDoctorById(final int id, OnDoctorLoaded callback) {
        new Thread(() -> {
            doctor = clinicDao.getDoctorById(id);
            callback.loaded(doctor);
        }).start();
    }

    public interface OnDoctorLoaded {
        void loaded(Doctor doctor);
    }


    public void insertVisit(Visit visit, OnVisitExecuted onVisitExecuted) {
        new Thread(() -> {
            List<Visit> checkIfVisitExsistInTime = clinicDao.checkIfVisitExistInTime(visit.getDate(), visit.getTimeStart(), visit.getTimeEnd(), visit.getDoctorId());
            if (checkIfVisitExsistInTime.isEmpty()) {
                clinicDao.insertVisit(visit);
                onVisitExecuted.executed(true);
            } else {
                onVisitExecuted.executed(false);
            }
        }).start();
    }

    public interface OnVisitExecuted {
        void executed(boolean executed);
    }

    public void updateVisit(Visit visit, OnVisitExecuted onVisitExecuted) {
        new Thread(() -> {
            List<Visit> checkIfVisitExsistInTime = clinicDao.checkIfVisitExistInTime(visit.getDate(), visit.getTimeStart(), visit.getTimeEnd(), visit.getDoctorId());
            if (checkIfVisitExsistInTime.isEmpty()) {
                clinicDao.updateVisit(visit);
                onVisitExecuted.executed(true);
            } else {
                onVisitExecuted.executed(false);
            }
        }).start();
    }

    public void deleteVisit(Visit visit) {
        new DeleteVisitAsyncTask(clinicDao).execute(visit);
    }

    public void deleteAllVisits() {
        new DeleteAllVisitsAsyncTask(clinicDao).execute();
    }

    public LiveData<List<Visit>> getAllVisits() {
        return allVisits;
    }

    public void getVisitById(final int id, OnVisitLoaded callback) {
        new Thread(() -> {
            visit = clinicDao.getVisitById(id);
            callback.loaded(visit);
        }).start();
    }

    public interface OnVisitLoaded {
        void loaded(Visit visit);
    }

    public LiveData<List<Visit>> getVisitsByPatientId(int id) {
        return clinicDao.getVisitsByPatientId(id);
    }

    public LiveData<List<Visit>> getVisitsByDoctorId(int id) {
        return clinicDao.getVisitsByDoctorId(id);
    }

    public LiveData<List<Visit>> getVisitsByDate(long date) {
        return clinicDao.getVisitsByDate(date);
    }

    private static class UpdatePatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private ClinicDao clinicDao;

        private UpdatePatientAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            clinicDao.updatePatient(patients[0]);
            return null;
        }
    }

    private static class DeletePatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private ClinicDao clinicDao;

        private DeletePatientAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            clinicDao.deletePatient(patients[0]);
            return null;
        }
    }

    private static class DeleteAllPatientsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClinicDao clinicDao;

        private DeleteAllPatientsAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clinicDao.deleteAllPatients();
            return null;
        }
    }


    private static class UpdatePatientAddressAsyncTask extends AsyncTask<PatientAddress, Void, Void> {
        private ClinicDao clinicDao;

        private UpdatePatientAddressAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(PatientAddress... patientAddresses) {
            clinicDao.updatePatientAddress(patientAddresses[0]);
            return null;
        }
    }

    private static class DeletePatientAddressAsyncTask extends AsyncTask<PatientAddress, Void, Void> {
        private ClinicDao clinicDao;

        private DeletePatientAddressAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(PatientAddress... patientAddresses) {
            clinicDao.deletePatientAddress(patientAddresses[0]);
            return null;
        }
    }

    private static class UpdatePatientContactAsyncTask extends AsyncTask<PatientContact, Void, Void> {
        private ClinicDao clinicDao;

        private UpdatePatientContactAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(PatientContact... patientContacts) {
            clinicDao.updatePatientContact(patientContacts[0]);
            return null;
        }
    }

    private static class DeletePatientContactAsyncTask extends AsyncTask<PatientContact, Void, Void> {
        private ClinicDao clinicDao;

        private DeletePatientContactAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(PatientContact... patientContacts) {
            clinicDao.deletePatientContact(patientContacts[0]);
            return null;
        }
    }

    private static class InsertDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private ClinicDao clinicDao;

        private InsertDoctorAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Doctor... doctors) {
            clinicDao.insertDoctor(doctors[0]);
            return null;
        }
    }

    private static class UpdateDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private ClinicDao clinicDao;

        private UpdateDoctorAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Doctor... doctors) {
            clinicDao.updateDoctor(doctors[0]);
            return null;
        }
    }

    private static class DeleteDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private ClinicDao clinicDao;

        private DeleteDoctorAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Doctor... doctors) {
            clinicDao.deleteDoctor(doctors[0]);
            return null;
        }
    }

    private static class DeleteAllDoctorsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClinicDao clinicDao;

        private DeleteAllDoctorsAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clinicDao.deleteAllDoctors();
            return null;
        }
    }


    private static class DeleteVisitAsyncTask extends AsyncTask<Visit, Void, Void> {
        private ClinicDao clinicDao;

        private DeleteVisitAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Visit... visits) {
            clinicDao.deleteVisit(visits[0]);
            return null;
        }
    }


    private static class DeleteAllVisitsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClinicDao clinicDao;

        private DeleteAllVisitsAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clinicDao.deleteAllVisits();
            return null;
        }
    }


}
