/* Bepen Neupane
 * NetID: bneupane
 * Project 3 - Fireworks
 * TR 11:05 - 12:20
 * I did not collaborate with anyone on this assignment.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Fireworks extends JFrame implements ActionListener, ItemListener, ChangeListener {
	protected JButton render;
	protected JButton clear;
	protected JCheckBox red;
	protected boolean redSelect;
	protected JCheckBox green;
	protected boolean greenSelect;
	protected JCheckBox blue;
	protected boolean blueSelect;
	protected JCheckBox branch;
	protected boolean branchSelect;
	protected JCheckBox volcano;
	protected boolean volcanoSelect;
	protected JCheckBox peony;
	protected boolean peonySelect;
	protected JCheckBox rain;
	protected boolean rainSelect;
	protected JCheckBox fourStar;
	protected boolean fourStarSelect;
	protected JSlider angleSelect;
	protected JLabel angleNum;
	protected double angle;
	protected JTextField velocitySelect;
	protected JLabel velocityNum;
	protected double velocity;
	protected JTextField timeSelect;
	protected JLabel timeNum;
	protected double timeDelay;
	protected Color color;
	protected Box cBox;
	protected Box fBox;
	protected ButtonGroup fireworks;
	protected ButtonModel selection;
	protected Random rand = new Random();

	public Fireworks() {
		setTitle("Fireworks"); // title of the frame
		setSize(600, 600); // initial size of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program ends when the frame is closed
		setLayout(new BorderLayout()); // border layout

		JPanel top = new JPanel(); // the panel 'top' will be at the top of the frame
		add(top, BorderLayout.NORTH);

		JPanel left = new JPanel(); // the panel 'left' will be on the left of the frame
		add(left, BorderLayout.WEST);

		JPanel right = new JPanel(); // the panel 'right' will be at the right of the frame
		add(right, BorderLayout.EAST);

		JPanel bottom = new JPanel(); // the panel 'bottom' will be at the bottom of the frame
		add(bottom, BorderLayout.SOUTH);

		red = new JCheckBox("Red"); // checkbox to select red
		red.addItemListener(this);

		green = new JCheckBox("Green"); // checkbox to select green
		green.addItemListener(this);

		blue = new JCheckBox("Blue"); // checkbox to select blue
		blue.addItemListener(this);

		branch = new JCheckBox("Branch"); // checkbox to select branch fireworks
		branch.addItemListener(this);

		volcano = new JCheckBox("Volcano"); // checkbox to select volcano fireworks
		volcano.addItemListener(this);

		peony = new JCheckBox("Peony"); // checkbox to select peony fireworks
		peony.addItemListener(this);

		rain = new JCheckBox("Rain"); // checkbox to select rain fireworks
		rain.addItemListener(this);

		fourStar = new JCheckBox("Four-Pointed Star"); // checkbox to select four-pointed star
		fourStar.addItemListener(this);

		render = new JButton("Render"); // button to render the fireworks
		render.addActionListener(this);

		clear = new JButton("Clear"); // button to clear the canvas
		clear.addActionListener(this);

		angleSelect = new JSlider(0, 90); // slider to select angle
		angleSelect.addChangeListener(this);

		angleNum = new JLabel("Angle"); // this label will change to display the angle

		velocitySelect = new JTextField(5); // text field to select the velocity
		velocitySelect.addActionListener(this);

		velocityNum = new JLabel("Velocity"); // this label will change to display the velocity

		timeSelect = new JTextField(5); // text field to select the time delay
		timeSelect.addActionListener(this);

		timeNum = new JLabel("Time (seconds)"); // this label will change to display the time

		cBox = Box.createVerticalBox(); // in order to have the checkboxes stay vertical, I had to create a box
		cBox.add(red);
		cBox.add(green);
		cBox.add(blue);
		left.add(cBox); // this adds the box to the left panel

		fBox = Box.createVerticalBox(); // in order to have the checkboxes stay vertical, I had to create a box
		fBox.add(branch);
		fBox.add(volcano);
		fBox.add(peony);
		fBox.add(rain);
		fBox.add(fourStar);
		right.add(fBox); // this adds the box to the right panel

		top.add(render); // the render and clear buttons are added to the top panel
		top.add(clear);

		bottom.add(angleNum); // the angle, velocity, and time delay are added to the bottom panel
		bottom.add(angleSelect);
		bottom.add(velocitySelect);
		bottom.add(velocityNum);
		bottom.add(timeSelect);
		bottom.add(timeNum);

		fireworks = new ButtonGroup(); // I had to create a button group to make sure that only one firework design could be selected at a time
		fireworks.add(branch);
		fireworks.add(volcano);
		fireworks.add(peony);
		fireworks.add(rain);
		fireworks.add(fourStar);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Graphics g = getGraphics();
		Object source = e.getSource();
		if (source == render) { // when render is pressed, this will run
			g.setColor(color); // the color variable is set by the colorSelect method
			int x1 = 0;
			int y1 = 0;
			for (double i = timeDelay / 15; i <= timeDelay; i += timeDelay / 15) { // this for loop draws the arc/trajectory of the firework
				int xArc = (int) (velocity * Math.cos(Math.toRadians(angle)) * i); // 15 different lines are drawn, one for each fifteenth of the time that the user inputted
				int yArc = (int) ((velocity * Math.sin(Math.toRadians(angle)) * i) - (0.5 * 9.8 * i * i));
				g.drawLine(x1, (getHeight()) - y1, xArc, getHeight() - yArc);
				x1 = xArc; // in order to save the value of the new x and y, x1 and y1 change. The reason for this is so the lines will be connected
				y1 = yArc;
			}
			if (branchSelect == true) { // if the user selects the branch fireworks, this runs
				int x2 = (int) (velocity * Math.cos(Math.toRadians(angle)) * timeDelay); //x2 and y2 are where the arc ends thus is the location for the explosion
				int y2 = (int) ((velocity * Math.sin(Math.toRadians(angle)) * timeDelay)
						- (0.5 * 9.8 * timeDelay * timeDelay));
				int cWidth = 200; // width of the oval
				int cHeight = 200; // height of the oval, since width and height are the same, it's a circle
				int xCorner = x2 - cWidth / 2; // xCorner and yCorner is the top left of the circle/where the circle's coordinates are for drawing it
				int yCorner = (getHeight() - y2) - cHeight / 2;
				int xCentered = xCorner + 100; // xCentered and yCentered are the coordinates for the center of the circle
				int yCentered = yCorner + 100;
				double inc = (Math.PI * 2) / 50; // this is how much i will be incremented by. 100 lines will be made into a circle
				for (double i = 0; i < Math.PI * 2; i += inc) { // until i is 2pi, the loop will keep running
					int xMain = rand.nextInt(25) + 75; // random radius for the circle, the lines will vary slightly by length
					int yMain = rand.nextInt(25) + 75;
					int xBranch = rand.nextInt(4) + 16; // random radius for the branch at the tip of the first line drawn, there will be 10 small lines at the tip of every first line
					int yBranch = rand.nextInt(4) + 16;
					int xCos = xCentered + (int) (xMain * Math.cos(i)); // the x value of the first line that will be drawn
					int ySin = yCentered + (int) (yMain * Math.sin(i)); // the y value of the first line that will be drawn
					for (double z = 0; z < Math.PI * 2; z += inc * 5) { // 20 small lines will be drawn at the tip of every line
						int xBranchCos = xCos + (int) (xBranch * Math.cos(z)); // the x value of the line that will be drawn at the tip of the first line
						int yBranchSin = ySin + (int) (yBranch * Math.sin(z)); // the y value of the line that will be drawn at the tip of the first line
						g.drawLine(xCentered, yCentered, xCos, ySin); // draws the first line
						g.drawLine(xCos, ySin, xBranchCos, yBranchSin); // draws the branch line
					}

				}

			}
			if (volcanoSelect == true) { // if the user selects the volcano firework, this will run
				int height = getHeight() - 1; // height of the frame
				int x2 = (int) (velocity * Math.cos(Math.toRadians(angle)) * timeDelay); // x2 and y2 are the coordinates of the explosion
				int y2 = height - (int) ((velocity * Math.sin(Math.toRadians(angle)) * timeDelay)
						- (0.5 * 9.8 * timeDelay * timeDelay));
				g.drawLine(x2 - 100, y2, x2 + 100, y2); // draws the base of the volcano
				g.drawLine(x1 - 45, y2 - 70, x2 + 45, y2 - 70); // draws the top of the volcano
				g.drawLine(x1 - 100, y2, x2 - 45, y2 - 70); // draws the left side of the volcano
				g.drawLine(x1 + 100, y2, x2 + 45, y2 - 70); // draws the right side of the volcano
				for (int i = 0; i < 300; i++) { // this is essentially the eruption of the volcano
					int randX = rand.nextInt(45); // a random x and y value will be generated that will dictate where the dot is drawn
					int randY = rand.nextInt(100);
					int negative = rand.nextInt(2) + 1;
					if (negative == 1) { // 50% of the time, randX will be negative in order to make sure that not all of the dots will be drawn on only the right side
						randX = randX * -1;
					}
					g.fillOval(x2 + randX, y2 - 75 - randY, 2, 2); // the dot that will be drawn

				}

			}
			if (peonySelect == true) { // if the user selects the peony firework, this will run
				int x2 = (int) (velocity * Math.cos(Math.toRadians(angle)) * timeDelay); // coordinates of the explosion
				int y2 = (int) ((velocity * Math.sin(Math.toRadians(angle)) * timeDelay)
						- (0.5 * 9.8 * timeDelay * timeDelay));
				int cWidth = 200; // dimensions of the circle
				int cHeight = 200;
				int xCorner = x2 - cWidth / 2; // corner coordinates where the circle originates
				int yCorner = (getHeight() - y2) - cHeight / 2;
				int xCentered = xCorner + 100; // coordinates for the center of the circle
				int yCentered = yCorner + 100;
				double inc = (Math.PI * 2) / 50; // this is what i will be incremented by
				for (double i = 0; i < Math.PI * 2; i += inc) { // 100 lines will be drawn with circles at the tip of each
					int xCos = xCentered + (int) (100 * Math.cos(i)); // xCos and ySin are the x and y coordinates for the end of the line
					int ySin = yCentered + (int) (100 * Math.sin(i));
					g.drawLine(xCentered, yCentered, xCos, ySin); // all the lines originate at the center and then are drawn to a certain x and y coordinate depending on the value of i
					g.drawOval(xCos, ySin, 10, 10); // circles are drawn at the tip of the lines
				}

			}
			if (rainSelect == true) { // if the rain firework is seleted, this will run
				int height = getHeight() - 1; // height of the canvas
				int x2 = (int) (velocity * Math.cos(Math.toRadians(angle)) * timeDelay); // coordinates for the explosion
				int y2 = height - (int) ((velocity * Math.sin(Math.toRadians(angle)) * timeDelay)
						- (0.5 * 9.8 * timeDelay * timeDelay));
				int ySpace = height - y2; // space between the explosion point and the bottom of the frame
				for (int i = 0; i < 20; i++) { // 20 random lines will be drawn originating from the point of explosion ending between the bottom of the frame and the point of the explosion
					int yEnd = rand.nextInt(ySpace) + y2; // ending y coordinate
					g.drawLine(x2, y2, x2 + 15 * i, yEnd); // draws the line
					g.drawOval(x2 + 15 * i, yEnd, 6, 6); // draws little circles at the tip of the lines
				}

			}
			if (fourStarSelect == true) { // if the four-pointed star is selected, this will run
				int x2 = (int) (velocity * Math.cos(Math.toRadians(angle)) * timeDelay); // origin of the explosion
				int y2 = (int) ((velocity * Math.sin(Math.toRadians(angle)) * timeDelay)
						- (0.5 * 9.8 * timeDelay * timeDelay));
				int xCentered = x2 - 100; // position of the explosion in relation to a shape being drawn
				int yCentered = (getHeight() - y2) - 100;
				g.drawLine(xCentered + 50, yCentered + 50, xCentered + 100, yCentered - 50); //these drawLines draw the lines that make up the four-pointed star
				g.drawLine(xCentered + 150, yCentered + 50, xCentered + 100, yCentered - 50);
				g.drawLine(xCentered + 150, yCentered + 50, xCentered + 250, yCentered + 100);
				g.drawLine(xCentered + 150, yCentered + 150, xCentered + 250, yCentered + 100);
				g.drawLine(xCentered + 150, yCentered + 150, xCentered + 100, yCentered + 250);
				g.drawLine(xCentered + 50, yCentered + 150, xCentered + 100, yCentered + 250);
				g.drawLine(xCentered + 50, yCentered + 150, xCentered - 50, yCentered + 100);
				g.drawLine(xCentered + 50, yCentered + 50, xCentered - 50, yCentered + 100);

			}
		} else if (source == clear) { // if the clear button is pressed, this will run
			repaint(); // canvas is repainted
		} else if (source == velocitySelect) { // if the user enters something in the velocity text box, this will run
			velocity = Double.parseDouble(velocitySelect.getText()); // the number that the user entered will be converted to a double and stored under velocity
			velocityNum.setText(Double.toString(velocity)); // the velocity value will now be the text on the label next to the velocity text box

		} else if (source == timeSelect) { // if the user enters something in the time text box, this will run
			timeDelay = Double.parseDouble(timeSelect.getText()); // the number that the user entered will be converted to a double and stored under timeDelay
			timeNum.setText(Double.toString(timeDelay)); // the timeDelay value will now be the text on the label next to the time text box
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) { // this contains the actions for all of the checkboxes
		Object source = e.getSource();
		if (source == red) { // if the red checkbox is interacted with, this will run
			if (red.isSelected() == true) { // if the red checkbox is selected, redSelect will be true and if it's not, then redSelect will be false
				redSelect = true;
			} else {
				redSelect = false;
			}
		}
		if (source == green) { // if the green checkbox is interacted with, this will run
			if (green.isSelected() == true) { // if the green checkbox is selected, greenSelect will be true and if it's not, then greenSelect will be false
				greenSelect = true;
			} else {
				greenSelect = false;
			}
		}
		if (source == blue) { // if the blue checkbox is interacted with, this will run
			if (blue.isSelected() == true) { // if the blue checkbox is selected, blueSelect will be true and if it's not, then blueSelect will be false
				blueSelect = true;
			} else {
				blueSelect = false;
			}
		}
		colorSelect(redSelect, greenSelect, blueSelect); // since multiple colors can be checked off, this method assigns a value to color depending on which combination of checkboxes are selected

		if (source == branch) { // if the branch checkbox is interacted with, this will run
			if (branch.isSelected() == true) { // if the branch checkbox is selected, branchSelect will be true and if it's not, then branchSelect will be false
				branchSelect = true;
			} else {
				branchSelect = false;
			}
		}

		if (source == volcano) { // if the volcano checkbox is interacted with, this will run
			if (volcano.isSelected() == true) { // if the volcano checkbox is selected, volcanoSelect will be true and if it's not, then volcanoSelect will be false
				volcanoSelect = true;
			} else {
				volcanoSelect = false;
			}
		}

		if (source == peony) { // if the peony checkbox is interacted with, this will run
			if (peony.isSelected() == true) { // if the peony checkbox is selected, peonySelect will be true and if it's not, then peonySelect will be false
				peonySelect = true;
			} else {
				peonySelect = false;
			}
		}

		if (source == rain) { // if the rain checkbox is interacted with, this will run
			if (rain.isSelected() == true) { // if the rain checkbox is selected, rainSelect will be true and if it's not, then rainSelect will be false
				rainSelect = true;
			} else {
				rainSelect = false;
			}
		}

		if (source == fourStar) { // if the fourStar checkbox is interacted with, this will run
			if (fourStar.isSelected() == true) { // if the fourStar checkbox is selected, fourStarSelect will be true and if it's not, then fourStarSelect will be false
				fourStarSelect = true;
			} else {
				fourStarSelect = false;
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) { // when the slider moves, this runs
		Object slider = e.getSource();
		if (slider == angleSelect) {
			angle = angleSelect.getValue(); // the value that the slider moved to will be the new value stored under 'angle'
			angleNum.setText(Double.toString(angle)); // the value of 'angle' is the new label for the label next to the slider
		}

	}

	public void colorSelect(boolean red, boolean green, boolean blue) { // colorSelect takes three arguments, red, green, and blue. A combination of trues and falses dictates the color what will be assigned to 'color'
		if (red == true && green == false && blue == false) { // red
			color = Color.RED;

		}
		if (red == false && green == true && blue == false) { // green
			color = Color.GREEN;

		}
		if (red == false && green == false && blue == true) { // blue
			color = Color.BLUE;

		} // from here to the bottom, if the user selects 2 or more colors, or selects no colors, a combination of the color will be drawn
		if (red == true && green == true && blue == false) { // red and green combine to form yellow
			color = Color.YELLOW;

		}
		if (red == true && green == false && blue == true) { // red and blue combine to form magenta
			color = Color.MAGENTA;

		}
		if (red == false && green == true && blue == true) { // green and blue combine to form cyan
			color = Color.CYAN;

		}
		if (red == false && green == false && blue == false) { // the definition of black is lack of color so if no colors are selected, the fireworks will be black
			color = Color.BLACK;

		}
		if (red == true && green == true && blue == true) { // if all the colors are selected, the fireworks will be white
			color = Color.WHITE;

		}
	}

	public static void main(String[] args) {
		Fireworks frame = new Fireworks(); // frame is created
		frame.setVisible(true); // frame is set to visible

	}

}
