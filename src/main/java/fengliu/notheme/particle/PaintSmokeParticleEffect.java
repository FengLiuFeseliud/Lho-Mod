package fengliu.notheme.particle;

import net.minecraft.particle.AbstractDustParticleEffect;
import net.minecraft.particle.ParticleType;
import org.joml.Vector3f;

public class PaintSmokeParticleEffect extends AbstractDustParticleEffect {
    public PaintSmokeParticleEffect(Vector3f color, float scale) {
        super(color, scale);
    }

    @Override
    public ParticleType<?> getType() {
        return null;
    }
}
