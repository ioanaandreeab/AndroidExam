package com.example.jucator;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "jucatori")
public class Jucator implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;
    @ColumnInfo(name="numar")
    private int numar;
    @ColumnInfo(name="nume")
    private String nume;
    @ColumnInfo(name="dataNasterii")
    private Date dataNasterii;
    @ColumnInfo(name = "pozitie")
    private String pozitie;

    public Jucator(int id, int numar, String nume, Date dataNasterii, String pozitie) {
        this.id = id;
        this.numar = numar;
        this.nume = nume;
        this.dataNasterii = dataNasterii;
        this.pozitie = pozitie;
    }

    @Ignore
    public Jucator(int numar, String nume, Date dataNasterii, String pozitie) {
        this.numar = numar;
        this.nume = nume;
        this.dataNasterii = dataNasterii;
        this.pozitie = pozitie;
    }

    protected Jucator(Parcel in) {
        numar = in.readInt();
        nume = in.readString();
        pozitie = in.readString();
        dataNasterii = new Date(in.readLong());
    }

    public static final Creator<Jucator> CREATOR = new Creator<Jucator>() {
        @Override
        public Jucator createFromParcel(Parcel in) {
            return new Jucator(in);
        }

        @Override
        public Jucator[] newArray(int size) {
            return new Jucator[size];
        }
    };

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Date getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Date dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public String getPozitie() {
        return pozitie;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numar);
        dest.writeString(nume);
        dest.writeString(pozitie);
        dest.writeLong(dataNasterii.getTime());
    }

    @Override
    public String toString() {
        return "Jucator{" +
                "numar=" + numar +
                ", nume='" + nume + '\'' +
                ", dataNasterii=" + dataNasterii +
                ", pozitie='" + pozitie + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
