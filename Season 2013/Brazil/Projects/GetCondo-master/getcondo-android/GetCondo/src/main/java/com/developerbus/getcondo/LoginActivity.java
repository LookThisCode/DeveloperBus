package com.developerbus.getcondo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.developerbus.getcondo.models.Issue;
import com.developerbus.getcondo.models.Session;
import com.developerbus.getcondo.utils.GsonRequest;
import com.developerbus.getcondo.utils.Settings;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().hide();
        if(Settings.getSession() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        EditText mEditTextEmail;
        EditText mEditTextPassword;
        RequestQueue mRequestQueue;
        Button mButtonCreateSession;
        View mRootView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.fragment_login, container, false);
            initializeGUI();
            return mRootView;
        }


        private void initializeGUI(){
            mEditTextEmail = (EditText) mRootView.findViewById(R.id.textFieldLoginEmail);
            mEditTextPassword = (EditText) mRootView.findViewById(R.id.textFieldLoginPassword);
            mButtonCreateSession = (Button) mRootView.findViewById(R.id.button_login);
            mButtonCreateSession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn(view);
                }
            });
        }

        public void signIn(View view) {
            mRequestQueue = Volley.newRequestQueue(getActivity());
            final String email = mEditTextEmail.getText().toString();
            final String password = mEditTextPassword.getText().toString();

            String url = Settings.apiHostname() + "/sessions";
            GsonRequest request = new GsonRequest(Request.Method.POST, url, Session.class, createSessionSuccessListener(), createSessionErrorListener()) {
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("password", password);
                    params.put("email", email);
                    return params;
                };
            };
            mRequestQueue.add(request);
        }

        private Response.Listener createSessionSuccessListener() {
            return new Response.Listener<Session>() {
                @Override
                public void onResponse(Session response) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    Settings.setSession(response);
                    getActivity().getActionBar().show();
                    startActivity(intent);
                }
            };
        }

        private Response.ErrorListener createSessionErrorListener() {
            return new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Senha ou email inválidos =(", Toast.LENGTH_SHORT).show();
                }
            };
        }
    }



}
