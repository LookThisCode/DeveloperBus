package mx.developerbus.foodbus.utl;

/**
 * Copyright [2013] [Diego Ernesto Franco Chanona, Irving Lopez Perez, Miriam Alejandra Lugo Muciño, Raymundo Juarez Cortes]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

import java.util.concurrent.TimeUnit;

import mx.developerbus.foodbus.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class foodBUtil {

	public static String milliSecondsToTimer(long milliseconds) {
		String finalTimerString = "";
		String secondsString = "";
		
		int hours = (int) (milliseconds / (1000*60*60));
		int minutes = (int) (milliseconds % (1000*60*60) / (1000*60));
		int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
		
		if(hours > 0) {
			finalTimerString = hours + ":";
		}
		if(seconds < 10) {
			secondsString = "0" + seconds;
		}
		else {
			secondsString = "" + seconds;
		}
		
		finalTimerString = finalTimerString + minutes + ":" + secondsString;
		
		return finalTimerString;
	}
	
	public static int getProgressPercentage(long currentDuration, long totalDuration) {
		Double percentage = (double) 0;
		
		long currentSeconds = (int) (currentDuration / 1000);
		long totalSeconds = (int) (totalDuration / 1000);
		
		percentage = (((double) currentSeconds) / totalSeconds) * 100;
		
		return percentage.intValue();
	}
	
	public static int progressToTimer(int progress, int totalDuration) {
		int currentDuration = 0;
		totalDuration = (int) (totalDuration / 1000);
		currentDuration = (int) ((((double) progress) / 100) * totalDuration);
		
		return currentDuration * 1000;
	}
	
	public static String milliToMinutes(String milliseconds) {
		Long millis = Long.parseLong(milliseconds);
		milliseconds = String.format("%dmin : %dsec", TimeUnit.MILLISECONDS.toMinutes(millis),
			    TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		
		return milliseconds;
	}
	
	public static boolean setFragmentWorkspace(boolean multipane,
			FragmentManager fragmentManager,
			Class<? extends Fragment> fragmentClass, boolean stacked)
			throws Exception {
		boolean replace = false;
		try {
			replace = setFragmentWorkspace(multipane, fragmentManager, fragmentClass, null, 0, 0, 0, 0, stacked);
		} catch (Exception e) {
			throw new Exception("ERROR Home - setFragment : " + e.getMessage());
		}

		return replace;
	}
	
	public static boolean setFragmentWorkspace(boolean multipane, FragmentManager fragmentManager, Class<? extends Fragment> fragmentClass) throws Exception {
		boolean replace = false;
		try {
			replace = setFragmentWorkspace(multipane, fragmentManager, fragmentClass, null, 0, 0, 0, 0, false);
		} catch (Exception e) {
			throw new Exception("ERROR Home - setFragment : " + e.getMessage());
		}

		return replace;
	}

	public static boolean setFragmentWorkspace(boolean multipane, FragmentManager fragmentManager, Class<? extends Fragment> fragmentClass, Bundle args, int enter, int exit, int popEnter, int popExit, boolean staked) throws Exception {
		boolean replace = false;
		try {

			if (fragmentClass != null) {
				Fragment fp = fragmentManager.findFragmentById(R.id.frgWorkspace);
				FragmentTransaction tra = fragmentManager.beginTransaction();

				if (enter > 0 && exit > 0 && popEnter > 0 && popExit > 0) {
					tra.setCustomAnimations(enter, exit, popEnter, popExit);
					tra.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				} else {
					tra.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				}
				
				Fragment f = fragmentClass.newInstance();
				Bundle b = new Bundle();
				b.putBoolean("multipane", multipane);
				if (args != null) {
					b.putAll(args);
				}
				f.setArguments(b);

				if (fp != null) {
					if (fp.getClass() != fragmentClass) {
						tra.replace(R.id.frgWorkspace, f);
						if (staked) {
							tra.addToBackStack(null);
						}
						replace = true;
					}
				} else {
					tra.add(R.id.frgWorkspace, f);
					replace = true;
				}
				tra.commit();
			}
		} catch (Exception e) {
			throw new Exception("ERROR Home - setFragment : " + e.getMessage());
		}
		return replace;
	}

	public static String getResourceString(Context cc, int stringId) {
		String s = null;
		if (cc != null) {
			s = cc.getString(stringId);
		}
		return s;
	}
	
	public static boolean isNullOrEmpty(String value) {
		boolean v = true;
		if (value != null && value.trim().equals("") == false
				&& value.equalsIgnoreCase("null") == false) {
			v = false;
		}
		return v;
	}


}
