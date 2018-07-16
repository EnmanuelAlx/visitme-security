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
        if (jsonObject.has("status"))
            return new Error(ErrorType.CUSTOM,jsonObject.optString("name"), jsonObject.optInt("status"));
        else
            return new Throwable("Error Desconocido");
    }

    public class Error extends Throwable
    {
        ErrorType type;
        int status;
        public Error(ErrorType type, String message, int status)
        {
            super(message);
            this.type = type;
            this.status = status;
        }

        public ErrorType getType()
        {
            return type;
        }

        public int getStatus() {
            return status;
        }
    }
}
