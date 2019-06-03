package com.example.festivalapptest.Wallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.festivalapptest.R;

public class WalletActivity extends AppCompatActivity {

    // 1 coin is €2,50

    public Bundle bundle;
    public int credit;
    public Button topUpButton;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        bundle = new Bundle();
        bundle.putInt("credit", credit);
        topUpButton = findViewById(R.id.top_up_button);

        // Open TopUp screen
        goToTopUp();

    }

    // Method to open the Top Up screen
    private void goToTopUp(){
        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), WalletAddMoney.class);
                startActivity(intent);
            }
        });
    }
}
