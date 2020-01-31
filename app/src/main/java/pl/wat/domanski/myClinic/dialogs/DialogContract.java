package pl.wat.domanski.myClinic.dialogs;

import pl.wat.domanski.myClinic.database.Doctor;
import pl.wat.domanski.myClinic.database.Patient;

public interface DialogContract {

    void sendPatientData(Patient patient);

    void sendDoctorData(Doctor doctor);
}
