/*
 * Cierre_periodoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Cierre_periodo;

public interface Cierre_periodoDao {

	void crear(Cierre_periodo cierre_periodo); 

	int actualizar(Cierre_periodo cierre_periodo); 

	Cierre_periodo consultar(Cierre_periodo cierre_periodo); 

	int eliminar(Cierre_periodo cierre_periodo); 

	List<Cierre_periodo> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean exist(Map<String,Object> param); 

}