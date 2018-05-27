package gil.mota.visitme.visitmesecurity.views.activities;

/**
 * Created by mota on 12/4/2018.
 */

import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Slaush on 18/06/2017.
 */

public abstract class BindeableActivity extends AppCompatActivity implements Observer
{

    public abstract  void update(Observable observable, Object o);
    public abstract void initDataBinding();
    void setupObserver(Observable observable)
    {
        observable.addObserver(this);
    }
}
