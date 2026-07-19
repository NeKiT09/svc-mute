package org.zawarka.svcmuteneoforge.data

import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.server.ServerStoppedEvent
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent
import net.neoforged.neoforge.server.permission.nodes.PermissionNode
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes
import org.zawarka.svcMuteCore.MutePermission
import org.zawarka.svcMuteCore.data.Permission
import org.zawarka.svcmuteneoforge.SvcMuteNeoforge.core

object NeoForgePermissionRegistry {

    private val nodes = HashMap<String, PermissionNode<Boolean>>()


    fun init(){
        NeoForge.EVENT_BUS.addListener<PermissionGatherEvent.Nodes> { e -> run {
            nodes.clear()

            MutePermission.entries.forEach {
                val node = create(it.permission)
                nodes[it.permission.node] = node
                e.addNodes(node)
            }
        }}
    }
    init {
        //MutePermission.entries.forEach {
        //    node(it.permission)
        //}


    }

    fun node(permission: Permission): PermissionNode<Boolean> =
        nodes.getOrPut(permission.node) { create(permission) }

    private fun create(permission: Permission): PermissionNode<Boolean> {
        return PermissionNode(
            "svcmute",
            permission.node.removePrefix("svcmute."),
            PermissionTypes.BOOLEAN,
            { _, _, _ ->
                permission.lvl <= 0
            }
        )
    }

//    @SubscribeEvent
//    fun gather(event: PermissionGatherEvent.Nodes) {
//        nodes.clear()
//
//        MutePermission.entries.forEach {
//            val node = create(it.permission)
//            nodes[it.permission.node] = node
//            event.addNodes(node)
//        }
//    }
}