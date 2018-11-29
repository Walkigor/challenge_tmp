package cisco.java.challenge.util;

public interface FileWorker<T> {
    T mapContent(String fullFilePath);
}