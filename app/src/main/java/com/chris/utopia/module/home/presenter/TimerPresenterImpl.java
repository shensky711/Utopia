package com.chris.utopia.module.home.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.module.home.activity.TimerActionView;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/3/5.
 */
public class TimerPresenterImpl implements TimerPresenter {

    private TimerActionView actionView;
    private Context mContext;

    @Inject
    private ThingInteractor interactor;

    @Override
    public void setActionView(TimerActionView actionView) {
        this.actionView = actionView;
        this.mContext = actionView.getContext();
    }

    @Override
    public void loadData(String dateStr) {
        try {
            Integer userId = SharedPrefsUtil.getIntValue(mContext, Constant.SP_KEY_LOGIN_USER_ID, 0);
            Thing thing = new Thing();
            thing.setUserId(userId);
            thing.setBeginDate(dateStr);
            thing.setType(Constant.THING_TYPE_THING);
            List<Thing> thingList = interactor.findThing(thing);
            actionView.loadData(thingList);
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showMessage("获取数据失败");
        }
    }
}
