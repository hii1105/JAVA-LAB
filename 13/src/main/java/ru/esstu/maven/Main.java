//13 глава
// 1 вар
// В каждом из заданий необходимо выполнить следующие действия:
//• организацию соединения с базой данных вынести в отдельный класс, метод которого возвращает соединение;
//• создать БД. Привести таблицы к одной из нормальных форм;
//• создать класс для выполнения запросов на извлечение информации из БД с использованием компилированных запросов;
//• создать класс на модификацию информации.
//1. Файловая система. В БД хранится информация о дереве каталогов файловой системы — каталоги, подкаталоги, файлы.
//Для каталогов необходимо хранить:
//• родительский каталог;
//• название.
//Для файлов необходимо хранить:
//• родительский каталог;
//• название;
//• место, занимаемое на диске.
//• Определить полный путь заданного файла (каталога).
//• Подсчитать количество файлов в заданном каталоге, включая вложенные файлы и каталоги.
//• Подсчитать место, занимаемое на диске содержимым заданного каталога.
//• Найти в базе файлы по заданной маске с выдачей полного пути.
//• Переместить файлы и подкаталоги из одного каталога в другой.
//• Удалить файлы и каталоги заданного каталога.

package ru.esstu.maven;

import ru.esstu.maven.models.Directory;
import ru.esstu.maven.models.FileItem;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args)
            throws SQLException {

        FileSystemQueries queries =
                new FileSystemQueries();

        FileSystemUpdate update =
                new FileSystemUpdate();

        //получение каталога
        Directory directory =
                queries.getDirectory(1);

        System.out.println(
                "ID каталога: " +
                        directory.getId()
        );

        System.out.println(
                "Родительский ID: " +
                        directory.getParentId()
        );

        System.out.println(
                "Название каталога: " +
                        directory.getName()
        );

        //полный путь
        String path =
                queries.getDirectoryPath(2);

        System.out.println(
                "Полный путь: " + path
        );

        //подсчёт файлов
        int count =
                queries.countFiles(2);

        System.out.println(
                "Количество файлов: " + count
        );

        //размер каталога
        long size =
                queries.getDirectorySize(2);

        System.out.println(
                "Размер каталога: " + size
        );

        //поиск файлов
        List<FileItem> files =
                queries.findFiles("%.txt");

        System.out.println(
                "Найденные файлы:"
        );

        for (FileItem file : files) {

            System.out.println(
                    "Имя файла: " +
                            file.getName()
            );

            System.out.println(
                    "Размер: " +
                            file.getSize()
            );
        }

        //перемещение файлов
        update.moveFiles(2, 3);

        System.out.println(
                "Файлы перемещены"
        );

        //перемещение каталогов
        update.moveDirectories(1, 3);

        System.out.println(
                "Каталоги перемещены"
        );

        //удаление содержимого
        update.deleteDirectoryContent(3);

        System.out.println(
                "Содержимое удалено"
        );
    }
}