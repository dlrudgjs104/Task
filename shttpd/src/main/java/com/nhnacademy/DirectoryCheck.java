package com.nhnacademy;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryCheck {
    public static final String rootPath = "/Shttpd"; // document-root 경로

    public boolean directoryCheckRun(String requestPath){
        // 요청된 경로가 상대적인지 확인
        boolean isRelative = isRelativePath(requestPath);
        System.out.println("Is relative path: " + isRelative);

        if (isRelative) {
            // 상대적인 경로를 document-root를 기준으로 절대적인 경로로 변환
            String absolutePath = getAbsolutePath(rootPath, requestPath);
            System.out.println("Absolute path: " + absolutePath);

            // 변환된 절대경로에 부모 디렉토리를 나타내는 문자열이 포함되어 있는지 확인
            boolean containsParent = containsParentDirectory(absolutePath);
            System.out.println("Contains parent directory: " + containsParent);

            // 상위 디렉토리를 요청했는지 확인
            if (!containsParent) {
                System.out.println("상위 디렉터리 접근 시도");
                return true;
            } else {
                System.out.println("정상적인 디렉터리 접근");
                return false;
            }
        } else {
            // 절대경로인 경우 요청된 파일을 서비스
            return true;
        }
    }

    public static boolean isRelativePath(String path) {
        // 상대경로인지 절대경로인지 확인
        Path filePath = Paths.get(path);
        return !filePath.isAbsolute();
    }

    public static String getAbsolutePath(String rootPath, String relativePath) {
        // 상대경로를 절대경로로 변환
        File rootDir = new File(rootPath);
        File absoluteFile = new File(rootDir, relativePath);
        return absoluteFile.getAbsolutePath();
    }

    public static boolean containsParentDirectory(String path) {
        // 경로에 부모 디렉토리를 나타내는 문자열이 포함되어 있는지 확인
        return path.contains("..");
    }

}
