/*
 * cierre_periodoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package contaweb.controller;


import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.event.Events;

import contaweb.exception.ContawebException;
import contaweb.modelo.bean.*;
import contaweb.framework.util.FormularioUtil;
import contaweb.framework.util.Utilidades;
import contaweb.framework.util.MensajesUtil;
import contaweb.modelo.service.Cierre_periodoService;

public class Cierre_periodoAction extends ZKWindow{

	private static Logger log = Logger.getLogger(Cierre_periodoAction.class);
	
	
	private Cierre_periodoService cierre_periodoService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxAnio;
	@View private Textbox tbxMes;
	private final String[] idsExcluyentes = {};

	
	@Override
	public void init(){
		listarCombos();
	}
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("id");
		listitem.setLabel("Id");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("anio");
		listitem.setLabel("Anio");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			buscarDatos();
		}else{
			//buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		tbxAnio.setStyle("text-transform:uppercase;background-color:white");
		tbxMes.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(tbxAnio.getText().trim().equals("")){
			tbxAnio.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxMes.getText().trim().equals("")){
			tbxMes.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...","Los campos marcados con (*) son obligatorios");
		}
		
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			cierre_periodoService.setLimit("limit 300 offset 0");
			
			List<Cierre_periodo> lista_datos = cierre_periodoService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Cierre_periodo cierre_periodo : lista_datos) {
				rowsResultado.appendChild(crearFilas(cierre_periodo, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Cierre_periodo cierre_periodo = (Cierre_periodo)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(cierre_periodo.getAnio()+""));
		fila.appendChild(new Label(cierre_periodo.getMes()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(cierre_periodo);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(cierre_periodo);
								buscarDatos();
							}
						}
					});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la información //
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if(validarForm()){
				//Cargamos los componentes //
				
				Cierre_periodo cierre_periodo = new Cierre_periodo();
				cierre_periodo.setId();
				cierre_periodo.setCodigo_empresa(empresa.getCodigo_empresa());
				cierre_periodo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				cierre_periodo.setAnio(tbxAnio.getValue());
				cierre_periodo.setMes(tbxMes.getValue());
				cierre_periodo.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				cierre_periodo.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				cierre_periodo.setCreacion_user(usuarios.getCodigo().toString());
				cierre_periodo.setUltimo_user(usuarios.getCodigo().toString());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					cierre_periodoService.crear(cierre_periodo);
					accionForm(true,"registrar");
				}else{
					int result = cierre_periodoService.actualizar(cierre_periodo);
					if(result==0){
						throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				
		MensajesUtil.mensajeInformacion("Información ..","Los datos se guardaron satisfactoriamente");
			}
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Cierre_periodo cierre_periodo = (Cierre_periodo)obj;
		try{
			tbxAnio.setValue(cierre_periodo.getAnio());
			tbxMes.setValue(cierre_periodo.getMes());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Cierre_periodo cierre_periodo = (Cierre_periodo)obj;
		try{
			int result = cierre_periodoService.eliminar(cierre_periodo);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Información se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (ContawebException e) {
			MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
		}catch(RuntimeException r){
			MensajesUtil.mensajeError(r, "", this);
		}
	}
}