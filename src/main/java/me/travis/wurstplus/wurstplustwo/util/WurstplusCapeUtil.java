package me.travis.wurstplus.wurstplustwo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class WurstplusCapeUtil {
    static final ArrayList<String> final_uuid_list = WurstplusCapeUtil.get_uuids();

    public static ArrayList<String> get_uuids() {
        try {
            String s;
            URL url = new URL("https://pastebin.com/raw/x1nBEiiA");
            BufferedReader reader = new BufferedReader((Reader)new InputStreamReader(url.openStream()));
            ArrayList uuid_list = new ArrayList();
            while ((s = reader.readLine()) != null) {
                uuid_list.add((Object)s);
            }
            return uuid_list;
        }
        catch (Exception ignored) {
            return null;
        }
    }

    public static boolean is_uuid_valid(UUID uuid) {
        for (String u : (ArrayList)Objects.requireNonNull(final_uuid_list)) {
            if (!u.equals((Object)uuid.toString())) continue;
            return true;
        }
        return false;
    }
}
