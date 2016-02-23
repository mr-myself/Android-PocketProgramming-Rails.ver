package ffab0.pocketprogramming.util;

import android.app.FragmentTransaction;

import ffab0.pocketprogramming.fragments.GlobalNavigationFragment;

public class GlobalNavigation {

    static public void set(FragmentTransaction transaction) {
        GlobalNavigationFragment global_navigation = new GlobalNavigationFragment();
        transaction.add(android.R.id.content, global_navigation);
    }
}
