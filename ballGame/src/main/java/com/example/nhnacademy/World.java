package com.example.nhnacademy;

import java.awt.Graphics;
import java.util.List;
import java.util.LinkedList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class World extends JPanel implements MouseMotionListener {
    private static int paddleX = 250;
    private static int paddleY = 350;
    private static final int PANEL_WIDTH = 480;
    private static final int PANEL_HEIGHT = 300;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    BoundedBox controlBar;
    
    List<Regionable> regionableList = new LinkedList<>();
    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    JLabel label;

    public World() {
        super();

        controlBar = new BoundedBox(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLUE);
        add(controlBar);

        label = new JLabel(); // 라벨 생성 및 초기 위치 표시
        scoreUpdate(0);
        add(label); // 라벨을 패널에 추가

        setFocusable(true);
        addMouseMotionListener(this);
    }

    /**
     *
     * @param object
     * @throw IllegalArgumentException 공간을 벗어나거나, null인 경우, 볼간 충돌된 경우
     */
    public void add(Regionable object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }

        // if ((object.getX() - object.getRegion().getWidth() / 2 < 0)
        // || (object.getX() + object.getRegion().getWidth() / 2 > getWidth())
        // || (object.getRegion().getMinY() < 0)
        // || (object.getRegion().getMaxY() > getHeight())) {
        // throw new IllegalArgumentException();
        // }

        for (Regionable item : regionableList) {
            if (((object instanceof Bounded) || (item instanceof Bounded))
                    && (object.getRegion().intersects(item.getRegion()))) {
                throw new IllegalArgumentException();
            }
        }

        regionableList.add(object);
    }

    public void remove(Regionable object) {
        regionableList.remove(object);
    }

    @Override
    public void remove(int index) {
        regionableList.remove(index);
    }

    public int getCount() {
        return regionableList.size();
    }

    public Regionable get(int index) {
        return regionableList.get(index);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Regionable object : regionableList) {
            if (object instanceof Paintable) {
                ((Paintable) object).paint(g);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (hasFocus()) {
            paddleX = e.getX();
        
            if (paddleX <= 11 + PADDLE_WIDTH / 2) {
                paddleX = 11 + PADDLE_WIDTH / 2;
            } else if (paddleX + PADDLE_WIDTH / 2 >= PANEL_WIDTH - 11) {
                paddleX = PANEL_WIDTH - PADDLE_WIDTH / 2 - 11;
            }

            // 컨트롤 바 위치 설정
            controlBar.setX(paddleX);

            // 그림 다시 그리기
            repaint();
        }
    }

    public void scoreUpdate(int score){
        label.setText("점수: " + score);
    }
}