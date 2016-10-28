package com.example.vladimir.students;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vladimir on 31-Aug-16.
 */
public class Student implements Parcelable {
    String Name;
    Integer Age;
    String School;

    public String getName() {
        return Name;
    }

    public Integer getAge() {
        return Age;
    }

    public String getSchool() {
        return School;
    }

    protected Student(String name, Integer age, String school) {
        Name = name;
        Age = age;
        School = school;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeValue(this.Age);
        dest.writeString(this.School);
    }

    protected Student(Parcel in) {
        this.Name = in.readString();
        this.Age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.School = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
