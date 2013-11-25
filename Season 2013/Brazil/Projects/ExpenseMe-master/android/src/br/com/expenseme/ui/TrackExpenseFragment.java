package br.com.expenseme.ui;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;
import br.com.expenseme.R;
import br.com.expenseme.core.Constants.Http;
import br.com.expenseme.core.Expense;
import br.com.expenseme.util.SafeAsyncTask;
import br.com.expenseme.util.Util;

import com.github.kevinsawicki.http.HttpRequest;

public class TrackExpenseFragment extends Fragment implements OnClickListener, OnDateSetListener {

    ImageView imgView;

    private static final int REQUEST_CODE_CAMERA = 144;
    private static final int REQUEST_CODE_GALLERY = 145;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private static final String TAG = "TrackExpenseFragment";
    private String mCurrentPhotoPath;
    private String mSelectedImage;
    private Calendar mCalendar;
    private EditText editDate;
    private EditText editDescription;
    private EditText editVenue;
    private EditText editPrice;

    private Button btnPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_track_expense, null);
        imgView = (ImageView)v.findViewById(R.id.image);
        imgView.setOnClickListener(this);
        editDate = (EditText)v.findViewById(R.id.editDate);
        editVenue = (EditText)v.findViewById(R.id.editVenue);
        editDescription = (EditText)v.findViewById(R.id.editDescription);
        editPrice = (EditText)v.findViewById(R.id.editPrice);
        mCalendar = Calendar.getInstance();
        btnPost = (Button)v.findViewById(R.id.b_post);
        btnPost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                postExpense();
            }
        });
        editDate.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(
                            getActivity(),
                            TrackExpenseFragment.this,
                            mCalendar.get(Calendar.YEAR),
                            mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show();
                }
            }
        });

        return v;
    }

    public void postExpense() {
        editDate.setError(null);
        editPrice.setError(null);
        editVenue.setError(null);
        editDescription.setError(null);

        boolean cancel = false;
        View focusView = null;

        final String venue = editVenue.getText().toString();
        final String description = editDescription.getText().toString();
        final String price = editPrice.getText().toString();
        final String date = editDate.getText().toString();

        if (TextUtils.isEmpty(date)) {
            editDate.setError(getActivity().getString(R.string.por_favor_preencha_a_data_da_despesa));
            focusView = editDate;
            cancel = true;
        } else if (!isValidDate(date)) {
            editDate.setError(getActivity().getString(R.string.data_inv_lida));
            focusView = editDate;
            cancel = true;
        }

        if (TextUtils.isEmpty(price)) {
            editPrice.setError(getActivity().getString(R.string.por_favor_preencha_o_valor_));
            focusView = editPrice;
            cancel = true;
        } else if (!isValidPrice(price)) {
            editPrice.setError(getActivity().getString(R.string.por_favor_insira_um_pre_o_v_lido));
            focusView = editPrice;
            cancel = true;
        }

        if (TextUtils.isEmpty(venue)) {
            editVenue.setError(getActivity().getString(R.string.por_favor_preencha_o_local_da_despesa));
            focusView = editVenue;
            cancel = true;
        }

        if (TextUtils.isEmpty(description)) {
            editDescription.setError(getActivity().getString(R.string.por_favor_preencha_a_descri_o));
            focusView = editDescription;
            cancel = true;
        }

        if (TextUtils.isEmpty(mSelectedImage)) {
            Toast.makeText(getActivity(), R.string.por_favor_insira_uma_imagem_com_a_nota_fiscal_do_seu_gasto, Toast.LENGTH_LONG).show();
            cancel = true;
        }

        if (cancel) {
            if (focusView != null)
                focusView.requestFocus();
        } else {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            final SafeAsyncTask<Boolean> asyncTask = new SafeAsyncTask<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Expense expense = new Expense();
                    expense.setDescription(description);
                    expense.setPrice(Float.parseFloat(price));
                    expense.setDateTimeParam(getDateTimeParam(date));
                    expense.setVenue(venue);

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("location", expense.getVenue());
                    params.put("valor", String.valueOf(expense.getPrice()));
                    params.put("descricao", expense.getDescription());
                    params.put("datetime", expense.getDateTimeParams());

                    final String query = String.format("%s=%s&%s=%s&%s=%s&%s=%s",
                        "location", expense.getVenue(),
                        "valor", String.valueOf(expense.getPrice()),
                        "descricao", expense.getDescription(),
                        "dateTime", expense.getDateTimeParams());

                    HttpRequest request = HttpRequest.post(Http.URL_EXPENSES).send(query);
                    // .part("location", expense.getVenue())
                    // .part("valor", String.valueOf(expense.getPrice()))
                    // .part("descricao", expense.getDescription())
                    // .part("comprovante", new File(mSelectedImage));

                    Log.d(TAG, "Response code = " + String.valueOf(request.code()) + " body: " + request.body());
                    return request.ok();
                }

                protected void onSuccess(Boolean result) throws Exception {
                    dialog.dismiss();
                    if (!result)
                        Toast.makeText(getActivity(), R.string.ocorreu_um_erro_ao_enviar_seu_gasto_, Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getActivity(), R.string.despesa_cadastrada_com_sucesso, Toast.LENGTH_LONG).show();
                };

                protected void onException(Exception e) throws RuntimeException {
                    Toast.makeText(getActivity(), R.string.ocorreu_um_erro_ao_enviar_seu_gasto_, Toast.LENGTH_LONG).show();
                    Log.e(TAG, e.getMessage());
                    dialog.dismiss();
                };

            };

            dialog.setMessage("Enviando...");
            dialog.setIndeterminate(true);
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    asyncTask.cancel(true);
                }
            });

            asyncTask.execute();
        }
    }

    private boolean isValidPrice(String priceString) {
        try {
            Float.parseFloat(priceString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidDate(String dateString) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);
            formatter.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private String getDateTimeParam(String dateString) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);
            Date date = formatter.parse(dateString);
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Failed to get time in milliseconds " + e.getMessage());
            return "";
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                addPicture(mCurrentPhotoPath);
                Util.addImageToDeviceGallery(getActivity(), mCurrentPhotoPath);
            }

        } else if (requestCode == REQUEST_CODE_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                final Uri selectedImage = intent.getData();
                final String[] filePathColumn = { MediaStore.Images.Media.DATA };

                final Cursor cursor = getActivity().getContentResolver().query(
                    selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                final int columnIndex = cursor
                        .getColumnIndex(filePathColumn[0]);
                final String filePath = cursor.getString(columnIndex);
                cursor.close();

                addPicture(filePath);
            }
        }
    }

    private void addPicture(final String filePath) {
        /* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */
        final int size = Util.dpToPx(getActivity(), 70);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                size, size);
        layoutParams.setMargins(0, 0, Util.dpToPx(getActivity(), 10), 0);
        imgView.setLayoutParams(layoutParams);
        imgView.setScaleType(ScaleType.CENTER_CROP);
        final int targetW = imgView.getWidth();
        final int targetH = imgView.getHeight();

        /* Get the size of the image */
        final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, bmOptions);
        final int photoW = bmOptions.outWidth;
        final int photoH = bmOptions.outHeight;

        /* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if (targetW > 0 || targetH > 0)
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        final Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);

        imgView.setImageBitmap(bitmap);
        imgView.setVisibility(View.VISIBLE);

        mSelectedImage = filePath;
    }

    private File setUpPhotoFile() throws IOException {
        final File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        final String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        final File albumF = getAlbumDir();
        final File imageF = File.createTempFile(imageFileName,
            JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "expenseme");

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d(TAG, "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(TAG, "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(getActivity())
                .setPositiveButton("Usar a Câmera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File f = null;

                        try {
                            final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            f = setUpPhotoFile();
                            mCurrentPhotoPath = f.getAbsolutePath();
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                        } catch (final IOException e) {
                            Log.e(TAG, e.getMessage());
                            f = null;
                            mCurrentPhotoPath = null;
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Escolher da Galeria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
                        dialog.dismiss();
                    }
                })
                .setTitle("Pergunta")
                .setMessage("De onde você quer buscar a foto?")
                .show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        editDate.setText(sdf.format(mCalendar.getTime()));
    }
}
