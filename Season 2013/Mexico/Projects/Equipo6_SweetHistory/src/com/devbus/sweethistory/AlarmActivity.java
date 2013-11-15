/**
 * File: AlarmActivity
 * CreationDate: 09/11/13
 * Author: "M. en C. Javier Silva Perez (JSP)"
 * Description: 
 *
 */
package com.devbus.sweethistory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author "Cynthia Palma Hernandez"
 * @version 1.0
 * @since 09/11/13
 */
public class AlarmActivity  extends BaseDrawerActivity {

    private static final String ALARMS_SP = "ALARMS_SP";
    SampleAlarmReceiver alarm = new SampleAlarmReceiver();
    EditText txtAlarm;
    Set<String> setAlarms;
    SharedPreferences.Editor alarmEditor;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    SharedPreferences sp;
    ArrayAdapter<String> alarmsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		FrameLayout mainFrame = (FrameLayout) findViewById(R.id.main_frame);
		View v = getLayoutInflater().inflate(R.layout.alarm_activity, null);
		mainFrame.addView(v);

        txtAlarm = (EditText) findViewById(R.id.txtAlarm);
        sp = getSharedPreferences("ALARMS_SWEETHISTORY", MODE_PRIVATE);
        alarmEditor= sp.edit();
        setAlarms = sp.getStringSet(ALARMS_SP, new TreeSet<String>());
        List<String> alarmsList = new LinkedList<String>();
        for (String setAlarm : setAlarms) {
            alarmsList.add(setAlarm);
        }

        alarmsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alarmsList);
        ListView alarmList =(ListView) findViewById(android.R.id.list);
        alarmList.setAdapter(alarmsAdapter);
        alarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alarm.cancelAlarm(AlarmActivity.this, alarmsAdapter.getItem(position));
                setAlarms.remove(alarmsAdapter.getItem(position));
                alarmsAdapter.remove(alarmsAdapter.getItem(position));

                Toast.makeText(AlarmActivity.this, R.string.alarmDeleted, Toast.LENGTH_SHORT).show();
                alarmEditor.putStringSet(ALARMS_SP, setAlarms);
                alarmEditor.commit();
                return true;
            }
        });

       final  TextView txtInitialDate = (TextView)findViewById(R.id.txtDate);
        txtInitialDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(txtInitialDate);
            }
        });

        final  TextView txtInitialTime = (TextView)findViewById(R.id.txtTime);
        txtInitialTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(txtInitialTime);
            }
        });

        final EditText txtEachTime = (EditText)findViewById(R.id.txtTimeEach);


        final Spinner spinner = (Spinner) findViewById(R.id.spinnerUnit);
        String[] arraySpinner=getResources().getStringArray(R.array.units);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinner.setAdapter(adapter);


        ImageView imgAddAlarm = (ImageView)findViewById(R.id.imgAddAlarm);
        imgAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(formatter.parse(txtInitialDate.getText().toString()));
                int hour = Integer.parseInt(txtInitialTime.getText().toString().split(":")[0]);
                int minutes = Integer.parseInt(txtInitialTime.getText().toString().split(":")[1]);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minutes);

                int intervalValue = Integer.parseInt(txtEachTime.getText().toString());
                    long intervalTotal = 1;
                int interval = spinner.getSelectedItemPosition();
                    switch (interval){
                        case 0:
                            //Seconds
                            intervalTotal = intervalValue * 1000;
                            break;

                        case 1:
                            //minutes
                            intervalTotal = intervalValue *60 * 1000;
                            break;

                        case 2:
                            //hours
                            intervalTotal = intervalValue *60*60 * 1000;
                            break;

                        case 3:
                            //days
                            intervalTotal = intervalValue *24*60*60 * 1000;
                            break;

                        case 4:
                            //weeks
                            intervalTotal = intervalValue* 7*24*60*60 * 1000;
                            break;
                    }


                alarm.setAlarm(AlarmActivity.this,txtAlarm.getText().toString(),calendar.getTimeInMillis(),intervalTotal);
                    setAlarms.add(txtAlarm.getText().toString());
                    alarmsAdapter.add(txtAlarm.getText().toString());
                    alarmEditor.putStringSet(ALARMS_SP, setAlarms);
                    alarmEditor.commit();

                    //Clean up fields
                    txtAlarm.setText("");
                    txtInitialDate.setText(R.string.txtDateInitial);
                    txtInitialTime.setText(R.string.txtTimeInitial);
                    txtEachTime.setText("");
                    spinner.setSelection(0);


                }catch (ParseException ex){
                    Toast.makeText(AlarmActivity.this,R.string.errorEmptyFiels,Toast.LENGTH_SHORT).show();
                }catch (NumberFormatException ex){
                    Toast.makeText(AlarmActivity.this,R.string.errorEmptyFiels,Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException ex){
                    Toast.makeText(AlarmActivity.this,R.string.errorEmptyFiels,Toast.LENGTH_SHORT).show();
                }catch (NullPointerException ex){
                    Toast.makeText(AlarmActivity.this,R.string.errorEmptyFiels,Toast.LENGTH_SHORT).show();
                }
            }
        });
	}

    private void showDateDialog(final TextView txtView)
    {
        // create a new dialog fragment
        DialogFragment newFragment;

            Date intialDate = new Date();

            try{
                intialDate = formatter.parse(txtView.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Gets a new instance of the DatePickerFragment using the
            // actual not after date and create a new DataSetListener
            newFragment = DatePickerFragment
                    .newInstance(intialDate,
                            new DatePickerFragment.OnDateSetListener() {
                                @Override
                                public void onDateSet(Date date) {
                                    txtView.setText(formatter.format(date));
                                }
                            }, false);
            newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    private void showTimeDialog(final TextView txtView)
    {
        // create a new dialog fragment
        DialogFragment newFragment;

            // Gets a new instance of the DatePickerFragment using the
            // actual not after date and create a new DataSetListener
            newFragment = TimePickerFragment
                    .newInstance(new TimePickerFragment.OnDateSetListener() {
                        @Override
                        public void onTimeSet(int hourOfDay, int minute) {
                            txtView.setText(hourOfDay+":"+minute);
                        }
                    });
            newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
