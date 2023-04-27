package fengliu.notheme.util.item.armor.material;

import net.minecraft.item.ArmorMaterial;

public abstract class ExtendArmorMaterial implements ArmorMaterial, IExtendMaterial {
    private final String name;

    public ExtendArmorMaterial(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
