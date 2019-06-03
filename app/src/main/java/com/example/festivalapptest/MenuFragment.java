package com.example.festivalapptest;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.festivalapptest.Checklist.ChecklistActivity;
import com.example.festivalapptest.Wallet.WalletActivity;

public class MenuFragment extends Fragment {

    private View v;

    ImageButton walletButton;
    ImageButton checklistButton;
    Intent intent;
    Intent setIntent;

    @Nullable
    @Override
    // Opens the view created in the layout file, which is called in the parameter of the inflater
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_menu, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        walletButton = (ImageButton) v.findViewById(R.id.wallet_button);
        walletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent);
            }
        });

        checklistButton = (ImageButton) v.findViewById(R.id.checklist_button);
        checklistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ChecklistActivity.class);
                startActivity(intent);
            }
        });


    }

}
