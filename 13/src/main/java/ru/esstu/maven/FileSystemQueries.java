package ru.esstu.maven;

import ru.esstu.maven.models.Directory;
import ru.esstu.maven.models.FileItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileSystemQueries {

    public Directory getDirectory(int id)
            throws SQLException {

        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        "SELECT * FROM directories " +
                                "WHERE id = ?"
                );

        statement.setInt(1, id);

        ResultSet result =
                statement.executeQuery();

        Directory directory = null;

        if (result.next()) {

            Integer parentId =
                    result.getObject(
                            "parent_id",
                            Integer.class
                    );

            directory = new Directory(
                    result.getInt("id"),
                    parentId,
                    result.getString("name")
            );
        }

        connection.close();

        return directory;
    }

    //полный путь каталога
    public String getDirectoryPath(int directoryId)
            throws SQLException {

        Connection connection =
                DBConnection.getConnection();

        StringBuilder path =
                new StringBuilder();

        while (directoryId != 0) {

            PreparedStatement statement =
                    connection.prepareStatement(
                            "SELECT parent_id, name " +
                                    "FROM directories " +
                                    "WHERE id = ?"
                    );

            statement.setInt(1, directoryId);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                path.insert(
                        0,
                        "/" + result.getString("name")
                );

                directoryId =
                        result.getInt("parent_id");

                if (result.wasNull()) {
                    directoryId = 0;
                }

            } else {
                break;
            }
        }

        connection.close();

        return path.toString();
    }

    //подсчёт файлов
    public int countFiles(int directoryId)
            throws SQLException {

        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        "SELECT COUNT(*) " +
                                "FROM files " +
                                "WHERE directory_id = ?"
                );

        statement.setInt(1, directoryId);

        ResultSet result =
                statement.executeQuery();

        int count = 0;

        if (result.next()) {
            count = result.getInt(1);
        }

        connection.close();

        return count;
    }

    public long getDirectorySize(int directoryId)
            throws SQLException {

        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        "SELECT SUM(size) " +
                                "FROM files " +
                                "WHERE directory_id = ?"
                );

        statement.setInt(1, directoryId);

        ResultSet result =
                statement.executeQuery();

        long size = 0;

        if (result.next()) {
            size = result.getLong(1);
        }

        connection.close();

        return size;
    }

    //поиск файлов
    public List<FileItem> findFiles(String mask)
            throws SQLException {

        Connection connection =
                DBConnection.getConnection();

        PreparedStatement statement =
                connection.prepareStatement(
                        "SELECT * FROM files " +
                                "WHERE name LIKE ?"
                );

        statement.setString(1, mask);

        ResultSet result =
                statement.executeQuery();

        List<FileItem> files =
                new ArrayList<>();

        while (result.next()) {

            FileItem file =
                    new FileItem(
                            result.getInt("id"),
                            result.getInt("directory_id"),
                            result.getString("name"),
                            result.getLong("size")
                    );

            files.add(file);
        }

        connection.close();

        return files;
    }
}