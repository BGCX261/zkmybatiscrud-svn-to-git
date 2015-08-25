/*
 * Cierre_periodoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package contaweb.modelo.service;

import java.util.List;
import java.util.Map;
import contaweb.exception.ContawebException;
import contaweb.modelo.bean.Cierre_periodo;
import contaweb.modelo.service.Cierre_periodoService;
import contaweb.modelo.dao.Cierre_periodoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cierre_periodoService")
public class Cierre_periodoService {

	@Autowired private Cierre_periodoDao cierre_periodoDao;
	private String limit;

	public void crear(Cierre_periodo cierre_periodo){ 
		try{
			if(consultar(cierre_periodo)!=null){
				throw new ContawebException("Cierre_periodo ya se encuentra en la base de datos");
			}
			cierre_periodoDao.crear(cierre_periodo);
		}catch(Exception e){
			throw new ContawebException(e.getMessage(),e);
		}
	}

	public int actualizar(Cierre_periodo cierre_periodo){ 
		try{
			return cierre_periodoDao.actualizar(cierre_periodo);
		}catch(Exception e){
			throw new ContawebException(e.getMessage(),e);
		}
	}

	public Cierre_periodo consultar(Cierre_periodo cierre_periodo){ 
		try{
			return cierre_periodoDao.consultar(cierre_periodo);
		}catch(Exception e){
			throw new ContawebException(e.getMessage(),e);
		}
	}

	public int eliminar(Cierre_periodo cierre_periodo){ 
		try{
			return cierre_periodoDao.eliminar(cierre_periodo);
		}catch(Exception e){
			throw new ContawebException(e.getMessage(),e);
		}
	}

	public List<Cierre_periodo> listar(Map<String,Object> parameters){ 
		try{
			parameters.put("limit",limit);
			return cierre_periodoDao.listar(parameters);
		}catch(Exception e){
			throw new ContawebException(e.getMessage(),e);
		}
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

	public boolean exist(Map<String,Object> parameters){ 
		return cierre_periodoDao.exist(parameters);
	}

}