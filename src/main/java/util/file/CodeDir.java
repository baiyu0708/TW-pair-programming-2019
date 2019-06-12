package util.file;

import java.io.File;

/**
 * 获取当前代码文件所在路径
 * 用途：在单元测试时，索引测试代码所在包内的资源文件
 */
public class CodeDir {

    public static final String MAVEN_MAIN = "main/java";
    public static final String MAVEN_TEST = "test/java";

    public final File path;

    /**
     * @param mavenFolder 当前代码文件位于maven项目的哪个文件夹，一般为下列之一：{@link #MAVEN_MAIN}、{@link #MAVEN_TEST}
     */
    public CodeDir(String mavenFolder) {
        // 调用该函数的类，形如“util.path.CodePathExample”
        String invokerName = Thread.currentThread().getStackTrace()[2].getClassName();
        path = getDirectory(invokerName, mavenFolder);
    }

    /**
     * 返回代码所在包内的文件
     */
    public File child(String fileName) {
        return new File(path.getAbsolutePath() + "/" + fileName);
    }

    public File[] children(String... fileNames) {
        File[] results = new File[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            results[i] = child(fileNames[i]);
        }
        return results;
    }

    /**
     * 例：
     * 输入：util.path.CodePathExample
     * 输出：包util.path所在目录
     */
    private static File getDirectory(String invokerName, String mavenDirectory) {
        String root = new File("src/" + mavenDirectory).getAbsolutePath();
        StringBuilder path = new StringBuilder(root);
        String[] elements = invokerName.split("[.]");
        for (int i = 0; i < elements.length - 1; i++) {
            path.append("/");
            path.append(elements[i]);
        }
        return new File(path.toString());
    }
}
