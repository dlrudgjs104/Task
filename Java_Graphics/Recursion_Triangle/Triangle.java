import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Insets;

public class Triangle {
    public static class MyCanvas extends JPanel {
        public void draw(Graphics g, int[] xPoint, int[] yPoint) {
            if ((xPoint[0] != xPoint[1]) && (xPoint[0] != xPoint[2])) {
                g.drawPolygon(xPoint, yPoint, xPoint.length);

                for (int i = 0; i < 3; i++) {
                    int[] xPoint1 = { (xPoint[i] + xPoint[0]) / 2, (xPoint[i] + xPoint[1]) / 2,
                            (xPoint[i] + xPoint[2]) / 2, };
                    int[] yPoint1 = { (yPoint[i] + yPoint[0]) / 2, (yPoint[i] + yPoint[1]) / 2,
                            (yPoint[i] + yPoint[2]) / 2, };

                    draw(g, xPoint1, yPoint1); // 재귀 호출로 작은 삼각형 그리기
                }
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            Insets insets = this.getInsets();

            int height = this.getHeight() - insets.top - insets.bottom;

            int width = 600;
            int x = width / 2;
            int y = this.getHeight() - (height * 1 / 3);
            int length = (int) (Math.sqrt(width / 2.0 * width / 2.0
                    + width / 2.0 * Math.tan(Math.toRadians(30)) * width / 2.0 * 2));

            int[] xPoint = { (int) (x + length * Math.cos(Math.toRadians(90))), (int) (x + length * Math.cos(Math.toRadians(210))),
                    (int) (x + length * Math.cos(Math.toRadians(330))) };

            int[] yPoint = { (int) (y - length * Math.sin(Math.toRadians(90))), (int) (y - length * Math.sin(Math.toRadians(210))),
                    (int) (y - length * Math.sin(Math.toRadians(330))) };

            draw(g, xPoint, yPoint);
        }
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Triangle");
        frame.setSize(800, 800);

        MyCanvas panel = new MyCanvas();

        frame.add(panel);
        frame.setVisible(true);
    }
}
