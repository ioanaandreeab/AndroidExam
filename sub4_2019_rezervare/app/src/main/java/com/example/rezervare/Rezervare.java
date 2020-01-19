package com.example.rezervare;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "rezervari")
public class Rezervare implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "idRezervare")
    private int idRezervare;
    @ColumnInfo(name = "numeClient")
    private String numeClient;
    @ColumnInfo(name = "tipCamera")
    private String tipCamera;
    @ColumnInfo(name = "durataSejur")
    private int durataSejur;
    @ColumnInfo(name="sumaPlata")
    private int sumaPlata;
    @ColumnInfo(name = "dataCazare")
    private Date dataCazare;

    public Rezervare(int idRezervare, String numeClient, String tipCamera, int durataSejur, int sumaPlata, Date dataCazare) {
        this.idRezervare = idRezervare;
        this.numeClient = numeClient;
        this.tipCamera = tipCamera;
        this.durataSejur = durataSejur;
        this.sumaPlata = sumaPlata;
        this.dataCazare = dataCazare;
    }

    protected Rezervare(Parcel in) {
        idRezervare = in.readInt();
        numeClient = in.readString();
        tipCamera = in.readString();
        durataSejur = in.readInt();
        sumaPlata = in.readInt();
        dataCazare = new Date(in.readLong());
    }

    public static final Creator<Rezervare> CREATOR = new Creator<Rezervare>() {
        @Override
        public Rezervare createFromParcel(Parcel in) {
            return new Rezervare(in);
        }

        @Override
        public Rezervare[] newArray(int size) {
            return new Rezervare[size];
        }
    };

    public int getIdRezervare() {
        return idRezervare;
    }

    public void setIdRezervare(int idRezervare) {
        this.idRezervare = idRezervare;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public String getTipCamera() {
        return tipCamera;
    }

    public void setTipCamera(String tipCamera) {
        this.tipCamera = tipCamera;
    }

    public int getDurataSejur() {
        return durataSejur;
    }

    public void setDurataSejur(int durataSejur) {
        this.durataSejur = durataSejur;
    }

    public int getSumaPlata() {
        return sumaPlata;
    }

    public void setSumaPlata(int sumaPlata) {
        this.sumaPlata = sumaPlata;
    }

    public Date getDataCazare() {
        return dataCazare;
    }

    public void setDataCazare(Date dataCazare) {
        this.dataCazare = dataCazare;
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "idRezervare=" + idRezervare +
                ", numeClient='" + numeClient + '\'' +
                ", tipCamera='" + tipCamera + '\'' +
                ", durataSejur=" + durataSejur +
                ", sumaPlata=" + sumaPlata +
                ", dataCazare=" + dataCazare +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idRezervare);
        dest.writeString(numeClient);
        dest.writeString(tipCamera);
        dest.writeInt(durataSejur);
        dest.writeInt(sumaPlata);
        dest.writeLong(dataCazare.getTime());
    }
}
