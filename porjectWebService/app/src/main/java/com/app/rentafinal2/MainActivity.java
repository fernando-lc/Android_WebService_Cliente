package com.app.rentafinal2;
import android.view.LayoutInflater;
import android.app.Activity;
import android.view.ViewGroup;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import com.app.movie.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends Activity {
	private String[] datos, arreglo1DP, arreglo1DC, arreglo1DPr, arreglo1DP2, arregloTupla2, arregloTupla;
	private String[][] arregloPedidos, arregloPedidosRemoto, arregloProductos, clientes, productos, arregloPedidos2, arregloClientes;
	private Button btnIngresar, btnActualizar, btnSubir, btnEliminar;
	private Button btnDescargar;
	private EditText textoRenta;
	private Spinner textoCliente;
	private Spinner textoPelicula;
	private EditText textoFecha;
	private EditText textoCantidad;

	private Context context;
	private int cantidadRegistrosP2, cantidadRegistrosP;//numero campos provicional
	public final int dialogo_alert=0;
	public String cadena="";
	
	// referencia a la clase url
	url variables=new url();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		textoRenta = (EditText)findViewById(R.id.editText1);
		textoCliente = (Spinner)findViewById(R.id.spinner1);
		textoPelicula = (Spinner)findViewById(R.id.spinner2);
		textoFecha = (EditText)findViewById(R.id.editText2);
		textoCantidad = (EditText)findViewById(R.id.editText3);
		btnIngresar = (Button)findViewById(R.id.button1);
		btnDescargar = (Button)findViewById(R.id.button4);
		btnSubir = (Button)findViewById(R.id.button5);
		btnActualizar = (Button)findViewById(R.id.button2);
		//AgregarDatos();
		
		btnIngresar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					AgregarBaseDatos(textoRenta.getText().toString(), clientes[textoCliente.getSelectedItemPosition()][0].toString(), productos[textoPelicula.getSelectedItemPosition()][0], textoFecha.getText().toString(), textoCantidad.getText().toString());
					actualizar();
					textoRenta.setText("");
					textoFecha.setText("");
					textoCantidad.setText("");
					/*
					Toast toast1 =
							Toast.makeText(getApplicationContext(),
									"Datos ingresados", Toast.LENGTH_SHORT);
					toast1.show();
					*/
					//MEJORANDO TOAST JAJAJAJAJAJAJAJAJAJA
					Toast toast3 = new Toast(getApplicationContext());

					LayoutInflater inflater = getLayoutInflater();
					View layout = inflater.inflate(R.layout.toast_layout,
							(ViewGroup) findViewById(R.id.lytLayout));

					TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
					txtMsg.setText("Renta Agregada");

					toast3.setDuration(Toast.LENGTH_SHORT);
					toast3.setView(layout);
					toast3.show();
					//TOAST FIN
				} catch (Exception e) {
					Toast toast1 =
							Toast.makeText(getApplicationContext(),
									"Error al ingresar los datos", Toast.LENGTH_SHORT);
					toast1.show();
				}
			}
		});
	
	btnDescargar.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			try
	 		{
			BorraBaseTablas();
			selectClientes();
	    	selectPeliculas();
	    	cargarCliente();
	    	cargarProducto();

				//MEJORANDO TOAST JAJAJAJAJAJAJAJAJAJA
				Toast toast3 = new Toast(getApplicationContext());

				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.toast_layout,
						(ViewGroup) findViewById(R.id.lytLayout));

				TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
				txtMsg.setText("Sincronización Exitosa");

				toast3.setDuration(Toast.LENGTH_SHORT);
				toast3.setView(layout);
				toast3.show();
				//TOAST FIN
	 		}catch(Exception e)
	 		{
				Toast toast1 =
						Toast.makeText(getApplicationContext(),
								"Error de Sincronizacion ", Toast.LENGTH_SHORT);
				toast1.show();
	 		}

		}
	});
	btnActualizar.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			try{
				ActualizarBaseDatos(textoRenta.getText().toString(), clientes[textoCliente.getSelectedItemPosition()][0].toString(), productos[textoPelicula.getSelectedItemPosition()][0], textoFecha.getText().toString(), textoCantidad.getText().toString());

				textoRenta.setText("");
				textoFecha.setText("");
				textoCantidad.setText("");
				actualizar();
				/*
				Toast toast1 =
						Toast.makeText(getApplicationContext(),
								"Datos Actualizados", Toast.LENGTH_SHORT);

				toast1.show();
				*/
				//MEJORANDO TOAST JAJAJAJAJAJAJAJAJAJA
				Toast toast3 = new Toast(getApplicationContext());

				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.toast_layout,
						(ViewGroup) findViewById(R.id.lytLayout));

				TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
				txtMsg.setText("Renta Actualizada");

				toast3.setDuration(Toast.LENGTH_SHORT);
				toast3.setView(layout);
				toast3.show();
				//TOAST FIN
			 	}catch(Exception e)
			 	{
					Toast toast1 =
							Toast.makeText(getApplicationContext(),
									"Error al Actualizar datos", Toast.LENGTH_SHORT);

					toast1.show();
			 	}

		}
	});
	btnSubir.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			try{
		    	subirRegistros();
		    	BorraBaseTablas();
		    	actualizar();
				/*
				Toast toast1 =
						Toast.makeText(getApplicationContext(),
								"Datos Guardados", Toast.LENGTH_SHORT);

				toast1.show();
				*/
				//MEJORANDO TOAST JAJAJAJAJAJAJAJAJAJA
				Toast toast3 = new Toast(getApplicationContext());

				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.toast_layout,
						(ViewGroup) findViewById(R.id.lytLayout));

				TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
				txtMsg.setText("Renta Guardada");

				toast3.setDuration(Toast.LENGTH_SHORT);
				toast3.setView(layout);
				toast3.show();
				//TOAST FIN
			 	}catch(Exception e)
			 	{
					Toast toast1 =
							Toast.makeText(getApplicationContext(),
									"Error al guardar los datos", Toast.LENGTH_SHORT);

					toast1.show();
			 	}

		}
	});
	}
public String[][] selectClientes()
{
	
	final String namespace = "http://tempuri.org/";
	final String url=variables.direccionIp+"/WSMoviles/Service.asmx";
	final String accionSoap = "http://tempuri.org/selectClientes";
	final String Metodo = "selectClientes";
	SoapObject request = new SoapObject(namespace, Metodo);
	 SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
     sobre.dotNet = true;
     sobre.setOutputSoapObject(request);
     HttpTransportSE transporte = new HttpTransportSE(url);
     	int cantidadRegistrosClientes = 0;
		try {
	         transporte.call(accionSoap, sobre);
	         SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();	         
	         cadena = resultado.toString(); 
	         cantidadRegistrosClientes = determinaRegistrosC(cadena);
	         arreglo1DC = new String [cantidadRegistrosClientes];
	         arregloTupla2 = new String [2];
	         arregloClientes = new String [2][cantidadRegistrosClientes];
	     	almacenaRegistrosC(cadena);
	     	for(int l = 0; l < arreglo1DC.length; l ++)
	     	 	{
	     	 		almacenaArreglo2DC(arreglo1DC[l], l);
	     	 	}
	         } 
	        catch (Exception e)
	         {
	        	Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_LONG).show();
	         }
	     	 	
	         
	        
		return arregloClientes;
}
	
public int determinaRegistrosC(String cadenaEntera)
{
		int numeroRegistros = 0;
		for(int  k = 0; k < cadenaEntera.length(); k ++)
		{
			if(cadenaEntera.charAt(k) == '*')
			{
				numeroRegistros++;
			}
		}
	//alerta
		return numeroRegistros;
}
	
public void almacenaRegistrosC(String cadena)
{
		int inicio = 0, posicion = 0;
		for(int  k = 0; k < cadena.length(); k ++)
		{
			if(cadena.charAt(k) == '*')
			{
				arreglo1DC[posicion] = cadena.substring(inicio, k);
				inicio = k+1;
				posicion ++;
			}
		}
}
	
public void almacenaArreglo2DC(String cadenaRegistro, int renglon)
{
		String Nombre = ""; String IdCliente="";
		int inicio = 0, posicion = 0;
		for(int  k = 0; k < cadenaRegistro.length(); k ++)
		{
			if(cadenaRegistro.charAt(k) == ',')
			{
				arregloTupla2[posicion] = cadenaRegistro.substring(inicio, k);
				arregloClientes[posicion][renglon] = cadenaRegistro.substring(inicio, k);
				inicio = k+1;
				posicion ++;
			}
		}
		AgregarCliente(arregloTupla2[0], arregloTupla2[1]);
}
public void AgregarCliente(String IdCliente, String Nombre){
	SQLHelper sqlhelper = new SQLHelper (this);
	SQLiteDatabase db = sqlhelper.getWritableDatabase();
	db.execSQL("INSERT INTO clientes(idCliente, nombreCliente) VALUES ('" + IdCliente + "','" + Nombre + "') ");
	db.close();
}
public void selectPeliculas()
{	
	final String namespace = "http://tempuri.org/";
	final String url=variables.direccionIp+"/WSMoviles/Service.asmx";
	final String accionSoap = "http://tempuri.org/selectPeliculas";
	final String Metodo = "selectPeliculas";
		int cantidadRegistrosProductos = 0;
		SoapObject request = new SoapObject(namespace, Metodo);
        SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        sobre.dotNet = true;
        sobre.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(url);
		try {	                  
	     	 transporte.call(accionSoap, sobre);
	         SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
	         cadena = resultado.toString();
			//alerta
	         cantidadRegistrosProductos = determinaRegistrosP(cadena);
	     	 arreglo1DPr = new String [cantidadRegistrosProductos];
	     	 arregloTupla2 = new String [2];
	     	 almacenaRegistrosP(cadena);
	     	 arregloProductos = new String [2][cantidadRegistrosProductos];
	     	 	for(int l = 0; l < arreglo1DPr.length; l ++)
	     	 	{
	     	 		almacenaArreglo2DP(arreglo1DPr[l], l);
	     	 	}
	         } 
	        catch (Exception e)
	         {
				 //alerta
	         }
}
	
public int determinaRegistrosP(String cadenaEntera)
{
		int numeroRegistros = 0;
		for(int  k = 0; k < cadenaEntera.length(); k ++)
		{
			if(cadenaEntera.charAt(k) == '*')
			{
				numeroRegistros++;
			}
		}
	    //alerta
		return numeroRegistros;
}


	
public void almacenaRegistrosP(String cadena)
{
		int inicio = 0, posicion = 0;
		for(int  k = 0; k < cadena.length(); k ++)
		{
			if(cadena.charAt(k) == '*')
			{
				arreglo1DPr[posicion] = cadena.substring(inicio, k);
				inicio = k+1;
				posicion ++;
			}
		}
		
}
	
public void almacenaArreglo2DP(String cadenaRegistro, int renglon)
{
		String Nombre = ""; String IdProducto = "";
		int inicio = 0, posicion = 0;
		for(int  k = 0; k < cadenaRegistro.length(); k ++)
		{
			if(cadenaRegistro.charAt(k) == ',')
			{
				arregloProductos[posicion][renglon] = cadenaRegistro.substring(inicio, k);
				arregloTupla2[posicion] = cadenaRegistro.substring(inicio, k);
				inicio = k+1;
				posicion ++;
			}
		}
		AgregarProducto(arregloTupla2[0], arregloTupla2[1]);
}
public void AgregarProducto(String IdProducto, String Nombre){
	SQLHelper sqlhelper = new SQLHelper (this);
	SQLiteDatabase db = sqlhelper.getWritableDatabase();
	db.execSQL("INSERT INTO peliculas(idPelicula, nombrePelicula) VALUES ('" + IdProducto + "','" + Nombre + "') ");
	db.close();
}
public void cargarCliente(){
	  		SQLHelper sqlhelper = new SQLHelper (this);
	  	    SQLiteDatabase db = sqlhelper.getWritableDatabase();
	  		Cursor c = db.rawQuery("select * from clientes", null);
	  		int j=0;
	    	final int elementos = c.getCount();
	  		clientes = new String[elementos][2];		
				if (c.moveToFirst()) {
	 			     do {
	 			    	String idCliente = c.getString(0);
	 			    	String nombre = c.getString(1);
	 			    	clientes[j][0]=""+idCliente;
	 			    	clientes[j][1]=""+nombre;
	 			        j++;
	 			     } while(c.moveToNext());
	 			}
	 			ArrayAdapter<String> adaptador = 
	 			        	new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cortaArreglo(clientes, elementos));    
	 			adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 			textoCliente.setAdapter(adaptador);
				db.close();
}
public String[] cortaArreglo(String[][] tablaCliente, int numeroDeElementos){
	String [] nombreClientes =  new String [numeroDeElementos];
	for(int k = 0; k < numeroDeElementos; k ++){
		nombreClientes [k] = tablaCliente[k][1];
	}
	return nombreClientes;
}	   

public void cargarProducto(){
	  		SQLHelper sqlhelper = new SQLHelper (this);
	  	    SQLiteDatabase db = sqlhelper.getWritableDatabase();
	  		Cursor prod = db.rawQuery("select * from peliculas", null);
	  		int h=0;
	    	final int cantidadProducto = prod.getCount();
	    	productos = new String[cantidadProducto][2];		
				if (prod.moveToFirst()) {
	 			     do {
	 			    	String idProducto = prod.getString(0);
	 			    	String nombre = prod.getString(1);
	 			    	productos[h][0]=""+idProducto;
	 			    	productos[h][1]=""+nombre;
	 			        h++;
	 			     } while(prod.moveToNext());
	 			}
			ArrayAdapter<String> adaptador = 
	 			        	new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cortaArreglo(productos, cantidadProducto));
adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 			textoPelicula.setAdapter(adaptador);
				db.close();
}

public void actualizar(){
	  		SQLHelper sqlhelper = new SQLHelper (this);
	  	    SQLiteDatabase db = sqlhelper.getWritableDatabase();
	  		String []args = new String []{"%"};
	  		int i = 0;
	  		Cursor c = db.rawQuery("select idRenta, nombreCliente, nombrePelicula, fecha, cantidadPelicula  from rentas where nombrePelicula like ? ",args);
	final int elementos = c.getCount()*5;
	  		 datos = new String[elementos];
			
	  		 for(int h=1; h<=elementos; h++)
	  		     datos[h-1] = "0";		
	  			if (c.moveToFirst()) {
	  			     do {
	  			    	String clave = c.getString(0);
	  			    	String cliente = c.getString(1);
	  			    	String producto = c.getString(2);
	  			    	String fecha = c.getString(3);
	  			    	String cantidad = c.getString(4);
	  			        datos[i]=""+clave; datos[i+1]=""+cliente; datos[i+2]=""+producto; datos[i+3]=""+fecha; datos[i+4]=""+cantidad;
	  			        i=i+5;
	  			     } while(c.moveToNext()); 
	  			     ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
	  			     final GridView grdOpciones = (GridView)findViewById(R.id.gridView1);
	  			     grdOpciones.setOnItemSelectedListener(
	  			    		 new AdapterView.OnItemSelectedListener() {
	  			    			 public void onItemSelected(AdapterView<?> parent, 
	  			    					 android.view.View v, int position, long id) {
	  			    				 		mostrarRegistro(position, elementos);
	  			    			 		}
	  			     public void onNothingSelected(AdapterView<?> parent) {
	  			        		}
	  			        });
	  			        grdOpciones.setAdapter(adaptador); 
	  			}
	  		db.close();
}
public void mostrarRegistro(int posicion, int elements){
		elements = elements / 5;
		int numeroElemento = 0;
		for(int j = 1; j <= elements; j++){
			if((posicion >= numeroElemento) && (posicion <= numeroElemento+4))
				break;
			numeroElemento = numeroElemento +5; 
		}
		textoRenta.setText("a"+datos[numeroElemento]);
		textoFecha.setText("a"+datos[numeroElemento+3]);
		textoCantidad.setText(""+datos[numeroElemento+4]);
		textoCliente.setFocusable(true);
		textoCliente.setSelection(devolverPosicionElemento(datos[numeroElemento+1],clientes));
		textoPelicula.setSelection(devolverPosicionElemento(datos[numeroElemento+2],productos));
	}
public int devolverPosicionElemento(String id, String [][] arregloBusqueda){
    int posicion = 0;
    	for(int k = 0; k < arregloBusqueda.length; k ++){
    		if(id.equals(arregloBusqueda[k][0]))
    			posicion = k;
    	}
    	return posicion;	
    }
public void  AgregarBaseDatos(String clave ,String empleado, String producto, String fecha, String cantidad){
	 SQLHelper sqlhelper = new SQLHelper (this);   
	 SQLiteDatabase db = sqlhelper.getWritableDatabase();
    db.execSQL("INSERT INTO rentas (idRenta,nombreCliente,nombrePelicula,fecha,cantidadPelicula) VALUES ('"+clave+"','"+empleado+"','"+producto+"','"+fecha+"','"+cantidad+"')");
    db.close();
   }
public void BorraBaseTablas()
{
    try{
    SQLHelper sqlhelper = new SQLHelper (this);   
   	SQLiteDatabase db = sqlhelper.getWritableDatabase();
	db.execSQL("DROP TABLE rentas");
	db.execSQL("DROP TABLE peliculas");
	db.execSQL("DROP TABLE clientes");
	
	db.execSQL("CREATE TABLE rentas (idRenta TEXT, nombreCliente TEXT, nombrePelicula TEXT, fecha TEXT, cantidadPelicula TEXT)");
	db.execSQL("CREATE TABLE clientes (idCliente TEXT, nombreCliente TEXT)");
	db.execSQL("CREATE TABLE peliculas (idPelicula PRIMARY KEY, nombrePelicula TEXT)" );
	
	db.close();
    }catch(Exception e)
    {
		alert("Error al borrar los registros");
    }
}
	public void  ActualizarBaseDatos (String clave, String cliente, String producto, String fecha, String cantidad){
		SQLHelper sqlhelper = new SQLHelper (this); 
		SQLiteDatabase db = sqlhelper.getWritableDatabase();
		db.execSQL("UPDATE rentas SET nombreCliente='"+cliente+"', nombrePelicula='"+producto+"', fecha='"+fecha+"', cantidadPelicula='"+cantidad+"' WHERE idRenta ='"+clave+"' ");
		db.close();
	}
	public void subirRegistros(){
 		
		SQLHelper sqlhelper = new SQLHelper (this); 
	    SQLiteDatabase db = sqlhelper.getWritableDatabase();
		Cursor c = db.rawQuery("select * from rentas", null);	
		int indice=0;
		final int numregistros = c.getCount();
		String[][] arregloPedidosLocal = new String[numregistros][5];
			if (c.moveToFirst()) {
			     do {
			    	String clave = c.getString(0);
			    	String cliente = c.getString(1);
			    	String producto = c.getString(2);
			    	String fecha = c.getString(3);
			    	String cantidad = c.getString(4);
			    	arregloPedidosLocal[indice][0] = clave;
			    	arregloPedidosLocal[indice][1] = cliente;
			    	arregloPedidosLocal[indice][2] = producto;
			    	arregloPedidosLocal[indice][3] = fecha;
			    	arregloPedidosLocal[indice][4] = cantidad;
			    	insertar(arregloPedidosLocal[indice][0], arregloPedidosLocal[indice][1], arregloPedidosLocal[indice][2], arregloPedidosLocal[indice][3], arregloPedidosLocal[indice][4]);
			    	indice++;
			     } while(c.moveToNext()); 
			}
		//alerta
		db.close();
	}
	
	public String[][] selectPedidosNoAlmacena()
	{
		final String namespace = "http://tempuri.org/";
		final String url=variables.direccionIp+"/WSMoviles/Service.asmx";
		final String accionSoap = "http://tempuri.org/selectRentas";
		final String Metodo = "selectRentas";


		try {

	         SoapObject request = new SoapObject(namespace, Metodo);
	         SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	         sobre.dotNet = true;
	         sobre.setOutputSoapObject(request);
	         HttpTransportSE transporte = new HttpTransportSE(url);
	         transporte.call(accionSoap, sobre);
	         SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
	         cadena = resultado.toString(); 
	         cantidadRegistrosP2 = determinaRegistrosN(cadena);
	         arreglo1DP2 = new String [cantidadRegistrosP2];
	     	 arregloPedidos2 = new String [5][cantidadRegistrosP2];
	     	 almacenaRegistrosN(cadena);
	     	 	for(int l = 0; l < arreglo1DP2.length; l ++)
	     	 	{
	     	 		almacenaArreglo2DN(arreglo1DP2[l], l);
	     	 	}
	         } 
	        catch (Exception e)
	         {
				 //alerta
	         }
		return arregloPedidos2;
	}
	public int determinaRegistrosN(String cadenaEntera)
	{
		int numeroRegistros = 0;
		for(int  k = 0; k < cadenaEntera.length(); k ++)
		{
			if(cadenaEntera.charAt(k) == '*')
			{
				numeroRegistros++;
			}
		}
		return numeroRegistros;
	}
	public void almacenaRegistrosN(String cadena)
	{
		int inicio = 0, posicion = 0;
		for(int  k = 0; k < cadena.length(); k ++)
		{
			if(cadena.charAt(k) == '*')
			{
				arreglo1DP2[posicion] = cadena.substring(inicio, k);
				inicio = k+1;
				posicion ++;
			}
		}
	}
	public void almacenaArreglo2DN(String cadenaRegistro, int pos)
	{
		int inicio = 0, posicion = 0;
		for(int  k = 0; k < cadenaRegistro.length(); k ++)
		{
			if(cadenaRegistro.charAt(k) == ',')
			{
				arregloPedidos2[posicion][pos] = cadenaRegistro.substring(inicio, k);
 				inicio = k+1;
				posicion ++;
			}
		}
	}
	
    public void insertar(String idPedido, String cliente, String producto, String fecha, String cantidad)
	{
    	final String namespace = "http://tempuri.org/";
		final String url=variables.direccionIp+"/WSMoviles/Service.asmx";
		final String accionSoap = "http://tempuri.org/insertarRenta";
		final String Metodo = "insertarRenta";
		
		SoapObject request = new SoapObject(namespace, Metodo);
		
		try {
	         request.addProperty("idRenta",idPedido);
	         request.addProperty("idCliente",cliente);
	         request.addProperty("idPelicula",producto);
	         request.addProperty("fecha",fecha);
	         request.addProperty("cantidadPelicula",cantidad);
	         
	         SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	         sobre.dotNet = true;
	         sobre.setOutputSoapObject(request);
	         HttpTransportSE transporte = new HttpTransportSE(url);
	         transporte.call(accionSoap, sobre);
	         SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
	         } 
	        catch (Exception e)
	         {
				 alert("Error al insertar...");
	         }
	}
      
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void alert(String text){
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}

}

