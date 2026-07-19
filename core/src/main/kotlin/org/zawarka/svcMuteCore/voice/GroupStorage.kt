package org.zawarka.svcMuteCore.voice

import de.maxhenkel.voicechat.api.Group
import java.util.UUID

class GroupStorage {

    private val groups: MutableSet<Group> = mutableSetOf()
    private val groupsId: MutableMap<UUID, Group> = mutableMapOf()
    private val groupsName: MutableMap<String, Group> = mutableMapOf()

    fun addGroup(group: Group) {
        groups.add(group)
        groupsId[group.id] = group
        groupsName[group.name] = group
    }

    fun removeGroup(group: Group) {
        groups.remove(group)
        groupsId.remove(group.id)
        groupsName.remove(group.name)
    }

    fun groups(): Set<Group> = groups
    fun groupNames() = groupsName.keys

    fun get(uuid: UUID): Group? = groupsId[uuid]
    fun get(name: String): Group? = groupsName[name]

}