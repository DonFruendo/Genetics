package launcher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.PopulationController;

public class GUI {

	final PopulationController population;
	final JPanel contentPanel;
	final Map<String, JSlider> values = new HashMap<String, JSlider>();
	
	public GUI(PopulationController population) {
		this.population = population;
		JFrame gui = new JFrame("Genetics");
		//gui.setPreferredSize(new Dimension(800, 00));
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(0, 1, 5, 5));
		initializeHeader(mainPanel);
		
		initializeSlider("Futtermenge", 0, 100);
		initializeSlider("Temperatur", 0, 100);
		//initializeSlider("", 0, 0);
		initializeSlider("Generationen", 0, 100);
		
		initializeSubmit(mainPanel);
		mainPanel.add(contentPanel);
		gui.add(mainPanel);
		gui.pack();
		gui.setSize(new Dimension(800, gui.getHeight()));
		gui.setVisible(true);
	}
	
	public void initializeHeader(JPanel mainPanel) {
		JPanel headerPanel = new JPanel();
		JLabel headerLabel = new JLabel("Umgebung");
		
		headerPanel.add(headerLabel);
		mainPanel.add(headerPanel, BorderLayout.NORTH);
	}
	
	public void initializeSlider(final String sliderName, final int minX, final int maxX) {
		final int minMaxSpan = maxX - minX;
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.LINE_AXIS));
		final JLabel lbName = new JLabel(sliderName + ":");
		final JLabel lbSliderPos = new JLabel("50");
		lbSliderPos.setHorizontalAlignment(JLabel.CENTER);
		lbSliderPos.setPreferredSize(new Dimension(30,10));
		final JButton bt = new JButton("Reset");
		final int sliderDefaultPosition =  (int)(minMaxSpan / 2);
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, minX, maxX, sliderDefaultPosition);
		values.put(sliderName, slider);
		
		bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				slider.setValue(sliderDefaultPosition);
			}
		});
		bt.setPreferredSize(new Dimension(bt.getPreferredSize().width, bt.getPreferredSize().height*2));
		slider.setPreferredSize(new Dimension(2000, slider.getPreferredSize().height));
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				//lbSliderPos.setText(slider.getValue() + "");
			}
		});
		slider.setMajorTickSpacing(minMaxSpan / 10);
		slider.setMinorTickSpacing(minMaxSpan / 50);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		sliderPanel.add(lbName);
		sliderPanel.add(slider);
		//sliderPanel.add(lbSliderPos);
		sliderPanel.add(bt);
		contentPanel.add(sliderPanel);
	}

	public void initializeSubmit(JPanel mainPanel) {
		JPanel submitPanel = new JPanel();
		JButton submitButton = new JButton("Start Simulation");
		submitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = values.get("Generationen").getValue();
				population.generateNextNGenerations(n);
			}
		});
		
		submitPanel.add(submitButton);
		mainPanel.add(submitPanel, BorderLayout.SOUTH);
	}
}
