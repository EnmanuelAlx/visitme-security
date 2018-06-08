package gil.mota.visitme.visitmesecurity.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import gil.mota.visitme.visitmesecurity.R;
import gil.mota.visitme.visitmesecurity.models.User;
import gil.mota.visitme.visitmesecurity.utils.Functions;
import gil.mota.visitme.visitmesecurity.utils.Pnotify;

public class VisitAccessActivity extends AppCompatActivity implements View.OnClickListener {

    boolean access;
    LinearLayout guestLayout, residentLayout;
    TextView accessText, residentName, guestName, residentDetail, guestIdentification;
    Button accept;
    ImageView residentImage, guestImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_visit_success);
        String data = getIntent().getStringExtra("data");

        Log.i("UNEXPECTED!", "DATA:" + data);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        fillViews();
        fillData(data);

    }

    private void fillViews() {
        accessText = findViewById(R.id.access_text);
        guestLayout = findViewById(R.id.guest_layout);
        residentLayout = findViewById(R.id.resident_layout);
        residentName = findViewById(R.id.residentName);
        residentDetail = findViewById(R.id.residentIdentification);
        guestName = findViewById(R.id.guestName);
        guestIdentification = findViewById(R.id.guestIdentification);
        residentImage = findViewById(R.id.residentImage);
        guestImage = findViewById(R.id.guestImage);
        accept = findViewById(R.id.aceptar);
        accept.setOnClickListener(this);
    }

    private void fillData(String data) {
        try {

            JSONObject obj = new JSONObject(data).getJSONObject("data");
            User resident = Functions.parse(obj.getJSONObject("visit").getJSONObject("resident"), User.class);
            User guest = Functions.parse(obj.getJSONObject("visit").getJSONObject("guest"), User.class);
            if (!obj.getBoolean("access"))
                accessText.setText("Acceso no concedido a");

            residentName.setText(resident.getName());
            residentDetail.setText(resident.getIdentification());
            guestName.setText(guest.getName());
            guestIdentification.setText(guest.getIdentification());

            Glide.with(this).load(resident.getImage()).placeholder(R.drawable.guy).error(R.drawable.guy).into(residentImage);
            Glide.with(this).load(guest.getImage()).placeholder(R.drawable.guy).error(R.drawable.guy).into(guestImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        Pnotify.makeText(this, "Accion Exitosa", Toast.LENGTH_SHORT, Pnotify.INFO).show();
        finish();
    }
}
