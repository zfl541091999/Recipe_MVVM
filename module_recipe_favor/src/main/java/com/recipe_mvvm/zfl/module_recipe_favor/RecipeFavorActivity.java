package com.recipe_mvvm.zfl.module_recipe_favor;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.recipe_mvvm.zfl.lib_base.event_bus_msg_entity.UpdateListEvent;
import com.recipe_mvvm.zfl.lib_base.mvvm.BaseActivity;
import com.recipe_mvvm.zfl.lib_base.recipe_entity.RecipeInfo;
import com.recipe_mvvm.zfl.lib_base.utils.ARouterPathUtil;
import com.recipe_mvvm.zfl.lib_res.widget.adapter.CommonRecyclerViewAdapter;
import com.recipe_mvvm.zfl.lib_res.widget.bga_refresh.RecipeStyleRefreshViewHolder;
import com.recipe_mvvm.zfl.module_recipe_favor.databinding.ActivityRecipeFavorBinding;
import com.recipe_mvvm.zfl.module_recipe_favor.vm.RecipeFavorViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
@Route(path = ARouterPathUtil.RecipeFavorActivity)
public class RecipeFavorActivity extends BaseActivity<RecipeFavorViewModel, ActivityRecipeFavorBinding>
{

    //适配器
    private CommonRecyclerViewAdapter mAdapter;

    private List<RecipeInfo> mList;

    @Override
    protected RecipeFavorViewModel getViewModel()
    {
        return new RecipeFavorViewModel(mViewDataBinding);
    }

    @Override
    protected int getLayoutResID()
    {
        return R.layout.activity_recipe_favor;
    }

    @Override
    protected void initData()
    {
        mList = new ArrayList<>();
        mAdapter = new CommonRecyclerViewAdapter(mContext, mList, mViewModel, mViewModel);
        mViewModel.setList(mList);
        mViewModel.setAdapter(mAdapter);
    }

    @Override
    protected void initView()
    {
        mViewDataBinding.tbRecipeFavor.setTitle("收藏菜谱");
        setSupportActionBar(mViewDataBinding.tbRecipeFavor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewDataBinding.tbRecipeFavor.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mViewDataBinding.bgaRlFavorRecipe.setDelegate(mViewModel);
        //不能上拉加载更多
        mViewDataBinding.bgaRlFavorRecipe.setRefreshViewHolder(new RecipeStyleRefreshViewHolder(mContext, false));
        mViewDataBinding.rvFavorRecipeList.setLayoutManager(new LinearLayoutManager(mContext));
        mViewDataBinding.rvFavorRecipeList.setAdapter(mAdapter);
    }

    @Override
    protected boolean isNeedEventBus()
    {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateReceived(UpdateListEvent event) {
        //来接收收藏菜谱更新的通知
        mViewDataBinding.bgaRlFavorRecipe.beginRefreshing();
    }
}
