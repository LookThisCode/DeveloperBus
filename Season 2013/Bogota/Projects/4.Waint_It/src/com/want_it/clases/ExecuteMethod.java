package com.want_it.clases;
import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;

public class ExecuteMethod extends AsyncTask{

 private static final String SOAP_ACTION = ""; 
 private static final String URL = "http://hallowed-pager-399.appspot.com/want_it";

 @Override
 protected Object doInBackground(Object... params) {
  HttpTransportSE httpt = new HttpTransportSE(URL);
  SoapSerializationEnvelope envelope = new SoapSerializationEnvelope( SoapEnvelope.VER11 );
  envelope.dotNet = false;
  envelope.bodyOut = params[0]; 
  httpt.debug = true; 
  try {
   httpt.call(SOAP_ACTION, envelope);
   return envelope.getResponse();
  } catch (IOException e) {
   
  } catch (XmlPullParserException e) {
   
  }catch (Exception e) {
   
   
  } 
     return null;
 }

}