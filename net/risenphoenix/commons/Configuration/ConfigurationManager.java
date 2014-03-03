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

package net.risenphoenix.commons.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.risenphoenix.commons.Plugin;
import net.risenphoenix.commons.Configuration.ConfigurationOption.ConfigOptionType;

@SuppressWarnings("unchecked")
public class ConfigurationManager {

	private final Plugin plugin;
	private Map<String,Object> configSettings;
	
	private ArrayList<ConfigurationOption> options = null;
	
	public ConfigurationManager(final Plugin plugin) {
		this.plugin = plugin;
		this.plugin.getConfig().options().copyDefaults(true);
		this.plugin.saveConfig();
		this.plugin.reloadConfig();
	}
	
	public final void initializeConfiguration(ArrayList<ConfigurationOption> options) {
		this.options = options;
		this.configSettings = new HashMap<String,Object>();
		
		for (ConfigurationOption configOpt:options) {
			try {
				if (configOpt.getType() == ConfigOptionType.Boolean) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getBoolean(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.BooleanList) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getBooleanList(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.ByteList) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getByteList(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.CharList) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getCharacterList(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.FloatList) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getFloatList(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.Integer) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getInt(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.IntegerList) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getIntegerList(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.Long) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getLong(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.LongList) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getLongList(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.String) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getString(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.StringList) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getStringList(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.Double) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getDouble(configOpt.getIdentifier()));
					
				} else if (configOpt.getType() == ConfigOptionType.DoubleList) {
					configSettings.put(configOpt.getIdentifier(), 
							this.plugin.getConfig().
                                    getDoubleList(configOpt.getIdentifier()));
				}
			} catch (Exception e) {
				this.plugin.sendConsoleMessage(Level.WARNING, 
						this.plugin.getLocalizationManager().
                                getLocalString("BAD_CFG_SET")
						+ configOpt.getIdentifier());
			}
		}
	}
	
	private final Object getConfigurationOption(String identifier) {
		return this.configSettings.get(identifier);
	}
	
	public final boolean getBoolean(String identifier) {
        return Boolean.parseBoolean(
                getConfigurationOption(identifier).toString());
	}
	
	public final List<Boolean> getBooleanList(String identifier) {
		return (List<Boolean>)getConfigurationOption(identifier);
	}
	
	public final List<Byte> getByteList(String identifier) {
		return (List<Byte>)getConfigurationOption(identifier);
	}
	
	public final List<Character> getCharacterList(String identifier) {
		return (List<Character>)getConfigurationOption(identifier);
	}
	
	public final List<Float> getFloatList(String identifier) {
		return (List<Float>)getConfigurationOption(identifier);
	}
	
	public final int getInteger(String identifier) {
        return Integer.parseInt(getConfigurationOption(identifier).toString());
	}
	
	public final List<Integer> getIntegerList(String identifier) {
		return (List<Integer>)getConfigurationOption(identifier);
	}
	
	public final long getLong(String identifier) {
        return Long.parseLong(getConfigurationOption(identifier).toString());
	}
	
	public final List<Long> getLongList(String identifier) {
		return (List<Long>)getConfigurationOption(identifier);
	}
	
	public final String getString(String identifier) {
		return (String)getConfigurationOption(identifier);
	}
	
	public final List<String> getStringList(String identifier) {
		return (List<String>)getConfigurationOption(identifier);
	}
	
	public final void setConfigurationOption(String identifier, Object arg0) {
		this.plugin.getConfig().set(identifier, arg0);
		
		if (this.options != null) {
			this.initializeConfiguration(this.options);
		} else {
			this.plugin.sendConsoleMessage(Level.SEVERE, 
					this.plugin.getLocalizationManager().
                            getLocalString("CFG_INIT_ERR"));
		}
	}
}
