package group.tonight.healthmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import group.tonight.healthmanagement.model.UserBean;

public class PedometerActivity extends AppCompatActivity {

    private UserBean mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        if (getIntent().hasExtra(LoginActivity.EXTRA_USER)) {
            mUser = (UserBean) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_pedometer_activity_bottom_menu, null);
        dialog.setContentView(inflate);
        dialog.show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                if (mUser == null) {
                    return;
                }
                Intent addIntent = new Intent(this, AddHealthInfoActivity.class);
                addIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(addIntent);
                break;
            case R.id.query:
                if (mUser == null) {
                    return;
                }
                Intent queryIntent = new Intent(this, QueryHealthInfoActivity.class);
                queryIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(queryIntent);
                break;
            case R.id.pedometer:
                if (mUser == null) {
                    return;
                }
                Intent pedometerIntent = new Intent(this, PedometerActivity.class);
                pedometerIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(pedometerIntent);
                break;
            case R.id.history:
                if (mUser == null) {
                    return;
                }
                Intent walkingHistoryIntent = new Intent(this, WalkingHistoryActivity.class);
                walkingHistoryIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(walkingHistoryIntent);
                break;
            case R.id.setting:
                if (mUser == null) {
                    return;
                }
                Intent targetSettingIntent = new Intent(this, TargetSettingActivity.class);
                targetSettingIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(targetSettingIntent);
                break;
            case R.id.progress:
                if (mUser == null) {
                    return;
                }
                Intent targetProgressIntent = new Intent(this, TargetProgressActivity.class);
                targetProgressIntent.putExtra(LoginActivity.EXTRA_USER, mUser);
                startActivity(targetProgressIntent);
                break;
            case R.id.exit:
                finish();
                break;
            default:
                break;
        }
    }
}
