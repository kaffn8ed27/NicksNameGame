package android.example.nicksnamegame.game;

import androidx.appcompat.app.AppCompatActivity;

import android.example.nicksnamegame.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GameActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.photo_1:
                Log.d("Photo clicked: ", getString(R.string.photo_label_1));
                findViewById(R.id.photo_label_1).setVisibility(View.VISIBLE);
                v.setBackground(getDrawable(R.drawable.chose_poorly));
                break;

            case R.id.photo_2:
                Log.d("Photo clicked: ", getString(R.string.photo_label_1));
                findViewById(R.id.photo_label_2).setVisibility(View.VISIBLE);
                v.setBackground(getDrawable(R.drawable.chose_poorly));
                break;

            case R.id.photo_3:
                Log.d("Photo clicked: ", getString(R.string.photo_label_1));
                findViewById(R.id.photo_label_3).setVisibility(View.VISIBLE);
                v.setBackground(getDrawable(R.drawable.chose_poorly));
                break;

            case R.id.photo_4:
                Log.d("Photo clicked: ", getString(R.string.photo_label_1));
                findViewById(R.id.photo_label_4).setVisibility(View.VISIBLE);
                v.setBackground(getDrawable(R.drawable.chose_wisely));
                break;

            case R.id.photo_5:
                Log.d("Photo clicked: ", getString(R.string.photo_label_1));
                findViewById(R.id.photo_label_5).setVisibility(View.VISIBLE);
                v.setBackground(getDrawable(R.drawable.chose_poorly));
                break;

            default:
                break;
        }
    }
}
