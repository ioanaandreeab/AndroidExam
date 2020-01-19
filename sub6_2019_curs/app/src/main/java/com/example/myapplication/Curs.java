package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cursuri")
public class Curs implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name="idCurs")
    private int idCurs;
    @ColumnInfo(name="denumire")
    private String denumire;
    @ColumnInfo(name="numarParticipanti")
    private int numarParticipanti;
    @ColumnInfo(name="sala")
    private String sala;
    @ColumnInfo(name="profesorTitular")
    private String profesorTitular;

    public Curs(int idCurs, String denumire, int numarParticipanti, String sala, String profesorTitular) {
        this.idCurs = idCurs;
        this.denumire = denumire;
        this.numarParticipanti = numarParticipanti;
        this.sala = sala;
        this.profesorTitular = profesorTitular;
    }

    protected Curs(Parcel in) {
        idCurs = in.readInt();
        denumire = in.readString();
        numarParticipanti = in.readInt();
        sala = in.readString();
        profesorTitular = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCurs);
        dest.writeString(denumire);
        dest.writeInt(numarParticipanti);
        dest.writeString(sala);
        dest.writeString(profesorTitular);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Curs> CREATOR = new Creator<Curs>() {
        @Override
        public Curs createFromParcel(Parcel in) {
            return new Curs(in);
        }

        @Override
        public Curs[] newArray(int size) {
            return new Curs[size];
        }
    };

    public int getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(int idCurs) {
        this.idCurs = idCurs;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNumarParticipanti() {
        return numarParticipanti;
    }

    public void setNumarParticipanti(int numarParticipanti) {
        this.numarParticipanti = numarParticipanti;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getProfesorTitular() {
        return profesorTitular;
    }

    public void setProfesorTitular(String profesorTitular) {
        this.profesorTitular = profesorTitular;
    }

    @Override
    public String toString() {
        return "Curs{" +
                "idCurs=" + idCurs +
                ", denumire='" + denumire + '\'' +
                ", numarParticipanti=" + numarParticipanti +
                ", sala=" + sala +
                ", profesorTitular='" + profesorTitular + '\'' +
                '}';
    }
}
