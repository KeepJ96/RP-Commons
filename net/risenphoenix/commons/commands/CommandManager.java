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

package net.risenphoenix.commons.commands;

import java.util.ArrayList;
import java.util.logging.Level;

import net.risenphoenix.commons.Plugin;
import net.risenphoenix.commons.commands.parsers.DynamicParser;
import net.risenphoenix.commons.commands.parsers.Parser;
import net.risenphoenix.commons.commands.parsers.StaticParser;
import net.risenphoenix.commons.commands.parsers.VariableParser;

public class CommandManager {

	private ArrayList<Command> commands = new ArrayList<Command>();
	private final Plugin plugin;
	
	public CommandManager(final Plugin plugin) {
		this.plugin = plugin;
	}
	
	public final boolean registerCommand(Command cmd) {
		if (this.commands.add(cmd)) {
			return true;
		} else {
			plugin.sendConsoleMessage(Level.WARNING, 
					this.plugin.getLocalizationManager().
                            getLocalString("CMD_REG_ERR") +
					cmd.getName());
			
			return false;
		}
	}
	
	public final Command getCommand(String identifier) {
		for (Command cmd:this.commands) {
			if (cmd.getName().equalsIgnoreCase(identifier)) return cmd;
		}
		
		return null;
	}

    public final ArrayList<Command> getAllCommands() {
        return this.commands;
    }
	
	public final ParseResult parseCommand(String[] args) {
        System.out.println("PARSING ARGUMENTS");
		for (Command cmd:this.commands) {
            Parser parser;

            if (cmd.getType() == CommandType.STATIC) {
                parser = new StaticParser(cmd, args);
            } else if (cmd.getType() == CommandType.VARIABLE) {
                parser = new VariableParser(cmd, args);
            } else if (cmd.getType() == CommandType.DYNAMIC) {
                parser = new DynamicParser(cmd, args);
            } else {
                parser = new StaticParser(cmd, args);
            }

            ComparisonResult result = parser.parseCommand();

            if (result.equals(ComparisonResult.GOOD)) {
                return new ParseResult(ResultType.SUCCESS, cmd);
            } else if (result.equals(ComparisonResult.ARG_ERR)) {
                return new ParseResult(ResultType.BAD_NUM_ARGS, cmd);
            } else {
                continue;
            }
		}
		
		return new ParseResult(ResultType.FAIL, null);
	}
}
