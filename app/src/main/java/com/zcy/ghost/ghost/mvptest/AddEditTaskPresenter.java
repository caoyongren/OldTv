package com.zcy.ghost.ghost.mvptest;

import android.support.annotation.NonNull;

import com.zcy.ghost.ghost.utils.StringUtils;

public class AddEditTaskPresenter implements AddEditTaskContract.Presenter {
    @NonNull
    private final AddEditTaskContract.View mAddTaskView;

    public AddEditTaskPresenter(@NonNull AddEditTaskContract.View addTaskView) {
        mAddTaskView = StringUtils.checkNotNull(addTaskView);
        mAddTaskView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void saveTask(String title) {
        //TODO 业务处理，
        if (mAddTaskView.isActive()) {
            mAddTaskView.setTitle("这是我点击的");
        }
    }
}
