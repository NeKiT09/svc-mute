package org.zawarka.svcMute.mute

import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDateTime
import java.util.UUID

class MuteStorage(
    private val plugin: JavaPlugin
) {
    private val dbFile = File(plugin.dataFolder, "mutes.db")
    private val jdbcUrl = "jdbc:sqlite:${dbFile.absolutePath}"

    fun init() {
        plugin.dataFolder.mkdirs()
        dbFile.parentFile?.mkdirs()
        createTables()
    }

    fun saveMute(info: MuteInfo) {
        getConnection().use { connection ->
            connection.prepareStatement(
                """
                INSERT INTO mutes (
                    uuid,
                    seconds,
                    reason,
                    start_time,
                    permanent
                ) VALUES (?, ?, ?, ?, ?)
                ON CONFLICT(uuid) DO UPDATE SET
                    seconds = excluded.seconds,
                    reason = excluded.reason,
                    start_time = excluded.start_time,
                    permanent = excluded.permanent
                """.trimIndent()
            ).use { statement ->
                bindMute(statement, info)
                statement.executeUpdate()
            }
        }
    }

    fun removeMute(uuid: UUID) {
        getConnection().use { connection ->
            connection.prepareStatement(
                "DELETE FROM mutes WHERE uuid = ?"
            ).use { statement ->
                statement.setString(1, uuid.toString())
                statement.executeUpdate()
            }
        }
    }

    fun loadMute(uuid: UUID): MuteInfo? {
        getConnection().use { connection ->
            connection.prepareStatement(
                """
                SELECT uuid, seconds, reason, start_time, permanent
                FROM mutes
                WHERE uuid = ?
                """.trimIndent()
            ).use { statement ->
                statement.setString(1, uuid.toString())

                statement.executeQuery().use { rs ->
                    return if (rs.next()) readMute(rs) else null
                }
            }
        }
    }

    fun saveAll() {
        getConnection().use { connection ->
            connection.autoCommit = false
            try {
                saveAllMutes(connection)
                saveGlobalState(connection)
                connection.commit()
            } catch (e: Exception) {
                connection.rollback()
                throw e
            }
        }
    }

    fun loadAll() {
        val loadedMutes = mutableListOf<MuteInfo>()

        getConnection().use { connection ->
            connection.prepareStatement(
                """
                SELECT uuid, seconds, reason, start_time, permanent
                FROM mutes
                """.trimIndent()
            ).use { statement ->
                statement.executeQuery().use { rs ->
                    while (rs.next()) {
                        val mute = readMute(rs) ?: continue
                        loadedMutes.add(mute)
                    }
                }
            }

            val allMuted = loadGlobalState(connection)
            MuteManager.replaceAll(loadedMutes, allMuted)
        }
    }

    fun saveAllMutes() {
        getConnection().use { connection ->
            connection.autoCommit = false
            try {
                saveAllMutes(connection)
                connection.commit()
            } catch (e: Exception) {
                connection.rollback()
                throw e
            }
        }
    }

    fun loadAllMutes(): List<MuteInfo> {
        val result = mutableListOf<MuteInfo>()

        getConnection().use { connection ->
            connection.prepareStatement(
                """
                SELECT uuid, seconds, reason, start_time, permanent
                FROM mutes
                """.trimIndent()
            ).use { statement ->
                statement.executeQuery().use { rs ->
                    while (rs.next()) {
                        val mute = readMute(rs) ?: continue
                        result.add(mute)
                    }
                }
            }
        }

        return result
    }

    fun saveAllState() {
        getConnection().use { connection ->
            saveGlobalState(connection)
        }
    }

    fun loadAllState(): Boolean {
        getConnection().use { connection ->
            return loadGlobalState(connection)
        }
    }

    private fun createTables() {
        getConnection().use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate(
                    """
                    CREATE TABLE IF NOT EXISTS mutes (
                        uuid TEXT PRIMARY KEY,
                        seconds INTEGER NOT NULL,
                        reason TEXT NOT NULL,
                        start_time TEXT NOT NULL,
                        permanent INTEGER NOT NULL
                    )
                    """.trimIndent()
                )

                statement.executeUpdate(
                    """
                    CREATE TABLE IF NOT EXISTS mute_state (
                        id INTEGER PRIMARY KEY CHECK (id = 1),
                        all_muted INTEGER NOT NULL
                    )
                    """.trimIndent()
                )

                statement.executeUpdate(
                    """
                    INSERT OR IGNORE INTO mute_state (id, all_muted)
                    VALUES (1, 0)
                    """.trimIndent()
                )
            }
        }
    }

    private fun saveAllMutes(connection: Connection) {
        connection.prepareStatement(
            """
            INSERT INTO mutes (
                uuid,
                seconds,
                reason,
                start_time,
                permanent
            ) VALUES (?, ?, ?, ?, ?)
            ON CONFLICT(uuid) DO UPDATE SET
                seconds = excluded.seconds,
                reason = excluded.reason,
                start_time = excluded.start_time,
                permanent = excluded.permanent
            """.trimIndent()
        ).use { statement ->
            for (info in MuteManager.allMutes().mapNotNull { MuteManager.getMute(it) }) {
                bindMute(statement, info)
                statement.addBatch()
            }
            statement.executeBatch()
        }
    }

    private fun saveGlobalState(connection: Connection) {
        connection.prepareStatement(
            """
            INSERT INTO mute_state (id, all_muted)
            VALUES (1, ?)
            ON CONFLICT(id) DO UPDATE SET
                all_muted = excluded.all_muted
            """.trimIndent()
        ).use { statement ->
            statement.setInt(1, if (MuteManager.isAllMuted) 1 else 0)
            statement.executeUpdate()
        }
    }

    private fun loadGlobalState(connection: Connection): Boolean {
        connection.prepareStatement(
            "SELECT all_muted FROM mute_state WHERE id = 1"
        ).use { statement ->
            statement.executeQuery().use { rs ->
                return if (rs.next()) rs.getInt("all_muted") != 0 else false
            }
        }
    }

    private fun bindMute(statement: java.sql.PreparedStatement, info: MuteInfo) {
        statement.setString(1, info.uuid.toString())
        statement.setLong(2, info.seconds)
        statement.setString(3, info.reason)
        statement.setString(4, info.startTime.toString())
        statement.setInt(5, if (info.permanent) 1 else 0)
    }

    private fun readMute(rs: java.sql.ResultSet): MuteInfo? {
        return try {
            val uuid = UUID.fromString(rs.getString("uuid"))
            val seconds = rs.getLong("seconds")
            val reason = rs.getString("reason") ?: ""
            val startTime = LocalDateTime.parse(rs.getString("start_time"))
            val permanent = rs.getInt("permanent") != 0

            MuteInfo(
                uuid = uuid,
                seconds = seconds,
                reason = reason,
                startTime = startTime,
                permanent = permanent
            )
        } catch (_: Exception) {
            null
        }
    }

    private fun getConnection(): Connection {
        dbFile.parentFile?.mkdirs()
        return DriverManager.getConnection(jdbcUrl)
    }
}