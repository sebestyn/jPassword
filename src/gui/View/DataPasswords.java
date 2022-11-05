package gui.View;

import dataTypes.Password;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashSet;

public class DataPasswords extends AbstractTableModel {
    ArrayList<Password> passwordsList;
    HashSet<Password> passwords;


    public DataPasswords(HashSet<Password> passwords) {
        this.passwords = passwords;
        this.passwordsList= new ArrayList<>(passwords);
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
        return passwordsList.size();
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
        Password password = passwordsList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> password.getName();
            case 1 -> password.getUsername();
            case 2 -> password.getPassword();
            default -> password.getCryptType().toString();
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Név";
            case 1 -> "Felhasználónév";
            case 2 -> "Jelszó";
            default -> "Titkosítás";
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 0 || columnIndex == 1 || columnIndex == 2){
            return true;
        }
        return super.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            passwordsList.get(rowIndex).setName((String)aValue);
        }
        else if(columnIndex == 1){
            passwordsList.get(rowIndex).setUsername((String)aValue);
        }
        else if(columnIndex == 2){
            passwordsList.get(rowIndex).setPassword((String)aValue);
        }
        passwords = new HashSet<>(passwordsList);
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    public void addPassword(Password newPass){
        passwords.add(newPass);
        passwordsList = new ArrayList<>(passwords);
        fireTableDataChanged();
    }
}
