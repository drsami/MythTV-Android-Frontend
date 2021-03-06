/**
 * This file is part of MythTV Android Frontend
 *
 * MythTV Android Frontend is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MythTV Android Frontend is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MythTV Android Frontend.  If not, see <http://www.gnu.org/licenses/>.
 *
 * This software can be found at <https://github.com/MythTV-Clients/MythTV-Android-Frontend/>
 */
package org.mythtv.client.ui;

import java.util.ArrayList;
import java.util.List;

import org.mythtv.R;
import org.mythtv.client.ui.dvr.DvrDashboardFragment;
import org.mythtv.client.ui.frontends.MythmoteActivity;
import org.mythtv.client.ui.media.MediaDashboardFragment;
import org.mythtv.client.ui.util.MenuHelper;
import org.mythtv.service.util.NetworkHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Daniel Frey
 * 
 */
public class HomeActivity extends AbstractLocationAwareFragmentActivity {

	private final static String TAG = HomeActivity.class.getSimpleName();

	private MenuHelper mMenuHelper;
	private NetworkHelper mNetworkHelper;
	
	/* (non-Javadoc)
	 * @see org.mythtv.client.ui.AbstractLocationAwareFragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		Log.d( TAG, "onCreate : enter" );
		super.onCreate( savedInstanceState );

		mMenuHelper = MenuHelper.newInstance( this );
		mNetworkHelper = NetworkHelper.newInstance( this );
		
		setContentView( R.layout.activity_home );

		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add( Fragment.instantiate( this, DvrDashboardFragment.class.getName() ) );
		fragments.add( Fragment.instantiate( this, MediaDashboardFragment.class.getName() ) );

		MythtvHomePagerAdapter mAdapter = new MythtvHomePagerAdapter( getSupportFragmentManager(), fragments );
		ViewPager mPager = (ViewPager) findViewById( R.id.home_pager );
		mPager.setAdapter( mAdapter );
		mPager.setCurrentItem( 0 );

		Log.d( TAG, "onCreate : exit" );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mythtv.client.ui.BaseActivity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		Log.d( TAG, "onCreateOptionsMenu : enter" );

		mMenuHelper.aboutMenuItem( menu );
		mMenuHelper.helpSubMenu( menu );
		mMenuHelper.mythmoteMenuItem( menu );
		
		Log.d( TAG, "onCreateOptionsMenu : exit" );
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mythtv.client.ui.BaseActivity#onOptionsItemSelected(android.view.
	 * MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		Log.d( TAG, "onOptionsItemSelected : enter" );

		switch( item.getItemId() ) {
		case MenuHelper.MYTHMOTE_ID:
			Log.d( TAG, "onOptionsItemSelected : Mythmote selected" );

			if( mNetworkHelper.isNetworkConnected() ) {
				startActivity( new Intent( this, MythmoteActivity.class ) );
			}
			
			return true;
//		case R.id.menu_setup:
//			Log.d( TAG, "onOptionsItemSelected : setup selected" );
//
//			startActivity( new Intent( this, SetupActivity.class ) );
//			return true;
		case MenuHelper.ABOUT_ID:
			Log.d( TAG, "onOptionsItemSelected : about selected" );

			mMenuHelper.handleAboutMenu();
		    
	        return true;
	    
		case MenuHelper.FAQ_ID:
			
			mMenuHelper.handleFaqMenu();
			
			return true;

		case MenuHelper.TROUBLESHOOT_ID:
			
			mMenuHelper.handleTroubleshootMenu();
			
			return true;
		
		case MenuHelper.ISSUES_ID:

			mMenuHelper.handleIssuesMenu();
			
			return true;
		
		}

		Log.d( TAG, "onOptionsItemSelected : exit" );
		return super.onOptionsItemSelected( item );
	}


	// internal helpers
	
	private class MythtvHomePagerAdapter extends FragmentStatePagerAdapter {

		private List<Fragment> fragments;
		
		public MythtvHomePagerAdapter( FragmentManager fm, List<Fragment> fragments ) {
			super( fm );
			
			this.fragments = fragments;
			
		}

		@Override
		public Fragment getItem( int position ) {
			return fragments.get( position );
		}

		public int getCount() {
			return fragments.size();
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
		 */
		@Override
		public CharSequence getPageTitle( int position ) {

			switch( position ) {
			case 0:
				return getMainApplication().getResources().getString( R.string.tab_dvr );
			case 1:
				return getMainApplication().getResources().getString( R.string.tab_multimedia );
			}

			return super.getPageTitle( position );
		}
		
	}

}
