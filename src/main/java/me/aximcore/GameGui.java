package me.aximcore;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import java.awt.event.MouseListener;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.BevelBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.FlowLayout;

/**
 * Játék felület swingben.
 * @author aximcore
 *
 */
public class GameGui {
	
	/**
	 * Logger beálítása.
	 */
	private static Logger logger = LoggerFactory.getLogger(GameGui.class);

	/**
	 * Játék váza.
	 */
	private JFrame frmSokoban;
	
	/**
	 * A játék játszható felületét adja.
	 */
	private JTable table;
	
	/**
	 * Label a helyére mozgatott kockák számának.
	 */
	private JLabel winCount;
	
	/**
	 * Label a, hogy hány mozgás volt.
	 */
	private JLabel stepCount;
	
	/**
	 * A játék szabályait figyelő és események következményeit kezeli.
	 */
	private Game game;
	
	/**
	 * A játék felület színezésért felelős függvény.
	 * @return játék színezet oszloppával.
	 */
	private TableCellRenderer getRenderer() {
		logger.trace("getRenderer");
		
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
				}
				return tableCellRendererComponent;
			}
		};
	}

	/**
	 * Egér események kezelése, bal klikkre lépés.
	 */
	private MouseListener tableMouseListener = new MouseAdapter(){
		@Override
		public void mouseClicked(MouseEvent e){
			if(e.getButton() == MouseEvent.BUTTON1){
				logger.trace("Mouse Button1 pushed");
				
				game.setClickedPos(table.rowAtPoint(e.getPoint()),table.columnAtPoint(e.getPoint()));
				game.setClickedValue(table.getValueAt(table.rowAtPoint(e.getPoint()),table.columnAtPoint(e.getPoint())));
				game.step();
				stepCount.setText(game.getStepCount());
				winCount.setText(game.getWinCount());
				fullTableRender();
			}
		}
	};
	
	/**
	 * A játék adatmodljének frissítése {@code Game} objektumból.
	 */
	@SuppressWarnings("serial")
	private void refreshDM(){
		logger.trace("refreshDM");
		
		table.setModel(new DefaultTableModel(this.game.getMap(), this.game.getHeader()){
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
	}

	/**
	 * Játék felület létrehozása.
	 * @param args parancssori argumentumok.
	 */
	public static void main(String[] args) {
		logger.info("Main");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGui window = new GameGui();
					window.frmSokoban.setVisible(true);
					window.table.addMouseListener(window.tableMouseListener);				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Frissíti az adatmodelt utána végig megy az oszlopokon és meghívja @see {@link #getRenderer()}-t.
	 */
	private void fullTableRender(){
		this.refreshDM();
		for(int i = 0; i < table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(getRenderer());
	}

	/**
	 * Játék felület/menet initalizálásának meghívása.
	 */
	public GameGui() {
		game = new Game();
		initialize();
	}

	/**
	 * Játék felület initalizálása.
	 */
	private void initialize() {
		frmSokoban = new JFrame();
		frmSokoban.setResizable(true);
		frmSokoban.setTitle("Sokoban");
		frmSokoban.setBounds(100, 100, 1280, 768);
		frmSokoban.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmSokoban.getContentPane().add(panel, BorderLayout.CENTER);

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(null);
		fullTableRender(); // Adatmodel betöltése és tábla színei beállítása
		
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
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(table);
		
		JLabel lblLepesekSzama = new JLabel("Lépések Száma:");
		panel.add(lblLepesekSzama);
		
		stepCount = new JLabel("0");
		panel.add(stepCount);
		
		JLabel lblHelyreMozgatottElemek = new JLabel("Helyére mozgatott elemek száma:");
		panel.add(lblHelyreMozgatottElemek);
		
		winCount = new JLabel("0");
		panel.add(winCount);
		
		logger.info("Gui init vége");
	}
}
