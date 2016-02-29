package com.globussoft.readydoctors.patient.model;

/**
 * Created by GLB-276 on 1/28/2016.
 */
public class VisitHistoryModel
{
    String patientName="";
    String patientAddress="";
    String patientDateOfBirth="";
    String appointmentId="";
    String doctorName="";
    String medicationsReport="";
    String instructions="";
    String appointment_start_time="";
    String appointment_end_time="";
    public String getAppointment_end_time() {
        return appointment_end_time;
    }

    public void setAppointment_end_time(String appointment_end_time) {
        this.appointment_end_time = appointment_end_time;
    }

    public String getAppointment_start_time() {
        return appointment_start_time;
    }

    public void setAppointment_start_time(String appointment_start_time) {
        this.appointment_start_time = appointment_start_time;
    }



    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    public void setPatientDateOfBirth(String patientDateOfBirth) {
        this.patientDateOfBirth = patientDateOfBirth;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMedicationsReport() {
        return medicationsReport;
    }

    public void setMedicationsReport(String medicationsReport) {
        this.medicationsReport = medicationsReport;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


}
