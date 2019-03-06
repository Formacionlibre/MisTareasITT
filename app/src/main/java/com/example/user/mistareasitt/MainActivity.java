package com.example.user.mistareasitt;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mistareasitt.db.ControladorDB;

public class MainActivity extends AppCompatActivity {

    ControladorDB controladorDB; //variable del controlador de forma global. desde cualquier sitio de esta clase puedo acceder al controlador
    ListView listViewTareas;//Creamos una referencia al ListView
    private ArrayAdapter<String> miAdapter;//para rellenar un ListView necesitamos un adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controladorDB = new ControladorDB(this); //el controlador lo creo despues de crear el mainActivity
        listViewTareas = (ListView) findViewById(R.id.listaTareas); //le doy valor
        actualizarUI();//llamamos al metodo cuando se crea el activity en onCreate
    }

    @Override  //con esto construiriamos los elementos dentro de la barra.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //aqui hacemos lo que pasaria al pulsar el icono de mas
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //aqui ponemos lo que queremos que haga el boton
        //como solo tenemos un elemento lo hacemos asi, sino deberiamos usar un switch como en los apuntes.
        //quitamos el toast para hacer el cuadro de dialogo Toast.makeText(this, "añadir tarea", Toast.LENGTH_SHORT).show();

        final EditText cajaTexto = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New Task")
                .setMessage("What is your next task?")
                .setView(cajaTexto)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        String tarea = cajaTexto.getText().toString();//almaceno la cadena de texto en una variable y luego le paso la variable


                        controladorDB.addTarea(tarea);//llamo al metodo addTarea del controlador que se ocupa de la parte de BD y le digo
                        // que tarea tiene que insertar que es el contenido de la cajaTexto
                        actualizarUI();//llamamos cuando agregamos una nueva tarea para que aparezca en la ListView

                    }
                })
                .setNegativeButton("Cancel", null)
                .create(); //el punto y como se pone al final como una sola instruccion
        dialog.show();

        return super.onOptionsItemSelected(item);
    }

    private void actualizarUI() {
        //antes de actualizar el ListView necesitamos saber cuantos registros tenemos en la bd. Para ello debemos ir al controlador y crear otro metodo. Si hay registros hacemos lo de abajo
        if (controladorDB.numeroRegistros() == 0) {
            listViewTareas.setAdapter(null);

        } else {
            miAdapter = new ArrayAdapter<>(this, R.layout.item_tarea, R.id.task_title, controladorDB.obtenerTareas());
            listViewTareas.setAdapter(miAdapter);
        }


    }

    public void borrarTarea(View view) {//publico, no devolver nada y recibir como parametro un view. Este es el metodo que llame cuando hago click en el boton

    View parent =(View) view.getParent();//obtenemos el padre del boton.Hacemos el casting porque requiere un view
    TextView tareaTextView = (TextView)parent.findViewById(R.id.task_title); //dentro del padre buscamos el identificador task_title para que me devuelva
                                                                            // una caja de texto que la guardaremos en una variable
    String tarea = tareaTextView.getText().toString();//queremos obtener el contendio que lo guardarmos en otra variable tarea
    controladorDB.borrarTarea(tarea);//le pasamos el String del textView que acompaña ese boton

        //Toast toast = Toast.makeText(this,"Deleted task", Toast.LENGTH_LONG);
      //  toast.show();
        Toast toast2 = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_personalizado, (ViewGroup) findViewById(R.id.idLayout));
        TextView texto = (TextView) layout.findViewById(R.id.textMensaje);
        texto.setText("Task successfully removed");
        toast2.setDuration(Toast.LENGTH_LONG);
        toast2.setView(layout);
        toast2.show();
        actualizarUI();//actualizamos de la base de datos

    }

}
