package app.morphe.extension.twitter.patches;

import android.util.Log;
import android.view.View;

@SuppressWarnings("unused")
public class ExplorePage {
    private static final String TAG = "EmptyExplorePage";

    // The list under the search bar (trends + "Posts For You").
    private static final String EXPLORE_LIST =
            "com.twitter.android.explore.dynamicchrome.ui.ExploreDynamicChromeTimelineFragment";

    // Set to true to log explore/search screens (helps if X changes names).
    private static final boolean DEBUG = true;

    /**
     * Injection point. Called at the end of Fragment.performViewCreated().
     */
    public static void onViewCreated(Object fragment, View view) {
        try {
            if (fragment == null || view == null) return;
            String name = fragment.getClass().getName();

            if (DEBUG && (name.contains("explore") || name.contains("Explore") || name.contains("search"))) {
                Log.d(TAG, "fragment view created: " + name);
            }

            if (EXPLORE_LIST.equals(name)) {
                view.setVisibility(View.GONE);
            }
        } catch (Throwable t) {
            Log.e(TAG, "hook failed", t);
        }
    }
}
