package gui.View;

import dataTypes.Note;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashSet;

public class DataNotes extends AbstractTableModel {
    ArrayList<Note> notesList;
    HashSet<Note> notes;


    public DataNotes(HashSet<Note> notes) {
        this.notes = notes;
        this.notesList= new ArrayList<>(notes);
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
        return notesList.size();
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
        return 3;
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
        Note password = notesList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> password.getName();
            case 1 -> password.getNote();
            default -> password.getCryptType().toString();
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Név";
            case 1 -> "Jegyzet";
            default -> "Titkosítás";
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 0 || columnIndex == 1){
            return true;
        }
        return super.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            notesList.get(rowIndex).setName((String)aValue);
        }
        else if(columnIndex == 1){
            notesList.get(rowIndex).setNote((String)aValue);
        }
        notes = new HashSet<>(notesList);
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    public void addNote(Note newNote){
        notes.add(newNote);
        notesList = new ArrayList<>(notes);
        fireTableDataChanged();
    }
}
