package reva.Actions;
import javax.swing.JPanel;

import docking.widgets.table.GTable;
import ghidra.program.model.address.Address;
import ghidra.program.model.address.GenericAddress;
import reva.RevaPlugin;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RevaActionTable extends JPanel {
    private JTable table;
    private RevaActionTableModel tableModel;
    private RevaPlugin plugin;

    public RevaActionTable(RevaPlugin plugin) {
        this.plugin = plugin;
        setLayout(new BorderLayout());

        // Create the table model with column names
        tableModel = new RevaActionTableModel();
        // Create the table with the table model
        table = new GTable(tableModel);
        // Scroll to the bottom as new things are added
        table.setAutoscrolls(true);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Double click
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();
                    if (column == 0) {
                        tableModel.acceptAction(row);
                    } else if (column == 1) {
                        tableModel.rejectAction(row);
                    } else if (column == 3) {
                        Address address = (Address)table.getValueAt(row, column);
                        plugin.goTo(address);
                    }
                }
            }
        });

        // Add a scroll pane to the table
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addAction(RevaAction action) {
        tableModel.addAction(action);
    }
}
