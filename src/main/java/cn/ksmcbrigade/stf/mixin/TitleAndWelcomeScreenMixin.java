package cn.ksmcbrigade.stf.mixin;

import cn.ksmcbrigade.stf.StartupToneFabric;
import net.minecraft.client.gui.screen.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.sound.sampled.*;
import java.io.IOException;

@Mixin({TitleScreen.class, AccessibilityOnboardingScreen.class})
public class TitleAndWelcomeScreenMixin {
    @Inject(method = "init",at = @At("HEAD"))
    public void init(CallbackInfo ci) throws IOException {
        if(!StartupToneFabric.sounded){
            if(!StartupToneFabric.init) StartupToneFabric.init();

            try {
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(StartupToneFabric.path);
                Clip clip = AudioSystem.getClip();

                clip.open(inputStream);
                clip.start();

                StartupToneFabric.sounded = true;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                StartupToneFabric.LOGGER.info("Error in play the sound: {}",StartupToneFabric.path,e);
            }
        }
    }
}
