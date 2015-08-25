/**
 *
 */
package com.zkcrud.tablemodel;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * @author FLIA JIMENEZ JULIO
 *
 */
public class TableModel extends DefaultTableModel implements javax.swing.table.TableModel {
    
    private boolean base;
    
    public TableModel(boolean base) {
        // TODO Auto-generated constructor stub
        this.base = base;
        if (base) {
            addColumn("CAMPO");
            addColumn("COMPONENTE ZK");
            addColumn("OBLIGATORIO?");
            addColumn("MOSTRAR EN CONS");
            addColumn("MOSTRAR EN REGT");
            addColumn("CONTENEDOR");
        }
    }
    
    @Override
    public void addTableModelListener(TableModelListener l) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // TODO Auto-generated method stub
        if (getColumnCount() > 0) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return String.class;
                case 2:
                    if (base) {
                        return Boolean.class;
                    } else {
                        return Integer.class;
                    }
                case 3:
                    return Boolean.class;
                
                case 4:
                    return Boolean.class;
                default:
                    break;
            }
            return Object.class;
        }
        return null;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        
        if (columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4 || columnIndex == 5) {
            if (base) {
                return true;
            } else {
              return false;
            }
        }
        return false;
    }
    
    @Override
    public void removeTableModelListener(TableModelListener l) {
        // TODO Auto-generated method stub
    }
    
}
