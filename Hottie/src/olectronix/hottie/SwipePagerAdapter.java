package olectronix.hottie;

import olectronix.hottie.config.ConfigFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SwipePagerAdapter extends FragmentPagerAdapter{

	public SwipePagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new Fragment();  
	    switch (position) {  
	    case 0:  
	        return fragment = new ConfigFragment();  
	    case 1:  
	        return fragment = new BasicTabFragment();  
	    case 2:  
	        return fragment = new ConfigFragment();  
	    default:  
	        break;  
	    }  
	    return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
