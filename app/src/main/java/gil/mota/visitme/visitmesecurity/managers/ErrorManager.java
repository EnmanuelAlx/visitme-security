package gil.mota.visitme.visitmesecurity.managers;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by user on 23/05/2017.
 */

public class ErrorManager
{
    public enum ErrorType
    {
        NETWORK,
        UNKNOWN,
        CUSTOM
    }
    private static ErrorManager instance;

    private ErrorManager()
    {

    }

    public static ErrorManager getInstance()
    {
        if(instance == null)
            instance = new ErrorManager();
        return instance;
    }

    public Throwable getError(JSONObject jsonObject)
    {
        if (jsonObject.has("error"))
            return new Error(ErrorType.CUSTOM,jsonObject.optString("error"));
        else
            return new Throwable("Error Desconocido");
    }

    public class Error extends Throwable
    {
        ErrorType type;
        public Error(ErrorType type, String message)
        {
            super(message);
            this.type = type;
        }

        public ErrorType getType()
        {
            return type;
        }
    }
}
