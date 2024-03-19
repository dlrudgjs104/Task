public class MazeRecursize {

    //미로
    static int maze[][] = new int[8][8];

    // 확인한 길
    static boolean[][] visited = new boolean[8][8];


    public static void mazeCreate(){
        while(true){
            // 랜덤 미로 생성
            for (int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    maze[i][j] = (int) (Math.random() * 2);
                }
            }

            // visited 배열 초기화
            for (int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    visited[i][j] = false;
                }
            }

            maze[0][0] = 0;
            maze[7][7] = 0;

            // 길이 있는 미로인지 검증
            if (isValidMaze(0, 0)){

                // visited 배열 초기화
                for (int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        visited[i][j] = false;
                    }
                }

                break;
            }
            
        }
        
    }

    // 우하좌상 이동을 위한 배열  1,0     0,1     -1,0    0,-1
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0}; 

    // 미로 범위 내에 있는지와 방문하지 않았는지 확인
    public static boolean isValid(int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == 0 && !visited[x][y];
    }

    // 목적지
    public static boolean destination(int x, int y){
        return (x == 7) && (y == 7);
    }

    public static boolean isValidMaze(int x, int y){
        // 현재 위치를 방문처리
        visited[x][y] = true;

        // 목적지를 도착했을 경우 종료
        if ( destination(x, y) ){
            System.out.printf("길 찾기 전 미로\n");

            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if ( visited[i][j] == true){
                        System.out.printf(maze[i][j] + " ");
                    }
                    else{
                        System.out.printf(maze[i][j] + " ");
                    }
                   
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
                if ( isValidMaze(tempX, tempY)){
                    return true;
                }
            }

            // 재귀 호출에서 돌아올 때 이전 위치로 되돌림
            tempX -= dx[i];
            tempY -= dy[i];
        }    

        return false;
    }

    public static boolean solveMaze(int x, int y){
        // 현재 위치를 방문처리
        visited[x][y] = true;

        // 목적지를 도착했을 경우 종료
        if ( destination(x, y) ){
            System.out.printf("\n길 찾은 후 미로\n");

            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if ( visited[i][j] == true){
                        maze[i][j] = 2;
                        System.out.printf(maze[i][j] + " ");
                    }
                    else{
                        System.out.printf(maze[i][j] + " ");
                    }
                   
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
        mazeCreate();
        solveMaze(0, 0);
    }
        
}
