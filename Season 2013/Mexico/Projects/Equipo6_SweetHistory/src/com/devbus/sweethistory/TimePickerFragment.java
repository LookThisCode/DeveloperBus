/**
 * File: DatePickerFragment.java
 * CreationDate: 22/03/2013
 * Author: "M. en C. Javier Silva Perez (JSP)"
 * Description: 
 * Date Picker Fragment, show a date picker with a initial date, contains a
 * listener which must be implemented by the calling activity, so the selected
 * date could be delivered
 */
package com.devbus.sweethistory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Date Picker Fragment, show a date picker with a initial date, contains a listener which must be implemented by the
 * calling activity, so the selected date could be delivered
 *
 * @version 1.0
 * @since 22/03/2013
 */
public class TimePickerFragment extends DialogFragment implements
        TimePickerDialog.OnTimeSetListener {
	/**
	 * Listener for OnDateSetListener on the dialog fragment, the implementation of this interface will determine what to
	 * do when the date is setted
	 */
	protected OnDateSetListener onDateSetListener;

	/**
	 * @return the onDateSetListener
	 */
	public OnDateSetListener getOnDateSetListener() {
		return onDateSetListener;
	}

	/**
	 * @param onDateSetListener
	 * 	the onDateSetListener to set
	 */
	public void setOnDateSetListener(OnDateSetListener onDateSetListener) {
		this.onDateSetListener = onDateSetListener;
	}


    /**
	 * creates a new instance of this dialog using a timeSet Listener
	 *
	 * @param onDateSetListener
	 * 	Listener to be called when the date is selected
	 * @return a new instance of DatePickerFragment
	 */
	public static TimePickerFragment newInstance(
	                                             OnDateSetListener onDateSetListener) {
		TimePickerFragment f = new TimePickerFragment();
		f.setOnDateSetListener(onDateSetListener);
		return f;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
        Locale spanishLocale = new Locale("es");
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog timeDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        timeDialog.setTitle(R.string.timePickerTile);
        return timeDialog;
	}

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        onDateSetListener.onTimeSet(hourOfDay, minute);
    }

    /**
	 * Interface to determine what to do when the date is chosen by the user
	 *
	 * @version 1.0
	 * @since 29/09/2012
	 */
	public interface OnDateSetListener {
		/**
		 * To be called from {@link com.devbus.sweethistory.TimePickerFragment} after the user chose the date
		 *
		 * @param hourOfDay
		 * 	Selected hour
         * 	@param minute Selected minute
		 */
		public void onTimeSet(int hourOfDay, int minute);
	}
}
