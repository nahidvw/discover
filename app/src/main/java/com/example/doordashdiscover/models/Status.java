package com.example.doordashdiscover.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Status implements Parcelable {
    private int[] asap_minutes_range;
    private boolean asap_available;

    public Status() {
    }

    public Status(int[] asap_minutes_range, boolean asap_available) {
        this.asap_minutes_range = asap_minutes_range;
        this.asap_available = asap_available;
    }

    protected Status(Parcel in) {
        asap_minutes_range = in.createIntArray();
        asap_available = in.readByte() != 0;
    }

    public int[] getAsap_minutes_range() {
        return asap_minutes_range;
    }

    public void setAsap_minutes_range(int[] asap_minutes_range) {
        this.asap_minutes_range = asap_minutes_range;
    }

    public boolean isAsap_available() {
        return asap_available;
    }

    public void setAsap_available(boolean asap_available) {
        this.asap_available = asap_available;
    }

    @Override
    public String toString() {
        return "Status{" +
                "asap_minutes_range=" + Arrays.toString(asap_minutes_range) +
                ", asap_available=" + asap_available +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(asap_minutes_range);
        dest.writeByte((byte) (asap_available ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
}