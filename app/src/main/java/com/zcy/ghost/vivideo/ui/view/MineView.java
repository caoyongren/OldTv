package com.zcy.ghost.vivideo.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.zcy.ghost.vivideo.R;
import com.zcy.ghost.vivideo.base.RootView;
import com.zcy.ghost.vivideo.model.bean.VideoInfo;
import com.zcy.ghost.vivideo.model.bean.VideoType;
import com.zcy.ghost.vivideo.presenter.contract.MineContract;
import com.zcy.ghost.vivideo.ui.activitys.CollectionActivity;
import com.zcy.ghost.vivideo.ui.activitys.HistoryActivity;
import com.zcy.ghost.vivideo.ui.activitys.SettingActivity;
import com.zcy.ghost.vivideo.ui.adapter.MineHistoryVideoListAdapter;
import com.zcy.ghost.vivideo.ui.fragments.MineFragment;
import com.zcy.ghost.vivideo.utils.BeanUtil;
import com.zcy.ghost.vivideo.utils.EventUtil;
import com.zcy.ghost.vivideo.utils.JumpUtil;
import com.zcy.ghost.vivideo.utils.ScreenUtil;
import com.zcy.ghost.vivideo.utils.StringUtils;
import com.zcy.ghost.vivideo.widget.theme.ColorTextView;

import org.simple.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zcy.ghost.vivideo.R.id.recyclerView;


/**
 * Description: MineView
 * Creator: cp
 * date: 2016/9/29 12:16
 */
public class MineView extends RootView<MineContract.Presenter> implements MineContract.View {

    MineHistoryVideoListAdapter mAdapter;
    VideoInfo videoInfo;
    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.rl_them)
    RelativeLayout rlThem;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(recyclerView)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.tv_history)
    TextView mTvHistory;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.tv_them)
    TextView tvThem;

    public MineView(Context context) {
        super(context);
    }

    public MineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_mine_view, this);
    }

    @Override
    protected void initView() {
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        titleName.setText(getResources().getString(R.string.mine_title));
        StringUtils.setIconDrawable(mContext, mTvHistory, MaterialDesignIconic.Icon.gmi_account_calendar, 16, 15);
        StringUtils.setIconDrawable(mContext, tvDown, MaterialDesignIconic.Icon.gmi_time_countdown, 16, 15);
        StringUtils.setIconDrawable(mContext, tvCollection, MaterialDesignIconic.Icon.gmi_collection_bookmark, 16, 15);
        StringUtils.setIconDrawable(mContext, tvThem, MaterialDesignIconic.Icon.gmi_palette, 16, 15);

        mRecyclerView.setAdapter(mAdapter = new MineHistoryVideoListAdapter(mContext));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        SpaceDecoration itemDecoration = new SpaceDecoration(ScreenUtil.dip2px(mContext, 8));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                videoInfo = BeanUtil.VideoType2VideoInfo(mAdapter.getItem(position), videoInfo);
                JumpUtil.go2VideoInfoActivity(getContext(), videoInfo);
            }
        });
    }

    @Override
    public void setPresenter(MineContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(mContext, msg);
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showContent(List<VideoType> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        if (list.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.rl_record, R.id.rl_down, R.id.rl_collection, R.id.rl_them, R.id.img_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_record:
                getContext().startActivity(new Intent(mContext, HistoryActivity.class));
                break;
            case R.id.rl_down:
                EventUtil.showToast(getContext(), "敬请期待");
                break;
            case R.id.rl_collection:
                getContext().startActivity(new Intent(mContext, CollectionActivity.class));
                break;
            case R.id.rl_them:
                EventBus.getDefault().post("", MineFragment.SET_THEME);
                break;
            case R.id.img_setting:
                getContext().startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }
}
