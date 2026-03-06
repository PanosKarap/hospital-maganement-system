CREATE DATABASE hospital;

USE hospital;

CREATE TABLE Users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(64) NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    salt VARCHAR(32) NOT NULL
);

CREATE TABLE Doctor (
    username VARCHAR(50) PRIMARY KEY,
    specialty VARCHAR(50),
    FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
);

CREATE TABLE Patient (
    username VARCHAR(50) PRIMARY KEY,
    amka CHAR(11) UNIQUE NOT NULL ,
    FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
);

CREATE TABLE Admin (
    username VARCHAR(50) PRIMARY KEY,
    FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
);

CREATE TABLE Appointment (
    doctor_username VARCHAR(50) NOT NULL,
    patient_username VARCHAR(50) NOT NULL,
    appointment_date DATE NOT NULL,
    PRIMARY KEY (doctor_username, patient_username, appointment_date),
    FOREIGN KEY (doctor_username) REFERENCES Doctor(username) ON DELETE CASCADE,
    FOREIGN KEY (patient_username) REFERENCES Patient(username) ON DELETE CASCADE
);

-- creating user (for mysql)
CREATE USER 'dbuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON hospital.* TO 'dbuser'@'localhost';
FLUSH PRIVILEGES;
