package co.bugg.advancedxp.themes;

/**
 * These is the basic XP orb settings.
 * Any changes should be made in the
 * constructor of extensions on this
 * class.
 */
public class Theme {

    public Theme() {}

    public String name = "Default";
    public boolean enabled = true;

    public float translateX = 0F;
    public float translateY = 0.1F;
    public float translateZ = 0F;

    public boolean globalColor = false;

    public float scale = 0.3F;

    public boolean redStatic = false;
    public int redStaticValue = 255;
    public boolean redSquareWave = false;
    public float redWaveOffset = 0F;
    public float redSpeed = 0.5F;
    public float redMultiplier = 1F;

    public boolean greenStatic = true;
    public int greenStaticValue = 255;
    public boolean greenSquareWave = false;
    public float greenWaveOffset = 2F;
    public float greenSpeed = 0.5F;
    public float greenMultiplier = 1F;

    public boolean blueStatic = false;
    public int blueStaticValue = 255;
    public boolean blueSquareWave = false;
    public float blueWaveOffset = 4F;
    public float blueSpeed = 0.5F;
    public float blueMultiplier = 0.2F;
}
