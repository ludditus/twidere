/*
 *				Twidere - Twitter client for Android
 * 
 * Copyright (C) 2012 Mariotaku Lee <mariotaku.lee@gmail.com>
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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mariotaku.twidere.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import org.mariotaku.twidere.adapter.iface.IStatusesAdapter;
import org.mariotaku.twidere.loader.TweetSearchLoader;
import org.mariotaku.twidere.model.ParcelableStatus;

import java.util.List;

public class SearchTweetsFragment extends ParcelableStatusesListFragment {

    @Override
    public Loader<List<ParcelableStatus>> newLoaderInstance(final Context context, final Bundle args) {
        if (args == null)
            return null;
        long account_id = -1, max_id = -1, since_id = -1;
        String query = null;
        int tab_position = -1;
        if (args != null) {
            account_id = args.getLong(INTENT_KEY_ACCOUNT_ID);
            max_id = args.getLong(INTENT_KEY_MAX_ID, -1);
            since_id = args.getLong(INTENT_KEY_SINCE_ID, -1);
            query = args.getString(INTENT_KEY_QUERY);
            tab_position = args.getInt(INTENT_KEY_TAB_POSITION, -1);
        }
        return new TweetSearchLoader(getActivity(), account_id, query, max_id, since_id, getData(),
                getSavedStatusesFileArgs(), tab_position);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final IStatusesAdapter<List<ParcelableStatus>> adapter = getListAdapter();
        adapter.setFiltersEnabled(true);
        adapter.setIgnoredFilterFields(false, false, false, false);
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        super.onScrollStateChanged(view, scrollState);
        if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            final Fragment parent = getParentFragment();
            if (parent instanceof SearchFragment) {
                ((SearchFragment) parent).hideIndicator();
            }
        }
    }

    @Override
    protected String[] getSavedStatusesFileArgs() {
        final Bundle args = getArguments();
        if (args == null)
            return null;
        final long account_id = args.getLong(INTENT_KEY_ACCOUNT_ID, -1);
        final String query = args.getString(INTENT_KEY_QUERY);
        return new String[] {
                AUTHORITY_SEARCH_TWEETS, "account" + account_id, "query" + query
        };
    }

}
