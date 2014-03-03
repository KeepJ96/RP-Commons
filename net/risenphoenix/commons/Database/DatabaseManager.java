/*
 * Copyright 2014 Jacob Keep (Jnk1296).
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, 
 *   this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimer in the documentation 
 *   and/or other materials provided with the distribution.
 *
 *  * Neither the name of JuNK Software nor the names of its contributors may 
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package net.risenphoenix.commons.Database;

import net.risenphoenix.commons.Plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class DatabaseManager {

    private DatabaseConnection connection;
    private DatabaseType type;

    private final Plugin plugin;

    private boolean debug = false;

    // SQLite Constructor
    public DatabaseManager (final Plugin plugin) {
        this.plugin = plugin;
        this.connection = new DatabaseConnection(plugin);
        this.type = DatabaseType.SQLITE;
    }

    // MySQL Constructor
    public DatabaseManager (final Plugin plugin, String hostname, int port,
                            String database, String username, String pwd) {
        this.plugin = plugin;
        this.connection = new DatabaseConnection(plugin, hostname, port,
                database, username, pwd);
        this.type = DatabaseType.MYSQL;
    }

    public void enableDebug(boolean shouldDebug) {
        this.debug = shouldDebug;
        this.plugin.sendConsoleMessage(Level.INFO, this.plugin
                .getLocalizationManager().getLocalString("DB_DEBUG_ACTIVE"));
    }

    // Statement Execute Master Method
    public final boolean executeStatement(StatementObject stmt) {
        try {
            if (this.debug) this.plugin.sendConsoleMessage(Level.INFO,
                    "DatabaseManager.executeStatement(): " + stmt
                            .getFinalSQL());

            this.connection.query(stmt.getStatement(
                    this.connection.getConnection()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Query Execute Master Method
    public final Object executeQuery(StatementObject stmt, QueryFilter filter) {
        ResultSet res = null;
        Object obj = null;

        try {
            if (this.debug) this.plugin.sendConsoleMessage(Level.INFO,
                    "DatabaseManager.executeQuery(): " + stmt.getFinalSQL());

            res = this.connection.query(stmt.getStatement(
                    this.connection.getConnection())).getResultSet();
            obj = filter.onExecute(res);
            res.close();
        } catch (SQLException e) {
            this.plugin.sendConsoleMessage(Level.SEVERE,
                    e.getLocalizedMessage());
        } finally {
            try {
                if (res != null) res.close();
            } catch (Exception e) {
                this.plugin.sendConsoleMessage(Level.SEVERE,
                        e.getMessage());
                res = null;
            }
        }

        return obj;
    }

    public final Plugin getPlugin() {
        return this.plugin;
    }

    public final DatabaseConnection getDatabaseConnection() {
        return this.connection;
    }

    public final DatabaseType getDatabaseType() {
        return this.type;
    }

    public enum DatabaseType {
        MYSQL,
        SQLITE;
    }

}
