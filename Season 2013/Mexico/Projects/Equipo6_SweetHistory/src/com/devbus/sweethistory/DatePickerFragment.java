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

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

/**
 * Date Picker Fragment, show a date picker with a initial date, contains a listener which must be implemented by the
 * calling activity, so the selected date could be delivered
 *
 * @author "M. en C. Javier Silva Perez (JSP)"
 * @version 1.0
 * @since 22/03/2013
 */
public class DatePickerFragment extends DialogFragment implements
	DatePickerDialog.OnDateSetListener {
	/**
	 * Listener for OnDateSetListener on the dialog fragment, the implementation of this interface will determine what to
	 * do when the date is setted
	 */
	protected OnDateSetListener onDateSetListener;
	protected Date initialDate;
    private boolean maxDateNow;

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
	 * @return the initialDate
	 */
	public Date getInitialDate() {
		return initialDate;
	}

	/**
	 * @param initialDate
	 * 	the initialDate to set
	 */
	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

    /**
     * @param maxDateNow
     * set whether the max date of the date picker is the current day or not
     */
    public void setMaxDateNow(boolean maxDateNow) {
        this.maxDateNow = maxDateNow;
    }

    /**
	 * creates a new instance of this dialog using a dateSet Listener and a initial date
	 *
	 * @param initialDate
	 * 	date that should be shown in the DatePicker
	 * @param onDateSetListener
	 * 	Listener to be called when the date is selected
	 * @return a new instance of DatePickerFragment
	 */
	public static DatePickerFragment newInstance(Date initialDate,
	                                             OnDateSetListener onDateSetListener, boolean maxDateNow) {
		DatePickerFragment f = new DatePickerFragment();
		f.setInitialDate(initialDate);
		f.setOnDateSetListener(onDateSetListener);
        f.setMaxDateNow(maxDateNow);
		return f;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
        Locale spanishLocale = new Locale("es");
        final Calendar c = Calendar.getInstance(spanishLocale);
		c.setTime(initialDate);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
        DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), this, year, month, day);
		dateDialog.setTitle(R.string.datePickerTitle);
        if(maxDateNow)
            dateDialog.getDatePicker().setMaxDate(CMUtils.removeTime(new Date()).getTime());
        return dateDialog;
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
		final Calendar c = Calendar.getInstance();
		c.set(year, month, day);

		onDateSetListener.onDateSet(c.getTime());
	}

	/**
	 * Interface to determine what to do when the date is chosen by the user
	 *
	 * @author Ing. Javier Silva Pérez - [javier]
	 * @version 1.0
	 * @since 29/09/2012
	 */
	public interface OnDateSetListener {
		/**
		 * To be called from {@link DatePickerFragment} after the user chose the date
		 *
		 * @param date
		 * 	Selected date
		 */
		public void onDateSet(Date date);
	}
}
