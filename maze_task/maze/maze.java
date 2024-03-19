public class Test {
    // 시작지점
    static int nowX = 0;
    static int nowY = 0;
    static int startX = 0;
    static int startY = 0;

    // 미로
    static int maze[][] = { {0, 0 ,1, 0, 1},
                            {1, 0, 0, 0, 1},
                            {1, 0, 1, 1, 1},
                            {1, 0, 0, 0, 0},
                            {1, 1, 1, 1, 1} };

    // 확인한 길
    static boolean[][] visited = new boolean[5][5];

    // 우하좌상 이동을 위한 배열  1,0     0,1     -1,0    0,-1
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0}; 

    // 미로 범위 내에 있는지와 방문하지 않았는지 확인
    public static boolean isValid(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == 0 && !visited[x][y];
    }

    // 목적지
    public static boolean destination(int x, int y){
        return (x == 3) && (y == 4);
    }

    public static boolean solveMaze(int x, int y){
        // 현재 위치를 방문처리
        visited[x][y] = true;

        // 목적지를 도착했을 경우 종료
        if ( destination(x, y) ){
            System.out.printf("목적지에 도착하였습니다.\n");

            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    System.out.printf(visited[i][j] + " ");
                }
                System.out.println();
            }
            return true;
        }

        // 임시 저장
        int tempX = x;
        int tempY = y;

        // 상하좌우 이동
        for(int i = 0; i < 4; i++){
            tempX += dx[i];
            tempY += dy[i];
            
            if ( isValid(tempX, tempY) ){
                if ( solveMaze(tempX, tempY)){
                    return true;
                }
            }

            // 재귀 호출에서 돌아올 때 이전 위치로 되돌림
            tempX -= dx[i];
            tempY -= dy[i];
        }    

        return false;
    }

    public static void main(String[] args){
        if ( !solveMaze(nowX, nowY) ) {
            System.out.println("목적지로 이동할 수 없습니다.");
        }
        
    }
}
