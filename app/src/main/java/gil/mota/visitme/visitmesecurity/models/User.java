package gil.mota.visitme.visitmesecurity.models;

import android.os.Parcel;
import android.os.Parcelable;

import gil.mota.visitme.visitmesecurity.managers.RequestManager;

/**
 * Created by mota on 12/4/2018.
 */

public class User implements Parcelable{

    private String _id;
    private String email;
    private String name;
    private String identification;
    private String password;
    private String image;
    private String homePhone;
    private String cellPhone;

    protected User(Parcel in) {
        _id = in.readString();
        email = in.readString();
        name = in.readString();
        identification = in.readString();
        password = in.readString();
        image = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getHomePhone() { return homePhone; }

    public void setHomePhone(String homePhone) { this.homePhone = homePhone; }

    public String getCellPhone() { return cellPhone; }

    public void setCellPhone(String cellPhone) { this.cellPhone = cellPhone; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (_id != null ? !_id.equals(user._id) : user._id != null) return false;
        return identification != null ? identification.equals(user.identification) : user.identification == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (identification != null ? identification.hashCode() : 0);
        return result;
    }

    public String getImage() {
        return RequestManager.getInstance().getUrl() + "/" + image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(identification);
        parcel.writeString(password);
        parcel.writeString(image);
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", identification='" + identification + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                '}';
    }
}

