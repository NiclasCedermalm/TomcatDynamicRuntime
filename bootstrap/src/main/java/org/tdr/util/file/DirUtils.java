package org.tdr.util.file;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.Objects;

/**
 * Directory Utils
 * @author nic
 */
public abstract class DirUtils {

    private DirUtils() {}

    /**
     * Walks file tree starting at the given path and deletes all files
     * but leaves the directory structure intact.
     *
     * @param path Base path to start from
     * @throws IOException
     */
    public static void clean(Path path) throws IOException {
        validate(path);
        Files.walkFileTree(path, new CleanDirVisitor());
    }

    /**
     * Walks file tree starting at the given path and deletes all files
     * but leaves the directory structure intact. If the given Path does not exist nothing
     * is done.
     *
     * @param path
     * @throws IOException
     */
    public static void cleanIfExists(Path path) throws IOException {
        if (Files.exists(path)) {
            validate(path);
            Files.walkFileTree(path, new CleanDirVisitor());
        }
    }


    /**
     * Completely removes given file tree starting at and including the given path.
     *
     * @param path
     * @throws IOException
     */
    public static void delete(Path path) throws IOException {
        validate(path);
        Files.walkFileTree(path, new DeleteDirVisitor());
    }


    /**
     * If the path exists, completely removes given file tree starting at and including the given path.
     *
     * @param path
     * @throws IOException
     */
    public static void deleteIfExists(Path path) throws IOException {
        if (Files.exists(path)) {
            validate(path);
            Files.walkFileTree(path, new DeleteDirVisitor());
        }
    }

    /**
     * Copies a directory tree
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public static void copy(Path from, Path to) throws IOException {
        validate(from);
        Files.walkFileTree(from, EnumSet.of(FileVisitOption.FOLLOW_LINKS),Integer.MAX_VALUE,new CopyDirVisitor(from, to));
    }

    /**
     * Moves one directory tree to another.  Not a true move operation in that the
     * directory tree is copied, then the original directory tree is deleted.
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public static void move(Path from, Path to) throws IOException {
        validate(from);
        Files.walkFileTree(from, new CopyDirVisitor(from, to));
        Files.walkFileTree(from, new DeleteDirVisitor());
    }

    private static void validate(Path... paths) {
        for (Path path : paths) {
            Objects.requireNonNull(path);
            if (!Files.isDirectory(path)) {
                throw new IllegalArgumentException(String.format("%s is not a directory", path.toString()));
            }
        }
    }
}
