package com.example.proyectoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MantenimientoClientes extends AppCompatActivity implements View.OnClickListener {

    Button btnAlta,btnBaja,btnModificacion,btnBuscar;
    EditText txtIdCliente,txtNombre,txtApellido,txtDni;
    SQLiteDatabase sqldb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_clientes);
        txtIdCliente=findViewById(R.id.txtIdCliente);
        txtNombre=findViewById(R.id.txtNombre);
        txtApellido=findViewById(R.id.txtApellido);
        txtDni=findViewById(R.id.txtDni);
        btnAlta=findViewById(R.id.btnAlta);
        btnBaja=findViewById(R.id.btnBaja);
        btnModificacion=findViewById(R.id.btnModificacion);
        btnBuscar=findViewById(R.id.btnBuscar);
        btnAlta.setOnClickListener(this);
        btnBaja.setOnClickListener(this);
        btnModificacion.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==btnBuscar){
            buscar();
        }
        else if(v==btnAlta){
            darDeAlta();
        }
        else if(v==btnBaja){
            darDeBaja();
        }
        else if(v==btnModificacion){
            modificar();
        }
    }

    private void modificar() {
    }

    private void darDeBaja() {
        String id=txtIdCliente.getText().toString();
        sqldb=MainActivity.toh.getWritableDatabase();
        sqldb.delete("Clientes","IdCliente=?",new String[]{id});
        btnBaja.setEnabled(false);
        btnModificacion.setEnabled(false);
        btnAlta.setEnabled(true);
    }

    private void darDeAlta() {
        sqldb=MainActivity.toh.getWritableDatabase();
        ContentValues cv=new ContentValues();
        llenarCv(cv);
        sqldb.insert("Clientes",null,cv);
        //Habilitar y deshabilitar los botones correspondientes
        btnAlta.setEnabled(false);
        btnBaja.setEnabled(true);
        btnModificacion.setEnabled(true);
    }

    private void llenarCv(ContentValues cv) {
        //Obtener los valores de cada caja de texto
        String idCliente=txtIdCliente.getText().toString();
        String nombre=txtNombre.getText().toString();
        String apellido=txtApellido.getText().toString();
        String dni=txtDni.getText().toString();
        //Llenar el contentValue con el valor de la caja y su nombre
        String[]elementos=new String[]{idCliente,nombre,apellido,dni};
        String[]nombreelementos=new String[]{"idCliente","nombre","apellido","DNI",};
        for (int i = 0; i < elementos.length; i++) {
            cv.put(nombreelementos[i],elementos[i]);
        }
    }

    private void buscar() {
        String idCliente=txtIdCliente.getText().toString();
        sqldb=MainActivity.toh.getReadableDatabase();
        Cursor c=sqldb.query("Clientes",new String[]
                        {"IdCliente","Nombre","Apellido","DNI"},
                "IdCliente=?",
                new String[]{idCliente},
                null,null,null);
        if (c.moveToNext()){
            //Insertar los datos del cursor en las cajas de texto
            txtNombre.setText(c.getString(1));
            txtApellido.setText(c.getString(2));
            txtDni.setText(c.getString(2));
            //Habilitar y deshabilitar los botones correspondientes
            btnAlta.setEnabled(false);
            btnBaja.setEnabled(true);
            btnModificacion.setEnabled(true);
        }
        else{
            mostrarMensaje("No se ha encontrado ese cliente");
            //Habilitar y deshabilitar los botones correspondientes
            btnAlta.setEnabled(true);
            btnBaja.setEnabled(false);
            btnModificacion.setEnabled(false);
        }
    }

    private void mostrarMensaje(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}