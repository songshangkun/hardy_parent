package org.hardy.alegole;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.hardy.alegole.geometry.Line;
import org.hardy.alegole.geometry.MultiForm;
import org.hardy.alegole.geometry.MultiLine;
import org.hardy.alegole.geometry.Point;
import org.hardy.alegole.geometry.Rectangle;
import org.hardy.alegole.geometry.Square;
import org.hardy.alegole.geometry.Triangle;

public class TestFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DrowPanel jp1 = new DrowPanel();
	private JPanel jp2 = new JPanel();
	
	public TestFrame(){
		 this.setSize(1024, 768);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.getContentPane().setLayout(new BorderLayout());
		 this.getContentPane().add(jp1, BorderLayout.CENTER);
		 this.getContentPane().add(jp2, BorderLayout.SOUTH);
		 jp2.setLayout(new FlowLayout());
		 Button b = new Button("test");
		 jp2.add(b);
	}
	
	public static void main(String[] args) {
		TestFrame tf = new TestFrame();
		Point p = new Point(400,700);	
		Rectangle mls = new Square(new Point(50,50),300);
		tf.jp1.sj=mls;	 
		tf.setVisible(true);
	}

}
