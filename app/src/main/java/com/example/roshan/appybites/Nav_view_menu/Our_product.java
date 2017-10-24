package com.example.roshan.appybites.Nav_view_menu;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roshan.appybites.MainActivity;
import com.example.roshan.appybites.Our_Product.Catering;
import com.example.roshan.appybites.Our_Product.Chef;
import com.example.roshan.appybites.Our_Product.Today_Menu;
import com.example.roshan.appybites.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Our_product extends Fragment {

    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public Our_product() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_our_product, container, false);

        setHasOptionsMenu(true);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)view. findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
       // setupTabIcons();
        tabLayout.getTabAt(0).setCustomView(R.layout.table_layout);
        tabLayout.getTabAt(1).setCustomView(R.layout.table_layout);
        tabLayout.getTabAt(2).setCustomView(R.layout.table_layout);
        View tab1_view = tabLayout.getTabAt(0).getCustomView();
        View tab2_view = tabLayout.getTabAt(1).getCustomView();
        View tab3_view = tabLayout.getTabAt(2).getCustomView();
        TextView tab1_title = (TextView) tab1_view.findViewById(R.id.tab_title);
        ImageView img1 = (ImageView) tab1_view.findViewById(R.id.tab_img);
        TextView tab2_title = (TextView) tab2_view.findViewById(R.id.tab_title);
        ImageView img2 = (ImageView) tab2_view.findViewById(R.id.tab_img);
        TextView tab3_title = (TextView) tab3_view.findViewById(R.id.tab_title);
        ImageView img3 = (ImageView) tab3_view.findViewById(R.id.tab_img);
        tab1_title.setText("Catering");
        img1.setImageResource(R.drawable.cater_select);
        tab2_title.setText("Today Menu");
        img2.setImageResource(R.drawable.today_selector);
        tab3_title.setText("Chef");
        img3.setImageResource(R.drawable.chef_selector);
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new Catering(), "Catering");
        adapter.addFrag(new Today_Menu(), "Today Menu");
        adapter.addFrag(new Chef(), "Chef");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }
    public void onResume(){
        super.onResume();

        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("Home");

    }
}