package br.com.anthony.bot.antinho.utils;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

public class Utils {
    public static Path toPath(String relativePath) {
       try {
            URL fileUrl = Utils.class.getClassLoader().getResource(relativePath);
            return Paths.get(fileUrl.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Error converting to path: " + e.getMessage(), e);
        }
    }

    public static PathMatcher glob(String glob) {
        return FileSystems.getDefault().getPathMatcher("glob:" + glob);
    }
}
