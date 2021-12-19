package mirantyJmartAK.jmart_android;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Fragment Adapter for the Main Activity,
 * so product and filter will be divided.
 *
 * @author Miranty Anjani Putri
 */
public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 1:
                return new SecondFragment();
        }

        return new FirstFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
