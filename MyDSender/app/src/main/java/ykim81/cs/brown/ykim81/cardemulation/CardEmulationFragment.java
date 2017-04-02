/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ykim81.cs.brown.ykim81.cardemulation;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

/**
 * Generic UI for sample discovery.
 */
public class CardEmulationFragment extends Fragment {

    public static final String TAG = "CardEmulationFragment";

    /** Called when sample is created. Displays generic UI with welcome text. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        EditText account = (EditText) v.findViewById(R.id.name_field);
        account.setText(AccountStorage.getName(getActivity()));
        account.addTextChangedListener(new AccountUpdater());

        Switch blind = (Switch) v.findViewById(R.id.blind_switch);
        blind.setChecked(AccountStorage.getBlind(getActivity()).equals("T"));
        blind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Switch blind = (Switch) v.findViewById(R.id.blind_switch);
              if (blind.isEnabled()) {
                AccountStorage.setBlind(getActivity(), "T");
              } else {
                AccountStorage.setBlind(getActivity(), "F");
              }
            }
        });

        Switch deaf = (Switch) v.findViewById(R.id.deaf_switch);
        deaf.setChecked(AccountStorage.getDeaf(getActivity()).equals("T"));
        deaf.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Switch deaf = (Switch) v.findViewById(R.id.deaf_switch);
            if (deaf.isEnabled()) {
              AccountStorage.setDeaf(getActivity(), "T");
            } else {
              AccountStorage.setDeaf(getActivity(), "F");
            }
          }
        });
        return v;
    }


    private class AccountUpdater implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not implemented.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not implemented.
        }

        @Override
        public void afterTextChanged(Editable s) {
            String account = s.toString();
            String[] words = account.split("¶");
            AccountStorage.setName(getActivity(), words[0]);
            AccountStorage.setBlind(getActivity(), words[1]);
            AccountStorage.setDeaf(getActivity(), words[2]);
        }
    }

}