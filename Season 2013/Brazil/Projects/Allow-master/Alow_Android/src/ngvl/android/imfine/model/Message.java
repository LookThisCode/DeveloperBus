package ngvl.android.imfine.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {
	public long id;
	public String sender;
	public String photo;
	public String message;
	public long time;
	public boolean read;
}
