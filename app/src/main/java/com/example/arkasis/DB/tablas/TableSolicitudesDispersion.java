package com.example.arkasis.DB.tablas;

import android.content.Context;
import android.database.Cursor;

import com.example.arkasis.interfaces.TableSQLite;
import com.example.arkasis.models.SolicitudDispersion;

import java.util.ArrayList;
import java.util.List;

public class TableSolicitudesDispersion extends Conexion implements TableSQLite {

    public static final String table = "solicitudesDispersion";
    public static final String col_idSolicitud = "idSolicitud";
    public static final String col_strFechaAlta = "strFechaAlta";
    public static final String col_strStatusSolicitud = "strStatusSolicitud";
    public static final String col_idSucursal = "idSucursal";
    public static final String col_strUsuarioPromotor = "strUsuarioPromotor";
    public static final String col_idPromotor = "idPromotor";
    public static final String col_strPromotor = "strPromotor";
    public static final String col_strCordinador = "strCordinador";
    public static final String col_idCliente = "idCliente";
    public static final String col_strApellidoPaterno = "strApellidoPaterno";
    public static final String col_strApellidoMaterno = "strApellidoMaterno";
    public static final String col_strNombre1 = "strNombre1";
    public static final String col_strNombre2 = "strNombre2";
    public static final String col_strFechaNacimiento = "strFechaNacimiento";
    public static final String col_idGenero = "idGenero";
    public static final String col_strGenero = "strGenero";
    public static final String col_strCURP = "strCURP";
    public static final String col_strDomicilio = "strDomicilio";
    public static final String col_strDomicilioCodigoPostal = "strDomicilioCodigoPostal";
    public static final String col_strDomicilioNumExt = "strDomicilioNumExt";
    public static final String col_strDomicilioNumInt = "strDomicilioNumInt";
    public static final String col_strDomicilioColonia = "strDomicilioColonia";
    public static final String col_idDomicilioEstado = "idDomicilioEstado";
    public static final String col_strDomicilioEstado = "strDomicilioEstado";
    public static final String col_idDomicilioMunicipio = "idDomicilioMunicipio";
    public static final String col_strDomicilioMunicipio = "strDomicilioMunicipio";
    public static final String col_strEstadoCivil = "strEstadoCivil";
    public static final String col_idEstadoCivil = "idEstadoCivil";
    public static final String col_strTelefono = "strTelefono";
    public static final String col_strCelular = "strCelular";
    public static final String col_strOcupacion = "strOcupacion";
    public static final String col_idActividad = "idActividad";
    public static final String col_strActividad = "strActividad";
    public static final String col_strNumeroINE = "strNumeroINE";
    public static final String col_strClaveINE = "strClaveINE";
    public static final String col_strPais = "strPais";
    public static final String col_strEstadoNacimiento = "strEstadoNacimiento";
    public static final String col_strNacionalidad = "strNacionalidad";
    public static final String col_strEmail = "strEmail";
    public static final String col_strNombreConyuge = "strNombreConyuge";
    public static final String col_strLugarNacimientoConyuge = "strLugarNacimientoConyuge";
    public static final String col_strFechaNacimientoConyuge = "strFechaNacimientoConyuge";
    public static final String col_strOcupacionConyuge = "strOcupacionConyuge";
    public static final String col_strReferenciaBancaria = "strReferenciaBancaria";
    public static final String col_strBanco = "strBanco";
    public static final String col_strProducto = "strProducto";
    public static final String col_intPlazo = "intPlazo";
    public static final String col_intQuedateCasa = "intQuedateCasa";
    public static final String col_dblMontoSolicitado = "dblMontoSolicitado";
    public static final String col_dblMontoAutorizado = "dblMontoAutorizado";
    public static final String col_dblIngresos = "dblIngresos";
    public static final String col_dblEgresos = "dblEgresos";
    public static final String col_strEstatusInserccionServidor = "strEstatusInserccionServidor";

    public TableSolicitudesDispersion(Context context) {
        super(context);
    }

    @Override
    public void insertar(Object o) {
        SolicitudDispersion item = (SolicitudDispersion)o;
        String query = "INSERT INTO "+table
                +" ( "+ col_strFechaAlta + ", "+ col_strStatusSolicitud + ", "+ col_idSucursal + ", "+ col_strUsuarioPromotor + ", "+ col_idPromotor + ", "+ col_strPromotor + ", "+ col_strCordinador + ", "+ col_idCliente + ", "+ col_strApellidoPaterno + ", "+ col_strApellidoMaterno + ", "+ col_strNombre1 + ", "+ col_strNombre2 + ", "+ col_strFechaNacimiento + ", "+ col_idGenero + ", "+ col_strGenero + ", "+ col_strCURP + ", "+ col_strDomicilio + ", "+ col_strDomicilioCodigoPostal + ", "+ col_strDomicilioNumExt + ", "+ col_strDomicilioNumInt + ", "+ col_strDomicilioColonia + ", "+ col_idDomicilioEstado + ", "+ col_strDomicilioEstado + ", "+ col_idDomicilioMunicipio + ", "+ col_strDomicilioMunicipio + ", "+ col_strEstadoCivil + ", "+ col_idEstadoCivil + ", "+ col_strTelefono + ", "+ col_strCelular + ", "+ col_strOcupacion + ", "+ col_idActividad + ", "+ col_strActividad + ", "+ col_strNumeroINE + ", "+ col_strClaveINE + ", "+ col_strPais + ", "+ col_strEstadoNacimiento + ", "+ col_strNacionalidad + ", "+ col_strEmail + ", "+ col_strNombreConyuge + ", "+ col_strLugarNacimientoConyuge + ", "+ col_strFechaNacimientoConyuge + ", "+ col_strOcupacionConyuge + ", "+ col_strReferenciaBancaria + ", "+ col_strBanco + ", "+ col_strProducto + ", "+ col_intPlazo + ", "+ col_intQuedateCasa + ", "+ col_dblMontoSolicitado + ", "+ col_dblMontoAutorizado + ", "+ col_dblIngresos + ", "+ col_dblEgresos + ", "+col_strEstatusInserccionServidor+" )"
                + " values"
                + " ('"+item.getStrFechaAlta()+"','"+item.getStrStatusSolicitud()+"','"+item.getIdSucursal()+"','"+item.getStrUsuarioPromotor()+"','"+item.getIdPromotor()+"','"+item.getStrPromotor()+"','"+item.getStrCordinador()+"','"+item.getIdCliente()+"','"+item.getStrApellidoPaterno()+"','"+item.getStrApellidoMaterno()+"','"+item.getStrNombre1()+"','"+item.getStrNombre2()+"','"+item.getStrFechaNacimiento()+"','"+item.getIdGenero()+"','"+item.getStrGenero()+"','"+item.getStrCURP()+"','"+item.getStrDomicilio()+"','"+item.getStrDomicilioCodigoPostal()+"','"+item.getStrDomicilioNumExt()+"','"+item.getStrDomicilioNumInt()+"','"+item.getStrDomicilioColonia()+"','"+item.getIdDomicilioEstado()+"','"+item.getStrDomicilioEstado()+"','"+item.getIdDomicilioMunicipio()+"','"+item.getStrDomicilioMunicipio()+"','"+item.getStrEstadoCivil()+"','"+item.getIdEstadoCivil()+"','"+item.getStrTelefono()+"','"+item.getStrCelular()+"','"+item.getStrOcupacion()+"','"+item.getIdActividad()+"','"+item.getStrActividad()+"','"+item.getStrNumeroINE()+"','"+item.getStrClaveINE()+"','"+item.getStrPais()+"','"+item.getStrEstadoNacimiento()+"','"+item.getStrNacionalidad()+"','"+item.getStrEmail()+"','"+item.getStrNombreConyuge()+"','"+item.getStrLugarNacimientoConyuge()+"','"+item.getStrFechaNacimientoConyuge()+"','"+item.getStrOcupacionConyuge()+"','"+item.getStrReferenciaBancaria()+"','"+item.getStrBanco()+"','"+item.getStrProducto()+"','"+item.getIntPlazo()+"','"+item.getIntQuedateCasa()+"','"+item.getDblMontoSolicitado()+"','"+item.getDblMontoAutorizado()+"','"+item.getDblIngresos()+"','"+item.getDblEgresos()+"', '"+item.getStrEstatusInserccionServidor()+"')";
        this.dbWrite.execSQL(query);
    }

    @Override
    public void insertarBatch(List<Object> objectList) {
        for (Object item: objectList) {
            this.insertar(item);
        };
    }

    public void actualizarEstatusInserccionServidor(Object o) {
        SolicitudDispersion item = (SolicitudDispersion)o;
        String query = "UPDATE "+table+" SET "+col_strEstatusInserccionServidor+" = '"+item.getStrEstatusInserccionServidor()+"' WHERE "+col_idSolicitud+" = '"+item.getIdSolicitud()+"';";
        this.dbWrite.execSQL(query);
    }

    @Override
    public int getCount() {
        Cursor cursor = dbRead.rawQuery("Select count(*) as totalRegistros from "+ table, null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    @Override
    public void truncate() {
        //No truncamos nada
    }

    @Override
    public List<Object> findAll(String buscar, Integer limit) {
        List<Object> list = new ArrayList<>();
        String query = "SELECT "
                + col_idSolicitud + ", "+
                col_strFechaAlta + ", "+
                col_strStatusSolicitud + ", "+
                col_idSucursal + ", "+
                col_strUsuarioPromotor + ", "+
                col_idPromotor + ", "+
                col_strPromotor + ", "+
                col_strCordinador + ", "+
                col_idCliente + ", "+
                col_strApellidoPaterno + ", "+
                col_strApellidoMaterno + ", "+
                col_strNombre1 + ", "+
                col_strNombre2 + ", "+
                col_strFechaNacimiento + ", "+
                col_idGenero + ", "+
                col_strGenero + ", "+
                col_strCURP + ", "+
                col_strDomicilio + ", "+
                col_strDomicilioCodigoPostal + ", "+
                col_strDomicilioNumExt + ", "+
                col_strDomicilioNumInt + ", "+
                col_strDomicilioColonia + ", "+
                col_idDomicilioEstado + ", "+
                col_strDomicilioEstado + ", "+
                col_idDomicilioMunicipio + ", "+
                col_strDomicilioMunicipio + ", "+
                col_strEstadoCivil + ", "+
                col_idEstadoCivil + ", "+
                col_strTelefono + ", "+
                col_strCelular + ", "+
                col_strOcupacion + ", "+
                col_idActividad + ", "+
                col_strActividad + ", "+
                col_strNumeroINE + ", "+
                col_strClaveINE + ", "+
                col_strPais + ", "+
                col_strEstadoNacimiento + ", "+
                col_strNacionalidad + ", "+
                col_strEmail + ", "+
                col_strNombreConyuge + ", "+
                col_strLugarNacimientoConyuge + ", "+
                col_strFechaNacimientoConyuge + ", "+
                col_strOcupacionConyuge + ", "+
                col_strReferenciaBancaria + ", "+
                col_strBanco + ", "+
                col_strProducto + ", "+
                col_intPlazo + ", "+
                col_intQuedateCasa + ", "+
                col_dblMontoSolicitado + ", "+
                col_dblMontoAutorizado + ", "+
                col_dblIngresos + ", "+
                col_dblEgresos + ", "+
                col_strEstatusInserccionServidor
                + " FROM "+table+" WHERE ("+col_strCURP+") like '%"+buscar+"%' GROUP BY "+col_strCURP+" ORDER BY "+col_strNombre1;
        Cursor cursor = dbRead.rawQuery( query , null);

        while (cursor.moveToNext()) {
            list.add(new SolicitudDispersion(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getString(13),
                    cursor.getString(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getString(17),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20),
                    cursor.getString(21),
                    cursor.getString(22),
                    cursor.getString(23),
                    cursor.getString(24),
                    cursor.getString(25),
                    cursor.getString(26),
                    cursor.getString(27),
                    cursor.getString(28),
                    cursor.getString(29),
                    cursor.getString(30),
                    cursor.getString(31),
                    cursor.getString(32),
                    cursor.getString(33),
                    cursor.getString(34),
                    cursor.getString(35),
                    cursor.getString(36),
                    cursor.getString(37),
                    cursor.getString(38),
                    cursor.getString(39),
                    cursor.getString(40),
                    cursor.getString(41),
                    cursor.getString(42),
                    cursor.getString(43),
                    cursor.getString(44),
                    cursor.getString(45),
                    cursor.getInt(46),
                    cursor.getInt(47),
                    cursor.getDouble(48),
                    cursor.getDouble(49),
                    cursor.getDouble(50),
                    cursor.getDouble(51),
                    cursor.getString(52)
            ));
        }

        return list;
    }

    public void eliminar(int idSolicitud) {
        String query = "DELETE FROM "+table+" WHERE "+col_idSolicitud+" = '"+idSolicitud+"'";
        this.dbWrite.execSQL(query);
    }
}
