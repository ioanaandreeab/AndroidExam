package com.example.televizor;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "televizoare")
public class Televizor implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "nr_inventar")
    private int nr_inventar;
    @ColumnInfo(name = "producator")
    private String producator;
    @ColumnInfo(name = "diagonala")
    private float diagonala;
    @ColumnInfo(name = "proprietar")
    private String proprietar;
    @ColumnInfo(name = "data_intrarii")
    private Date data_intrarii;

    public Televizor(int nr_inventar, String producator, float diagonala, String proprietar, Date data_intrarii) {
        this.nr_inventar = nr_inventar;
        this.producator = producator;
        this.diagonala = diagonala;
        this.proprietar = proprietar;
        this.data_intrarii = data_intrarii;
    }

    protected Televizor(Parcel in) {
        nr_inventar = in.readInt();
        producator = in.readString();
        diagonala = in.readFloat();
        proprietar = in.readString();
        data_intrarii = new Date(in.readLong());
    }

    public static final Creator<Televizor> CREATOR = new Creator<Televizor>() {
        @Override
        public Televizor createFromParcel(Parcel in) {
            return new Televizor(in);
        }

        @Override
        public Televizor[] newArray(int size) {
            return new Televizor[size];
        }
    };

    public int getNr_inventar() {
        return nr_inventar;
    }

    public void setNr_inventar(int nr_inventar) {
        this.nr_inventar = nr_inventar;
    }

    public String getProducator() {
        return producator;
    }

    public void setProducator(String producator) {
        this.producator = producator;
    }

    public float getDiagonala() {
        return diagonala;
    }

    public void setDiagonala(float diagonala) {
        this.diagonala = diagonala;
    }

    public String getProprietar() {
        return proprietar;
    }

    public void setProprietar(String proprietar) {
        this.proprietar = proprietar;
    }

    public Date getData_intrarii() {
        return data_intrarii;
    }

    public void setData_intrarii(Date data_intrarii) {
        this.data_intrarii = data_intrarii;
    }

    @Override
    public String toString() {
        return "Televizor{" +
                "nr_inventar=" + nr_inventar +
                ", producator='" + producator + '\'' +
                ", diagonala=" + diagonala +
                ", proprietar='" + proprietar + '\'' +
                ", data_intrarii=" + data_intrarii +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nr_inventar);
        dest.writeString(producator);
        dest.writeFloat(diagonala);
        dest.writeString(proprietar);
        dest.writeLong(data_intrarii.getTime());
    }
}
