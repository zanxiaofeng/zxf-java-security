# Ways to create file
- Using java.io.File class.
- Using java.io.FileOutputStream class.
- Using classes in java.nio.file package.

# The file mode of the newly created file or Directory
- For File, new mode = 666 + umask
- For Dir,  new mode = 777 + umask
- Specified the mode by using the java.nio.file.attribute.PosixFilePermission