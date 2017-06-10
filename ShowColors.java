package hw4;

/*
    Author       : Tyler Jones
    Date         : 11/04/2014
    Assignment   : CS211S Homework #4(GUI)
    Program Name : ShowColors.java
    Objective    : This program creates a GUI that can be used to help find
                   the perfect custom color to use in your own program. It
                   allows the user to adjust red, green, and blue levels via
                   sliders and displays the new color in a color filled oval.
                   The user can also change the number format of the color
                   levels to be decimal, hexadecimal, binary, or octal. 
*/

//package hw4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

//**************************Class ShowColors*****************************
public class ShowColors extends JFrame
{
    //Declare all variables
    public final int WIDTH = 800, HEIGHT = 600;
    public final int COLOR_MIN = 0, COLOR_MAX = 255;
    private int redVal, greenVal, blueVal;
    private JRadioButton dec, hex, bin, oct;
    private JButton reset;
    private JSlider redSlider, greenSlider, blueSlider;
    private JPanel numFormatPanel, colorPanel;
    private JLabel redLabel, greenLabel, blueLabel, redLevel, 
                   greenLevel, blueLevel, empty1, empty2;
    //Create the ColorCanvas Object
    ColorCanvas cc = new ColorCanvas();
    
//******************************ShowColors()******************************
    public ShowColors()
    {
        //Title the JFrame and create the JPanels
        super("Show Colors"); 
        createNumFormatPanel();
        createColorPanel();
        //Set up the JFrame
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        setVisible(true);
        //Add the JPanels and the Canvas
        add(numFormatPanel, BorderLayout.EAST);
        add(colorPanel, BorderLayout.SOUTH); 
        add(cc, BorderLayout.CENTER);
    }   
    
//*************************createNumFormatPanel()*************************
    private void createNumFormatPanel()
    {
        numFormatPanel = new JPanel();
        //Set up all number format Radio buttons in a group
        ButtonGroup group = new ButtonGroup();
        dec = new JRadioButton("Decimal", true);
        dec.setToolTipText("Select to display color level in decimal format");
        group.add(dec);
        dec.addActionListener(new NumFormatListener());
        hex = new JRadioButton("Hexadecimal");
        hex.setToolTipText("Select to display color level in hexadecimal format");
        group.add(hex);
        hex.addActionListener(new NumFormatListener());
        bin = new JRadioButton("Binary");
        bin.setToolTipText("Select to display color level in binary format");
        group.add(bin);
        bin.addActionListener(new NumFormatListener());
        oct = new JRadioButton("Octal");
        oct.setToolTipText("Select to display color level in octal format");        
        group.add(oct);
        oct.addActionListener(new NumFormatListener());
        //Add all of the buttons to this JPanel 
        numFormatPanel.setLayout(new GridLayout(6, 1));
        numFormatPanel.add(dec);
        numFormatPanel.add(hex);
        numFormatPanel.add(bin);
        numFormatPanel.add(oct);      
    }
    
//***************************createColorPanel()***************************
    private void createColorPanel()
    {
        colorPanel = new JPanel();
        //Set up JLabels to show color level
        redLevel = new JLabel("0", JLabel.CENTER);
        greenLevel = new JLabel("0", JLabel.CENTER);
        blueLevel = new JLabel("0", JLabel.CENTER);
        //Set up JLabels to label each color and emptys for formatting
        redLabel = new JLabel("Red", JLabel.CENTER);
        greenLabel = new JLabel("Green", JLabel.CENTER);
        blueLabel = new JLabel("Blue", JLabel.CENTER);
        empty1 = new JLabel("");
        empty2 = new JLabel("");
        //Set up the Sliders to change color level
        redSlider = new JSlider(COLOR_MIN, COLOR_MAX, COLOR_MIN);
        redSlider.setToolTipText("Slide to adjust red value");
        redSlider.addChangeListener(new ColorSliderListener());
        greenSlider = new JSlider(COLOR_MIN, COLOR_MAX, COLOR_MIN);
        greenSlider.setToolTipText("Slide to adjust green value");
        greenSlider.addChangeListener(new ColorSliderListener());
        blueSlider = new JSlider(COLOR_MIN, COLOR_MAX, COLOR_MIN);
        blueSlider.setToolTipText("Slide to adjust blue value");
        blueSlider.addChangeListener(new ColorSliderListener());
        //Set up JButton to reset everything to defaults
        reset = new JButton("Reset");
        reset.setToolTipText("Click to reset all color levels");
        reset.addActionListener(new ColorResetListener());
        //Add all buttons, sliders, and labels to the JPanel
        colorPanel.setLayout(new GridLayout(3, 4));
        colorPanel.add(redLabel);
        colorPanel.add(greenLabel);
        colorPanel.add(blueLabel);
        colorPanel.add(empty1);
        colorPanel.add(redLevel);
        colorPanel.add(greenLevel);
        colorPanel.add(blueLevel);
        colorPanel.add(empty2);
        colorPanel.add(redSlider);
        colorPanel.add(greenSlider);
        colorPanel.add(blueSlider);
        colorPanel.add(reset);
    }

//*********************************main()*********************************
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                ShowColors sc = new ShowColors();
                sc.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);  
            }
        });
    }

//****************************Class ColorCanvas***************************    
//This class creates the canvas and deals with all of the Graphics
    private class ColorCanvas extends Canvas
    {
//********************************paint()*********************************
        public void paint(Graphics g)
        {
            Color custColor = new Color(redVal, greenVal, blueVal);
            int w = getWidth();
            int h = getHeight();
            //Draw an oval filled with color based on the selected color levels
            g.setColor(custColor);
            g.fillOval(20, 20, w-40, h-300);
            //Draw the 3 color level bars with size determined by selected levels
            g.setColor(Color.red);
            g.fillRect(20, h-redVal, w/4, h-10);
            g.setColor(Color.green);
            g.fillRect((w/4)+40, h-greenVal, w/4, h-10);
            g.setColor(Color.blue);
            g.fillRect((w/2)+60, h-blueVal, w/4, h-10);
        }
    }

//***********************Class NumFormatListener**************************
//This Listener will change number format of the color level
// based on which number format radio button is selected
    private class NumFormatListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JRadioButton button = (JRadioButton) e.getSource();
            if(button == dec)
            {
                redLevel.setText(Integer.toString(redVal));
                greenLevel.setText(Integer.toString(greenVal));
                blueLevel.setText(Integer.toString(blueVal));
            }
            else if(button == hex)
            {	
                redLevel.setText(Integer.toHexString(redVal));
                greenLevel.setText(Integer.toHexString(greenVal));
                blueLevel.setText(Integer.toHexString(blueVal));
            }
            else if(button == bin)
            {
                redLevel.setText(Integer.toBinaryString(redVal));
                greenLevel.setText(Integer.toBinaryString(greenVal));
                blueLevel.setText(Integer.toBinaryString(blueVal));
            }
            else if(button == oct)
            {
                redLevel.setText(Integer.toOctalString(redVal));
                greenLevel.setText(Integer.toOctalString(greenVal));
                blueLevel.setText(Integer.toOctalString(blueVal));
            }
        }
    }

//***********************Class ColorSliderListener************************
//This listener changes the color level value depending on which slider is 
// being manipulated.
    private class ColorSliderListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            JSlider slider = (JSlider) e.getSource();
            if(slider == redSlider)
            {
                redVal = slider.getValue();
                if(dec.isSelected())
                    redLevel.setText(Integer.toString(redVal));
                else if(hex.isSelected())
                    redLevel.setText(Integer.toHexString(redVal));
                else if(bin.isSelected())
                    redLevel.setText(Integer.toBinaryString(redVal));
                else if(oct.isSelected())
                    redLevel.setText(Integer.toOctalString(redVal));
            }
            else if(slider == greenSlider)
            {
                greenVal = slider.getValue();
                if(dec.isSelected())
                    greenLevel.setText(Integer.toString(greenVal));
                else if(hex.isSelected())
                    greenLevel.setText(Integer.toHexString(greenVal));
                else if(bin.isSelected())
                    greenLevel.setText(Integer.toBinaryString(greenVal));
                else if(oct.isSelected())
                    greenLevel.setText(Integer.toOctalString(greenVal));
            }
            else if(slider == blueSlider)
            {
                blueVal = slider.getValue();
                if(dec.isSelected())
                    blueLevel.setText(Integer.toString(blueVal));
                else if(hex.isSelected())
                    blueLevel.setText(Integer.toHexString(blueVal));
                else if(bin.isSelected())
                    blueLevel.setText(Integer.toBinaryString(blueVal));
                else if(oct.isSelected())
                    blueLevel.setText(Integer.toOctalString(blueVal));
            }
            cc.repaint();
        }
    }

//************************Class ColorResetListener************************    
//This listener resets all fields when reset button is clicked
    private class ColorResetListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            redVal = 0;
            greenVal = 0;
            blueVal = 0;
            redLevel.setText(Integer.toString(redVal));
            greenLevel.setText(Integer.toString(greenVal));
            blueLevel.setText(Integer.toString(blueVal));
            redSlider.setValue(redVal);
            greenSlider.setValue(greenVal);
            blueSlider.setValue(blueVal);
        }
    }
}