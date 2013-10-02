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
import android.support.v4.content.Loader;

import org.mariotaku.twidere.loader.IntentExtrasStatusesLoader;
import org.mariotaku.twidere.model.ParcelableStatus;

import java.util.List;

public class StatusesListFragment extends ParcelableStatusesListFragment {

    @Override
    public Loader<List<ParcelableStatus>> newLoaderInstance(final Context context, final Bundle args) {
        if (args == null)
            return null;
        if (args.containsKey(INTENT_KEY_STATUSES))
            return new IntentExtrasStatusesLoader(getActivity(), args, getData());
        return null;
    }

    @Override
    protected String[] getSavedStatusesFileArgs() {
        return null;
    }

}
