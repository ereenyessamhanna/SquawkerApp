package com.example.ereenyessam.squawker.provider;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;
/**
 * Created by Ereeny Essam on 7/16/2017.
 */
@Database(version = SquawkDataBase.VERSION)
public class SquawkDataBase {


    public static final int VERSION = 1;

    @Table(SquawkContract.class)
    public static final String SQUAWK_MESSAGES = "squawk_messages";

}
