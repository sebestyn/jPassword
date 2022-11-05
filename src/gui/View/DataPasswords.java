package gui.View;

import dataTypes.Password;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DataPasswords extends AbstractTableModel {
    List<Password> passwords;


    public DataPasswords(HashSet<Password> passwords) {
        this.passwords = new ArrayList<>(passwords);
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    @Override
    public int getRowCount() {
        return passwords.size();
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Password password = passwords.get(rowIndex);
        switch(columnIndex) {
            case 0: return password.getName();
            case 1: return password.getUsername();
            case 2: return password.getPassword();
            default: return password.getCryptType().toString();
        }
    }
}
