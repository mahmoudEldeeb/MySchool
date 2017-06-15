package com.programs.lala.myschool.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by melde on 6/14/2017.
 */

public class MessageContract {

    public static final String CONTENT_AUTHORITY = "com.programs.lala.myschool";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MESSAGE= "MESSAGE";

    public static final class MessageEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MESSAGE)
                .build();

        public static final String FIRST_TABLE_NAME = "messages";

        public static final String COLUMN_id = "id";

        public static final String COLUMN_sender_id = "sender_id";
        public static final String COLUMN_receive_id = "receive_id";
        public static final String COLUMN_name = "name";
        public static final String COLUMN_message = "message";
        public static final String COLUMN_type= "type";
        public static final String COLUMN_sender_image = "sender_image";

    }
}
