package com.example.shopingmodule.test;

import com.example.shopingmodule.service.impl.ShopingService;
import com.example.shopingmodule.ui.vo.ShopBannerVo;
import com.example.shopingmodule.ui.vo.ShopCategoryVo;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

import java.util.HashMap;
import java.util.List;

public class Test {
    public void main() {
        ShopingService shopingService = new ShopingService();
        Observable.zip(shopingService.getShopCategory(), shopingService.getBanner(), new BiFunction<List<ShopCategoryVo>, List<ShopBannerVo>, HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> apply(List<ShopCategoryVo> shopCategoryVos, List<ShopBannerVo> shopBannerVos) throws Exception {
                return null;
            }
        });
    }
}
