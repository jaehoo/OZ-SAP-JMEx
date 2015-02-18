package oz.service;

/**
 * Created by jaehoo on 2/8/15.
 */
public interface FileManager {

    /**
     * Persist content media
     * @param fileName file name
     * @param content text file
     */
    public abstract void persist(String fileName, String content);

    /**
     * true to enable persist, false to exclude
     * @param enable
     */
    public abstract void enabled(boolean enable);

}
