package com.want_it.clases;

import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

public class BPOServer {
	private static final String NAMESPACE="http://server.wantit.com/";
	
	public static boolean crearOfertar(String id,String categoria, String presupuesto, String largo, String ancho,String color,String material, String ciudad,String producto){
		String methodname="CrearOfertaCliente";
		SoapObject request = new SoapObject(NAMESPACE,methodname);
		  request.addProperty("arg0",id);
		  request.addProperty("arg1",categoria);
		  request.addProperty("arg2",presupuesto);
		  request.addProperty("arg3",largo);
		  request.addProperty("arg4",ancho);
		  request.addProperty("arg5",color);
		  request.addProperty("arg6",material);
		  request.addProperty("arg7",ciudad);
		  request.addProperty("arg8",producto);
		  ExecuteMethod em = new ExecuteMethod();
		  em.execute(request);
		  try{
		   return Boolean.parseBoolean(em.get().toString());
		  }catch(InterruptedException IE){
		   return false;
		  }catch (ExecutionException ee) {
		   return false;
		  }
	}
	
	public static String buscarOfertar(String atributo,String valor){
		String methodname="BuscarOferta";
		SoapObject request = new SoapObject(NAMESPACE,methodname);
		  request.addProperty("arg0",atributo);
		  request.addProperty("arg1",valor);
		  ExecuteMethod em = new ExecuteMethod();
		  em.execute(request);
		  try{
		   return em.get().toString();
		  }catch(InterruptedException IE){
		   return null;
		  }catch (ExecutionException ee) {
		   return null;
		  }
	}

}
