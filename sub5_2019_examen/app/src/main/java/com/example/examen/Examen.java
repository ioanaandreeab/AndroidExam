package com.example.examen;

import android.os.Parcel;
import android.os.Parcelable;

public class Examen implements Parcelable {
    private int id;
    private String denumireMaterie;
    private int numarStudenti;
    private String sala;
    private String supraveghetor;

    public Examen(int id, String denumireMaterie, int numarStudenti, String sala, String supraveghetor) {
        this.id = id;
        this.denumireMaterie = denumireMaterie;
        this.numarStudenti = numarStudenti;
        this.sala = sala;
        this.supraveghetor = supraveghetor;
    }

    protected Examen(Parcel in) {
        id = in.readInt();
        denumireMaterie = in.readString();
        numarStudenti = in.readInt();
        sala = in.readString();
        supraveghetor = in.readString();
    }

    public static final Creator<Examen> CREATOR = new Creator<Examen>() {
        @Override
        public Examen createFromParcel(Parcel in) {
            return new Examen(in);
        }

        @Override
        public Examen[] newArray(int size) {
            return new Examen[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumireMaterie() {
        return denumireMaterie;
    }

    public void setDenumireMaterie(String denumireMaterie) {
        this.denumireMaterie = denumireMaterie;
    }

    public int getNumarStudenti() {
        return numarStudenti;
    }

    public void setNumarStudenti(int numarStudenti) {
        this.numarStudenti = numarStudenti;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getSupraveghetor() {
        return supraveghetor;
    }

    public void setSupraveghetor(String supraveghetor) {
        this.supraveghetor = supraveghetor;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "id=" + id +
                ", denumireMaterie='" + denumireMaterie + '\'' +
                ", numarStudenti=" + numarStudenti +
                ", sala='" + sala + '\'' +
                ", supraveghetor='" + supraveghetor + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(denumireMaterie);
        dest.writeInt(numarStudenti);
        dest.writeString(sala);
        dest.writeString(supraveghetor);
    }
}
