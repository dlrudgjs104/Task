package com.example.nhnacademy;

import java.awt.Color;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TestWorld {
    static final int FRAME_WIDTH = 480;
    static final int FRAME_HEIGHT = 400;
    static final int MIN_RADIUS = 10;
    static final int MAX_RADIUS = 50;
    static final int FIXED_BALL_COUNT = 0;
    static int BOUNDED_BALL_COUNT = 1;
    static final int BOUNDED_BOX_COUNT = 0;
    static final int BRICK_WIDTH = 50;
    static final int BRICK_HEIGHT = 20;
    static final int MIN_DELTA = 5;
    static final int MAX_DELTA = 7;
    static final int MAX_MOVE_COUNT = 0;
    static final int DT = 10;
    static final int WALL_THICKNESS = 10;
    static final int BOUNDED_BALL_RADIUS = 50;
    static final Color[] COLOR_TABLE = {
            Color.BLACK,
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW
    };

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MovableWorld world = new MovableWorld();
        world.setBounds(-WALL_THICKNESS, -WALL_THICKNESS,FRAME_WIDTH + WALL_THICKNESS * 2,FRAME_HEIGHT + WALL_THICKNESS * 2);

        frame.add(world);

        Random random = new Random();

        // 왼쪽 벽
        world.add(new PaintableBox(-WALL_THICKNESS / 2 + 10, FRAME_HEIGHT / 2,
                WALL_THICKNESS, FRAME_HEIGHT + 2 * WALL_THICKNESS, Color.DARK_GRAY));
        // 위쪽 벽
        world.add(new PaintableBox(FRAME_WIDTH / 2, -WALL_THICKNESS / 2 + 30,
                FRAME_WIDTH + 2 * WALL_THICKNESS, WALL_THICKNESS, Color.DARK_GRAY));
        // 오른쪽 벽
        world.add(new PaintableBox(FRAME_WIDTH + WALL_THICKNESS / 2 - 10, FRAME_HEIGHT / 2,
                WALL_THICKNESS, FRAME_HEIGHT + 2 * WALL_THICKNESS, Color.DARK_GRAY));
        // 아래쪽 벽
        world.add(new PaintableBox(FRAME_WIDTH / 2, FRAME_HEIGHT + WALL_THICKNESS / 2,
                FRAME_WIDTH + 2 * WALL_THICKNESS, WALL_THICKNESS, Color.DARK_GRAY, 100));

        // 벽돌 생성
        for (int i = 51 + BRICK_HEIGHT / 2; i <= 200; i += (BRICK_HEIGHT + 1)) {
            for (int j = 50 - BRICK_WIDTH / 2 + 11; j <= FRAME_WIDTH - BRICK_WIDTH / 2 - 11; j += (BRICK_WIDTH + 1)) {
                world.add(new Brick(j, i, BRICK_WIDTH, BRICK_HEIGHT,
                        COLOR_TABLE[random.nextInt(COLOR_TABLE.length)]));
            }
        }

        while (BOUNDED_BALL_COUNT > 0) {
            try {
                BoundedBall ball = new BoundedBall(250, 340, BOUNDED_BALL_RADIUS, Color.GRAY);

                int dx = MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1);
                int dy = MIN_DELTA - random.nextInt(MAX_DELTA - MIN_DELTA + 1);

                ball.setDX(dx);
                ball.setDY(dy);

                world.add(ball);
                BOUNDED_BALL_COUNT--;
            } catch (IllegalArgumentException ignore) {
                //
            }
        }

        frame.setVisible(true);

        world.setMaxMoveCount(MAX_MOVE_COUNT);
        world.setDT(DT);
        world.run();
    }
}