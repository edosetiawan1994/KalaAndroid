package com.kala.kala;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SettingFragment extends ListFragment {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    UserSessionManager session;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Settings, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        //getListView().setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onListItemClick(ListView listview, View view, int position,long id) {
        super.onListItemClick(listview, view, position, id);
        if(position == 1){
            Toast.makeText(getActivity(), "Account Clicked !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity().getApplicationContext(),AccountActivity.class);
            startActivity(intent);
        }
        else if(position == 2) {
            Toast.makeText(getActivity(), "Logged Out !", Toast.LENGTH_SHORT).show();
            UserSessionManager session = new UserSessionManager(getActivity().getApplicationContext());
            session.logoutUser();
        }
        else{
            Toast.makeText(getActivity(), "NightMode Clicked !", Toast.LENGTH_SHORT).show();
        }
    }
}
