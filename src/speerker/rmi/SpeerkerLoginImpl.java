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
import speerker.db.UserGateway;
import speerker.types.User;

public class SpeerkerLoginImpl implements SpeerkerLogin {

	private static final long serialVersionUID = -8931887112987746759L;

	protected SpeerkerLoginImpl() throws RemoteException {
		super();
	}

	@Override
	public User login(User user) throws RemoteException {
		App.logger.info("Login user: " + user.getUsername());
		User dbUser = UserGateway.findByUserName(user.getUsername());
		if (dbUser.getValid()
				&& dbUser.getPassword().equals(user.getPassword()))
			return dbUser;
		else
			return user;
	}

	public void bind() throws RemoteException {

	}
}
