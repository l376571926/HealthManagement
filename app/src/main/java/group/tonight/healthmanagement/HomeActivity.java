package group.tonight.healthmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import group.tonight.healthmanagement.model.UserBean;

/**
 * 首页
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mWelcomeTextView;
    private UserBean mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mWelcomeTextView = (TextView) findViewById(R.id.welcome_text);

        findViewById(R.id.insert).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);
        findViewById(R.id.pedometer).setOnClickListener(this);

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUser = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);
            if (mUser != null) {
                mWelcomeTextView.setText(getString(R.string.activity_home_text_1, mUser.getUserName()));
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_home_activity_bottom_menu, null);
        inflate.findViewById(R.id.history).setOnClickListener(this);
        inflate.findViewById(R.id.setting).setOnClickListener(this);
        inflate.findViewById(R.id.progress).setOnClickListener(this);
        inflate.findViewById(R.id.log_out).setOnClickListener(this);
        inflate.findViewById(R.id.exit).setOnClickListener(this);
        dialog.setContentView(inflate);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                Intent addIntent = new Intent(this, AddHealthInfoActivity.class);
                addIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(addIntent);
                break;
            case R.id.query:
                Intent queryIntent = new Intent(this, QueryHealthInfoActivity.class);
                queryIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(queryIntent);
                break;
            case R.id.pedometer:
                Intent pedometerIntent = new Intent(this, PedometerActivity.class);
                pedometerIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(pedometerIntent);
                break;
            case R.id.history:
                Toast.makeText(this, "步行历史", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "目标设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress:
                Toast.makeText(this, "目标完成度", Toast.LENGTH_SHORT).show();
                break;
            case R.id.log_out:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                preferences.edit().remove(LoginActivity.PREF_USER_ID).apply();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.exit:
                finish();
                break;
            default:
                break;
        }
    }
}
