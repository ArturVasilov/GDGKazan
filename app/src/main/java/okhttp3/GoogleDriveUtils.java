package okhttp3;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public final class GoogleDriveUtils {

    private static final String GOOGLE_DRIVE_LINK_FORMAT = "https://drive.google.com/uc?export=download&id=%s";

    private GoogleDriveUtils() {
    }

    public static String modifyDriveDownloadUrl(@NonNull final String url) {
        try {
            if (url.startsWith("https://doc")) {
                String[] parts = url.split("/*/");
                String imageId = parts[parts.length - 1].split("\\?")[0];
                return String.format(GOOGLE_DRIVE_LINK_FORMAT, imageId);
            }
            return url;
        } catch (Exception e) {
            return url;
        }
    }

}
