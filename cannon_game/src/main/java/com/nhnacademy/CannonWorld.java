package com.nhnacademy;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;

import java.util.Random;

public class CannonWorld extends MovableWorld implements MouseMotionListener, KeyListener, ComponentListener {
    static final int WALL_THICKNESS = 1000;
    static final int BODY1_WIDTH = 50;
    static final int BODY1_THICKNESS = 20;
    static final int BODY1_SPEED = 10;
    static final int MIN_HEIGHT = WALL_THICKNESS * 2 + BODY1_THICKNESS;
    static final int MIN_WIDTH = WALL_THICKNESS * 2 + BODY1_WIDTH;
    int blockHeight = 50;
    int blockWidth = 100;
    double speed = 10;
    int angle = 45;
    int gravity = 1;
    int windSpeed = 0;
    int score = 0;

    final Box leftWall;
    final Box rightWall;
    final Box topWall;
    final Box bottomWall;
    final MovableBox body1;
    final MovableBall body2;
    MovableBox body3;
    JLabel label5;
    final List<Box> boxList = new LinkedList<>();
    final List<Ball> ballList = new LinkedList<>();
    ExecutorService threadPool = Executors.newFixedThreadPool(5);

    int width;
    int height;

    Random random = new Random();

    final Color[] colors = { Color.YELLOW, Color.WHITE, Color.BLUE, Color.GREEN };

    public CannonWorld(int x, int y, int width, int height) {
        super();
        this.width = width;
        this.height = height;

        setBounds(x, y, width, height);

        // 점수 컴포넌트 구성
        label5 = new JLabel();
        Font font = new Font(label5.getFont().getName(), Font.PLAIN, 20);
        label5.setFont(font);
        label5.setBounds(300, 540, 290, 30);
        label5.setText("점수: " + getScore());
        
        // 폰트 크기를 20으로 설정
        label5.setFont(font);
        add(label5);


        leftWall = new PaintableBox(-WALL_THICKNESS / 2, height / 2, WALL_THICKNESS, height, Color.RED);
        rightWall = new PaintableBox(width + WALL_THICKNESS / 2, height / 2, WALL_THICKNESS, height,
                Color.BLUE);
        topWall = new PaintableBox(width / 2, -WALL_THICKNESS / 2, width + 2 * WALL_THICKNESS, WALL_THICKNESS,
                Color.YELLOW);
        bottomWall = new PaintableBox(width / 2, height + WALL_THICKNESS / 2, width + 2 * WALL_THICKNESS,
                WALL_THICKNESS, Color.GREEN);

        add(leftWall);
        add(rightWall);
        add(topWall);
        add(bottomWall);

        bottomWall.setHitListener(other -> {
            // remove(other);
            // if (other instanceof Movable) {
            // ((Movable) other).stop();
            // }

            if (other instanceof Bounceable) {
                Vector motion = ((Movable) other).getMotion();

                if (motion.getDX() <= 2 && motion.getDY() <= 2) {
                    motion.set(new Vector(0, 0));
                    ((Movable) other).setMotion(motion);
                } else {
                    motion.multiply(0.5);
                    ((Movable) other).setMotion(motion);
                }
            }
        });

        initObstacle();
        initTarget();

        body1 = new MovableBox(BODY1_WIDTH / 2, height - BODY1_THICKNESS / 2, BODY1_WIDTH, BODY1_THICKNESS,
                Color.BLACK);
        add(body1);

        body2 = new MovableBall(BODY1_WIDTH / 2, height - BODY1_THICKNESS, BODY1_THICKNESS, Color.BLACK);
        add(body2);

        body3 = new MovableBox(BODY1_WIDTH / 2 + BODY1_THICKNESS, height - BODY1_THICKNESS * 5 / 4,
                (BODY1_THICKNESS * 2) + (int) (speed * 0.5), BODY1_THICKNESS / 2, Color.BLACK);
        add(body3);

        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);

    }

    public void initTarget() {
        int x = random.nextInt(150) + 900; // 900 ~ 1050
        int y = random.nextInt(350) + 100; // 100 ~ 450
        int colorIndex = random.nextInt(4);

        Box box = new BrittleBox(x, y, blockWidth, blockHeight, colors[colorIndex]);
        boxList.add(box);
        add(box);
        box.setHitListener(other -> {
            remove(box);
            score++;
            label5.setText("점수: " + getScore());
            initTarget();
        });

    }

    public void initObstacle() {
        int y = 330;
        for (int line = 0; line < 4; line++) {
            int x = 400;

            while (x + blockWidth / 2 <= getWidth() && x < 800) {
                if (random.nextInt(2) == 1) {
                    Box box = new PaintableBox(x, y, blockWidth, blockHeight, Color.DARK_GRAY);
                    boxList.add(box);
                    add(box);
                }
                x += blockWidth;
            }
            y += blockHeight;
        }

    }

    @Override
    public void add(Bounded object) {
        super.add(object);
        if (object instanceof Movable) {
            threadPool.execute((Movable) object);
        }
    }

    public void start() {
        BounceableBall ball = new BounceableBall(body3.getX() + BODY1_THICKNESS + (int) (speed * 0.5) + 5, body3.getY(),
                5, Color.RED);

        Vector basicMotion = ball.getMotion();
        basicMotion.velocity(speed, angle);
        ball.setMotion(basicMotion);
        ball.setDT(getDT());

        ball.addStartedActionListener(() -> {

        });

        ball.addMovedActionListener(() -> {
            List<Bounded> removeList = new LinkedList<>();

            Vector newMotion = ball.getMotion();
            newMotion.add(new Vector(0, gravity));
            newMotion.add(new Vector(windSpeed, 0));

            ball.setMotion(newMotion);

            if (ball instanceof Bounceable) {
                for (int j = 0; j < getCount(); j++) {
                    Bounded other = get(j);

                    if (ball != other && ball.isCollision(other.getBounds())) {
                        //((Bounceable) ball).bounce(other);
                        removeList.add(ball);
                        if (other instanceof HitListener) {
                            ((HitListener) other).hit(ball);
                        }
                    }
                }
            }

            for (Bounded item : removeList) {
                remove(item);
            }
        });

        add(ball);
    }

    public void clear() {
        ballList.clear();

        // boundedList에서 Ball 인스턴스 제거
        Iterator<Bounded> iterator = boundedList.iterator();
        while (iterator.hasNext()) {
            Bounded bounded = iterator.next();
            if (bounded instanceof BounceableBall) {
                iterator.remove(); // 안전하게 제거
            }
        }

        // ExecutorService를 안전하게 종료하고 재할당
        if (threadPool != null) {
            threadPool.shutdown(); // 작업 실행을 종료  
        }
        threadPool = Executors.newFixedThreadPool(6);
    }

    @Override
    public void keyPressed(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
        logger.info("{}", event.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent event) {
        //
        logger.info("{}", event.getKeyCode());
    }

    @Override
    public void componentHidden(ComponentEvent event) {
        logger.info("Hidden");
    }

    @Override
    public void componentMoved(ComponentEvent event) {
        logger.info("Moved");
    }

    @Override
    public void componentResized(ComponentEvent event) {
        if (ballList.isEmpty() && (getWidth() > BODY1_WIDTH) && (getHeight() > BODY1_THICKNESS)) {
            leftWall.setBounds(new Bounds(-WALL_THICKNESS, -WALL_THICKNESS,
                    WALL_THICKNESS, WALL_THICKNESS * 2 + getHeight()));
            rightWall.setBounds(new Bounds(getWidth(), -WALL_THICKNESS,
                    WALL_THICKNESS, WALL_THICKNESS * 2 + getHeight()));
            topWall.setBounds(new Bounds(-WALL_THICKNESS, -WALL_THICKNESS,
                    getWidth() + WALL_THICKNESS * 2, WALL_THICKNESS));
            bottomWall.setBounds(new Bounds(-WALL_THICKNESS, getHeight(),
                    getWidth() + WALL_THICKNESS * 2, WALL_THICKNESS));
            body1.moveTo(new Point(100, getHeight() - BODY1_THICKNESS / 2));
            body2.moveTo(new Point(100, getHeight() - BODY1_THICKNESS / 2));
            body3.moveTo(new Point(100, getHeight() - BODY1_THICKNESS / 2));
        }

    }

    @Override
    public void componentShown(ComponentEvent event) {
        logger.info("Shown");
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (event.getX() > body1.getWidth() / 2 && event.getX() < getWidth() - body1.getWidth() / 2 && event.getX() < 250){
            body1.setLocation(new Point(event.getX(), body1.getCenterY()));
            body2.setLocation(new Point(event.getX(), body2.getCenterY()));
            body3.setLocation(new Point(event.getX() + BODY1_THICKNESS, body3.getCenterY()));
        }
    }

    @Override
    public void mouseMoved(MouseEvent event) { 
    }

    // 포속도 설정 및 속도 값에 따른 포의 길이 조정
    public void setSpeed(double speed) {
        this.speed = speed;
        remove(body3);
        body3 = new MovableBox(body1.getX() + BODY1_THICKNESS, height - BODY1_THICKNESS * 5 / 4, (BODY1_THICKNESS * 2) + (int) (speed * 0.5), BODY1_THICKNESS / 2, Color.BLACK);
        add(body3);
    }

    // 포의 각도 조정
    public void setAngle(int angle) {
        this.angle = angle;
    }

    // 포탄의 중력 설정
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    // 포탄의 바람 설정
    public void setWindSpeed(int speed) {
        this.speed = speed;
    }

    public void addScore(){
        score++;
    }

    public int getScore(){
        return score;
    }

}