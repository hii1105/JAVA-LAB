package ru.esstu.maven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileSystemUpdate {

    //перемещение файлов
    public void moveFiles(int fromDir,
                          int toDir)
            throws SQLException {

        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        "UPDATE files " +
                                "SET directory_id = ? " +
                                "WHERE directory_id = ?"
                );

        statement.setInt(1, toDir);
        statement.setInt(2, fromDir);

        statement.executeUpdate();

        connection.close();
    }

    public void moveDirectories(int fromDir,
                                int toDir)
            throws SQLException {

        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        "UPDATE directories " +
                                "SET parent_id = ? " +
                                "WHERE parent_id = ?"
                );

        statement.setInt(1, toDir);
        statement.setInt(2, fromDir);

        statement.executeUpdate();

        connection.close();
    }

    //удаление содержимого каталога
    public void deleteDirectoryContent(
            int directoryId)
            throws SQLException {

        Connection connection =
                DBConnection.getConnection();

        PreparedStatement deleteFiles =
                connection.prepareStatement(
                        "DELETE FROM files " +
                                "WHERE directory_id = ?"
                );

        deleteFiles.setInt(1, directoryId);

        deleteFiles.executeUpdate();

        PreparedStatement deleteDirs =
                connection.prepareStatement(
                        "DELETE FROM directories " +
                                "WHERE parent_id = ?"
                );

        deleteDirs.setInt(1, directoryId);

        deleteDirs.executeUpdate();

        connection.close();
    }
}