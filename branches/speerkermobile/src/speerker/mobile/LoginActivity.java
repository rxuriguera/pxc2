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

package speerker.mobile;

import speerker.types.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {
	private EditText username;
	private EditText password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Control.currentContext = this;
		Control.initializeProperties();

		this.username = (EditText) findViewById(R.id.usernameEditText);
		this.password = (EditText) findViewById(R.id.passwordEditText);
	}

	public void loginClicked(View view) {
		User user = new User();
		user.setUsername(this.username.getText().toString());
		user.setPassword(this.password.getText().toString());

		Control.username = user.getUsername();
		Control.password = user.getPassword();

		Boolean logged = Control.logIn();
		String loginMessage = "";
		if (logged)
			loginMessage = "You're logged in";
		else
			loginMessage = "Could not log you in";
		Control.toastMessage(this, loginMessage);

		// If user is logged in, change activity
		if (logged) {
			Control.initializeApplication();
			Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
			startActivity(intent);
			this.finish();
		}
	}

}
