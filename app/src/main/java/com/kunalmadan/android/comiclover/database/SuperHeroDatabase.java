package com.kunalmadan.android.comiclover.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by KUNAL on 28-08-2016.
 */

@Database(version = SuperHeroDatabase.VERSION)
public final class SuperHeroDatabase {

    private SuperHeroDatabase(){}

    public static final int VERSION = 2;

    @Table(SuperHeroColumns.class) public static final String SUPERHERO = "superhero";
}
