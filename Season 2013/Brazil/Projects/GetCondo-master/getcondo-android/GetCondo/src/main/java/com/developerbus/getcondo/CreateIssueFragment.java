package com.developerbus.getcondo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.InputMethodService;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.provider.MediaStore;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.developerbus.getcondo.models.Issue;
import com.developerbus.getcondo.models.NewsList;
import com.developerbus.getcondo.utils.GsonRequest;
import com.developerbus.getcondo.utils.Settings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.HashMap;

public class CreateIssueFragment extends Fragment {

    View mCurrentView;
    EditText mEditDescription;
    RequestQueue mRequestQueue;
    Button mButtonCreateIssue;
    Button mButtonImageFromGallery;
    String mEncodedImage;
    Boolean mSendingIssue = false;
    ProgressDialog mProgress;

    int RESULT_LOAD_IMAGE = 1;

    public CreateIssueFragment(RequestQueue requestQueue) {
        super();
        mRequestQueue = requestQueue;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        // Not working, even overriding onCreateOptionsMenu with an empty menu.
        setHasOptionsMenu(false);

        mCurrentView = (View) inflater.inflate(R.layout.fragment_create_issue, container, false);
        mRequestQueue = Volley.newRequestQueue(getActivity());

        initializeGUI(mCurrentView);
        return mCurrentView;
    }


    private void initializeGUI(View view) {
        mEditDescription = (EditText) view.findViewById(R.id.textFieldIssueDescription);
        mButtonCreateIssue = (Button) view.findViewById(R.id.buttonCreateIssue);
        mButtonImageFromGallery = (Button) view.findViewById(R.id.buttonImageFromGallery);
        mButtonCreateIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CLICK", "OI");
                if(mSendingIssue == false) {
                    mSendingIssue = true;
                    createIssue(view);
                }
            }
        });
        mButtonImageFromGallery.setOnClickListener(new View.OnClickListener() {
            Uri imageUri;

            public void onClick(View view) {
               Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bm = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress( Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            mEncodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        }


    }

    public void createIssue(View view) {
        mProgress = ProgressDialog.show(getActivity(), "Aguarde", "Enviando incidente");
        final Issue issue = new Issue();
        issue.setDescription(mEditDescription.getText().toString());
        String url = Settings.apiHostname() + "/condos/" + Settings.getSession().getCondoId() + "/issues?token=" + Settings.getSession().getToken();
        GsonRequest request = new GsonRequest(Request.Method.POST, url, null, createIssueSuccessListener(), createIssueErrorListener()) {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("description", issue.getDescription());
                if(mEncodedImage != null) {
                    params.put("image_data", mEncodedImage);
                }
                return params;
            };
        };
        mRequestQueue.add(request);
    }

    private Response.Listener createIssueSuccessListener() {
        mProgress.dismiss();
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Fragment issuesFragment = (Fragment) new IssuesFragment(mRequestQueue);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                String title = getString(R.string.title_problems);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditDescription.getWindowToken(), 0);
                mSendingIssue = false;
                getActivity().getActionBar().setTitle(title);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, issuesFragment)
                        .commit();
            }
        };
    }

    private Response.ErrorListener createIssueErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Ooops =(", Toast.LENGTH_SHORT).show();

            }
        };
    }


}