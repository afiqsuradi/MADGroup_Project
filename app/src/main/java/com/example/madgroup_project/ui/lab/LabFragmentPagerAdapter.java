package com.example.madgroup_project.ui.lab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.madgroup_project.data.models.Lab;
import com.example.madgroup_project.ui.item.fragments.LabItemDashboardFragment;
import com.example.madgroup_project.ui.item.fragments.LabItemsFragment;

public class LabFragmentPagerAdapter extends FragmentStateAdapter {

    private Lab lab;
    public LabFragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity, Lab lab) {
        super(fragmentActivity);
        this.lab = lab;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return LabItemDashboardFragment.newInstance(lab.getId());
            case 1:
                return LabItemsFragment.newInstance(lab.getId());
        }
        return LabItemDashboardFragment.newInstance(lab.getId());
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
