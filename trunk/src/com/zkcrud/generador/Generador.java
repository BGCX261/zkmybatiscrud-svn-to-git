/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dario Perez Campillo
 */
/**
 *
 */
package com.zkcrud.generador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.zkcrud.model.Contenedor;
import com.zkcrud.model.DataDAO;


public class Generador {

	private final String nombre_programador = "Tecnologo: \tDario Perez Campillo";
	private List fields;
	private Vector columType;
	private Vector columName;
	private String project;
	private String codigo_ente = "";         
	private String cons_ente = "";
	private String table;
	private String esquema;

	public Generador(String project, List fields, String table, String esquema) {
		this.project = project;
		this.fields = fields;
		this.table = table;
		this.esquema = esquema;
	}

	public void generarCrud(DataDAO dataDAO) throws Exception {
		try {
			setData(table);
			List llaves = getLlaves(dataDAO, table, esquema);

			String bean = generarBean(project, table + "");
			String dao = generarDao(project, table);
			String service = generarService(project, table);
			String sqlMap = generarMapper(project, table, llaves);
			String action_zk = generarAction(project, table);
			String vista_zk = generarVistaZk(project, table);

			ManejoFichero manejo = new ManejoFichero();
			String direccion = System.getProperty("user.dir");

			String nameClassBean = clase(table);
			String nameClassDao = clase(table) + "Dao";
			String nameSqlMap = table + "Dao";
			String nameClassService = clase(table) + "Service";
			String nameClassAction = clase(table) + "Action";
			String nameVistaZk = table;

			manejo.crearFile(direccion, new String[] {
					"files/main/java/" + project + "/modelo", "bean" },
					nameClassBean + ".java");
			manejo.writeFile(bean);
			System.out.println("archivo " + nameClassBean
					+ ".java generado........");

			manejo.crearFile(direccion, new String[] {
					"files/main/java/" + project + "/modelo", "dao" },
					nameClassDao + ".java");
			manejo.writeFile(dao);
			System.out.println("archivo " + nameClassDao
					+ ".java generado........");


			manejo.crearFile(direccion, new String[] {
					"files/main/resources/" + project + "/modelo", "dao" },
					nameSqlMap + ".xml");
			manejo.writeFile(sqlMap);
			System.out.println("archivo " + nameSqlMap
					+ ".xml generado........");

			manejo.crearFile(direccion, new String[] {
					"files/main/java/" + project + "/modelo", "service" },
					nameClassService + ".java");
			manejo.writeFile(service);
			System.out.println("archivo " + nameClassService
					+ ".java generado........");
			

			manejo.crearFile(direccion, new String[] { "files/main/java/"
					+ project + "/controller" }, nameClassAction + ".java");
			manejo.writeFile(action_zk);
			System.out.println("archivo " + nameClassAction
					+ ".java generado........");

			manejo.crearFile(direccion,
					new String[] { "files/main/webapp/pages/" }, nameVistaZk
							+ ".zul");
			manejo.writeFile(vista_zk);
			System.out.println("archivo " + nameVistaZk
					+ ".zul generado........");

		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage(), e);
		}
	}

	public List getLlaves(DataDAO dao, Object object, String esquema) {
		String string = (String) object;
		try {
			List llaves = dao.getPrimaryKeys(esquema, string);
			return llaves;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new LinkedList();
	}

	private void setData(Object object) {
		// String string = (String) object;
		columName = new Vector();
		columType = new Vector();

		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (v.get(0).toString().equals("codigo_dane")
					|| v.get(0).toString().equals("codigo_empresa")) {
				codigo_ente = v.get(0).toString();
			}

			if (v.get(0).toString().equals("cons_sede")
					|| v.get(0).toString().equals("codigo_sucursal")) {
				cons_ente = v.get(0).toString();
			}

			columName.add(v.get(0));
			columType.add(v.get(1));
		}

	}

	private String clase(Object object) {
		String string = (String) object;
		string = string.toLowerCase();
		String str = (string.charAt(0) + "").toUpperCase();
		for (int i = 1; i < string.length(); i++) {
			str += string.charAt(i);
		}
		return str;
	}

	private String type(Object object) {
		String string = (String) object;
		if (string.toLowerCase().startsWith("varchar")) {
			return "String";
		} else if (string.toLowerCase().startsWith("text")) {
			return "String";
		} else if (string.toLowerCase().startsWith("tinytext")) {
			return "String";
		} else if (string.toLowerCase().startsWith("char")) {
			return "String";
		} else if (string.toLowerCase().startsWith("int")) {
			return "Integer";
		} else if (string.toLowerCase().startsWith("long")) {
			return "Integer";
		} else if (string.toLowerCase().startsWith("timestamp")) {
			return "Timestamp";
		}  else if (string.toLowerCase().startsWith("date")) {
			return "java.util.Date";
		}else if (string.toLowerCase().startsWith("double")) {
			return "double";
		} else if (string.toLowerCase().startsWith("bigdecimal")) {
			return "double";
		} else if (string.toLowerCase().startsWith("numeric")) {
			return "double";
		} else if (string.toLowerCase().startsWith("float8")) {
			return "double";
		} else if (string.toLowerCase().startsWith("bool")) {
			return "boolean";
		} else if (string.toLowerCase().startsWith("bytea")) {
			return "byte[]";
		}
		return (String) object;
	}

	// ////////////////////////////// Bean ///////////////////////////////////
	private String generarBean(String paquete, String bean) {

		String codigo = "/*\n";
		codigo += " * " + clase(bean) + ".java\n";
		codigo += " * \n";
		codigo += " * Generado Automaticamente .\n";
		codigo += " * " + nombre_programador + "\n";
		codigo += " */ \n";
		codigo += "package "
				+ paquete
				+ ".modelo.bean;\n\nimport java.io.Serializable;\nimport java.sql.*;"
				+ "\nimport org.apache.commons.lang.builder.ReflectionToStringBuilder;"
				+ "\nimport org.apache.ibatis.type.Alias;\n\n";
		codigo += "@Alias(\""+bean+"\")\n";
		codigo += "public class " + clase(bean)
				+ " implements Serializable {\n";
		// Atributos //
		codigo += "\n\t/************** ATRIBUTOS **************/\n";
		for (int i = 0; i < columName.size(); i++) {
			codigo += "\n\tprivate  " + type(columType.get(i)) + " "
					+ columName.get(i) + ";";
		}
		codigo += "\n";
		String constructor = "\n\t/*** Constructor Por Defecto ***/\n";
		constructor += "\tpublic " + clase(bean) + "(){}\n\n";
		constructor += "\n\t/*** Sobre carga de Constructor ***/\n";
		constructor += "\tpublic " + clase(bean) + "(";
		for (int i = 0; i < columName.size(); i++) {
			String campo = ((String) columName.get(i)).trim();
			String tipo = ((String) columType.get(i)).trim();
			constructor += "\n\t\t" + type(tipo) + " " + campo.toLowerCase()
					+ ",";
		}
		constructor = constructor.substring(0, constructor.length() - 1)
				+ "){\n";
		for (int i = 0; i < columName.size(); i++) {
			String campo = ((String) columName.get(i)).trim();
			constructor += "\t\tthis." + campo.toLowerCase() + " = "
					+ campo.toLowerCase() + ";\n";
		}
		constructor += "\t}\n";
		codigo += constructor;
		String mToString = "\t@Override\n\tpublic String toString(){\n";
		mToString += "\t\treturn ReflectionToStringBuilder.toString(this);\n";
		mToString += "\t}";
		codigo += mToString;
		codigo += "\n\t/************** METODOS SET ****************/\n";
		for (int i = 0; i < columName.size(); i++) {

			String atributo = type(columType.get(i));

			String cmp = (String) columName.get(i);
			String campo = "";
			String primerLetra = cmp.charAt(0) + "";
			primerLetra = primerLetra.toUpperCase();
			for (int k = 1; k < cmp.length(); k++) {
				campo = campo + cmp.charAt(k);
			}
			campo = primerLetra + campo;

			codigo += "\n\tpublic void set" + campo + "(" + atributo + " "
					+ cmp + "){\n\t\tthis." + cmp + "=" + cmp + ";\n\t}";
		}
		// get //
		codigo += "\n\t/************** METODOS GET **************/\n";
		for (int i = 0; i < columName.size(); i++) {

			String atributo = type(columType.get(i));

			String cmp = (String) columName.get(i);
			String campo = "";
			String primerLetra = cmp.charAt(0) + "";
			primerLetra = primerLetra.toUpperCase();
			for (int k = 1; k < cmp.length(); k++) {
				campo = campo + cmp.charAt(k);
			}
			campo = primerLetra + campo;

			codigo += "\n\tpublic " + atributo + " get" + campo + "(){\n"
					+ "\t\treturn " + cmp + ";\n\t}";
		}
		codigo += "\n}";
		String classBean = codigo;
		return classBean;
	}

	// //////////////////////////////Dao ///////////////////////////////////
	private String generarDao(String namePackage, String nameClassBean) {

		String codigo = "";

		codigo += "/*\n";
		codigo += " * " + clase(nameClassBean) + "Dao.java\n";
		codigo += " * \n";
		codigo += " * Generado Automaticamente .\n";
		codigo += " * " + nombre_programador + "\n";
		codigo += " */ \n";
		codigo += "\n";
		codigo += "package " + namePackage + ".modelo.dao;\n";
		codigo += "\n";
		codigo += "import java.util.List;\n";
		codigo += "import java.util.Map;\n";
		codigo += "import " + namePackage + ".modelo.bean."
				+ clase(nameClassBean) + ";\n\n";
		codigo += "public interface " + clase(nameClassBean) + "Dao {\n";
		codigo += "\n";
		codigo += "\tvoid crear(" + clase(nameClassBean) + " " + nameClassBean
				+ "); \n";
		codigo += "\n";
		codigo += "\tint actualizar(" + clase(nameClassBean) + " "
				+ nameClassBean + "); \n";
		codigo += "\n";
		codigo += "\t" + clase(nameClassBean) + " consultar("
				+ clase(nameClassBean) + " " + nameClassBean + "); \n";
		codigo += "\n";
		codigo += "\tint eliminar(" + clase(nameClassBean) + " "
				+ nameClassBean + "); \n";
		codigo += "\n";
		codigo += "\tList<" + clase(nameClassBean)
				+ "> listar(Map<String,Object> parameters); \n";
		codigo += "\n";
		codigo += "\tvoid setLimit(String limit); \n";
		codigo += "\n";
		codigo += "\tboolean exist(Map<String,Object> param); \n";
		codigo += "\n";
		codigo += "}";
		return codigo;
	}

	// //////////////////////////////SQLMap///////////////////////////////////
	private String generarMapper(String namePackage, String nameClassBean,
			List llaves) {

		String codigo = "";
		codigo = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		codigo += "\n";
		codigo += "<!DOCTYPE mapper\n";
		codigo += "PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n";
		codigo += "\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n";
		codigo += "\n";
		codigo += "<mapper namespace=\""+namePackage+".modelo.dao." + clase(nameClassBean) + "Dao\">\n";
		codigo += "\n";
		codigo += "\t<resultMap id=\"" + nameClassBean + "Map\" type=\""
				+ nameClassBean + "\">\n";
		for (int i = 0; i < columName.size(); i++) {
			String campos[] = { (String) columName.get(i),
					(String) columType.get(i) };
			codigo += "\t\t<result column=\"" + campos[0].trim()
					+ "\" property=\"" + campos[0].trim() + "\" />\n";
		}
		codigo += "\t</resultMap>\n";
		codigo += "\n";
		/**
		 * ***************** CREAR ******************************
		 */
		String columns = "";
		columns += "\t<insert id=\"crear\" parameterType=\"" + nameClassBean
				+ "\">\n";
		columns += "\t\tinsert into " + esquema + "." + nameClassBean + " (";
		int salto_linea = 2;
		String tab = "";
		String values = "\n\n\t\tvalues (";
		for (int i = 0; i < columName.size(); i++) {
			String campos[] = { (String) columName.get(i),
					(String) columType.get(i) };
			String name_salto_linea = "";

			if (salto_linea == 3) {
				name_salto_linea = "\n";
				salto_linea = 0;
			}
			if (i == columName.size() - 1) {
				columns += tab + campos[0].trim() + ")";
				values += tab + "#{" + campos[0].trim() + "}";
			} else {
				columns += tab + campos[0].trim() + "," + name_salto_linea;
				values += tab + "#{" + campos[0].trim() + "},"
						+ name_salto_linea;
			}
			if (salto_linea == 0) {
				tab = "\t\t";
			} else {
				tab = "";
			}
			// System.out.println("salto_linea: "+salto_linea+" valor:"+columns+values);

			salto_linea++;

		}
		values += ")\n";
		codigo += columns + values;
		codigo += "\t</insert>\n";

		/**
		 * ***************** ACTUALIZAR ******************************
		 */
		codigo += "\n";
		codigo += "\t<update id=\"actualizar\" parameterType=\""
				+ nameClassBean + "\">\n";
		codigo += "\t\tupdate " + esquema + "." + nameClassBean + " set ";
		salto_linea = 2;
		tab = "";
		for (int i = 0; i < columName.size(); i++) {
			String campos[] = { (String) columName.get(i),
					(String) columType.get(i) };
			String name_salto_linea = "";

			if (salto_linea == 2) {
				name_salto_linea = "\n";
				salto_linea = 0;
			}
			if (i == columName.size() - 1) {
				codigo += tab + campos[0].trim() + " = #{" + campos[0].trim()
						+ "}\n";
				codigo += "\t\twhere ";
				salto_linea = 2;
				for (int j = 0; j < llaves.size(); j++) {
					String llave[] = (String[]) llaves.get(j);
					String and = "";
					if (salto_linea == 2) {
						name_salto_linea = "\n";
						salto_linea = 0;
					}
					if (j > 0) {
						and += "and ";
					}
					if (j == llaves.size() - 1) {
						codigo += tab + and + llave[0] + " = #{" + llave[0]
								+ "}\n";
					} else {
						codigo += tab + and + llave[0] + " = #{" + llave[0]
								+ "}" + name_salto_linea;
					}

					if ((j + 1) != 0) {
						tab = "\t\t";
					} else {
						tab = "";
					}

					salto_linea++;
				}
			} else {
				codigo += tab + campos[0].trim() + " = #{" + campos[0].trim()
						+ "}," + name_salto_linea;
			}

			if (salto_linea == 0) {
				tab = "\t\t";
			} else {
				tab = "";
			}

			salto_linea++;
		}
		codigo += "\t</update>\n";

		/**
		 * ***************** CONSULTAR ******************************
		 */
		codigo += "\n";
		codigo += "\t<select id=\"consultar\" parameterType=\"" + nameClassBean
				+ "\" resultMap=\"" + nameClassBean + "Map\">\n";
		codigo += "\t\tselect ";
		salto_linea = 3;
		tab = "";
		for (int i = 0; i < columName.size(); i++) {
			String campos[] = { (String) columName.get(i),
					(String) columType.get(i) };
			String name_salto_linea = "";
			if (salto_linea == 4) {
				name_salto_linea = "\n";
				salto_linea = 0;
			}
			if (i == columName.size() - 1) {
				codigo += tab + campos[0].trim() + "\n";
			} else {
				codigo += tab + campos[0].trim() + "," + name_salto_linea;
			}

			if (salto_linea == 0) {
				tab = "\t\t";
			} else {
				tab = "";
			}
			salto_linea++;
		}
		codigo += "\t\tfrom " + esquema + "." + nameClassBean + " where ";
		salto_linea = 2;
		tab = "";
		for (int j = 0; j < llaves.size(); j++) {
			String llave[] = (String[]) llaves.get(j);
			String name_salto_linea = "";
			String and = "";
			if (salto_linea == 2) {
				name_salto_linea = "\n";
				salto_linea = 0;
			}
			if (j > 0) {
				and += "and ";
			}
			if (j == llaves.size() - 1) {
				codigo += tab + and + llave[0] + " = #{" + llave[0] + "}\n";
			} else {
				codigo += tab + and + llave[0] + " = #{" + llave[0] + "}"
						+ name_salto_linea;
			}

			if (salto_linea == 0) {
				tab = "\t\t";
			} else {
				tab = " ";
			}

			salto_linea++;
		}
		codigo += "\t</select>\n";

		/**
		 * ***************** ELIMINAR ******************************
		 */
		codigo += "\n";
		codigo += "\t<delete id=\"eliminar\" parameterType=\"" + nameClassBean
				+ "\">\n";
		codigo += "\t\tdelete from " + esquema + "." + nameClassBean + "\n";
		codigo += "\t\twhere ";
		salto_linea = 2;
		tab = "";
		for (int j = 0; j < llaves.size(); j++) {
			String llave[] = (String[]) llaves.get(j);
			String name_salto_linea = "";
			String and = "";
			if (salto_linea == 2) {
				name_salto_linea = "\n";
				salto_linea = 0;
			}
			if (j > 0) {
				and += "and ";
			}
			if (j == llaves.size() - 1) {
				codigo += tab + and + llave[0] + " = #{" + llave[0] + "}\n";
			} else {
				codigo += tab + and + llave[0] + " = #{" + llave[0] + "}"
						+ name_salto_linea;
			}

			if (salto_linea == 0) {
				tab = "\t\t";
			} else {
				tab = " ";
			}

			salto_linea++;
		}
		codigo += "\t</delete>\n";
		codigo += "\n";
		/**
		 * ***************** LISTAR ******************************
		 */
		codigo += "\t<select id=\"listar\" parameterType=\"java.util.Map\" resultMap=\""
				+ nameClassBean + "Map\">\n";
		codigo += "\t\tselect ";
		salto_linea = 3;
		tab = "";
		for (int i = 0; i < columName.size(); i++) {
			String campos[] = { (String) columName.get(i),
					(String) columType.get(i) };
			String name_salto_linea = "";
			if (salto_linea == 4) {
				name_salto_linea = "\n";
				salto_linea = 0;
			}
			if (i == columName.size() - 1) {
				codigo += tab + campos[0].trim() + "\n";
			} else {
				codigo += tab + campos[0].trim() + "," + name_salto_linea;
			}

			if (salto_linea == 0) {
				tab = "\t\t";
			} else {
				tab = "";
			}
			salto_linea++;
		}
		codigo += "\t\tfrom " + esquema + "." + nameClassBean + " \n";
		codigo += "\t\t<trim prefix=\"WHERE\" prefixOverrides=\"AND |OR \">\n";
		if (!codigo_ente.equals("")) {
			codigo += "\t\t\t<if test=\"" + codigo_ente + " != null\">\n";
			codigo += "\t\t\tAND " + codigo_ente + " = " + "#{" + codigo_ente
					+ "}\n";
			codigo += "\t\t\t</if>\n";
		}

		if (!cons_ente.equals("")) {
			codigo += "\t\t\t<if test=\"" + cons_ente + " != null\">\n";
			codigo += "\t\t\tAND " + cons_ente + " = " + "#{" + cons_ente
					+ "}\n";
			codigo += "\t\t\t</if>\n";
		}

		codigo += "\t\t\t<if test=\"parameter != null\">\n";
		codigo += "\t\t\tAND ${parameter} like '${value}'\n";
		codigo += "\t\t\t</if>\n";

		codigo += "\t\t</trim>\n";

		codigo += "\t\t<if test=\"limit != null\">\n";
		codigo += "\t\t${limit}\n";
		codigo += "\t\t</if>\n";
		codigo += "\t</select>\n";
		codigo += "\n";

		/**
		 * * exist
		 */
		codigo += "\t<select id=\"exist\" resultType=\"java.lang.Boolean\">\n";
		codigo += "\t\tselect count(*) != 0 AS exist ";
		codigo += "\t\tfrom " + esquema + "." + nameClassBean + " \n";
		codigo += "\t\t<trim prefix=\"WHERE\" prefixOverrides=\"AND |OR \">\n";
		if (!codigo_ente.equals("")) {
			codigo += "\t\t\t<if test=\"" + codigo_ente + " != null\">\n";
			codigo += "\t\t\tAND " + codigo_ente + " = " + "#{" + codigo_ente
					+ "}\n";
			codigo += "\t\t\t</if>\n";
		}

		if (!cons_ente.equals("")) {
			codigo += "\t\t\t<if test=\"" + cons_ente + " != null\">\n";
			codigo += "\t\t\tAND " + cons_ente + " = " + "#{" + cons_ente
					+ "}\n";
			codigo += "\t\t\t</if>\n";
		}

		codigo += "\t\t\t<if test=\"parameter != null\">\n";
		codigo += "\t\t\tAND ${parameter} like '${value}'\n";
		codigo += "\t\t\t</if>\n";

		codigo += "\t\t</trim>\n";

		codigo += "\t\t<if test=\"limit != null\">\n";
		codigo += "\t\t${limit}\n";
		codigo += "\t\t</if>\n";
		codigo += "\t</select>\n";
		codigo += "\n";

		codigo += "</mapper>\n";

		return codigo;
	}

	// //////////////////////////////Service/////////////////////////////////
	private String generarService(String namePackage, String nameClassBean) {

		String codigo = "";

		codigo += "/*\n";
		codigo += " * " + clase(nameClassBean) + "ServiceImpl.java\n";
		codigo += " * \n";
		codigo += " * Generado Automaticamente .\n";
		codigo += " * " + nombre_programador + "\n";
		codigo += " */ \n";
		codigo += "\n";
		codigo += "package " + namePackage + ".modelo.service;\n";
		codigo += "\n";
		codigo += "import java.util.List;\n";
		codigo += "import java.util.Map;\n"
				+ "import " + namePackage + ".exception."
				+ clase(namePackage) + "Exception;\nimport " + namePackage
				+ ".modelo.bean." + clase(nameClassBean) + ";\nimport "
				+ namePackage + ".modelo.service." + clase(nameClassBean)
				+ "Service;\nimport " + namePackage + ".modelo.dao."
				+ clase(nameClassBean) + "Dao;\n"
				+"import org.springframework.beans.factory.annotation.Autowired;\n"
				+"import org.springframework.stereotype.Service;"
						+ "\n\n";
		codigo += "@Service(\""+nameClassBean+"Service"+"\")\n";
		codigo += "public class " + clase(nameClassBean)
				+ "Service {\n";
		codigo += "\n";
		codigo += "\t@Autowired private " + clase(nameClassBean) + "Dao " + nameClassBean
				+ "Dao;\n";
		codigo += "\tprivate String limit;\n";
		codigo += "\n";
		
		codigo += "\tpublic void crear(" + clase(nameClassBean) + " "
				+ nameClassBean + "){ \n";
		codigo += "\t\ttry{\n";
		codigo += "\t\t\tif(consultar(" + nameClassBean + ")!=null){\n";
		codigo += "\t\t\t\tthrow new " + clase(namePackage) + "Exception(\""
				+ clase(nameClassBean)
				+ " ya se encuentra en la base de datos\");\n";
		codigo += "\t\t\t}\n";
		codigo += "\t\t\t" + nameClassBean + "Dao.crear(" + nameClassBean
				+ ");\n";
		codigo += "\t\t}catch(Exception e){\n";
		codigo += "\t\t\tthrow new " + clase(namePackage)
				+ "Exception(e.getMessage(),e);\n";
		codigo += "\t\t}\n";
		codigo += "\t}\n";
		codigo += "\n";
		
		codigo += "\tpublic int actualizar(" + clase(nameClassBean) + " "
				+ nameClassBean + "){ \n";
		codigo += "\t\ttry{\n";
		codigo += "\t\t\treturn " + nameClassBean + "Dao.actualizar("
				+ nameClassBean + ");\n";
		codigo += "\t\t}catch(Exception e){\n";
		codigo += "\t\t\tthrow new " + clase(namePackage)
				+ "Exception(e.getMessage(),e);\n";
		codigo += "\t\t}\n";
		codigo += "\t}\n";
		codigo += "\n";
		codigo += "\tpublic " + clase(nameClassBean) + " consultar("
				+ clase(nameClassBean) + " " + nameClassBean + "){ \n";
		codigo += "\t\ttry{\n";
		codigo += "\t\t\treturn " + nameClassBean + "Dao.consultar("
				+ nameClassBean + ");\n";
		codigo += "\t\t}catch(Exception e){\n";
		codigo += "\t\t\tthrow new " + clase(namePackage)
				+ "Exception(e.getMessage(),e);\n";
		codigo += "\t\t}\n";
		codigo += "\t}\n";
		codigo += "\n";
		
		codigo += "\tpublic int eliminar(" + clase(nameClassBean) + " "
				+ nameClassBean + "){ \n";
		codigo += "\t\ttry{\n";
		codigo += "\t\t\treturn " + nameClassBean + "Dao.eliminar("
				+ nameClassBean + ");\n";
		codigo += "\t\t}catch(Exception e){\n";
		codigo += "\t\t\tthrow new " + clase(namePackage)
				+ "Exception(e.getMessage(),e);\n";
		codigo += "\t\t}\n";
		codigo += "\t}\n";
		codigo += "\n";
		codigo += "\tpublic List<" + clase(nameClassBean)
				+ "> listar(Map<String,Object> parameters){ \n";
		codigo += "\t\ttry{\n";
		codigo += "\t\t\tparameters.put(\"limit\",limit);\n";
		codigo += "\t\t\treturn " + nameClassBean + "Dao.listar(parameters);\n";
		codigo += "\t\t}catch(Exception e){\n";
		codigo += "\t\t\tthrow new " + clase(namePackage)
				+ "Exception(e.getMessage(),e);\n";
		codigo += "\t\t}\n";
		codigo += "\t}\n";
		codigo += "\n";

		codigo += "\tpublic void setLimit(String limit){\n";
		codigo += "\t\tthis.limit = limit;\n";
		codigo += "\t}\n";
		codigo += "\n";
		codigo += "\tpublic boolean exist(Map<String,Object> parameters){ \n";
		codigo += "\t\treturn " + nameClassBean
				+ "Dao.exist(parameters);\n";
		codigo += "\t}\n";
		codigo += "\n";
		codigo += "}";
		return codigo;
	}

	// //////////////////////////////Action///////////////////////////////////
	private String generarAction(String paquete, String bean) {

		String codigo = "/*\n";
		codigo += " * " + bean + "Action.java\n";
		codigo += " * \n";
		codigo += " * Generado Automaticamente .\n";
		codigo += " * " + nombre_programador + "\n";
		codigo += " */ \n";
		codigo += "package " + paquete + ".controller;\n";
		codigo += "\n";
		codigo += "\nimport java.sql.Timestamp;";
		codigo += "\nimport java.util.Collection;";
		codigo += "\nimport java.util.Date;";
		codigo += "\nimport java.util.GregorianCalendar;";
		codigo += "\nimport java.util.HashMap;";
		codigo += "\nimport java.util.List;";
		codigo += "\nimport java.util.Map;";
		codigo += "\n";
		codigo += "\nimport javax.servlet.ServletContext;";
		codigo += "\nimport javax.servlet.http.HttpServletRequest;";
		codigo += "\n";
		codigo += "\nimport org.apache.log4j.Logger;";
		codigo += "\nimport org.zkoss.zk.ui.Component;";
		codigo += "\nimport org.zkoss.zk.ui.Executions;";
		codigo += "\nimport org.zkoss.zk.ui.event.Event;";
		codigo += "\nimport org.zkoss.zk.ui.event.EventListener;";
		codigo += "\nimport org.zkoss.zk.ui.ext.AfterCompose;";
		codigo += "\nimport org.zkoss.zul.Borderlayout;";
		codigo += "\nimport org.zkoss.zul.Checkbox;";
		codigo += "\nimport org.zkoss.zul.Combobox;";
		codigo += "\nimport org.zkoss.zul.Datebox;";
		codigo += "\nimport org.zkoss.zul.Doublebox;";
		codigo += "\nimport org.zkoss.zul.Grid;";
		codigo += "\nimport org.zkoss.zul.Groupbox;";
		codigo += "\nimport org.zkoss.zul.Hbox;";
		codigo += "\nimport org.zkoss.zul.Image;";
		codigo += "\nimport org.zkoss.zul.Intbox;";
		codigo += "\nimport org.zkoss.zul.Label;";
		codigo += "\nimport org.zkoss.zul.Listbox;";
		codigo += "\nimport org.zkoss.zul.Listitem;";
		codigo += "\nimport org.zkoss.zul.Messagebox;";
		codigo += "\nimport org.zkoss.zul.Radio;";
		codigo += "\nimport org.zkoss.zul.Radiogroup;";
		codigo += "\nimport org.zkoss.zul.Row;";
		codigo += "\nimport org.zkoss.zul.Rows;";
		codigo += "\nimport org.zkoss.zul.Space;";
		codigo += "\nimport org.zkoss.zul.Textbox;";
		codigo += "\nimport org.zkoss.zk.ui.event.Events;";
		codigo += "\n";
		codigo += "\nimport " + paquete + ".exception." + clase(paquete)
				+ "Exception;";
		codigo += "\nimport " + paquete + ".modelo.bean.*;";
		if (!paquete.equals("healthmanager")) {
			codigo += "\nimport " + paquete + ".framework.util.FormularioUtil;";
			codigo += "\nimport " + paquete + ".framework.util.Utilidades;";
			codigo += "\nimport " + paquete + ".framework.util.MensajesUtil;";
		} else {
			codigo += "\nimport com.framework.util.FormularioUtil;";
			codigo += "\nimport com.framework.util.Utilidades;";
			codigo += "\nimport com.framework.util.MensajesUtil;";
		}
		codigo += "\nimport "+paquete+".modelo.service."+clase(bean)+"Service;";
		codigo += "\n";
		codigo += "\npublic class " + clase(bean) + "Action extends ZKWindow{";// comienza
																				// la
																				// clase
		codigo += "\n";
		codigo += "\n\tprivate static Logger log = Logger.getLogger("
				+ clase(bean) + "Action.class);";
		codigo += "\n\t";
		codigo += "\n\t";
		codigo += "\n\tprivate "+clase(bean)+"Service "+bean+"Service;";
		codigo += "\n\t";
		codigo += "\n\t//Componentes //";
		codigo += "\n\t@View private Listbox lbxParameter;";
		codigo += "\n\t@View private Textbox tbxValue;";
		codigo += "\n\t@View private Textbox tbxAccion;";
		codigo += "\n\t@View private Borderlayout groupboxEditar;";
		codigo += "\n\t@View private Groupbox groupboxConsulta;";
		codigo += "\n\t@View private Rows rowsResultado;";
		codigo += "\n\t@View private Grid gridResultado;";
		codigo += "\n\t";
		// Aqui colocamos los componentes de la vista de registro //
		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (!v.get(0).toString().equals("codigo_dane")
					&& !v.get(0).toString().equals("codigo_empresa")
					&& !v.get(0).toString().equals("cons_sede")
					&& !v.get(0).toString().equals("codigo_sucursal")
					&& !v.get(0).toString().equals("creacion_date")
					&& !v.get(0).toString().equals("ultimo_update")
					&& !v.get(0).toString().equals("creacion_user")
					&& !v.get(0).toString().equals("ultimo_user")) {
				if (v.get(5).toString().equals("true")) {
					if (v.get(2).toString().equals("Textbox")
							|| v.get(2).toString().equals("Textbox_area")) {
						codigo += "\n\t@View private Textbox tbx"
								+ clase(v.get(0).toString()) + ";";
					} else if (v.get(2).toString().equals("Intbox")) {
						codigo += "\n\t@View private Intbox ibx"
								+ clase(v.get(0).toString()) + ";";
					} else if (v.get(2).toString().equals("Doublebox")) {
						codigo += "\n\t@View private Doublebox dbx"
								+ clase(v.get(0).toString()) + ";";
					} else if (v.get(2).toString().equals("Listbox")) {
						codigo += "\n\t@View private Listbox lbx"
								+ clase(v.get(0).toString()) + ";";
					} else if (v.get(2).toString().equals("Checkbox")) {
						codigo += "\n\t@View private Checkbox chb"
								+ clase(v.get(0).toString()) + ";";
					} else if (v.get(2).toString().equals("Datebox")) {
						codigo += "\n\t@View private Datebox dtbx"
								+ clase(v.get(0).toString()) + ";";
					} else if (v.get(2).toString().equals("Radio")) {
						codigo += "\n\t@View private Radiogroup rdb"
								+ clase(v.get(0).toString()) + ";";
					}
				}

			}
		}
		codigo += "\n\tprivate final String[] idsExcluyentes = {};\n";
		// init
		codigo += "\n\t";
		codigo += "\n\t@Override";
		codigo += "\n\tpublic void init(){";
		codigo += "\n\t\tlistarCombos();";
		codigo += "\n\t}";

		// Listar combos //
		codigo += "\n\t";
		codigo += "\n\tpublic void listarCombos(){";
		codigo += "\n\t\tlistarParameter();";
		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (!v.get(0).toString().equals("codigo_dane")
					&& !v.get(0).toString().equals("codigo_empresa")
					&& !v.get(0).toString().equals("cons_sede")
					&& !v.get(0).toString().equals("codigo_sucursal")
					&& !v.get(0).toString().equals("creacion_date")
					&& !v.get(0).toString().equals("ultimo_update")
					&& !v.get(0).toString().equals("creacion_user")
					&& !v.get(0).toString().equals("ultimo_user")) {
				if (v.get(5).toString().equals("true")) {
					if (v.get(2).toString().equals("Listbox")) {
						codigo += "\n\t\tUtilidades.listarElementoListbox(lbx"
								+ clase(v.get(0).toString())
								+ ",true,getServiceLocator());";
					}
				}
			}
		}
		codigo += "\n\t}";// Fin listarCombos()

		// Listar parametros //
		codigo += "\n\t";
		codigo += "\n\tpublic void listarParameter() {";
		codigo += "\n\t\tlbxParameter.getChildren().clear();";
		codigo += "\n\t\t";
		int count = 1;
		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (!v.get(0).toString().equals("codigo_dane")
					&& !v.get(0).toString().equals("codigo_empresa")
					&& !v.get(0).toString().equals("cons_sede")
					&& !v.get(0).toString().equals("codigo_sucursal")
					&& !v.get(0).toString().equals("creacion_date")
					&& !v.get(0).toString().equals("ultimo_update")
					&& !v.get(0).toString().equals("creacion_user")
					&& !v.get(0).toString().equals("ultimo_user")) {
				if (count < 3) {
					codigo += "\n\t\t" + (count == 1 ? "Listitem " : "")
							+ "listitem = new Listitem();";
					codigo += "\n\t\tlistitem.setValue(\""
							+ v.get(0).toString() + "\");";
					codigo += "\n\t\tlistitem.setLabel(\""
							+ clase(v.get(0).toString()) + "\");";
					codigo += "\n\t\tlbxParameter.appendChild(listitem);";
					codigo += "\n\t\t";
					count++;
				} else {
					i = fields.size();
				}
			}
		}
		codigo += "\n\t\tif (lbxParameter.getItemCount() > 0) {";
		codigo += "\n\t\t\tlbxParameter.setSelectedIndex(0);";
		codigo += "\n\t\t}";
		codigo += "\n\t}";// Fin listarParameter() //

		// Accion del formulario //
		codigo += "\n\t";
		codigo += "\n\t//Accion del formulario si es nuevo o consultar //";
		codigo += "\n\tpublic void accionForm(boolean sw,String accion)throws Exception{";
		codigo += "\n\t\tgroupboxConsulta.setVisible(!sw);";
		codigo += "\n\t\tgroupboxEditar.setVisible(sw);";
		codigo += "\n\t\t";
		codigo += "\n\t\tif(!accion.equalsIgnoreCase(\"registrar\")){";
		codigo += "\n\t\t\tbuscarDatos();";
		codigo += "\n\t\t}else{";
		codigo += "\n\t\t\t//buscarDatos();";
		codigo += "\n\t\t\tlimpiarDatos();";
		codigo += "\n\t\t}";
		codigo += "\n\t\ttbxAccion.setValue(accion);";
		codigo += "\n\t}";// Fin accionForm();
		
		// Limpiar campos del formulario //
		codigo += "\n\t";
		codigo += "\n\t// Limpiamos los campos del formulario //";
		codigo += "\n\tpublic void limpiarDatos()throws Exception{";
		codigo += "\n\t\tFormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);";
		
		codigo += "\n\t}";// Fin limpiarDatos();

		// Validar campos del formulario //
		codigo += "\n\t";
		codigo += "\n\t//Metodo para validar campos del formulario //";
		codigo += "\n\tpublic boolean validarForm()throws Exception{";
		codigo += "\n\t\t";
		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (!v.get(0).toString().equals("codigo_dane")
					&& !v.get(0).toString().equals("codigo_empresa")
					&& !v.get(0).toString().equals("cons_sede")
					&& !v.get(0).toString().equals("codigo_sucursal")
					&& !v.get(0).toString().equals("creacion_date")
					&& !v.get(0).toString().equals("ultimo_update")
					&& !v.get(0).toString().equals("creacion_user")
					&& !v.get(0).toString().equals("ultimo_user")) {
				if (v.get(3).toString().equals("true")) {
					if (v.get(2).toString().equals("Textbox")
							|| v.get(2).toString().equals("Textbox_area")) {
						codigo += "\n\t\ttbx"
								+ clase(v.get(0).toString())
								+ ".setStyle(\"text-transform:uppercase;background-color:white\");";
					} else if (v.get(2).toString().equals("Intbox")) {
						codigo += "\n\t\tibx" + clase(v.get(0).toString())
								+ ".setStyle(\"background-color:white\");";
					} else if (v.get(2).toString().equals("Doublebox")) {
						codigo += "\n\t\tdbx" + clase(v.get(0).toString())
								+ ".setStyle(\"background-color:white\");";
					} else if (v.get(2).toString().equals("Listbox")) {
						codigo += "\n\t\tlbx" + clase(v.get(0).toString())
								+ ".setStyle(\"background-color:white\");";
					} else if (v.get(2).toString().equals("Datebox")) {
						codigo += "\n\t\tdtbx" + clase(v.get(0).toString())
								+ ".setStyle(\"background-color:white\");";
					}
				}
			}
		}
		codigo += "\n\t\t";
		codigo += "\n\t\tboolean valida = true;";
		codigo += "\n\t\t";
		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (!v.get(0).toString().equals("codigo_dane")
					&& !v.get(0).toString().equals("codigo_empresa")
					&& !v.get(0).toString().equals("cons_sede")
					&& !v.get(0).toString().equals("codigo_sucursal")
					&& !v.get(0).toString().equals("creacion_date")
					&& !v.get(0).toString().equals("ultimo_update")
					&& !v.get(0).toString().equals("creacion_user")
					&& !v.get(0).toString().equals("ultimo_user")) {
				if (v.get(3).toString().equals("true")) {
					if (v.get(2).toString().equals("Textbox")
							|| v.get(2).toString().equals("Textbox_area")) {
						codigo += "\n\t\tif(tbx" + clase(v.get(0).toString())
								+ ".getText().trim().equals(\"\")){";
						codigo += "\n\t\t\ttbx"
								+ clase(v.get(0).toString())
								+ ".setStyle(\"text-transform:uppercase;background-color:#F6BBBE\");";
						codigo += "\n\t\t\tvalida = false;";
						codigo += "\n\t\t}";
					} else if (v.get(2).toString().equals("Intbox")) {
						codigo += "\n\t\tif(ibx" + clase(v.get(0).toString())
								+ ".getText().trim().equals(\"\")){";
						codigo += "\n\t\t\tibx" + clase(v.get(0).toString())
								+ ".setStyle(\"background-color:#F6BBBE\");";
						codigo += "\n\t\t\tvalida = false;";
						codigo += "\n\t\t}";
					} else if (v.get(2).toString().equals("Doublebox")) {
						codigo += "\n\t\tif(dbx" + clase(v.get(0).toString())
								+ ".getText().trim().equals(\"\")){";
						codigo += "\n\t\t\tdbx" + clase(v.get(0).toString())
								+ ".setStyle(\"background-color:#F6BBBE\");";
						codigo += "\n\t\t\tvalida = false;";
						codigo += "\n\t\t}";
					} else if (v.get(2).toString().equals("Listbox")) {
						codigo += "\n\t\tif(lbx"
								+ clase(v.get(0).toString())
								+ ".getSelectedItem().getValue().toString().trim().equals(\"\")){";
						codigo += "\n\t\t\tlbx" + clase(v.get(0).toString())
								+ ".setStyle(\"background-color:#F6BBBE\");";
						codigo += "\n\t\t\tvalida = false;";
						codigo += "\n\t\t}";

					} else if (v.get(2).toString().equals("Datebox")) {
						codigo += "\n\t\tif(dtbx" + clase(v.get(0).toString())
								+ ".getText().trim().equals(\"\")){";
						codigo += "\n\t\t\tdtbx" + clase(v.get(0).toString())
								+ ".setStyle(\"background-color:#F6BBBE\");";
						codigo += "\n\t\t\tvalida = false;";
						codigo += "\n\t\t}";
					}
				}
			}
		}
		codigo += "\n\t\t";
		codigo += "\n\t\tif(!valida){";
		if (paquete.equals("servinotas")) {
			codigo += "\n\t\t\tMensajesUtil.mensajeAlerta(usuarios.getNom_usr()+\" recuerde que...\","
					+ "\"Los campos marcados con (*) son obligatorios\");";
		} else {
			codigo += "\n\t\t\tMensajesUtil.mensajeAlerta(usuarios.getNombres()+\" recuerde que...\","
					+ "\"Los campos marcados con (*) son obligatorios\");";
		}

		codigo += "\n\t\t}";
		codigo += "\n\t\t";
		codigo += "\n\t\treturn valida;";
		codigo += "\n\t}";// Fin validarForm();

		// Buscar //
		codigo += "\n\t//Metodo para buscar //";
		codigo += "\n\tpublic void buscarDatos()throws Exception{";
		codigo += "\n\t\ttry {";
		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (v.get(0).toString().equals("codigo_dane")) {
				codigo += "\n\t\t\tString codigo_dane = institucion.getCodigo_dane();";
			}
			if (v.get(0).toString().equals("cons_sede")) {
				codigo += "\n\t\t\tString cons_sede = sedes.getCons_sede();";
			}
		}
		codigo += "\n\t\t\tString parameter = lbxParameter.getSelectedItem().getValue().toString();";
		codigo += "\n\t\t\tString value = tbxValue.getValue().toUpperCase().trim();";
		codigo += "\n\t\t\t";
		codigo += "\n\t\t\tMap<String,Object> parameters = new HashMap<String,Object>();";
		for (int i = 0; i < fields.size(); i++) {
			Vector<Object> v = (Vector<Object>) fields.get(i);
			if (v.get(0).toString().equals("codigo_dane")) {
				codigo += "\n\t\t\tparameters.put(\"codigo_dane\", codigo_dane);";
			}
			if (v.get(0).toString().equals("cons_sede")) {
				codigo += "\n\t\t\tparameters.put(\"cons_sede\", cons_sede);";
			}
			if (v.get(0).toString().equals("codigo_empresa")) {
				codigo += "\n\t\t\tparameters.put(\"codigo_empresa\", codigo_empresa);";
			}
			if (v.get(0).toString().equals("codigo_sucursal")) {
				codigo += "\n\t\t\tparameters.put(\"codigo_sucursal\", codigo_sucursal);";
			}
		}
		codigo += "\n\t\t\tparameters.put(\"parameter\", parameter);";
		codigo += "\n\t\t\tparameters.put(\"value\", \"%\"+value+\"%\");";
		codigo += "\n\t\t\t";
		codigo += "\n\t\t\t"+bean+"Service.setLimit(\"limit 300 offset 0\");";
		codigo += "\n\t\t\t";
		codigo += "\n\t\t\tList<" + clase(bean)
				+ "> lista_datos = "+bean+"Service.listar(parameters);";
		codigo += "\n\t\t\trowsResultado.getChildren().clear();";
		codigo += "\n\t\t\t";
		codigo += "\n\t\t\tfor (" + clase(bean) + " " + bean
				+ " : lista_datos) {";
		codigo += "\n\t\t\t\trowsResultado.appendChild(crearFilas(" + bean
				+ ", this));";
		codigo += "\n\t\t\t}";
		codigo += "\n\t\t\t";
		codigo += "\n\t\t\tgridResultado.setVisible(true);";
		codigo += "\n\t\t\tgridResultado.setMold(\"paging\");";
		codigo += "\n\t\t\tgridResultado.setPageSize(20);";
		codigo += "\n\t\t\t";
		codigo += "\n\t\t\tgridResultado.applyProperties();";
		codigo += "\n\t\t\tgridResultado.invalidate();";
		codigo += "\n\t\t\t";
		codigo += "\n\t\t} catch (Exception e) {";
		codigo += "\n\t\t\tMensajesUtil.mensajeError(e, \"\", this);";
		codigo += "\n\t\t}";
		codigo += "\n\t}";// Fin buscarDatos();

		// Metodo crear filas//
		codigo += "\n\t";
		codigo += "\n\tpublic Row crearFilas(Object objeto, Component componente)throws Exception{";
		codigo += "\n\t\tRow fila = new Row();";
		codigo += "\n\t\t";
		codigo += "\n\t\tfinal " + clase(bean) + " " + bean + " = ("
				+ clase(bean) + ")objeto;";
		codigo += "\n\t\t";
		codigo += "\n\t\tHbox hbox = new Hbox();";
		codigo += "\n\t\tSpace space = new Space();";
		codigo += "\n\t\t";
		codigo += "\n\t\tfila.setStyle(\"text-align: justify;nowrap:nowrap\");";
		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (!v.get(0).toString().equals("codigo_dane")
					&& !v.get(0).toString().equals("codigo_empresa")
					&& !v.get(0).toString().equals("cons_sede")
					&& !v.get(0).toString().equals("codigo_sucursal")
					&& !v.get(0).toString().equals("creacion_date")
					&& !v.get(0).toString().equals("ultimo_update")
					&& !v.get(0).toString().equals("creacion_user")
					&& !v.get(0).toString().equals("ultimo_user")) {
				if (v.get(4).toString().equals("true")) {
					codigo += "\n\t\tfila.appendChild(new Label(" + bean
							+ ".get" + clase(v.get(0).toString())
							+ "()+\"\"));";
				}
			}
		}
		codigo += "\n\t";
		codigo += "\n\t\thbox.appendChild(space);";
		codigo += "\n\t\t";
		codigo += "\n\t\tImage img = new Image();";
		codigo += "\n\t\timg.setSrc(\"/images/editar.gif\");";
		codigo += "\n\t\timg.setTooltiptext(\"Editar\");";
		codigo += "\n\t\timg.setStyle(\"cursor: pointer\");";
		codigo += "\n\t\timg.addEventListener(Events.ON_CLICK, new EventListener<Event>() {";
		codigo += "\n\t\t\t@Override";
		codigo += "\n\t\t\tpublic void onEvent(Event arg0) throws Exception {";
		codigo += "\n\t\t\t\tmostrarDatos(" + bean + ");";
		codigo += "\n\t\t\t}";
		codigo += "\n\t\t});";
		codigo += "\n\t\thbox.appendChild(img);";
		codigo += "\n\t\t";
		codigo += "\n\t\timg = new Image();";
		codigo += "\n\t\timg.setSrc(\"/images/borrar.gif\");";
		codigo += "\n\t\timg.setTooltiptext(\"Eliminar\");";
		codigo += "\n\t\timg.setStyle(\"cursor: pointer\");";
		codigo += "\n\t\timg.addEventListener(Events.ON_CLICK, new EventListener<Event>() {";
		codigo += "\n\t\t\t@Override";
		codigo += "\n\t\t\tpublic void onEvent(Event arg0) throws Exception {";
		codigo += "\n\t\t\t\tMessagebox.show(\"Esta seguro que desea eliminar este registro? \",";
		codigo += "\n\t\t\t\t\t\"Eliminar Registro\", Messagebox.YES + Messagebox.NO,";
		codigo += "\n\t\t\t\t\tMessagebox.QUESTION,";
		codigo += "\n\t\t\t\t\tnew org.zkoss.zk.ui.event.EventListener<Event>() {";
		codigo += "\n\t\t\t\t\t\tpublic void onEvent(Event event) throws Exception {";
		codigo += "\n\t\t\t\t\t\t\tif (\"onYes\".equals(event.getName())) {";
		codigo += "\n\t\t\t\t\t\t\t\t// do the thing";
		codigo += "\n\t\t\t\t\t\t\t\teliminarDatos(" + bean + ");";
		codigo += "\n\t\t\t\t\t\t\t\tbuscarDatos();";
		codigo += "\n\t\t\t\t\t\t\t}";
		codigo += "\n\t\t\t\t\t\t}";
		codigo += "\n\t\t\t\t\t});";
		codigo += "\n\t\t\t}";
		codigo += "\n\t\t});";
		codigo += "\n\t\thbox.appendChild(space);";
		codigo += "\n\t\thbox.appendChild(img);";
		codigo += "\n\t\t";
		codigo += "\n\t\tfila.appendChild(hbox);";
		codigo += "\n\t\t";
		codigo += "\n\t\treturn fila;";
		codigo += "\n\t}";// Fin crearFilas();

		// Guardar información //
		codigo += "\n\t";
		codigo += "\n\t//Metodo para guardar la información //";
		codigo += "\n\tpublic void guardarDatos()throws Exception{";
		codigo += "\n\t\ttry {";
		codigo += "\n\t\t\tFormularioUtil.setUpperCase(groupboxEditar);";
		codigo += "\n\t\t\tif(validarForm()){";
		codigo += "\n\t\t\t\t//Cargamos los componentes //";
		codigo += "\n\t\t\t\t";
		codigo += "\n\t\t\t\t" + clase(bean) + " " + bean + " = new "
				+ clase(bean) + "();";
		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (v.get(0).toString().equals("codigo_dane")) {
				codigo += "\n\t\t\t\t" + bean
						+ ".setCodigo_dane(institucion.getCodigo_dane());";
			} else if (v.get(0).toString().equals("codigo_empresa")) {
				codigo += "\n\t\t\t\t" + bean
						+ ".setCodigo_empresa(empresa.getCodigo_empresa());";
			} else if (v.get(0).toString().equals("cons_sede")) {
				codigo += "\n\t\t\t\t" + bean
						+ ".setCons_sede(sedes.getCons_sede());";
			} else if (v.get(0).toString().equals("codigo_sucursal")) {
				codigo += "\n\t\t\t\t" + bean
						+ ".setCodigo_sucursal(sucursal.getCodigo_sucursal());";
			} else if (v.get(0).toString().equals("creacion_date")) {
				codigo += "\n\t\t\t\t"
						+ bean
						+ ".setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));";
			} else if (v.get(0).toString().equals("ultimo_update")) {
				codigo += "\n\t\t\t\t"
						+ bean
						+ ".setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));";
			} else if (v.get(0).toString().equals("creacion_user")) {
				if (paquete.equals("servinotas")) {
					codigo += "\n\t\t\t\t" + bean
							+ ".setCreacion_user(usuarios.getCodigo());";
				} else {
					codigo += "\n\t\t\t\t"
							+ bean
							+ ".setCreacion_user(usuarios.getCodigo().toString());";
				}

			} else if (v.get(0).toString().equals("ultimo_user")) {
				if (paquete.equals("servinotas")) {
					codigo += "\n\t\t\t\t" + bean
							+ ".setUltimo_user(usuarios.getCodigo());";
				} else {
					codigo += "\n\t\t\t\t"
							+ bean
							+ ".setUltimo_user(usuarios.getCodigo().toString());";
				}
			} else {
				if (v.get(5).toString().equals("true")) {
					if (v.get(2).toString().equals("Textbox")
							|| v.get(2).toString().equals("Textbox_area")) {
						codigo += "\n\t\t\t\t" + bean + ".set"
								+ clase(v.get(0).toString()) + "(tbx"
								+ clase(v.get(0).toString()) + ".getValue());";
					} else if (v.get(2).toString().equals("Intbox")) {
						codigo += "\n\t\t\t\t" + bean + ".set"
								+ clase(v.get(0).toString()) + "((ibx"
								+ clase(v.get(0).toString())
								+ ".getValue()!=null?ibx"
								+ clase(v.get(0).toString())
								+ ".getValue():0));";
					} else if (v.get(2).toString().equals("Doublebox")) {
						codigo += "\n\t\t\t\t" + bean + ".set"
								+ clase(v.get(0).toString()) + "((dbx"
								+ clase(v.get(0).toString())
								+ ".getValue()!=null?dbx"
								+ clase(v.get(0).toString())
								+ ".getValue():0.00));";
					} else if (v.get(2).toString().equals("Listbox")) {
						codigo += "\n\t\t\t\t" + bean + ".set"
								+ clase(v.get(0).toString()) + "(lbx"
								+ clase(v.get(0).toString())
								+ ".getSelectedItem().getValue().toString());";
					} else if (v.get(2).toString().equals("Checkbox")) {
						codigo += "\n\t\t\t\t" + bean + ".set"
								+ clase(v.get(0).toString()) + "(chb"
								+ clase(v.get(0).toString())
								+ ".isChecked()?\"S\":\"N\");";
					} else if (v.get(2).toString().equals("Radio")) {
						codigo += "\n\t\t\t\t" + bean + ".set"
								+ clase(v.get(0).toString()) + "(rdb"
								+ clase(v.get(0).toString())
								+ ".getSelectedItem().getValue().toString());";
					} else if (v.get(2).toString().equals("Datebox")) {
						codigo += "\n\t\t\t\t" + bean + ".set"
								+ clase(v.get(0).toString())
								+ "(new Timestamp(dtbx"
								+ clase(v.get(0).toString())
								+ ".getValue().getTime()));";
					}
				} else {
					codigo += "\n\t\t\t\t" + bean + ".set"
							+ clase(v.get(0).toString()) + "();";
				}
			}
		}

		codigo += "\n\t\t\t\t";
		codigo += "\n\t\t\t\tif(tbxAccion.getText().equalsIgnoreCase(\"registrar\")){";
		codigo += "\n\t\t\t\t\t"+bean+"Service.crear(" + bean + ");";
		codigo += "\n\t\t\t\t\taccionForm(true,\"registrar\");";
		codigo += "\n\t\t\t\t}else{";
		codigo += "\n\t\t\t\t\tint result = "+bean+"Service.actualizar(" + bean + ");";
		codigo += "\n\t\t\t\t\tif(result==0){";
		codigo += "\n\t\t\t\t\t\tthrow new Exception(\"Lo sentimos pero los datos a modificar no se encuentran en base de datos\");";
		codigo += "\n\t\t\t\t\t}";
		codigo += "\n\t\t\t\t}";
		codigo += "\n\t\t\t\t";
		codigo += "\n\t\tMensajesUtil.mensajeInformacion(\"Información ..\","
				+ "\"Los datos se guardaron satisfactoriamente\");";
		codigo += "\n\t\t\t}";
		codigo += "\n\t\t\t";
		codigo += "\n\t\t}catch (Exception e) {";
		codigo += "\n\t\t\tMensajesUtil.mensajeError(e, \"\", this);";
		codigo += "\n\t\t}";
		codigo += "\n\t\t";
		codigo += "\n\t}";// Fin guardarDatos();

		// Mostar datos //
		codigo += "\n\t";
		codigo += "\n\t//Metodo para colocar los datos del objeto que se consulta en la vista //";
		codigo += "\n\tpublic void mostrarDatos(Object obj)throws Exception{";
		codigo += "\n\t\t" + clase(bean) + " " + bean + " = (" + clase(bean)
				+ ")obj;";
		codigo += "\n\t\ttry{";
		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);
			if (!v.get(0).toString().equals("codigo_dane")
					&& !v.get(0).toString().equals("codigo_empresa")
					&& !v.get(0).toString().equals("cons_sede")
					&& !v.get(0).toString().equals("codigo_sucursal")
					&& !v.get(0).toString().equals("creacion_date")
					&& !v.get(0).toString().equals("ultimo_update")
					&& !v.get(0).toString().equals("creacion_user")
					&& !v.get(0).toString().equals("ultimo_user")) {
				if (v.get(5).toString().equals("true")) {
					if (v.get(2).toString().equals("Textbox")
							|| v.get(2).toString().equals("Textbox_area")) {
						codigo += "\n\t\t\ttbx" + clase(v.get(0).toString())
								+ ".setValue(" + bean + ".get"
								+ clase(v.get(0).toString()) + "());";
					} else if (v.get(2).toString().equals("Intbox")) {
						codigo += "\n\t\t\tibx" + clase(v.get(0).toString())
								+ ".setValue(" + bean + ".get"
								+ clase(v.get(0).toString()) + "());";
					} else if (v.get(2).toString().equals("Doublebox")) {
						codigo += "\n\t\t\tdbx" + clase(v.get(0).toString())
								+ ".setValue(" + bean + ".get"
								+ clase(v.get(0).toString()) + "());";
					} else if (v.get(2).toString().equals("Listbox")) {
						codigo += "\n\t\t\tfor(int i=0;i<lbx"
								+ clase(v.get(0).toString())
								+ ".getItemCount();i++){";
						codigo += "\n\t\t\t\tListitem listitem = lbx"
								+ clase(v.get(0).toString())
								+ ".getItemAtIndex(i);";
						codigo += "\n\t\t\t\tif(listitem.getValue().toString().equals("
								+ bean
								+ ".get"
								+ clase(v.get(0).toString())
								+ "())){";
						codigo += "\n\t\t\t\t\tlistitem.setSelected(true);";
						codigo += "\n\t\t\t\t\ti = lbx"
								+ clase(v.get(0).toString())
								+ ".getItemCount();";
						codigo += "\n\t\t\t\t}";
						codigo += "\n\t\t\t}";
					} else if (v.get(2).toString().equals("Checkbox")) {
						codigo += "\n\t\t\tchb" + clase(v.get(0).toString())
								+ ".setChecked(" + bean + ".get"
								+ clase(v.get(0).toString())
								+ "().equals(\"S\")?true:false);";
					} else if (v.get(2).toString().equals("Radio")) {
						codigo += "\n\t\t\tUtilidades.seleccionarRadio(rdb"
								+ clase(v.get(0).toString()) + ", " + bean
								+ ".get" + clase(v.get(0).toString()) + "());";
					} else if (v.get(2).toString().equals("Datebox")) {
						codigo += "\n\t\t\tdtbx" + clase(v.get(0).toString())
								+ ".setValue(" + bean + ".get"
								+ clase(v.get(0).toString()) + "());";
					}
				}
			}
		}
		codigo += "\n\t\t\t";
		codigo += "\n\t\t\t//Mostramos la vista //";
		codigo += "\n\t\t\ttbxAccion.setText(\"modificar\");";
		codigo += "\n\t\t\taccionForm(true, tbxAccion.getText());";

		codigo += "\n\t\t}catch (Exception e) {";
		codigo += "\n\t\t\tMensajesUtil.mensajeError(e, \"\", this);";
		codigo += "\n\t\t}";
		codigo += "\n\t}";// Fin mostrarDatos();

		// Metodo Eliminar //
		codigo += "\n\t";
		codigo += "\n\tpublic void eliminarDatos(Object obj)throws Exception{";
		codigo += "\n\t\t" + clase(bean) + " " + bean + " = (" + clase(bean)
				+ ")obj;";
		codigo += "\n\t\ttry{";
		codigo += "\n\t\t\tint result = "+bean+"Service.eliminar(" + bean + ");";
		codigo += "\n\t\t\tif(result==0){";
		codigo += "\n\t\t\t\tthrow new Exception(\"Lo sentimos pero los datos a eliminar no se encuentran en base de datos\");";
		codigo += "\n\t\t\t}";
		codigo += "\n\t\t\t";
		codigo += "\n\t\t\tMessagebox.show(\"Información se eliminó satisfactoriamente !!\",";
		codigo += "\n\t\t\t\t\"Information\", Messagebox.OK, Messagebox.INFORMATION);";
		codigo += "\n\t\t}catch (" + clase(paquete) + "Exception e) {";
		codigo += "\n\t\t\tMensajesUtil.mensajeError(e, "
				+ "\"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos\", this);";
		codigo += "\n\t\t}catch(RuntimeException r){";
		codigo += "\n\t\t\tMensajesUtil.mensajeError(r, \"\", this);";
		codigo += "\n\t\t}";
		codigo += "\n\t}";// Fin eliminarDatos()
		codigo += "\n}";// cierra la clase

		return codigo;
	}

	// //////////////////////////////Vista en
	// zk///////////////////////////////////
	private String generarVistaZk(String paquete, String bean) throws Exception {

		Map<String, Contenedor> mapa_contenedor = new HashMap<String, Contenedor>();
		Map<String, List> mapa_campos = new HashMap<String, List>();

		for (int i = 0; i < fields.size(); i++) {
			Vector v = (Vector) fields.get(i);

			Object dato = v.get(7);

			if (dato instanceof String) {
				dato = new Contenedor(dato.toString(), dato.toString(), 1);
			}

			mapa_contenedor.put(dato.toString(), (Contenedor) dato);

			if (mapa_campos.containsKey(dato.toString())) {
				List fields_contenedor = (List) mapa_campos
						.get(dato.toString());
				fields_contenedor.add(v);
				mapa_campos.put(dato.toString(), fields_contenedor);
			} else {
				List fields_contenedor = new ArrayList();
				fields_contenedor.add(v);
				mapa_campos.put(dato.toString(), fields_contenedor);
			}
		}

		String codigo = "";

		try {

			// Inicio de la pagina //
			codigo += "<?page title=\"" + clase(bean)
					+ "\" contentType=\"text/html;charset=UTF-8\"?>";
			codigo += "\n<zk xmlns:h=\"http://www.w3.org/1999/xhtml\">";

			// Inicio de Border layout //
			codigo += "\n<window id=\"form" + clase(bean)
					+ "\" width=\"100%\" height=\"97%\" use=\"" + paquete
					+ ".controller." + clase(bean) + "Action\">";
			// codigo += "\n<center autoscroll=\"true\" border=\"none\">";

			// style
			codigo += "\n<style>"
					+ "\n\t	body ,table{ margin:0; padding:0;"
					+ "\n\t	} .z-fieldset{ -moz-border-radius:5px; border-radius: 5px;"
					+ "\n\t	-webkit-border-radius: 5px; } .combobox{ font-size:12px;"
					+ "\n\t	border: 1px solid silver; }"
					+ "\n\t	tr.z-row .z-cell{ background-image:none; }"
					+ "\n\t	tr.z-row td.z-row-hover{ background-image:none; }"
					+ "\n\t	.GridSinBorde tr.z-row td.z-row-inner, tr.z-row"
					+ "\n\t		.z-cell,div.z-grid { border: none; overflow: hidden;"
					+ "\n\t		zoom: 1; border-top: none; border-left: none;"
					+ "\n\t		border-right: none; border-bottom: none; }"
					+ "\n		</style>";

			// Comienza Groubbox objetivo
			codigo += "\n";
			codigo += "\n<!-- **************** -->";
			codigo += "\n<!-- Zona de Objetivo -->";
			codigo += "\n<!-- **************** -->";
			codigo += "\n<groupbox id=\"groupboxObjetivo\" closable=\"false\" mold=\"3d\">";
			codigo += "\n<caption label=\"Modulo de " + clase(bean)
					+ "\" style=\"color: blue;font-weight: bold\"/>";
			codigo += "\n<label value=\"Esta pagina permite gestionar los "
					+ clase(bean).replace("_", " ") + ".\"/>";
			codigo += "\n</groupbox>";// Fin groupboxObjetivo

			int columns = 1;
			String columsSting = "";
			for (int i = 0; i < fields.size(); i++) {
				Vector v = (Vector) fields.get(i);
				if (!v.get(0).toString().equals("codigo_dane")
						&& !v.get(0).toString().equals("codigo_empresa")
						&& !v.get(0).toString().equals("cons_sede")
						&& !v.get(0).toString().equals("codigo_sucursal")
						&& !v.get(0).toString().equals("creacion_date")
						&& !v.get(0).toString().equals("ultimo_update")
						&& !v.get(0).toString().equals("creacion_user")
						&& !v.get(0).toString().equals("ultimo_user")) {
					if (v.get(4).toString().equals("true")) {
						columsSting += "\n\t<column label=\""
								+ clase(v.get(0).toString()).replace("_", " ")
								+ "\"/>";
						columns++;
					}
				}
			}

			// Comienza Groubbox consulta //
			codigo += "\n";
			codigo += "\n<!-- **************** -->";
			codigo += "\n<!-- Zona de Consulta -->";
			codigo += "\n<!-- **************** -->";
			codigo += "\n<groupbox id=\"groupboxConsulta\" visible=\"true\" closable=\"false\" mold=\"3d\" height=\"90%\">";
			codigo += "\n<caption label=\"Consultar\" style=\"color: blue;font-weight: bold\"/>";
			codigo += "\n";
			codigo += "\n<separator />";
			codigo += "\n";
			codigo += "\n<!-- ***************************** -->";
			codigo += "\n<!--  Resultado Consulta Maestros  -->";
			codigo += "\n<!-- ***************************** -->";
			codigo += "\n<grid id=\"gridResultado\" height=\"90%\" mold=\"paging\" pageSize=\"20\"  vflex=\"1\">";
			codigo += "\n<auxhead>" + "\n\t					<auxheader colspan=\""
					+ columns
					+ "\">"
					+ "\n\t\t						<hbox align=\"center\">"
					+ "\n\t\t\t							<label value=\"Criterios de B&#250;squeda :\" />"
					+ "\n\t\t\t							<space />"
					+ "\n\t\t\t							<listbox id=\"lbxParameter\" mold=\"select\""
					+ "\n\t\t\t\t								sclass=\"combobox\" width=\"150px\" />"
					+ "\n\t\t\t							<separator />"
					+ "\n\t\t\t							<textbox id=\"tbxValue\" width=\"200px\""
					+ "\n\t\t\t\t								onOK=\"form" + clase(bean)
					+ ".buscarDatos();\""
					+ "\n\t\t\t\t								style=\"text-transform:uppercase\" />"
					+ "\n\t\t\t							<separator />"
					+ "\n\t\t\t							<button mold=\"trendy\""
					+ "\n\t\t\t\t								image=\"/images/Magnifier.gif\""
					+ "\n\t\t\t\t								onClick=\"form" + clase(bean)
					+ ".buscarDatos();\""
					+ "\n\t\t\t\t								tooltiptext=\"Consultar "
					+ clase(bean).replace("_", " ") + "\" />"
					+ "\n\t\t\t							<separator />"
					+ "\n\t\t\t							<button mold=\"trendy\""
					+ "\n\t\t\t\t								image=\"/images/New16.gif\""
					+ "\n\t\t\t\t								onClick='form" + clase(bean)
					+ ".accionForm(true,\"registrar\");'"
					+ "\n\t\t\t\t								tooltiptext=\"Nuevo "
					+ clase(bean).replace("_", " ") + "\" />"
					+ "\n\t\t						</hbox>" + "\n\t					</auxheader>"
					+ "\n				</auxhead>";
			codigo += "\n<columns sizable=\"true\">";
			codigo += columsSting;
			codigo += "\n\t<column label=\"Acciones\" align=\"center\" width=\"80px\"/>";
			codigo += "\n</columns>";
			codigo += "\n<rows id=\"rowsResultado\" width=\"100%\" />";
			codigo += "\n</grid>";
			codigo += "\n</groupbox>";// Fin groupboxConsulta

			/* cambio alineacion center de toda la aplicacion */
			codigo += "\n\n<borderlayout width=\"100%\" height=\"90%\" id=\"groupboxEditar\" visible=\"false\">"
					+ "\n\t			<north border=\"none\">"
					+ "\n\t\t				<toolbar width=\"100%\" align=\"start\">"
					+ "\n\t\t	\t				<textbox id=\"tbxAccion\" value=\"registrar\""
					+ "\n\t\t	\t\t					visible=\"false\" />"
					+ "\n\t\t	\t				<toolbarbutton image=\"/images/Save16.gif\""
					+ "\n\t\t	\t\t					label=\"Guardar "
					+ clase(bean).replace("_", " ")
					+ "\""
					+ "\n\t\t	\t\t					onClick=\"form"
					+ clase(bean)
					+ ".guardarDatos();\" id=\"btGuardar\" />"
					+ "\n\t\t \t\t                  <toolbarbutton image=\"/images/New16.gif\""
					+ "\n\t\t	\t\t					label=\"Nueva "
					+ clase(bean).replace("_", " ")
					+ "\""
					+ "\n\t\t	\t\t					onClick='form"
					+ clase(bean)
					+ ".accionForm(true,\"registrar\");'"
					+ "\n\t\t	\t\t					id=\"btNew\" />"
					+ "\n\t\t	\t				<space />"
					+ "\n\t\t \t\t                  <toolbarbutton image=\"/images/trasladar.gif\""
					+ "\n\t\t	\t\t					label=\"Cancelar\" onClick='form"
					+ clase(bean)
					+ ".accionForm(false,tbxAccion.getText());'"
					+ "\n\t\t	\t\t					id=\"btCancel\" />"
					+ "\n\t\t	\t				<toolbarbutton image=\"/images/print_ico.gif\""
					+ "\n\t\t	\t\t					label=\"Imprimir "
					+ clase(bean).replace("_", " ")
					+ "\""
					+ "\n\t\t	\t\t					onClick=\"form"
					+ clase(bean)
					+ ".imprimir();\""
					+ "\n\t\t	\t\t					id=\"btImprimir\" visible=\"false\"/>"
					+ "\n\t\t	\t				<space />"
					+ "\n\t\t	\t				<label value=\"Formato impresión: \" visible=\"false\"/>"
					+ "\n\t\t	\t\t				<listbox id=\"lbxFormato\" mold=\"select\" width=\"150px\" visible=\"false\""
					+ "\n\t\t	\t\t					sclass=\"combobox\">"
					+ "\n\t\t	\t\t					<listitem value=\"pdf\" label=\"PDF\""
					+ "\n\t\t	\t\t\t						selected=\"true\" />"
					+ "\n\t\t	\t\t					<listitem value=\"rtf\" label=\"WORD\" />"
					+ "\n\t\t	\t				</listbox>"
					+ "\n\t\t				</toolbar>"
					+ "\n\t			</north>"
					+ "\n\t			<center autoscroll=\"true\" border=\"none\" >"
					+ "\n\t			<div align=\"center\">";

			// Comienza Groubbox Insertar/Actualizar //
			codigo += "\n";
			codigo += "\n<!-- *************************** -->";
			codigo += "\n<!-- *** Insertar/Actualizar *** -->";
			codigo += "\n<!-- *************************** -->";
			codigo += "\n<groupbox  closable=\"false\"  width=\"905px\" mold=\"3d\">";

			if (mapa_campos.containsKey("Defecto")) {

				List field_defecto = mapa_campos.get("Defecto");

				codigo += "\n<grid sclass=\"GridSinBorde\" hflex=\"1\">";
				codigo += "\n<columns>";
				codigo += "\n<column label=\"\" width=\"276px\"/>";
				codigo += "\n<column label=\"\" width=\"170px\" align=\"left\"/>";
				codigo += "\n<column label=\"\" width=\"182px\"/>";
				codigo += "\n<column label=\"\" width=\"276px\"/>";
				codigo += "\n</columns>";
				codigo += "\n<rows>";
				codigo += "\n";
				int count = 1;

				for (int i = 0; i < field_defecto.size(); i++) {
					Vector v = (Vector) field_defecto.get(i);
					if (!v.get(0).toString().equals("codigo_dane")
							&& !v.get(0).toString().equals("codigo_empresa")
							&& !v.get(0).toString().equals("cons_sede")
							&& !v.get(0).toString().equals("codigo_sucursal")
							&& !v.get(0).toString().equals("creacion_date")
							&& !v.get(0).toString().equals("ultimo_update")
							&& !v.get(0).toString().equals("creacion_user")
							&& !v.get(0).toString().equals("ultimo_user")) {
						if (v.get(5).toString().equals("true")) {
							codigo += "\n<!-- fila " + count + " -->";
							codigo += "\n<row>";
							codigo += "\n<cell></cell>";
							codigo += "\n<cell height=\"30px\">"
									+ (v.get(3).toString().equals("true") ? "<label value=\"* \" style=\"color: red\" tooltiptext=\"Campo obligatorio\"/>"
											: "")
									+ "<label id=\"lb"
									+ clase(v.get(0).toString())
									+ "\" value=\""
									+ clase(v.get(0).toString()).replace("_",
											" ") + ": \"/></cell>";
							if (v.get(2).toString().equals("Textbox")) {
								codigo += "\n<cell><textbox id=\"tbx"
										+ clase(v.get(0).toString())
										+ "\" width=\"170px\" style=\"text-transform:uppercase\" maxlength=\""
										+ v.get(6) + "\"/></cell>";
							} else if (v.get(2).toString()
									.equals("Textbox_area")) {
								codigo += "\n<cell><textbox id=\"tbx"
										+ clase(v.get(0).toString())
										+ "\" width=\"170px\" rows=\"4\" style=\"text-transform:uppercase\" maxlength=\""
										+ v.get(6) + "\"/></cell>";
							} else if (v.get(2).toString().equals("Intbox")) {
								codigo += "\n<cell><intbox constraint=\"no negative:Este valor no puede ser negativo\" id=\"ibx"
										+ clase(v.get(0).toString())
										+ "\" width=\"170px\" style=\"text-transform:uppercase\"/></cell>";
							} else if (v.get(2).toString().equals("Doublebox")) {
								codigo += "\n<cell><doublebox id=\"dbx"
										+ clase(v.get(0).toString())
										+ "\" format=\"#,##0.00\" width=\"170px\" value=\"0.00\"/></cell>";
							} else if (v.get(2).toString().equals("Listbox")) {
								codigo += "\n<cell><listbox id=\"lbx"
										+ clase(v.get(0).toString())
										+ "\" name=\""
										+ clase(v.get(0).toString())
												.toLowerCase()
										+ "\" mold=\"select\" width=\"170px\" sclass=\"combobox\"/></cell>";
							} else if (v.get(2).toString().equals("Checkbox")) {
								codigo += "\n<cell><checkbox id=\"chb"
										+ clase(v.get(0).toString())
										+ "\"/></cell>";
							} else if (v.get(2).toString().equals("Radio")) {
								codigo += "\n<cell><radiogroup id=\"rdb"
										+ clase(v.get(0).toString())
										+ "\"><radio label=\"\" value=\"S\"/><space/><radio label=\"\" value=\"N\" checked=\"true\" /></radiogroup></cell>";
							} else if (v.get(2).toString().equals("Datebox")) {
								codigo += "\n<cell><datebox id=\"dtbx"
										+ clase(v.get(0).toString())
										+ "\" onCreate=\"self.value = new Date();\" format=\"yyyy-MM-dd\" width=\"170px\"/></cell>";
							}
							codigo += "\n<cell></cell>";
							codigo += "\n</row>";
							codigo += "\n";
							count++;
						}
					}
				}
				codigo += "\n</rows>";
				codigo += "\n</grid>";

			}

			for (String key_map : mapa_campos.keySet()) {
				if (!key_map.equals("Defecto")) {
					List fields_aux = mapa_campos.get(key_map);
					Contenedor contenedor = mapa_contenedor.get(key_map);
					if (contenedor.getTipo().equals("Grid_radio1")) {
						Integer columnas = contenedor.getColumnas();
						codigo += "\n\n";
						codigo += "\n<grid>";
						codigo += "\n\t<columns>";

						int campos_x_fila = (int) (columnas / 3);

						for (int i = 0; i < campos_x_fila; i++) {
							codigo += "\n\t\t<column />";
							codigo += "\n\t\t<column label=\"\" width=\"30px\" />";
							codigo += "\n\t\t<column label=\"\" width=\"30px\" />";
						}

						codigo += "\n\t</columns>";

						codigo += "\n\t<rows>";

						int rows = 1;

						if (fields_aux.size() <= campos_x_fila) {
							rows = 1;
						} else {
							int rows_mod = (fields_aux.size() % campos_x_fila);
							rows = (int) (fields_aux.size() / campos_x_fila);
							if (rows_mod != 0) {
								rows = rows + 1;
							}
						}

						int count_campos = 0;

						for (int i = 0; i < rows; i++) {
							codigo += "\n\t\t<row >";
							for (int j = (count_campos * campos_x_fila); j < ((count_campos + 1) * campos_x_fila); j++) {
								if (j < fields_aux.size()) {
									Vector v = (Vector) fields_aux.get(j);
									if (!v.get(0).toString()
											.equals("codigo_dane")
											&& !v.get(0).toString()
													.equals("codigo_empresa")
											&& !v.get(0).toString()
													.equals("cons_sede")
											&& !v.get(0).toString()
													.equals("codigo_sucursal")
											&& !v.get(0).toString()
													.equals("creacion_date")
											&& !v.get(0).toString()
													.equals("ultimo_update")
											&& !v.get(0).toString()
													.equals("creacion_user")
											&& !v.get(0).toString()
													.equals("ultimo_user")) {
										if (v.get(5).toString().equals("true")) {
											codigo += "\n\n\t\t\t<cell>"
													+ (v.get(3).toString()
															.equals("true") ? "<label value=\"* \" style=\"color: red\" tooltiptext=\"Campo obligatorio\"/>"
															: "")
													+ "<label id=\"lb"
													+ clase(v.get(0).toString())
													+ "\" value=\""
													+ clase(v.get(0).toString())
															.replace("_", " ")
													+ ": \"/></cell>";
											if (v.get(2).toString()
													.equals("Textbox")) {
												codigo += "\n\t\t\t<cell colspan=\"2\" ><textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"170px\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Textbox_area")) {
												codigo += "\n\t\t\t<cell colspan=\"2\" ><textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"170px\" rows=\"4\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Intbox")) {
												codigo += "\n\t\t\t<cell colspan=\"2\" ><intbox constraint=\"no negative:Este valor no puede ser negativo\" id=\"ibx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"170px\" style=\"text-transform:uppercase\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Doublebox")) {
												codigo += "\n\t\t\t<cell colspan=\"2\" ><doublebox id=\"dbx"
														+ clase(v.get(0)
																.toString())
														+ "\" format=\"#,##0.00\" width=\"170px\" value=\"0.00\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Listbox")) {
												codigo += "\n\t\t\t<cell colspan=\"2\" ><listbox id=\"lbx"
														+ clase(v.get(0)
																.toString())
														+ "\" name=\""
														+ clase(
																v.get(0)
																		.toString())
																.toLowerCase()
														+ "\" mold=\"select\" width=\"170px\" sclass=\"combobox\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Checkbox")) {
												codigo += "\n\t\t\t<cell colspan=\"2\" ><checkbox id=\"chb"
														+ clase(v.get(0)
																.toString())
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Radio")) {
												codigo += "\n\t\t\t<cell colspan=\"2\" ><radiogroup id=\"rdb"
														+ clase(v.get(0)
																.toString())
														+ "\"><radio label=\"\" value=\"S\"/><space width=\"5px\" /><radio label=\"\" value=\"N\" checked=\"true\" /></radiogroup></cell>";
											} else if (v.get(2).toString()
													.equals("Datebox")) {
												codigo += "\n\t\t\t<cell colspan=\"2\" ><datebox id=\"dtbx"
														+ clase(v.get(0)
																.toString())
														+ "\" onCreate=\"self.value = new Date();\" format=\"yyyy-MM-dd\" width=\"170px\"/></cell>";
											}
										}
									}
								} else {
									codigo += "\n\t\t\t<cell /><cell colspan=\"2\" />";
								}
							}

							codigo += "\n\t\t</row >";
							count_campos++;
						}

						codigo += "\n\t</rows>";

						codigo += "\n</grid>";

					} else if (contenedor.getTipo().equals("Grid_checkbox1")) {

						Integer columnas = contenedor.getColumnas();
						codigo += "\n\n";
						codigo += "\n<grid>";
						codigo += "\n\t<columns>";

						int campos_x_fila = (int) (columnas / 2);

						for (int i = 0; i < campos_x_fila; i++) {
							codigo += "\n\t\t<column />";
							codigo += "\n\t\t<column label=\"\" width=\"50px\" />";
						}

						codigo += "\n\t</columns>";

						codigo += "\n\t<rows>";

						int rows = 1;

						if (fields_aux.size() <= campos_x_fila) {
							rows = 1;
						} else {
							int rows_mod = (fields_aux.size() % campos_x_fila);
							rows = (int) (fields_aux.size() / campos_x_fila);
							if (rows_mod != 0) {
								rows = rows + 1;
							}
						}

						int count_campos = 0;

						for (int i = 0; i < rows; i++) {
							codigo += "\n\t\t<row >";
							for (int j = (count_campos * campos_x_fila); j < ((count_campos + 1) * campos_x_fila); j++) {
								if (j < fields_aux.size()) {
									Vector v = (Vector) fields_aux.get(j);
									if (!v.get(0).toString()
											.equals("codigo_dane")
											&& !v.get(0).toString()
													.equals("codigo_empresa")
											&& !v.get(0).toString()
													.equals("cons_sede")
											&& !v.get(0).toString()
													.equals("codigo_sucursal")
											&& !v.get(0).toString()
													.equals("creacion_date")
											&& !v.get(0).toString()
													.equals("ultimo_update")
											&& !v.get(0).toString()
													.equals("creacion_user")
											&& !v.get(0).toString()
													.equals("ultimo_user")) {
										if (v.get(5).toString().equals("true")) {
											codigo += "\n\t\t\t<cell>"
													+ (v.get(3).toString()
															.equals("true") ? "<label value=\"* \" style=\"color: red\" tooltiptext=\"Campo obligatorio\"/>"
															: "")
													+ "<label id=\"lb"
													+ clase(v.get(0).toString())
													+ "\" value=\""
													+ clase(v.get(0).toString())
															.replace("_", " ")
													+ ": \"/></cell>";
											if (v.get(2).toString()
													.equals("Textbox")) {
												codigo += "\n\t\t\t<cell ><textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"98%\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Textbox_area")) {
												codigo += "\n\t\t\t<cell ><textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"98%\" rows=\"4\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Intbox")) {
												codigo += "\n\t\t\t<cell ><intbox constraint=\"no negative:Este valor no puede ser negativo\" id=\"ibx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"98%\" style=\"text-transform:uppercase\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Doublebox")) {
												codigo += "\n\t\t\t<cell ><doublebox id=\"dbx"
														+ clase(v.get(0)
																.toString())
														+ "\" format=\"#,##0.00\" width=\"98%\" value=\"0.00\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Listbox")) {
												codigo += "\n\t\t\t<cell ><listbox id=\"lbx"
														+ clase(v.get(0)
																.toString())
														+ "\" name=\""
														+ clase(
																v.get(0)
																		.toString())
																.toLowerCase()
														+ "\" mold=\"select\" width=\"98%\" sclass=\"combobox\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Checkbox")) {
												codigo += "\n\t\t\t<cell ><checkbox id=\"chb"
														+ clase(v.get(0)
																.toString())
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Radio")) {
												codigo += "\n\t\t\t<cell ><radiogroup id=\"rdb"
														+ clase(v.get(0)
																.toString())
														+ "\"><radio label=\"\" value=\"S\"/><space width=\"5px\" /><radio label=\"\" value=\"N\" checked=\"true\" /></radiogroup></cell>";
											} else if (v.get(2).toString()
													.equals("Datebox")) {
												codigo += "\n\t\t\t<cell ><datebox id=\"dtbx"
														+ clase(v.get(0)
																.toString())
														+ "\" onCreate=\"self.value = new Date();\" format=\"yyyy-MM-dd\" width=\"98%\"/></cell>";
											}
										}
									}
								} else {
									codigo += "\n\t\t\t<cell /><cell colspan=\"2\" />";
								}
							}

							codigo += "\n\t\t</row >";
							count_campos++;
						}

						codigo += "\n\t</rows>";

						codigo += "\n</grid>";

					} else if (contenedor.getTipo().equals("Grid_tabla1")) {
						Integer columnas = contenedor.getColumnas();
						codigo += "\n\n";
						codigo += "\n<grid>";
						codigo += "\n\t<columns>";

						int campos_x_fila = (int) (columnas / 2);

						for (int i = 0; i < campos_x_fila; i++) {
							codigo += "\n\t\t<column />";
							codigo += "\n\t\t<column />";
						}

						codigo += "\n\t</columns>";

						codigo += "\n\t<rows>";

						int rows = 1;

						if (fields_aux.size() <= campos_x_fila) {
							rows = 1;
						} else {
							int rows_mod = (fields_aux.size() % campos_x_fila);
							rows = (int) (fields_aux.size() / campos_x_fila);
							if (rows_mod != 0) {
								rows = rows + 1;
							}
						}

						int count_campos = 0;

						for (int i = 0; i < rows; i++) {
							codigo += "\n\t\t<row >";
							for (int j = (count_campos * campos_x_fila); j < ((count_campos + 1) * campos_x_fila); j++) {
								if (j < fields_aux.size()) {
									Vector v = (Vector) fields_aux.get(j);
									if (!v.get(0).toString()
											.equals("codigo_dane")
											&& !v.get(0).toString()
													.equals("codigo_empresa")
											&& !v.get(0).toString()
													.equals("cons_sede")
											&& !v.get(0).toString()
													.equals("codigo_sucursal")
											&& !v.get(0).toString()
													.equals("creacion_date")
											&& !v.get(0).toString()
													.equals("ultimo_update")
											&& !v.get(0).toString()
													.equals("creacion_user")
											&& !v.get(0).toString()
													.equals("ultimo_user")) {
										if (v.get(5).toString().equals("true")) {
											codigo += "\n\t\t\t<cell>"
													+ (v.get(3).toString()
															.equals("true") ? "<label value=\"* \" style=\"color: red\" tooltiptext=\"Campo obligatorio\"/>"
															: "")
													+ "<label id=\"lb"
													+ clase(v.get(0).toString())
													+ "\" value=\""
													+ clase(v.get(0).toString())
															.replace("_", " ")
													+ ": \"/></cell>";
											if (v.get(2).toString()
													.equals("Textbox")) {
												codigo += "\n\t\t\t<cell ><textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"98%\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Textbox_area")) {
												codigo += "\n\t\t\t<cell ><textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"98%\" rows=\"4\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Intbox")) {
												codigo += "\n\t\t\t<cell ><intbox constraint=\"no negative:Este valor no puede ser negativo\" id=\"ibx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"98%\" style=\"text-transform:uppercase\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Doublebox")) {
												codigo += "\n\t\t\t<cell ><doublebox id=\"dbx"
														+ clase(v.get(0)
																.toString())
														+ "\" format=\"#,##0.00\" width=\"98%\" value=\"0.00\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Listbox")) {
												codigo += "\n\t\t\t<cell ><listbox id=\"lbx"
														+ clase(v.get(0)
																.toString())
														+ "\" name=\""
														+ clase(
																v.get(0)
																		.toString())
																.toLowerCase()
														+ "\" mold=\"select\" width=\"98%\" sclass=\"combobox\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Checkbox")) {
												codigo += "\n\t\t\t<cell ><checkbox id=\"chb"
														+ clase(v.get(0)
																.toString())
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Radio")) {
												codigo += "\n\t\t\t<cell ><radiogroup id=\"rdb"
														+ clase(v.get(0)
																.toString())
														+ "\"><radio label=\"\" value=\"S\"/><space width=\"15px\" /><radio label=\"\" value=\"N\" checked=\"true\" /></radiogroup></cell>";
											} else if (v.get(2).toString()
													.equals("Datebox")) {
												codigo += "\n\t\t\t<cell ><datebox id=\"dtbx"
														+ clase(v.get(0)
																.toString())
														+ "\" onCreate=\"self.value = new Date();\" format=\"yyyy-MM-dd\" width=\"98%\"/></cell>";
											}
										}
									}
								} else {
									codigo += "\n\t\t\t<cell /><cell />";
								}
							}

							codigo += "\n\t\t</row >";
							count_campos++;
						}

						codigo += "\n\t</rows>";

						codigo += "\n</grid>";
					} else if (contenedor.getTipo().equals("Grid_tabla2")) {
						Integer columnas = contenedor.getColumnas();
						codigo += "\n\n";
						codigo += "\n<grid>";
						codigo += "\n\t<columns>";

						int campos_x_fila = (int) (columnas);

						for (int i = 0; i < campos_x_fila; i++) {
							codigo += "\n\t\t<column />";
						}

						codigo += "\n\t</columns>";

						codigo += "\n\t<rows>";

						int rows = 1;

						if (fields_aux.size() <= campos_x_fila) {
							rows = 1;
						} else {
							int rows_mod = (fields_aux.size() % campos_x_fila);
							rows = (int) (fields_aux.size() / campos_x_fila);
							if (rows_mod != 0) {
								rows = rows + 1;
							}
						}

						int count_campos = 0;

						for (int i = 0; i < rows; i++) {
							codigo += "\n\t\t<row >";
							for (int j = (count_campos * campos_x_fila); j < ((count_campos + 1) * campos_x_fila); j++) {
								if (j < fields_aux.size()) {
									Vector v = (Vector) fields_aux.get(j);
									if (!v.get(0).toString()
											.equals("codigo_dane")
											&& !v.get(0).toString()
													.equals("codigo_empresa")
											&& !v.get(0).toString()
													.equals("cons_sede")
											&& !v.get(0).toString()
													.equals("codigo_sucursal")
											&& !v.get(0).toString()
													.equals("creacion_date")
											&& !v.get(0).toString()
													.equals("ultimo_update")
											&& !v.get(0).toString()
													.equals("creacion_user")
											&& !v.get(0).toString()
													.equals("ultimo_user")) {
										if (v.get(5).toString().equals("true")) {
											codigo += "\n\t\t\t<cell>"
													+ (v.get(3).toString()
															.equals("true") ? "<label value=\"* \" style=\"color: red\" tooltiptext=\"Campo obligatorio\"/>"
															: "")
													+ "<label id=\"lb"
													+ clase(v.get(0).toString())
													+ "\" value=\""
													+ clase(v.get(0).toString())
															.replace("_", " ")
													+ ": \"/>";
											if (v.get(2).toString()
													.equals("Textbox")) {
												codigo += "\n\t\t\t\t<space />"
														+ "\n\t\t\t\t<textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"170px\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Textbox_area")) {
												codigo += "\n\t\t\t\t<space />"
														+ "\n\t\t\t\t<textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"170px\" rows=\"4\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Intbox")) {
												codigo += "\n\t\t\t\t<space />"
														+ "\n\t\t\t\t<intbox constraint=\"no negative:Este valor no puede ser negativo\" id=\"ibx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"170px\" style=\"text-transform:uppercase\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Doublebox")) {
												codigo += "\n\t\t\t\t<space />"
														+ "\n\t\t\t\t<doublebox id=\"dbx"
														+ clase(v.get(0)
																.toString())
														+ "\" format=\"#,##0.00\" width=\"170px\" value=\"0.00\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Listbox")) {
												codigo += "\n\t\t\t\t<space />"
														+ "\n\t\t\t\t<listbox id=\"lbx"
														+ clase(v.get(0)
																.toString())
														+ "\" name=\""
														+ clase(
																v.get(0)
																		.toString())
																.toLowerCase()
														+ "\" mold=\"select\" width=\"170px\" sclass=\"combobox\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Checkbox")) {
												codigo += "\n\t\t\t\t<space />"
														+ "\n\t\t\t\t<checkbox id=\"chb"
														+ clase(v.get(0)
																.toString())
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Radio")) {
												codigo += "\n\t\t\t\t<space />"
														+ "\n\t\t\t\t<radiogroup id=\"rdb"
														+ clase(v.get(0)
																.toString())
														+ "\"><radio label=\"\" value=\"S\"/><space width=\"15px\" /><radio label=\"\" value=\"N\" checked=\"true\" /></radiogroup></cell>";
											} else if (v.get(2).toString()
													.equals("Datebox")) {
												codigo += "\n\t\t\t\t<space />"
														+ "\n\t\t\t\t<datebox id=\"dtbx"
														+ clase(v.get(0)
																.toString())
														+ "\" onCreate=\"self.value = new Date();\" format=\"yyyy-MM-dd\" width=\"170px\"/></cell>";
											}
										}
									}
								} else {
									codigo += "\n\t\t\t\t<cell />";
								}
							}

							codigo += "\n\t\t</row >";
							count_campos++;
						}

						codigo += "\n\t</rows>";

						codigo += "\n</grid>";
					} else if (contenedor.getTipo().equals("Grid_tabla3")) {
						Integer columnas = contenedor.getColumnas();
						codigo += "\n\n";
						codigo += "\n<grid>";
						codigo += "\n\t<columns>";

						int campos_x_fila = (int) (columnas);

						for (int i = 0; i < campos_x_fila; i++) {
							codigo += "\n\t\t<column />";
						}

						codigo += "\n\t</columns>";

						codigo += "\n\t<rows>";

						int rows = 1;

						if (fields_aux.size() <= campos_x_fila) {
							rows = 1;
						} else {
							int rows_mod = (fields_aux.size() % campos_x_fila);
							rows = (int) (fields_aux.size() / campos_x_fila);
							if (rows_mod != 0) {
								rows = rows + 1;
							}
						}

						int count_campos = 0;

						for (int i = 0; i < rows; i++) {
							codigo += "\n\t\t<row >";
							for (int j = (count_campos * campos_x_fila); j < ((count_campos + 1) * campos_x_fila); j++) {
								if (j < fields_aux.size()) {
									Vector v = (Vector) fields_aux.get(j);
									if (!v.get(0).toString()
											.equals("codigo_dane")
											&& !v.get(0).toString()
													.equals("codigo_empresa")
											&& !v.get(0).toString()
													.equals("cons_sede")
											&& !v.get(0).toString()
													.equals("codigo_sucursal")
											&& !v.get(0).toString()
													.equals("creacion_date")
											&& !v.get(0).toString()
													.equals("ultimo_update")
											&& !v.get(0).toString()
													.equals("creacion_user")
											&& !v.get(0).toString()
													.equals("ultimo_user")) {
										if (v.get(5).toString().equals("true")) {
											if (v.get(2).toString()
													.equals("Textbox")) {
												codigo += "\n\t\t\t<cell><textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"98%\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Textbox_area")) {
												codigo += "\n\t\t\t<cell><textbox id=\"tbx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"98%\" rows=\"4\" style=\"text-transform:uppercase\" maxlength=\""
														+ v.get(6)
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Intbox")) {
												codigo += "\n\t\t\t<cell><intbox constraint=\"no negative:Este valor no puede ser negativo\" id=\"ibx"
														+ clase(v.get(0)
																.toString())
														+ "\" width=\"98%\" style=\"text-transform:uppercase\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Doublebox")) {
												codigo += "\n\t\t\t<cell><doublebox id=\"dbx"
														+ clase(v.get(0)
																.toString())
														+ "\" format=\"#,##0.00\" width=\"98%\" value=\"0.00\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Listbox")) {
												codigo += "\n\t\t\t\t<space />"
														+ "\n\t\t\t\t<listbox id=\"lbx"
														+ clase(v.get(0)
																.toString())
														+ "\" name=\""
														+ clase(
																v.get(0)
																		.toString())
																.toLowerCase()
														+ "\" mold=\"select\" width=\"98%\" sclass=\"combobox\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Checkbox")) {
												codigo += "\n\t\t\t<cell><checkbox id=\"chb"
														+ clase(v.get(0)
																.toString())
														+ "\"/></cell>";
											} else if (v.get(2).toString()
													.equals("Radio")) {
												codigo += "\n\t\t\t<cell><radiogroup id=\"rdb"
														+ clase(v.get(0)
																.toString())
														+ "\"><radio label=\"\" value=\"S\"/><space width=\"15px\" /><radio label=\"\" value=\"N\" checked=\"true\" /></radiogroup></cell>";
											} else if (v.get(2).toString()
													.equals("Datebox")) {
												codigo += "\n\t\t\t<cell><datebox id=\"dtbx"
														+ clase(v.get(0)
																.toString())
														+ "\" onCreate=\"self.value = new Date();\" format=\"yyyy-MM-dd\" width=\"98%\"/></cell>";
											}
										}
									}
								} else {
									codigo += "\n\t\t\t<cell />";
								}
							}

							codigo += "\n\t\t</row >";
							count_campos++;
						}

						codigo += "\n\t</rows>";

						codigo += "\n</grid>";
					}
				}
			}

			// Barra de botones //
			codigo += "\n";
			codigo += "\n</groupbox>";// Fin groupboxEditar

			codigo += "\n\t</div>";
			codigo += "\n\t</center>";
			codigo += "\n\t</borderlayout>";

			// codigo += "\n</center>";//Fin de Center
			codigo += "\n</window>";// Fin de window

			codigo += "\n</zk>";// fin de la vista
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}

		return codigo;

	}
}
