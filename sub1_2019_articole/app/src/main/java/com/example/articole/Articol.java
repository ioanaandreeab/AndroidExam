package com.example.articole;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "articole")
public class Articol implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name="titlu")
    private String titlu;
    @ColumnInfo(name = "primaPagina")
    private int primaPagina;
    @ColumnInfo(name = "ultimaPagina")
    private int ultimaPagina;
    @ColumnInfo(name = "numarAutori")
    private int numarAutori;

    public Articol(int id, String titlu, int primaPagina, int ultimaPagina, int numarAutori) {
        this.id = id;
        this.titlu = titlu;
        this.primaPagina = primaPagina;
        this.ultimaPagina = ultimaPagina;
        this.numarAutori = numarAutori;
    }

    @Ignore
    public Articol(String titlu, int primaPagina, int ultimaPagina, int numarAutori) {
        this.titlu = titlu;
        this.primaPagina = primaPagina;
        this.ultimaPagina = ultimaPagina;
        this.numarAutori = numarAutori;
    }

    protected Articol(Parcel in) {
        titlu = in.readString();
        primaPagina = in.readInt();
        ultimaPagina = in.readInt();
        numarAutori = in.readInt();
    }

    public static final Creator<Articol> CREATOR = new Creator<Articol>() {
        @Override
        public Articol createFromParcel(Parcel in) {
            return new Articol(in);
        }

        @Override
        public Articol[] newArray(int size) {
            return new Articol[size];
        }
    };

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public int getPrimaPagina() {
        return primaPagina;
    }

    public void setPrimaPagina(int primaPagina) {
        this.primaPagina = primaPagina;
    }

    public int getUltimaPagina() {
        return ultimaPagina;
    }

    public void setUltimaPagina(int ultimaPagina) {
        this.ultimaPagina = ultimaPagina;
    }

    public int getNumarAutori() {
        return numarAutori;
    }

    public void setNumarAutori(int numarAutori) {
        this.numarAutori = numarAutori;
    }

    @Override
    public String toString() {
        return "Articol{" +
                "titlu='" + titlu + '\'' +
                ", primaPagina=" + primaPagina +
                ", ultimaPagina=" + ultimaPagina +
                ", numarAutori=" + numarAutori +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titlu);
        dest.writeInt(primaPagina);
        dest.writeInt(ultimaPagina);
        dest.writeInt(numarAutori);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
