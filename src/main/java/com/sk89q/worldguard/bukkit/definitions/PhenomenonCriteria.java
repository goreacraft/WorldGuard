// $Id$
/*
 * This file is a part of WorldGuard.
 * Copyright (c) sk89q <http://www.sk89q.com>
 * Copyright (c) the WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.sk89q.worldguard.bukkit.definitions;

import org.bukkit.event.Event;
import org.bukkit.event.weather.LightningStrikeEvent;

import com.sk89q.rulelists.Criteria;
import com.sk89q.worldguard.bukkit.BukkitContext;

public class PhenomenonCriteria implements Criteria<BukkitContext> {
    
    private Boolean isLightning = null;

    public PhenomenonCriteria(Boolean isLightning) {
    }

    public Boolean getIsLightning() {
        return isLightning;
    }

    public void setIsLightning(Boolean isLightning) {
        this.isLightning = isLightning;
    }

    @Override
    public boolean matches(BukkitContext context) {
        Event event = context.getEvent();

        if (isLightning != null && isLightning && event instanceof LightningStrikeEvent) {
            return true;
        } else {
            return isLightning == null;
        }
    }

}