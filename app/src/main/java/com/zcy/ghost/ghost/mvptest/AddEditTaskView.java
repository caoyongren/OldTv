package com.zcy.ghost.ghost.mvptest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zcy.ghost.ghost.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class AddEditTaskView extends ScrollView implements AddEditTaskContract.View, View.OnClickListener {

    private TextView mTitle;

    private TextView mTvSure;
    private AddEditTaskContract.Presenter mPresenter;

    @Override
    public void setPresenter(AddEditTaskContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    private boolean mActive;

    public AddEditTaskView(Context context) {
        super(context);
        init();
    }

    public AddEditTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.addtask_view_content, this);
        mTitle = (TextView) findViewById(R.id.add_task_title);
        mTvSure = (TextView) findViewById(R.id.tv_sure);
        mTvSure.setOnClickListener(this);
        mActive = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mActive = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mActive = false;
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }


    @Override
    public boolean isActive() {
        return mActive;
    }


    public String getTitle() {
        return mTitle.getText().toString();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                mPresenter.saveTask(getTitle());
                break;
        }
    }
}
