package gil.mota.visitme.visitmesecurity.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import gil.mota.visitme.visitmesecurity.models.User;

/**
 * Created by Slaush on 23/06/2017.
 */

public class Functions {

    public static void showConfirmDialog(Context context, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Estas Seguro?")
                .setCancelable(false)
                .setPositiveButton("Si", okListener)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAskDialog(Context context, String title, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(title)
                .setCancelable(false)
                .setPositiveButton("Si", okListener)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String[] convertToUsersIdsArray(List<User> users) {
        String[] ids = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            ids[i] = users.get(i).get_id();
        }
        return ids;
    }


    public static JSONArray concatJSONArray(JSONArray... arrs)
            throws JSONException {
        JSONArray result = new JSONArray();
        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
        }
        return result;
    }

    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }


    public static String formatDate(String dateStr) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dateStr);
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            try {
                date = new SimpleDateFormat("dd/MM/yyyy-HH:mm").parse(dateStr);
                return new SimpleDateFormat("dd/MM/yyyy").format(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

        }
        return "";
    }


    public static <T> T parse(JSONObject obj, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), type);
    }

    public static <T> T parse(JSONArray obj, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), type);
    }

    public static JSONObject toJSON(Object obj) throws JSONException {
        Gson gson = new Gson();
        return new JSONObject(gson.toJson(obj));
    }

    public static String intHourToStr(int hour) {
        StringBuilder str = new StringBuilder(Integer.toString(hour));
        if (str.length() < 4)
            fillWithZeroes(str, 4);
        return str.substring(0, 2) + ":" + str.substring(2);
    }

    private static StringBuilder fillWithZeroes(StringBuilder str, int i) {
        while (str.length() != i)
            str.insert(0, "0");
        return str;
    }
}

