package app.morphe.extension.twitter.patches;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

@SuppressWarnings("unused")
public class ExplorePage {
    private static final String TAG = "EmptyExplorePage";

    // The list under the search bar (trends + "Posts For You").
    private static final String EXPLORE_LIST =
            "com.twitter.android.explore.dynamicchrome.ui.ExploreDynamicChromeTimelineFragment";

    // DEBUG: logs every screen name, and shows a small popup for explore/search screens.
    // Set both to false once it works.
    private static final boolean DEBUG_LOG = true;
    private static final boolean DEBUG_TOAST = true;

    /**
     * Injection point. Called at the end of Fragment.performViewCreated().
     */
    public static void onViewCreated(Object fragment, final View view) {
        try {
            if (fragment == null || view == null) return;
            String name = fragment.getClass().getName();

            if (DEBUG_LOG) Log.d(TAG, "fragment view created: " + name);

            boolean exploreRelated = name.contains("xplore") || name.contains("earch");
            if (DEBUG_TOAST && exploreRelated) {
                Toast.makeText(view.getContext(), name, Toast.LENGTH_SHORT).show();
            }

            if (EXPLORE_LIST.equals(name)) {
                hideForever(view);
            }
        } catch (Throwable t) {
            Log.e(TAG, "hook failed", t);
        }
    }

    // X may show the view again later, so hide it again every time before it is drawn.
    private static void hideForever(final View view) {
        view.setVisibility(View.GONE);
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                    return false;
                }
                return true;
            }
        });
    }
}