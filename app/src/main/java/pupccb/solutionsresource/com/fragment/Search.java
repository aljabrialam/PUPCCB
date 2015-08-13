package pupccb.solutionsresource.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.ExampleAdapter;

public class Search extends Fragment implements SearchView.OnQueryTextListener {

    private static final String[] MOVIES = new String[]{
            "The Woman in Black: Angel of Death",
            "20 Once Again",
            "Taken 3",
            "Tevar",
            "I",
            "Blackhat",
            "Spare Parts",
            "The Wedding Ringer",
            "Ex Machina",
            "Mortdecai",
            "Strange Magic",
            "The Boy Next Door",
            "The SpongeBob Movie: Sponge Out of Water",
            "Kingsman: The Secret Service",
            "Boonie Bears: Mystical Winter",
            "Project Almanac",
            "Running Man",
            "Wild Card",
            "It Follows",
            "C'est si bon",
            "Yennai Arindhaal",
            "Shaun the Sheep Movie",
            "Jupiter Ascending",
            "Old Fashioned",
            "Somewhere Only We Know",
            "Fifty Shades of Grey",
            "Dragon Blade",
            "Zhong Kui: Snow Girl and the Dark Crystal",
            "Badlapur",
            "Hot Tub Time Machine 2",
            "McFarland, USA",
            "The Duff",
            "The Second Best Exotic Marigold Hotel",
            "A la mala",
            "Focus",
            "The Lazarus Effect",
            "Chappie",
            "Faults",
            "Road Hard",
            "Unfinished Business",
            "Cinderella",
            "NH10",
            "Run All Night",
            "X+Y",
            "Furious 7",
            "Danny Collins",
            "Do You Believe?",
            "Jalaibee",
            "The Divergent Series: Insurgent",
            "The Gunman",
            "Get Hard",
            "Home"
    };
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private List<pupccb.solutionsresource.com.model.Search> mModels;

    public static Search newInstance() {
        return new Search();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mModels = new ArrayList<>();

        for (String movie : MOVIES) {
            mModels.add(new pupccb.solutionsresource.com.model.Search(movie));
        }

        mAdapter = new ExampleAdapter(getActivity(), mModels);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<pupccb.solutionsresource.com.model.Search> filteredModelList = filter(mModels, query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<pupccb.solutionsresource.com.model.Search> filter(List<pupccb.solutionsresource.com.model.Search> models, String query) {
        query = query.toLowerCase();

        final List<pupccb.solutionsresource.com.model.Search> filteredModelList = new ArrayList<>();
        for (pupccb.solutionsresource.com.model.Search model : models) {
            final String text = model.getSearch().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
