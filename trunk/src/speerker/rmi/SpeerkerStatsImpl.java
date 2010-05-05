/*
 * Speerker 
 * Copyright (C) 2010  Jordi Bartrolí, Hector Mañosas i Ramon Xuriguera
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package speerker.rmi;

import java.rmi.RemoteException;

import speerker.App;
import speerker.db.PlayGateway;
import speerker.types.Play;

public class SpeerkerStatsImpl implements SpeerkerStats {

	private static final long serialVersionUID = -8764807497016061203L;

	@Override
	public void submitPlay(Play play) throws RemoteException {
		App.logger.info("Submitting play: " + play.getSong().getTitle()
				+ " listened by: " + play.getUsername());

		PlayGateway.newPlay(play);
	}

}
