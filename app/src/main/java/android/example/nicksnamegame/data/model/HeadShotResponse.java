package android.example.nicksnamegame.data.model;

import androidx.annotation.Nullable;

class HeadShotResponse {

    private String url;
    private String type;
    private int height;
    private int width;

    HeadShotResponse(@Nullable String headShotUrl,
                            @Nullable String type,
                            int height,
                            int width) {
        this.url = headShotUrl;
        this.type = type;
        this.height = height;
        this.width = width;
    }

    String getHeadShotUrl() {
        if (this.url != null) {
            // already properly formatted
            if (this.url.startsWith("http")) {
                return this.url;
            } else {
                // missing "http:" or "https:"
                return "https:" + this.url;
            }
        } else return null;
    }

    String getType() {
        return this.type;
    }

    int getHeight() {
        return this.height;
    }

    int getWidth() {
        return this.width;
    }

}
