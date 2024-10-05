package cn.ksmcbrigade.stf;

import net.fabricmc.api.ClientModInitializer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class StartupToneFabric implements ClientModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static File path = new File("config/st-sound.wav");

    public static boolean sounded = false;
    public static boolean init = false;

    @Override
    public void onInitializeClient() {
        if(!init){
            try {
                init();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void init() throws IOException {
        File config = new File("config");
        if(!config.exists()) config.mkdir();
        if(!path.exists()){
            FileUtils.writeByteArrayToFile(path, IOUtils.toByteArray(Objects.requireNonNull(StartupToneFabric.class.getResourceAsStream("/assets/stf/sound.wav"))));
        }
        LOGGER.info("Startup Tone mod loaded.");
        init = true;
    }
}
