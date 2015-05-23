package me.aximcore;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.BevelBorder;

public class GameGui {
	
	private JFrame frmSokoban;
	private JTable table;
	private DefaultTableModel dm;
	private Game game = new Game();
	
	private TableCellRenderer getRenderer() {
		return new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
				if("jatekos".equals(value)){
					tableCellRendererComponent.setBackground(Color.YELLOW);
				} else if("t".equals(value)){
					tableCellRendererComponent.setBackground(Color.GREEN);
				} else if("k".equals(value)){
					tableCellRendererComponent.setBackground(Color.GRAY);
				} else if("c".equals(value)){
					tableCellRendererComponent.setBackground(Color.RED);
				} else if(value == null){
					tableCellRendererComponent.setBackground(Color.BLUE);
					//tableCellRendererComponent.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
				}
				return tableCellRendererComponent;
			}
		};
	}

	private MouseListener tableMouseListener = new MouseAdapter(){
		@Override
		public void mouseClicked(MouseEvent e){
			if(e.getButton() == MouseEvent.BUTTON1){
				game.setClickedPos(table.rowAtPoint(e.getPoint()),table.columnAtPoint(e.getPoint()));
				game.setClickedValue(table.getValueAt(table.rowAtPoint(e.getPoint()),table.columnAtPoint(e.getPoint())));
				game.step();
				fullTableRender();
			}
		}
	};
	
	@SuppressWarnings("serial")
	private void refreshDM(){
		table.setModel(new DefaultTableModel(game.getMap(),
				new String[] {
			"", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
		}
				)
		{
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGui window = new GameGui();
					window.frmSokoban.setVisible(true);
					window.table.addMouseListener(window.tableMouseListener);
					window.game.addStepData(1, 1);
							
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void fullTableRender(){
		this.refreshDM();
		//table.repaint();
		for(int i = 0; i < table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(getRenderer());
	}

	/**
	 * Create the application.
	 */
	public GameGui() {
		initialize();
		game.addStepData(1, 1);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frmSokoban = new JFrame();
		frmSokoban.setResizable(false);
		frmSokoban.setTitle("Sokoban");
		frmSokoban.setBounds(100, 100, 1164, 852);
		frmSokoban.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmSokoban.getContentPane().add(panel, BorderLayout.CENTER);

		table = new JTable();
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(null);

		table.setBounds(12, 12, 1129, 750);
		this.refreshDM();

		table.setRowHeight(75);

		table.getColumnModel().getColumn(0).setMinWidth(75);	
		table.getColumnModel().getColumn(1).setMinWidth(75);
		table.getColumnModel().getColumn(2).setMinWidth(75);
		table.getColumnModel().getColumn(3).setMinWidth(75);
		table.getColumnModel().getColumn(4).setMinWidth(75);
		table.getColumnModel().getColumn(5).setMinWidth(75);
		table.getColumnModel().getColumn(6).setMinWidth(75);
		table.getColumnModel().getColumn(7).setMinWidth(75);
		table.getColumnModel().getColumn(8).setMinWidth(75);
		table.getColumnModel().getColumn(9).setMinWidth(75);

		panel.setLayout(null);
		panel.add(table);
		fullTableRender();
	}
}
