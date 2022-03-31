package me.travis.wurstplus.wurstplustwo.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.WurstplusGUI;
import me.travis.wurstplus.wurstplustwo.guiscreen.WurstplusHUD;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusFrame;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusCommandManager;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusConfigManager;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDrawnUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEnemyUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEzMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;

public class WurstplusConfigManager {
    private final String MAIN_FOLDER = "Hope+/";
    private final String CONFIGS_FOLDER = "Hope+/configs/";
    private String ACTIVE_CONFIG_FOLDER = "Hope+/configs/default/";
    private final String CLIENT_FILE = "client.json";
    private final String CONFIG_FILE = "config.txt";
    private final String DRAWN_FILE = "drawn.txt";
    private final String EZ_FILE = "ez.txt";
    private final String ENEMIES_FILE = "enemies.json";
    private final String FRIENDS_FILE = "friends.json";
    private final String HUD_FILE = "hud.json";
    private final String BINDS_FILE = "binds.txt";
    private final String CLIENT_DIR = "Hope+/client.json";
    private final String CONFIG_DIR = "Hope+/config.txt";
    private final String DRAWN_DIR = "Hope+/drawn.txt";
    private final String EZ_DIR = "Hope+/ez.txt";
    private final String ENEMIES_DIR = "Hope+/enemies.json";
    private final String FRIENDS_DIR = "Hope+/friends.json";
    private final String HUD_DIR = "Hope+/hud.json";
    private String CURRENT_CONFIG_DIR = "Hope+/Hope+/configs/" + this.ACTIVE_CONFIG_FOLDER;
    private String BINDS_DIR = this.CURRENT_CONFIG_DIR + "binds.txt";
    private final Path MAIN_FOLDER_PATH = Paths.get((String)"Hope+/", (String[])new String[0]);
    private final Path CONFIGS_FOLDER_PATH = Paths.get((String)"Hope+/configs/", (String[])new String[0]);
    private Path ACTIVE_CONFIG_FOLDER_PATH = Paths.get((String)this.ACTIVE_CONFIG_FOLDER, (String[])new String[0]);
    private final Path CLIENT_PATH = Paths.get((String)"Hope+/client.json", (String[])new String[0]);
    private final Path CONFIG_PATH = Paths.get((String)"Hope+/config.txt", (String[])new String[0]);
    private final Path DRAWN_PATH = Paths.get((String)"Hope+/drawn.txt", (String[])new String[0]);
    private final Path EZ_PATH = Paths.get((String)"Hope+/ez.txt", (String[])new String[0]);
    private final Path ENEMIES_PATH = Paths.get((String)"Hope+/enemies.json", (String[])new String[0]);
    private final Path FRIENDS_PATH = Paths.get((String)"Hope+/friends.json", (String[])new String[0]);
    private final Path HUD_PATH = Paths.get((String)"Hope+/hud.json", (String[])new String[0]);
    private Path BINDS_PATH = Paths.get((String)this.BINDS_DIR, (String[])new String[0]);
    private Path CURRENT_CONFIG_PATH = Paths.get((String)this.CURRENT_CONFIG_DIR, (String[])new String[0]);

    public boolean set_active_config_folder(String folder) {
        if (folder.equals((Object)this.ACTIVE_CONFIG_FOLDER)) {
            return false;
        }
        this.ACTIVE_CONFIG_FOLDER = "Hope+/configs/" + folder;
        this.ACTIVE_CONFIG_FOLDER_PATH = Paths.get((String)this.ACTIVE_CONFIG_FOLDER, (String[])new String[0]);
        this.CURRENT_CONFIG_DIR = "Hope+/Hope+/configs/" + this.ACTIVE_CONFIG_FOLDER;
        this.CURRENT_CONFIG_PATH = Paths.get((String)this.CURRENT_CONFIG_DIR, (String[])new String[0]);
        this.BINDS_DIR = this.CURRENT_CONFIG_DIR + "binds.txt";
        this.BINDS_PATH = Paths.get((String)this.BINDS_DIR, (String[])new String[0]);
        this.load_settings();
        return true;
    }

    private void save_ezmessage() throws IOException {
        FileWriter writer = new FileWriter("Hope+/ez.txt");
        try {
            writer.write(WurstplusEzMessageUtil.get_message());
        }
        catch (Exception ignored) {
            writer.write("kinda sus bro");
        }
        writer.close();
    }

    private void load_ezmessage() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String s : Files.readAllLines((Path)this.EZ_PATH)) {
            sb.append(s);
        }
        WurstplusEzMessageUtil.set_message((String)sb.toString());
    }

    private void save_drawn() throws IOException {
        FileWriter writer = new FileWriter("Hope+/drawn.txt");
        for (String s : WurstplusDrawnUtil.hidden_tags) {
            writer.write(s + System.lineSeparator());
        }
        writer.close();
    }

    private void load_drawn() throws IOException {
        WurstplusDrawnUtil.hidden_tags = Files.readAllLines((Path)this.DRAWN_PATH);
    }

    private void save_friends() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson((Object)WurstplusFriendUtil.friends);
        OutputStreamWriter file = new OutputStreamWriter((OutputStream)new FileOutputStream("Hope+/friends.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void load_friends() throws IOException {
        Gson gson = new Gson();
        BufferedReader reader = Files.newBufferedReader((Path)Paths.get((String)"Hope+/friends.json", (String[])new String[0]));
        WurstplusFriendUtil.friends = (ArrayList)gson.fromJson((Reader)reader, new /* Unavailable Anonymous Inner Class!! */.getType());
        reader.close();
    }

    private void save_enemies() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson((Object)WurstplusEnemyUtil.enemies);
        OutputStreamWriter file = new OutputStreamWriter((OutputStream)new FileOutputStream("Hope+/enemies.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void load_enemies() throws IOException {
        Gson gson = new Gson();
        BufferedReader reader = Files.newBufferedReader((Path)Paths.get((String)"Hope+/enemies.json", (String[])new String[0]));
        WurstplusEnemyUtil.enemies = (ArrayList)gson.fromJson((Reader)reader, new /* Unavailable Anonymous Inner Class!! */.getType());
        reader.close();
    }

    /*
     * Exception decompiling
     */
    private void save_hacks() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:251)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:215)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:25)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:96)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:839)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:191)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:136)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:369)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:770)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:702)
        // org.benf.cfr.reader.Main.doClass(Main.java:46)
        // org.benf.cfr.reader.PluginRunner.getDecompilationFor(PluginRunner.java:117)
        // me.grax.jbytemod.decompiler.CFRDecompiler.decompile(CFRDecompiler.java:113)
        // me.grax.jbytemod.decompiler.Decompiler.decompile(Decompiler.java:54)
        // me.grax.jbytemod.decompiler.Decompiler.run(Decompiler.java:42)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    private void load_hacks() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:251)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:215)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:25)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:96)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:839)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:191)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:136)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:369)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:770)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:702)
        // org.benf.cfr.reader.Main.doClass(Main.java:46)
        // org.benf.cfr.reader.PluginRunner.getDecompilationFor(PluginRunner.java:117)
        // me.grax.jbytemod.decompiler.CFRDecompiler.decompile(CFRDecompiler.java:113)
        // me.grax.jbytemod.decompiler.Decompiler.decompile(Decompiler.java:54)
        // me.grax.jbytemod.decompiler.Decompiler.run(Decompiler.java:42)
        throw new IllegalStateException("Decompilation failed");
    }

    private void save_client() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonObject main_json = new JsonObject();
        JsonObject config = new JsonObject();
        JsonObject gui = new JsonObject();
        config.add("name", (JsonElement)new JsonPrimitive(Wurstplus.get_name()));
        config.add("version", (JsonElement)new JsonPrimitive(Wurstplus.get_version()));
        config.add("user", (JsonElement)new JsonPrimitive(Wurstplus.get_actual_user()));
        config.add("prefix", (JsonElement)new JsonPrimitive(WurstplusCommandManager.get_prefix()));
        for (me.travis.wurstplus.wurstplustwo.guiscreen.render.components.WurstplusFrame frames_gui : Wurstplus.click_gui.get_array_frames()) {
            JsonObject frame_info = new JsonObject();
            frame_info.add("name", (JsonElement)new JsonPrimitive(frames_gui.get_name()));
            frame_info.add("tag", (JsonElement)new JsonPrimitive(frames_gui.get_tag()));
            frame_info.add("x", (JsonElement)new JsonPrimitive((Number)frames_gui.get_x()));
            frame_info.add("y", (JsonElement)new JsonPrimitive((Number)frames_gui.get_y()));
            gui.add(frames_gui.get_tag(), (JsonElement)frame_info);
        }
        main_json.add("configuration", (JsonElement)config);
        main_json.add("gui", (JsonElement)gui);
        JsonElement json_pretty = parser.parse(main_json.toString());
        String json = gson.toJson(json_pretty);
        OutputStreamWriter file = new OutputStreamWriter((OutputStream)new FileOutputStream("Hope+/client.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void load_client() throws IOException {
        InputStream stream = Files.newInputStream((Path)this.CLIENT_PATH, (OpenOption[])new OpenOption[0]);
        JsonObject json_client = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        JsonObject json_config = json_client.get("configuration").getAsJsonObject();
        JsonObject json_gui = json_client.get("gui").getAsJsonObject();
        WurstplusCommandManager.set_prefix((String)json_config.get("prefix").getAsString());
        for (me.travis.wurstplus.wurstplustwo.guiscreen.render.components.WurstplusFrame frames : Wurstplus.click_gui.get_array_frames()) {
            JsonObject frame_info = json_gui.get(frames.get_tag()).getAsJsonObject();
            me.travis.wurstplus.wurstplustwo.guiscreen.render.components.WurstplusFrame frame_requested = Wurstplus.click_gui.get_frame_with_tag(frame_info.get("tag").getAsString());
            frame_requested.set_x(frame_info.get("x").getAsInt());
            frame_requested.set_y(frame_info.get("y").getAsInt());
        }
        stream.close();
    }

    private void save_hud() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonObject main_json = new JsonObject();
        JsonObject main_frame = new JsonObject();
        JsonObject main_hud = new JsonObject();
        main_frame.add("name", (JsonElement)new JsonPrimitive(Wurstplus.click_hud.get_frame_hud().get_name()));
        main_frame.add("tag", (JsonElement)new JsonPrimitive(Wurstplus.click_hud.get_frame_hud().get_tag()));
        main_frame.add("x", (JsonElement)new JsonPrimitive((Number)Wurstplus.click_hud.get_frame_hud().get_x()));
        main_frame.add("y", (JsonElement)new JsonPrimitive((Number)Wurstplus.click_hud.get_frame_hud().get_y()));
        for (WurstplusPinnable pinnables_hud : Wurstplus.get_hud_manager().get_array_huds()) {
            JsonObject frame_info = new JsonObject();
            frame_info.add("title", (JsonElement)new JsonPrimitive(pinnables_hud.get_title()));
            frame_info.add("tag", (JsonElement)new JsonPrimitive(pinnables_hud.get_tag()));
            frame_info.add("state", (JsonElement)new JsonPrimitive(Boolean.valueOf((boolean)pinnables_hud.is_active())));
            frame_info.add("dock", (JsonElement)new JsonPrimitive(Boolean.valueOf((boolean)pinnables_hud.get_dock())));
            frame_info.add("x", (JsonElement)new JsonPrimitive((Number)pinnables_hud.get_x()));
            frame_info.add("y", (JsonElement)new JsonPrimitive((Number)pinnables_hud.get_y()));
            main_hud.add(pinnables_hud.get_tag(), (JsonElement)frame_info);
        }
        main_json.add("frame", (JsonElement)main_frame);
        main_json.add("hud", (JsonElement)main_hud);
        JsonElement pretty_json = parser.parse(main_json.toString());
        String json = gson.toJson(pretty_json);
        this.delete_file("Hope+/hud.json");
        this.verify_file(this.HUD_PATH);
        OutputStreamWriter file = new OutputStreamWriter((OutputStream)new FileOutputStream("Hope+/hud.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void load_hud() throws IOException {
        InputStream input_stream = Files.newInputStream((Path)this.HUD_PATH, (OpenOption[])new OpenOption[0]);
        JsonObject main_hud = new JsonParser().parse((Reader)new InputStreamReader(input_stream)).getAsJsonObject();
        JsonObject main_frame = main_hud.get("frame").getAsJsonObject();
        JsonObject main_huds = main_hud.get("hud").getAsJsonObject();
        Wurstplus.click_hud.get_frame_hud().set_x(main_frame.get("x").getAsInt());
        Wurstplus.click_hud.get_frame_hud().set_y(main_frame.get("y").getAsInt());
        for (WurstplusPinnable pinnables : Wurstplus.get_hud_manager().get_array_huds()) {
            JsonObject hud_info = main_huds.get(pinnables.get_tag()).getAsJsonObject();
            WurstplusPinnable pinnable_requested = Wurstplus.get_hud_manager().get_pinnable_with_tag(hud_info.get("tag").getAsString());
            pinnable_requested.set_active(hud_info.get("state").getAsBoolean());
            pinnable_requested.set_dock(hud_info.get("dock").getAsBoolean());
            pinnable_requested.set_x(hud_info.get("x").getAsInt());
            pinnable_requested.set_y(hud_info.get("y").getAsInt());
        }
        input_stream.close();
    }

    private void save_binds() throws IOException {
        String file_name = this.ACTIVE_CONFIG_FOLDER + "BINDS.txt";
        Path file_path = Paths.get((String)file_name, (String[])new String[0]);
        this.delete_file(file_name);
        this.verify_file(file_path);
        File file = new File(file_name);
        BufferedWriter br = new BufferedWriter((Writer)new FileWriter(file));
        for (WurstplusHack modules : Wurstplus.get_hack_manager().get_array_hacks()) {
            br.write(modules.get_tag() + ":" + modules.get_bind(1) + ":" + modules.is_active() + "\r\n");
        }
        br.close();
    }

    private void load_binds() throws IOException {
        String line;
        String file_name = this.ACTIVE_CONFIG_FOLDER + "BINDS.txt";
        File file = new File(file_name);
        FileInputStream fi_stream = new FileInputStream(file.getAbsolutePath());
        DataInputStream di_stream = new DataInputStream((InputStream)fi_stream);
        BufferedReader br = new BufferedReader((Reader)new InputStreamReader((InputStream)di_stream));
        while ((line = br.readLine()) != null) {
            try {
                String colune = line.trim();
                String tag = colune.split(":")[0];
                String bind = colune.split(":")[1];
                String active = colune.split(":")[2];
                WurstplusHack module = Wurstplus.get_hack_manager().get_module_with_tag(tag);
                module.set_bind(Integer.parseInt((String)bind));
                module.set_active(Boolean.parseBoolean((String)active));
            }
            catch (Exception colune) {}
        }
        br.close();
    }

    public void save_settings() {
        try {
            this.verify_dir(this.MAIN_FOLDER_PATH);
            this.verify_dir(this.CONFIGS_FOLDER_PATH);
            this.verify_dir(this.ACTIVE_CONFIG_FOLDER_PATH);
            this.save_hacks();
            this.save_binds();
            this.save_friends();
            this.save_enemies();
            this.save_client();
            this.save_drawn();
            this.save_ezmessage();
            this.save_hud();
        }
        catch (IOException e) {
            Wurstplus.send_minecraft_log((String)"Something has gone wrong while saving settings");
            Wurstplus.send_minecraft_log((String)e.toString());
        }
    }

    public void load_settings() {
        try {
            this.load_binds();
            this.load_client();
            this.load_drawn();
            this.load_enemies();
            this.load_ezmessage();
            this.load_friends();
            this.load_hacks();
            this.load_hud();
        }
        catch (IOException e) {
            Wurstplus.send_minecraft_log((String)"Something has gone wrong while loading settings");
            Wurstplus.send_minecraft_log((String)e.toString());
        }
    }

    public boolean delete_file(String path) throws IOException {
        File f = new File(path);
        return f.delete();
    }

    public void verify_file(Path path) throws IOException {
        if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
            Files.createFile((Path)path, (FileAttribute[])new FileAttribute[0]);
        }
    }

    public void verify_dir(Path path) throws IOException {
        if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
            Files.createDirectory((Path)path, (FileAttribute[])new FileAttribute[0]);
        }
    }
}
