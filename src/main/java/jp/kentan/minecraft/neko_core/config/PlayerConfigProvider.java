package jp.kentan.minecraft.neko_core.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class PlayerConfigProvider {
    private final static Charset UTF_8 = StandardCharsets.UTF_8;

    private static String sFolderPath;

    public static void setup(File dataFolder){
        sFolderPath = dataFolder + File.separator + "players" + File.separator;
    }

    public static Object get(UUID uuid, String path, Object def) {
        try (Reader reader = new InputStreamReader(new FileInputStream(sFolderPath + uuid + ".yml"), UTF_8)) {

            FileConfiguration config = new YamlConfiguration();

            config.load(reader);

            reader.close();

            return config.get(path, def);
        } catch (Exception e) {
            return def;
        }
    }

    public static Object get(Player player, String path, Object def) {
        final UUID uuid = player.getUniqueId();
        return get(uuid, path, def);
    }

    public static int getOwnerAreaTotalNumber(UUID uuid, String nameWorld) {
        try (Reader reader = new InputStreamReader(new FileInputStream(sFolderPath + uuid + ".yml"), UTF_8)) {

            FileConfiguration config = new YamlConfiguration();

            config.load(reader);

            reader.close();

            return config.getStringList("OwnerArea." + nameWorld).size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean save(UUID uuid, Map<String, Object> dataList) {
        final File file = new File(sFolderPath + uuid + ".yml");

        try {
            FileConfiguration config = new YamlConfiguration();
            config.load(file);

            dataList.forEach(config::set);

            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    static boolean save(UUID uuid, String path, Object data) {
        final File file = new File(sFolderPath + uuid + ".yml");

        try {
            if(!file.exists()){
                file.createNewFile();
            }

            FileConfiguration config = new YamlConfiguration();
            config.load(file);

            config.set(path, data);

            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    static boolean addToArray(UUID uuid, String path, String data) {
        final List<String> list = new ArrayList<>();

        try (Reader reader = new InputStreamReader(new FileInputStream(sFolderPath + uuid + ".yml"), UTF_8)) {

            FileConfiguration config = new YamlConfiguration();

            config.load(reader);

            reader.close();

            list.addAll(config.getStringList(path));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        list.add(data);

        return save(uuid, path, list);
    }
}
