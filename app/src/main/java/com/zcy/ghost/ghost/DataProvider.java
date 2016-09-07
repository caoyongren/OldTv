package com.zcy.ghost.ghost;


import com.zcy.ghost.ghost.bean.Ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Jude on 2016/1/6.
 */
public class DataProvider {
//    public static List<Person> getPersonList(int page){
//        ArrayList<Person> arr = new ArrayList<>();
//        arr.add(new Person("http://i2.hdslb.com/52_52/user/61175/6117592/myface.jpg", "月の星く雪" + "————————第" + page + "页", "完结来补"));
//        arr.add(new Person("http://i1.hdslb.com/52_52/user/6738/673856/myface.jpg", "影·蓝玉", "一看评论被***了一脸，伐开心。"));
//        arr.add(new Person("http://i1.hdslb.com/account/face/1467772/e1afaf4a/myface.png", "i琳夏i", "(｀・ω・´)"));
//        arr.add(new Person("http://i0.hdslb.com/52_52/user/18494/1849483/myface.jpg", "Minerva。", "为啥下载不能了？π_π"));
//        arr.add(new Person("http://i0.hdslb.com/52_52/account/face/4613528/303f4f5a/myface.png", "如歌行极", "求生肉（/TДT)/"));
//        arr.add(new Person("http://i0.hdslb.com/52_52/account/face/611203/76c02248/myface.png", "GERM", "第一次看 看弹幕那些说什么影帝模式啥的 感觉日了狗了 让我怎么往后看啊 艹"));
//        arr.add(new Person("http://i2.hdslb.com/52_52/user/46230/4623018/myface.jpg", "じ★ve↘魅惑", "开头吾王裙子被撩起来怎么回事！→_→"));
//        arr.add(new Person("http://i2.hdslb.com/52_52/user/66723/6672394/myface.jpg", "道尘一梦", "@伪 · 卫宫士郎"));
//        arr.add(new Person("http://i1.hdslb.com/user/3039/303946/myface.jpg", "潘多哥斯拉", "朋友，听说过某R吗……..我呸，听说过虫群吗？(｀・ω・´)"));
//        arr.add(new Person("http://i2.hdslb.com/account/face/9034989/aabbc52a/myface.png", "一只红发的猫", "道理我都懂，我就问，几楼开车←_←"));
//        arr.add(new Person("http://i0.hdslb.com/account/face/1557783/8733bd7b/myface.png", "Mikuの草莓胖次", "扶..扶我起来,喝了最后这一瓶营养快线，让我撸死up"));
//        arr.add(new Person("http://i2.hdslb.com/user/3716/371679/myface.jpg", "Absolute Field", "朋也，看过里番吗？"));
//        arr.add(new Person("http://i1.hdslb.com/account/face/9045165/4b11d894/myface.png", "琪雅之约", "摩西摩西.警察局么？"));
//        return arr;
//    }

    public static List<Ad> getAdList(){
        ArrayList<Ad> arr = new ArrayList<>();
        arr.add(new Ad("http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/09/07/1473231650079099797.jpg","http://h5.svipmovie.com/hdzb/000000005601e0bf0156026a6393000d.shtml?fromTo=shoujimovie"));
        arr.add(new Ad("http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/09/06/1473128081585076261.jpg","http://h5.svipmovie.com/dkjc/CMCC_00000000000000001_621484559.shtml?fromTo=shoujimovie"));
        arr.add(new Ad("http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/09/06/1473128054703037991.jpg","http://yoo.bilibili.com/html/activity/cq2015/index.html"));
        arr.add(new Ad("http://phonemovie.ks3-cn-beijing.ksyun.com/image/2016/08/30/1472550536432022270.jpg","http://h5.svipmovie.com/dkjc/CMCC_00000000000000001_621459824.shtml?fromTo=shoujimovie"));
        return arr;
    }

//    public static List<Object> getPersonWithAds(int page){
//        ArrayList<Object> arrAll = new ArrayList<>();
//        List<Ad> arrAd = getAdList();
//        int index = 0;
//        for (Person person : getPersonList(page)) {
//            arrAll.add(person);
//            //按比例混合广告
//            if (Math.random()<0.2){
//                arrAll.add(arrAd.get(index%arrAd.size()));
//                index++;
//            }
//        }
//
//        return arrAll;
//    }

//    static final Picture[] VIRTUAL_PICTURE = {
//            new Picture("疯狂试爱","","http://yweb2.cnliveimg.com/img/CMCC_MOVIE/618556611_699022_HSJ1080V.jpg"),
//            new Picture("魔轮","","http://yweb2.cnliveimg.com/img/CMCC_MOVIE/621149646_699022_HSJ1080V.jpg"),
//            new Picture("十日拍拖手册","","http://yweb0.cnliveimg.com/img/CMCC_MOVIE/618547697_699022_HSJ1080V.jpg"),
//            new Picture("万万没想到","","http://yweb0.cnliveimg.com/img/CMCC_MOVIE/618250891_699022_HSJ1080V.jpg"),
//            new Picture("栀子花开","","http://yweb3.cnliveimg.com/img/CMCC_MOVIE/618249542_699022_HSJ1080V.jpg"),
//            new Picture("车逝","","http://yweb1.cnliveimg.com/img/CMCC_MOVIE/618545227_699022_HSJ1080V.jpg"),
//            new Picture("哎哟青春期","","http://yweb3.cnliveimg.com/img/CMCC_MOVIE/621362508_699022_HSJ1080V.jpg"),
//            new Picture("鹅滴个神","","http://yweb0.cnliveimg.com/img/CMCC_MOVIE/621313534_699022_HSJ1080V.jpg"),
//            new Picture("爱，要做两次","","http://yweb2.cnliveimg.com/img/CMCC_MOVIE/618567422_699022_HSJ1080V.jpg"),
//            new Picture("烈日灼心","","http://yweb2.cnliveimg.com/img/CMCC_MOVIE/618560930_699022_HSJ1080V.jpg"),
//    };
//    public static Collection<Picture> getPictures(int page){
//        ArrayList<Picture> arrayList = new ArrayList<>();
//        for (int i = 0; i < VIRTUAL_PICTURE.length; i++) {
//            arrayList.add(VIRTUAL_PICTURE[i]);
//        }
//        return arrayList;
//    }
}
