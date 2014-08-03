package olectronix.hottie.general.adapters;

import olectronix.hottie.config.fragments.ConfigFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import olectronix.hottie.general.fragments.BasicTabFragment;

public class SwipePagerAdapter extends FragmentPagerAdapter{

	public SwipePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
	    switch (position) {  
	    case 0:  
	        return new ConfigFragment();
	    case 1:  
	        return new BasicTabFragment();
	    case 2:  
	        return new ConfigFragment();
	    default:  
	        break;  
	    }  
	    return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
