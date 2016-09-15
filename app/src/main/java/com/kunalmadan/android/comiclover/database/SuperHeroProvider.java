package com.kunalmadan.android.comiclover.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by KUNAL on 28-08-2016.
 */

@ContentProvider(authority = SuperHeroProvider.AUTHORITY, database = SuperHeroDatabase.class)
public class SuperHeroProvider {

    public static final String AUTHORITY =
            "com.kunalmadan.android.comiclover.database.SuperHeroProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    interface Path{
        String SUPERHERO = "superhero";
    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = SuperHeroDatabase.SUPERHERO) public static class SuperHero{
        @ContentUri(
                path = Path.SUPERHERO,
                type = "vnd.android.cursor.dir/superhero"
               )
        public static final Uri CONTENT_URI = buildUri(Path.SUPERHERO);

        @InexactContentUri(
                name = "SUPERHERO_ID",
                path = Path.SUPERHERO + "/#",
                type = "vnd.android.cursor.item/superhero",
                whereColumn = SuperHeroColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.SUPERHERO, String.valueOf(id));
        }
    }


}
