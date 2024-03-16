package com.nhnacademy;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CannonGame extends JFrame implements ComponentListener {
    static final int FRAME_WIDTH = 1500;
    static final int FRAME_HEIGHT = 700;
    static final int MIN_RADIUS = 10;
    static final int MAX_RADIUS = 50;
    static final int MIN_WIDTH = 10;
    static final int MAX_WIDTH = 50;
    static final int MIN_HEIGHT = 10;
    static final int MAX_HEIGHT = 50;
    static final int FIXED_BALL_COUNT = 0;
    static final int FIXED_BOX_COUNT = 3;
    static final int BOUNDED_BALL_COUNT = 5;
    static final int MIN_DELTA = 5;
    static final int MAX_DELTA = 7;
    static final int MAX_MOVE_COUNT = 0;
    static final int DT = 50;
    static final int BLOCK_WIDTH = 80;
    static final Color[] COLOR_TABLE = {
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.YELLOW
    };

    Logger logger = LogManager.getLogger();

    CannonWorld world;

    public CannonGame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(this);

        setLayout(null);

        world = new CannonWorld(300, 0, FRAME_WIDTH - 400, FRAME_HEIGHT - 200);
        world.setDT(DT);
        world.setBackground(Color.WHITE);
        add(world);

        // 속도 컴포넌트 구성
        JLabel label1 = new JLabel();
        label1.setBounds(10, 20, 290, 30);
        label1.setText("속도");
        
        // 폰트 크기를 20으로 설정
        Font font = new Font(label1.getFont().getName(), Font.PLAIN, 20);
        label1.setFont(font);
        add(label1);

        JSlider speedSlider = new JSlider(0, 100, 10);
        speedSlider.setBounds(0, 50, 300, 100);
        speedSlider.setPaintTrack(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setMajorTickSpacing(20);
        speedSlider.setMinorTickSpacing(4);
        add(speedSlider);
        
        // 각도 컴포넌트 구성
        JLabel label2 = new JLabel();
        label2.setBounds(10, 150, 290, 30);
        label2.setText("각도");
        
        // 폰트 크기를 20으로 설정
        label2.setFont(font);
        add(label2);

        JSlider angleSlider = new JSlider(0, 100, 45);
        angleSlider.setBounds(0, 180, 300, 100);
        angleSlider.setPaintTrack(true);
        angleSlider.setPaintTicks(true);
        angleSlider.setPaintLabels(true);
        angleSlider.setMajorTickSpacing(20);
        angleSlider.setMinorTickSpacing(4);
        add(angleSlider);

        // 중력 컴포넌트 구성
        JLabel label3 = new JLabel();
        label3.setBounds(10, 280, 290, 30);
        label3.setText("중력");
        
        // 폰트 크기를 20으로 설정
        label3.setFont(font);
        add(label3);

        JSlider gravitySlider = new JSlider(0, 10, 1);
        gravitySlider.setBounds(0, 310, 300, 100);
        gravitySlider.setPaintTrack(true);
        gravitySlider.setPaintTicks(true);
        gravitySlider.setPaintLabels(true);
        gravitySlider.setMajorTickSpacing(2);
        add(gravitySlider);

        // 바람 컴포넌트 구성
        JLabel label4 = new JLabel();
        label4.setBounds(10, 410, 290, 30);
        label4.setText("바람");
        
        // 폰트 크기를 20으로 설정
        label4.setFont(font);
        add(label4);

        JSlider windSlider = new JSlider(-10, 10, 0);
        windSlider.setBounds(0, 440, 300, 100);
        windSlider.setPaintTrack(true);
        windSlider.setPaintTicks(true);
        windSlider.setPaintLabels(true);
        windSlider.setMajorTickSpacing(2);
        add(windSlider);

        // Fire 컴포넌트 구성
        JButton fireButton = new JButton("Fire!");
        fireButton.setBounds(5, 540, 140, 100);
        fireButton.setFont(font);
        add(fireButton);

        // Clear 컴포넌트 구성
        JButton clearButton = new JButton("Clear!");
        clearButton.setBounds(155, 540, 140, 100);
        clearButton.setFont(font);
        add(clearButton);

        speedSlider.addChangeListener(e -> world.setSpeed(speedSlider.getValue()));
        angleSlider.addChangeListener(e -> world.setAngle(angleSlider.getValue()));
        gravitySlider.addChangeListener(e -> world.setGravity(gravitySlider.getValue()));
        windSlider.addChangeListener(e -> world.setWindSpeed(windSlider.getValue()));

        fireButton.addActionListener(e -> world.start());
        clearButton.addActionListener(e -> world.clear());

    }

    public void start() {
        setVisible(true);
        setEnabled(true);

        world.run();
    }

    public static void main(String[] args) {
        CannonGame frame = new CannonGame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        frame.start();
    }

    public void componentHidden(ComponentEvent event) {
        //
    }

    @Override
    public void componentMoved(ComponentEvent event) {
        //
    }

    @Override
    public void componentResized(ComponentEvent event) {
        if (getWidth() % BLOCK_WIDTH != 0) {
            setSize(getWidth() / BLOCK_WIDTH * BLOCK_WIDTH, getHeight());
        }
    }

    @Override
    public void componentShown(ComponentEvent event) {
        //
    }
}