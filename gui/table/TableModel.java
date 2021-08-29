package gui.table;

import resource.data.Row;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class TableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private List<Row> rows;

    private void updateModel(){

    	int columnCount = 0;
    	if(rows.size() > 0) {
    		columnCount = rows.get(0).getFields().keySet().size();  		
    	} else {
    		return;
    	}

        Vector<?> columnVector = DefaultTableModel.convertToVector(rows.get(0).getFields().keySet().toArray());
        Vector<Vector<?>> dataVector = new Vector<Vector<?>>(columnCount);

        for (int i=0; i<rows.size(); i++){
            dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().values().toArray()));
        }
        
        setDataVector(dataVector, columnVector);
        
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        updateModel();
    }
}
