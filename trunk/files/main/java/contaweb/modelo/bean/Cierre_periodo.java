/*
 * Cierre_periodo.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("cierre_periodo")
public class Cierre_periodo implements Serializable {

	/************** ATRIBUTOS **************/

	private  Integer id;
	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String anio;
	private  String mes;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  String creacion_user;
	private  String ultimo_user;

	/*** Constructor Por Defecto ***/
	public Cierre_periodo(){}


	/*** Sobre carga de Constructor ***/
	public Cierre_periodo(
		Integer id,
		String codigo_empresa,
		String codigo_sucursal,
		String anio,
		String mes,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		String creacion_user,
		String ultimo_user){
		this.id = id;
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.anio = anio;
		this.mes = mes;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setId(Integer id){
		this.id=id;
	}
	public void setCodigo_empresa(String codigo_empresa){
		this.codigo_empresa=codigo_empresa;
	}
	public void setCodigo_sucursal(String codigo_sucursal){
		this.codigo_sucursal=codigo_sucursal;
	}
	public void setAnio(String anio){
		this.anio=anio;
	}
	public void setMes(String mes){
		this.mes=mes;
	}
	public void setCreacion_date(Timestamp creacion_date){
		this.creacion_date=creacion_date;
	}
	public void setUltimo_update(Timestamp ultimo_update){
		this.ultimo_update=ultimo_update;
	}
	public void setCreacion_user(String creacion_user){
		this.creacion_user=creacion_user;
	}
	public void setUltimo_user(String ultimo_user){
		this.ultimo_user=ultimo_user;
	}
	/************** METODOS GET **************/

	public Integer getId(){
		return id;
	}
	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getAnio(){
		return anio;
	}
	public String getMes(){
		return mes;
	}
	public Timestamp getCreacion_date(){
		return creacion_date;
	}
	public Timestamp getUltimo_update(){
		return ultimo_update;
	}
	public String getCreacion_user(){
		return creacion_user;
	}
	public String getUltimo_user(){
		return ultimo_user;
	}
}