package gil.mota.visitme.visitmesecurity.managers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import gil.mota.visitme.visitmesecurity.Consts;
import gil.mota.visitme.visitmesecurity.MyApplication;
import gil.mota.visitme.visitmesecurity.models.Community;
import gil.mota.visitme.visitmesecurity.models.User;
import gil.mota.visitme.visitmesecurity.utils.Functions;
import gil.mota.visitme.visitmesecurity.utils.PreferencesHelper;


/**
 * Created by Slaush on 22/05/2017.
 */

public class UserManager
{
    private static UserManager instance;

    private UserManager()
    {

    }

    public static UserManager getInstance()
    {
        if(instance == null )
            instance = new UserManager();
        return  instance;
    }


    public void saveUserCredentials(User user, String auth, String deviceID) throws JSONException {
        PreferencesHelper.writeString(MyApplication.getInstance(), Consts.USER, Functions.toJSON(user).toString());
        PreferencesHelper.writeString(MyApplication.getInstance(), Consts.AUTH, auth);
        PreferencesHelper.writeString(MyApplication.getInstance(), Consts.DEVICE_ID, deviceID);
    }

    public void saveUserCredentials(User user) throws JSONException {
        PreferencesHelper.writeString(MyApplication.getInstance(), Consts.USER, Functions.toJSON(user).toString());
    }



    public User getUser(Context context) throws JSONException {
        String jsonStr = PreferencesHelper.readString(context,Consts.USER,"");
        return Functions.parse(new JSONObject(jsonStr), User.class);
    }

    public String getAuth()
    {
        return PreferencesHelper.readString(MyApplication.getInstance(), Consts.AUTH,"");
    }

    public void logout()
    {
        PreferencesHelper.deleteKey(MyApplication.getInstance(),Consts.USERNAME);
        PreferencesHelper.deleteKey(MyApplication.getInstance(),Consts.COMMUNITIES);
        PreferencesHelper.deleteKey(MyApplication.getInstance(), Consts.DEFAULT_COMMUNITY);
        PreferencesHelper.deleteKey(MyApplication.getInstance(),Consts.PASSWORD);
        PreferencesHelper.deleteKey(MyApplication.getInstance(), Consts.USER_ID);
        PreferencesHelper.deleteKey(MyApplication.getInstance(), Consts.AUTH);
        PreferencesHelper.deleteKey(MyApplication.getInstance(), Consts.DEVICE_ID);
    }


    public void saveCommunities(JSONObject obj) {
        PreferencesHelper.writeString(MyApplication.getInstance(), Consts.COMMUNITIES, obj.toString());
    }

    public List<Community> getCommunities() throws JSONException {
        String jsonStr = PreferencesHelper.readString(MyApplication.getInstance(),Consts.COMMUNITIES,"");
        JSONObject obj = new JSONObject(jsonStr);
        JSONArray arry = obj.getJSONArray(Consts.COMMUNITIES);
        Community [] communities = Functions.parse(arry,Community[].class);
        return Arrays.asList(communities);
    }

    public String getDevice() {
        return PreferencesHelper.readString(MyApplication.getInstance(), Consts.DEVICE_ID,"");
    }

    public void setDefaultCommunity(Community community) throws JSONException {
        String str = Functions.toJSON(community).toString();
        PreferencesHelper.writeString(MyApplication.getInstance(), Consts.DEFAULT_COMMUNITY, str);
    }

    public Community getDefaultCommunity() throws JSONException {
        String jsonStr = PreferencesHelper.readString(MyApplication.getInstance(), Consts.DEFAULT_COMMUNITY, "");
        return Functions.parse(new JSONObject(jsonStr),Community.class);
    }
}
