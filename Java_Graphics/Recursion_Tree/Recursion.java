import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Recursion {
    public static class MyCanvas extends JPanel {
        private int startX = 400; // 시작점 x 좌표
        private int startY = 600; // 시작점 y 좌표
        private int length = 128; // 선의 길이
        private int growth = 80; // 선의 성장률
        private int rotate = 20; // 선의 회전 각도

        // 나무 그리기
        public void tree(Graphics g, int startX, int startY, int degree, int length) {
            if (length > 1) {
                int endX = (int) (startX - length * Math.cos(Math.toRadians(degree)));
                int endY = (int) (startY - length * Math.sin(Math.toRadians(degree)));
                int branchLength = (int) (length * growth * 0.01);

                g.drawLine(startX, startY, endX, endY);
                tree(g, endX, endY, degree - rotate, branchLength); // 왼쪽 가지
                tree(g, endX, endY, degree + rotate, branchLength); // 오른쪽 가지
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            tree(g, startX, startY, 90, length); // 처음 시작은 수직으로 위쪽
        }
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Recursion");
        frame.setSize(800, 650);

        MyCanvas panel = new MyCanvas();

        frame.add(panel);
        frame.setVisible(true);
    }
}
