package com.master.old.tv.view.fragments.tab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.master.old.tv.presenter.tab.TabChoicePresenter;
import com.master.old.tv.presenter.tab.TabMyselfPresenter;
import com.master.old.tv.view.activitys.HistoryListActivity;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.master.old.tv.R;
import com.master.old.tv.base.BaseMvpFragment;
import com.master.old.tv.model.bean.VideoInfo;
import com.master.old.tv.model.bean.VideoType;
import com.master.old.tv.presenter.contract.tab.TabMyselfContract;
import com.master.old.tv.view.activitys.drawer.CollectionListActivity;
import com.master.old.tv.view.activitys.drawer.SettingActivity;
import com.master.old.tv.view.activitys.VideoInfoActivity;
import com.master.old.tv.view.adapter.tab.TabMyselfHistoryAdapter;
import com.master.old.tv.utils.BeanUtil;
import com.master.old.tv.utils.EventUtil;
import com.master.old.tv.utils.ScreenUtil;
import com.master.old.tv.utils.StringUtils;
import com.master.old.tv.widget.theme.ColorTextView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.master.old.tv.R.id.fg_choice_recyclerView;

/*****************************
 * Create by MasterMan
 * Description:
 *   我的模块
 * Email: MatthewCaoYongren@gmail.com
 * Blog: http://blog.csdn.net/zhenxi2735768804/
 * Githup: https://github.com/caoyongren
 * Motto: 坚持自己的选择, 不动摇！
 * Date: ２０１７年１１月２８日
 *****************************/
public class TabMySelfFragment extends BaseMvpFragment<TabMyselfPresenter>
                                       implements TabMyselfContract.View {
    private static final String TAG = "TabMySelfFragment";
    public static final String SET_THEME = "SET_THEME";

    TabMyselfHistoryAdapter mMyselfHistoryAdapter;

    VideoInfo videoInfo;
    @BindView(R.id.fg_title_name)
    ColorTextView titleName;
    @BindView(R.id.fg_myself_theme)
    RelativeLayout rlThem;

    @BindView(R.id.fg_myself_toolbar)
    Toolbar toolbar;
    @BindView(fg_choice_recyclerView)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.fg_myself_tv_history)
    TextView mTvHistory;
    @BindView(R.id.fg_myself_down_cache)
    TextView tvDown;

    @BindView(R.id.fg_myself_tv_collection)
    TextView tvCollection;
    @BindView(R.id.fg_myself_tv_them)
    TextView tvThem;

    @Override
    protected int getLayout() {
        return R.layout.fragment_myself;
    }

    @Override
    protected void initView(LayoutInflater inflater) {

        EventBus.getDefault().register(this);
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        titleName.setText(getResources().getString(R.string.mine_title));
        StringUtils.setIconDrawable(mContext, mTvHistory, MaterialDesignIconic.Icon.gmi_account_calendar, 16, 15);
        StringUtils.setIconDrawable(mContext, tvDown, MaterialDesignIconic.Icon.gmi_time_countdown, 16, 15);
        StringUtils.setIconDrawable(mContext, tvCollection, MaterialDesignIconic.Icon.gmi_collection_bookmark, 16, 15);
        StringUtils.setIconDrawable(mContext, tvThem, MaterialDesignIconic.Icon.gmi_palette, 16, 15);

        mRecyclerView.setAdapter(mMyselfHistoryAdapter = new TabMyselfHistoryAdapter(mContext));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSpanSizeLookup(mMyselfHistoryAdapter.obtainGridSpanSizeLookUp(3));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(mContext, 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);

    }

    @Override
    protected void initEvent() {
        mMyselfHistoryAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                videoInfo = BeanUtil.VideoType2VideoInfo(mMyselfHistoryAdapter.getItem(position), videoInfo);
                VideoInfoActivity.start(getContext(), videoInfo);
            }
        });
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    public void showContent(List<VideoType> list) {
        mMyselfHistoryAdapter.clear();
        mMyselfHistoryAdapter.addAll(list);
        if (list.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.fg_myself_rl_record, R.id.fg_myslef_rl_down_cache, R.id.fg_myself_collection,
              R.id.fg_myself_theme, R.id.img_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fg_myself_rl_record:
                getContext().startActivity(new Intent(mContext, HistoryListActivity.class));
                break;
            case R.id.fg_myslef_rl_down_cache:
                EventUtil.showToast(getContext(), "敬请期待");
                break;
            case R.id.fg_myself_collection:
                getContext().startActivity(new Intent(mContext, CollectionListActivity.class));
                break;
            case R.id.fg_myself_theme:
                EventBus.getDefault().post("", TabMySelfFragment.SET_THEME);
                break;
            case R.id.img_setting:
                getContext().startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }

    @Subscriber(tag = TabChoicePresenter.VideoInfoPresenter.Refresh_History_List)
    public void setData(String tag) {
        mPresenter.getHistoryData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        mPresenter.getHistoryData();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }
}