package com.kunalmadan.android.comiclover.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by KUNAL on 27-08-2016.
 */
public interface SuperHeroColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String NAME = "name";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String ID = "id";

    @DataType(DataType.Type.TEXT)  public static final String DESCRIPTION =
            "description";

    @DataType(DataType.Type.TEXT)  public static final String THUMBNAIL =
            "thumbnail_path";

    @DataType(DataType.Type.TEXT)  public static final String COMIC_NAME =
            "comic_name";

    @DataType(DataType.Type.TEXT)  public static final String RESOURCE_URI =
            "resource_uri";

}
