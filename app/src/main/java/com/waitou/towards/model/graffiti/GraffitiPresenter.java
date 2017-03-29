package com.waitou.towards.model.graffiti;

import android.databinding.ObservableInt;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.tarek360.instacapture.InstaCapture;
import com.waitou.towards.R;
import com.waitou.towards.bean.GraffitiToolInfo;
import com.waitou.towards.enums.GraffitiToolEnum;
import com.waitou.towards.util.AlertToast;
import com.waitou.towards.view.dialog.BaseDialog;
import com.waitou.towards.view.dialog.ListOfDialog;
import com.waitou.wt_library.base.XPresent;
import com.waitou.wt_library.kit.UImage;
import com.waitou.wt_library.recycler.LayoutManagerUtli;
import com.waitou.wt_library.recycler.adapter.BaseViewAdapter;
import com.waitou.wt_library.recycler.adapter.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by waitou on 17/3/27.
 * 涂鸦
 */

public class GraffitiPresenter extends XPresent<GraffitiActivity> implements BaseViewAdapter.Presenter {

    //工具选择的type
    public ObservableInt toolType  = new ObservableInt(0);
    //画笔粗细控制
    public ObservableInt toolWidth = new ObservableInt(14);
    //画笔颜色
    public ObservableInt toolColor = new ObservableInt(Color.BLUE);

    private SingleTypeAdapter<GraffitiToolInfo> mGraffitiToolAdapter;
    private BaseDialog                          mToolDialog;

    /*--------------- 选择工具 ---------------*/
    public void selectToolShowDialog() {
        if (mGraffitiToolAdapter == null) {
            mGraffitiToolAdapter = new SingleTypeAdapter<>(getV(), R.layout.item_select_tool);
            mGraffitiToolAdapter.setPresenter(this);
            List<GraffitiToolInfo> toolInfoList = new ArrayList<>();
            for (GraffitiToolEnum toolEnum : GraffitiToolEnum.values()) {
                GraffitiToolInfo info = new GraffitiToolInfo();
                info.drawable = ContextCompat.getDrawable(getV(), toolEnum.getRedId());
                info.tool = toolEnum.getTool();
                toolInfoList.add(info);
            }
            mGraffitiToolAdapter.set(toolInfoList);
        }
        if (mToolDialog == null) {
            mToolDialog = new ListOfDialog(getV())
                    .setLayoutManager(LayoutManagerUtli.getGridLayoutManager(getV(), 3))
                    .setAdapter(mGraffitiToolAdapter)
                    .setTitle("工具选择");
        }
        mToolDialog.show();
    }

    public void selectToolItemClick(int position) {
        toolType.set(position);
        mToolDialog.dismiss();
    }
    /*--------------- 选择工具 end---------------*/

    public void save(GraffitiView layout) {
        InstaCapture.getInstance(getV()).captureRx().subscribe(new Subscriber<Bitmap>() {
            @Override public void onCompleted() {
                //TODO..
            }

            @Override public void onError(Throwable e) {
                //TODO..
            }

            @Override public void onNext(Bitmap bitmap) {
                //TODO..
                UImage.saveImageToGallery(getV(), bitmap, true);
                AlertToast.show(" 保存 --- " );
            }
        });

    }
}