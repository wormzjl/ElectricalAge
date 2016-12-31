package mods.eln.client

import cpw.mods.fml.client.registry.ClientRegistry
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.FMLCommonHandler
import mods.eln.CommonProxy
import mods.eln.Eln
import mods.eln.entity.ReplicatorEntity
import mods.eln.entity.ReplicatorRender
import mods.eln.node.six.SixNodeEntity
import mods.eln.node.six.SixNodeRender
import mods.eln.node.transparent.TransparentNodeEntity
import mods.eln.node.transparent.TransparentNodeRender
import mods.eln.sixnode.tutorialsign.TutorialSignOverlay
import mods.eln.sound.SoundClientEventListener
import net.minecraft.client.model.ModelSilverfish
import net.minecraftforge.client.MinecraftForgeClient
import net.minecraftforge.common.MinecraftForge

class ClientProxy : CommonProxy() {

    override fun registerRenderers() {
        ClientPacketHandler()
        ClientRegistry.bindTileEntitySpecialRenderer(SixNodeEntity::class.java, SixNodeRender())
        ClientRegistry.bindTileEntitySpecialRenderer(TransparentNodeEntity::class.java, TransparentNodeRender())

        MinecraftForgeClient.registerItemRenderer(Eln.transparentNodeItem, Eln.transparentNodeItem)
        MinecraftForgeClient.registerItemRenderer(Eln.sixNodeItem, Eln.sixNodeItem)
        MinecraftForgeClient.registerItemRenderer(Eln.sharedItem, Eln.sharedItem)
        MinecraftForgeClient.registerItemRenderer(Eln.sharedItemStackOne, Eln.sharedItemStackOne)

        RenderingRegistry.registerEntityRenderingHandler(ReplicatorEntity::class.java, ReplicatorRender(ModelSilverfish(), 0.3.toFloat()))

        Eln.clientKeyHandler = ClientKeyHandler()
        FMLCommonHandler.instance().bus().register(Eln.clientKeyHandler)
        MinecraftForge.EVENT_BUS.register(TutorialSignOverlay())

        if (Eln.versionCheckEnabled)
            FMLCommonHandler.instance().bus().register(VersionCheckerHandler.getInstance())

        if (Eln.analyticsEnabled)
            FMLCommonHandler.instance().bus().register(AnalyticsHandler.getInstance())

        FrameTime()
        ConnectionListener()
    }

    companion object {
        val uuidManager = UuidManager()
        val soundClientEventListener = SoundClientEventListener(uuidManager)
    }
}
