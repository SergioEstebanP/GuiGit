package services;

import org.eclipse.jgit.api.Git;

import java.io.File;

public class Global {
    public static String filePath;
    public static String repoDir = filePath + "\\.git";
    public static boolean filesAdded = false;
    public static boolean registered = false;
    public static boolean repoOpen = false;
    public static String username = "";
    public static String password = "";
    public static Git git;

}
