package com.example.ereenyessam.squawker.provider;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Ereeny Essam on 7/16/2017.
 */
@ContentProvider(
        authority = SquawkProvider.AUTHORITY,
        database = SquawkDataBase.class)

public final  class SquawkProvider {

    public static final String AUTHORITY = "com.example.ereenyessam.squawker.provider.provider";

    @TableEndpoint(table = SquawkDataBase.SQUAWK_MESSAGES)
    public static class SquawkMessages{
        @ContentUri(
                path = "messages",
                type = "vnd.android.cursor.dir/messages",
                defaultSort = SquawkContract.COLUMN_DATE + " DESC ")
        public static final Uri CONTENT_URI =Uri.parse("content://" + AUTHORITY + "/messages");
    }
}
