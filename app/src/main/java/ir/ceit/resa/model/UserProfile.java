package ir.ceit.resa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private ERole role;

    public UserProfile(String username, String email, String firstName, String lastName, ERole role) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public UserProfile(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);
        this.username = data[0];
        this.email = data[1];
        this.firstName = data[2];
        this.lastName = data[3];
        this.role = ERole.valueOf(data[4]);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]
                {
                        this.username,
                        this.email,
                        this.firstName,
                        this.lastName,
                        String.valueOf(this.role)});
    }
}
