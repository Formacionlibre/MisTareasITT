package com.example.user.mistareasitt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//LO BASICO: necesitamos que el controlador extienda de sqLiteOpenHelper. Necesita un controlador donde crear la base
// //de datos y la version y con el metodo oncreate creamos la tabla
public class ControladorDB extends SQLiteOpenHelper {

    //creamos un constructor
    public ControladorDB(Context context) {
        super(context, "com.example.user.mistareasitt.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //aqui se crea la tabla
        db.execSQL("CREATE TABLE TAREAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL);");



    }
    //este solo se usa si cambaia la version de la bd habria que hacer una migracion de la base anterior x una nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addTarea (String tarea){

    //creamos el ContentValues que vamos a necesitar dos lineas mas abajo
        ContentValues registro = new ContentValues();
        registro.put("NOMBRE",tarea); //campo y valor. //si estuvieramos insertando un registro con mas campos deberiamos repetir el put con esos campos columna y valor



        //abrimos la base de datos
        SQLiteDatabase db = this.getWritableDatabase();//este metodo va a devolver una referencia a la base de datos que debemos guardar en una variable

        //hacemos la operacion, en este caso un insert
        db.insert("TAREAS",null,registro);//el registro que vamos a insertar debe ser de tipo ContentValues que es para ir poniendo campo y valor
    //el instert tb lo podemos hacer con un db.execSQUL("INSERT INTO TAREAS VALUES (null, ´+ tarea + ´);");

        //cerramos la bd
        db.close();

    }
    //creamos un metodo para obtener los registros de la tabla.Es publico y debe devolver un array de cadenas
    public String [] obtenerTareas(){
        SQLiteDatabase db = this.getReadableDatabase();//la abrimos para solo lectura
       Cursor cursor = db.rawQuery("SELECT * FROM TAREAS",null);//hacemos la consulta con el metodo rawQuery al que se le pasa la consulta en Sql. Si queremos todos los
        // registros se hace como se muestra. tb necesita parametros de la sentencia sql
        //con esta consulta obtenemos un conjunto de registros que es lo que se llama cursor que es otra clase se muestra el resultado de hacer una select que son registros

        int regs= cursor.getCount(); //guardamos el numero de recursos para poder recorrerlo
        if (regs == 0) {//preguntamos por el numero de registros
            db.close();
            return null;

        }else{
            String[] tareas = new String[regs];//vamos guardando en un array del tamaño regs
            cursor.moveToFirst(); //nos movemos al principio
            for (int i=0; i<regs;i++) { //recorremos el cursor
                tareas[i] = cursor.getString(1); //me traido del cursor el string de la posicion 1.si hubiesemos puesto SELECT NOMBRE pondriamos un 0 en lugar de 1
                cursor.moveToNext();//nos movemos al siguiente
            }
            db.close();//cerramos la bd
            return tareas;


        }
    }
    public int numeroRegistros() {//para saber los registros que tiene la tabla
        //abrimos la base de datos para lectura, hacemos un selec, lo guardamos en el cursor y retornamos el numero de registros.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS", null);
        return cursor.getCount();
    }
    //metodo para dar de baja
    public void borrarTarea (String tarea){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TAREAS", "NOMBRE=?",new String[]{tarea});//La interrogacion son los parametros.Cada vez que encuentre un ? se va al parametro tarea
    //en la fila anterior convertimos tarea en un array de un solo elemento, y le pasamos el valor entre las llaves {tarea}
    //borra de la tabla tareas los registros que el campo nombre sea igual al primer valor del array tarea.
        db.close();//cerramos la base de datos.

}

}
