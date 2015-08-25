/**
 * 
 */
package com.zkcrud.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * @author FLIA JIMENEZ JULIO
 *
 */
public class DataDAO {
	
	private Model model;
	
	public DataDAO(Model model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}
	
	public List getFieldsTable(String esquema,String tabla) throws SQLException {
        List fieldTabla = new LinkedList();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = this.model.getConnection().prepareStatement("select * from " +esquema+"."+ tabla+" limit 0 offset 0");
            rs = pstm.executeQuery();
            ResultSetMetaData mdata_sel = rs.getMetaData();
            int num_cols_sel = mdata_sel.getColumnCount();
            for (int i = 1; i <= num_cols_sel; i++) {
                Vector v = new Vector();
                v.add(mdata_sel.getColumnLabel(i));
                v.add(mdata_sel.getColumnTypeName(i));
                v.add(mdata_sel.getColumnDisplaySize(i));
                fieldTabla.add(v);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
        }

        return fieldTabla;
    }

    public List getPrimaryKeys(String esquema,String tabla) throws SQLException {
        List keys = new LinkedList();
        ResultSet rs = null;
        try {
            rs = model.getConnection().getMetaData().getPrimaryKeys(null, null, tabla.toLowerCase());
            while (rs.next()) {
                String data = rs.getString(4);
                keys.add(new String[]{data, getTypeData(esquema,tabla, data)});
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return keys;
    }

    public String getTypeData(String esquema,String tabla, String campo) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = model.getConnection().prepareStatement("select * from " +esquema+"."+ tabla+" limit 0 offset 0");
            rs = pstm.executeQuery();
            ResultSetMetaData mdata_sel = rs.getMetaData();
            int num_cols_sel = mdata_sel.getColumnCount();
            for (int i = 1; i <= num_cols_sel; i++) {
                if (mdata_sel.getColumnLabel(i).equals(campo)) {
                    return mdata_sel.getColumnTypeName(i);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
        }
        return "";
    }
}
