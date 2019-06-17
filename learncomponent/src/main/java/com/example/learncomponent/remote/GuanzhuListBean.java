package com.example.learncomponent.remote;

import java.io.Serializable;
import java.util.List;

public class GuanzhuListBean implements Serializable {

    private int count;
    private int total;
    private String nextPageUrl;
    private boolean adExist;
    private List<ItemListBean> itemList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public boolean isAdExist() {
        return adExist;
    }

    public void setAdExist(boolean adExist) {
        this.adExist = adExist;
    }

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public static class ItemListBean implements Serializable{

        private String type;
        private DataBeanX data;
        private Object tag;
        private int id;
        private int adIndex;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public DataBeanX getData() {
            return data;
        }

        public void setData(DataBeanX data) {
            this.data = data;
        }

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAdIndex() {
            return adIndex;
        }

        public void setAdIndex(int adIndex) {
            this.adIndex = adIndex;
        }

        public static class DataBeanX implements Serializable{


            private String dataType;
            private HeaderBean header;
            private ContentBean content;
            private Object adTrack;

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public HeaderBean getHeader() {
                return header;
            }

            public void setHeader(HeaderBean header) {
                this.header = header;
            }

            public ContentBean getContent() {
                return content;
            }

            public void setContent(ContentBean content) {
                this.content = content;
            }

            public Object getAdTrack() {
                return adTrack;
            }

            public void setAdTrack(Object adTrack) {
                this.adTrack = adTrack;
            }

            public static class HeaderBean implements Serializable{
                /**
                 * id : 159842
                 * actionUrl : eyepetizer://pgc/detail/3116/?title=%E6%88%91%E5%9C%A8%E7%9F%AD%E8%A7%86%E9%A2%91&userType=PGC&tabIndex=1
                 * labelList : null
                 * icon : http://img.kaiyanapp.com/8acf5d03a5646e70c9df40aec203f97c.png?imageMogr2/quality/60/format/jpg
                 * iconType : round
                 * time : 1557381607000
                 * showHateVideo : false
                 * followType : author
                 * tagId : 0
                 * tagName : null
                 * issuerName : 我在短视频
                 * topShow : false
                 */

                private int id;
                private String actionUrl;
                private Object labelList;
                private String icon;
                private String iconType;
                private long time;
                private boolean showHateVideo;
                private String followType;
                private int tagId;
                private Object tagName;
                private String issuerName;
                private boolean topShow;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getActionUrl() {
                    return actionUrl;
                }

                public void setActionUrl(String actionUrl) {
                    this.actionUrl = actionUrl;
                }

                public Object getLabelList() {
                    return labelList;
                }

                public void setLabelList(Object labelList) {
                    this.labelList = labelList;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getIconType() {
                    return iconType;
                }

                public void setIconType(String iconType) {
                    this.iconType = iconType;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public boolean isShowHateVideo() {
                    return showHateVideo;
                }

                public void setShowHateVideo(boolean showHateVideo) {
                    this.showHateVideo = showHateVideo;
                }

                public String getFollowType() {
                    return followType;
                }

                public void setFollowType(String followType) {
                    this.followType = followType;
                }

                public int getTagId() {
                    return tagId;
                }

                public void setTagId(int tagId) {
                    this.tagId = tagId;
                }

                public Object getTagName() {
                    return tagName;
                }

                public void setTagName(Object tagName) {
                    this.tagName = tagName;
                }

                public String getIssuerName() {
                    return issuerName;
                }

                public void setIssuerName(String issuerName) {
                    this.issuerName = issuerName;
                }

                public boolean isTopShow() {
                    return topShow;
                }

                public void setTopShow(boolean topShow) {
                    this.topShow = topShow;
                }
            }

            public static class ContentBean implements Serializable{
                /**
                 * type : video
                 * data : {"dataType":"VideoBeanForClient","id":159842,"title":"酥心 曾经的烂尾楼如何蜕变成网红聚集地","description":"酥心susum是一家集沙龙，花艺为一体的综合性网红民宿，它位于龙华区梅花庄园别墅群中，这一次我们访谈了它的主理人慧儿，共同探寻酥心的前世今生，看它如何从一栋烂尾楼脱胎换骨成为现在的网红聚集地。","library":"DEFAULT","tags":[{"id":729,"name":"生活方式","actionUrl":"eyepetizer://tag/729/?title=%E7%94%9F%E6%B4%BB%E6%96%B9%E5%BC%8F","adTrack":null,"desc":"在生活里找到理想的自己","bgPicture":"http://img.kaiyanapp.com/df0ad616931c3c08b6775b8be5f418fa.gif","headerImage":"http://img.kaiyanapp.com/df0ad616931c3c08b6775b8be5f418fa.gif","tagRecType":"IMPORTANT","childTagList":null,"childTagIdList":null,"communityIndex":0},{"id":794,"name":"民宿","actionUrl":"eyepetizer://tag/794/?title=%E6%B0%91%E5%AE%BF","adTrack":null,"desc":null,"bgPicture":"http://img.kaiyanapp.com/75bd1992de91ad9ba6cc82d28a4cfada.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/75bd1992de91ad9ba6cc82d28a4cfada.jpeg?imageMogr2/quality/60/format/jpg","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"communityIndex":0},{"id":721,"name":"旅行灵感","actionUrl":"eyepetizer://tag/721/?title=%E6%97%85%E8%A1%8C%E7%81%B5%E6%84%9F","adTrack":null,"desc":"","bgPicture":"http://img.kaiyanapp.com/32b7e220d9c012c96e5f9a68e6f3ad9a.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/32b7e220d9c012c96e5f9a68e6f3ad9a.jpeg?imageMogr2/quality/60/format/jpg","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"communityIndex":0},{"id":666,"name":"生活","actionUrl":"eyepetizer://tag/666/?title=%E7%94%9F%E6%B4%BB","adTrack":null,"desc":"匠心、健康、生活感悟","bgPicture":"http://img.kaiyanapp.com/95ba262a92ac99db76794ca56233d5d1.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/314a1e399de4c5708058e7f391619647.jpeg?imageMogr2/quality/60/format/jpg","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"communityIndex":0},{"id":564,"name":"都市","actionUrl":"eyepetizer://tag/564/?title=%E9%83%BD%E5%B8%82","adTrack":null,"desc":null,"bgPicture":"http://img.kaiyanapp.com/f9936f6c170c9843731f7b15a8e34c51.jpeg?imageMogr2/quality/100","headerImage":"http://img.kaiyanapp.com/f9936f6c170c9843731f7b15a8e34c51.jpeg?imageMogr2/quality/100","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"communityIndex":0},{"id":342,"name":"中国","actionUrl":"eyepetizer://tag/342/?title=%E4%B8%AD%E5%9B%BD","adTrack":null,"desc":null,"bgPicture":"http://img.kaiyanapp.com/be55d0120d41d9eff30495c254233a60.jpeg?imageMogr2/quality/60/format/jpg","headerImage":"http://img.kaiyanapp.com/be55d0120d41d9eff30495c254233a60.jpeg?imageMogr2/quality/60/format/jpg","tagRecType":"NORMAL","childTagList":null,"childTagIdList":null,"communityIndex":0}],"consumption":{"collectionCount":0,"shareCount":0,"replyCount":0},"resourceType":"video","slogan":null,"provider":{"name":"PGC","alias":"PGC","icon":""},"category":"生活","author":{"id":3116,"icon":"http://img.kaiyanapp.com/8acf5d03a5646e70c9df40aec203f97c.png?imageMogr2/quality/60/format/jpg","name":"我在短视频","description":"原创视频记录身边的人，讲述深圳人的故事，深圳人的美好生活平台","link":"","latestReleaseTime":1557381607000,"videoNum":300,"adTrack":null,"follow":{"itemType":"author","itemId":3116,"followed":false},"shield":{"itemType":"author","itemId":3116,"shielded":false},"approvedNotReadyVideoCount":0,"ifPgc":true,"recSort":0,"expert":false},"cover":{"feed":"http://img.kaiyanapp.com/e4a29dd101bbaa1157bb9818f22ff02d.png?imageMogr2/quality/60/format/jpg","detail":"http://img.kaiyanapp.com/e4a29dd101bbaa1157bb9818f22ff02d.png?imageMogr2/quality/60/format/jpg","blurred":"http://img.kaiyanapp.com/1a9a1f92a145d09ddd7bc2f709cfa81b.jpeg?imageMogr2/quality/60/format/jpg","sharing":null,"homepage":null},"playUrl":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss","thumbPlayUrl":null,"duration":326,"webUrl":{"raw":"http://www.eyepetizer.net/detail.html?vid=159842","forWeibo":"http://www.eyepetizer.net/detail.html?vid=159842&resourceType=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0"},"releaseTime":1557381607000,"playInfo":[{"height":270,"width":480,"urlList":[{"name":"aliyun","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=low&source=aliyun&playUrlType=url_oss","size":16836834},{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=low&source=qcloud&playUrlType=url_oss","size":16836834},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=low&source=ucloud&playUrlType=url_oss","size":16836834}],"name":"流畅","type":"low","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=low&source=aliyun&playUrlType=url_oss"},{"height":480,"width":854,"urlList":[{"name":"aliyun","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss","size":42479565},{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=normal&source=qcloud&playUrlType=url_oss","size":42479565},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=normal&source=ucloud&playUrlType=url_oss","size":42479565}],"name":"标清","type":"normal","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss"},{"height":720,"width":1280,"urlList":[{"name":"aliyun","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss","size":76612078},{"name":"qcloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=high&source=qcloud&playUrlType=url_oss","size":76612078},{"name":"ucloud","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=high&source=ucloud&playUrlType=url_oss","size":76612078}],"name":"高清","type":"high","url":"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=159842&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss"}],"campaign":null,"waterMarks":null,"ad":false,"adTrack":null,"type":"NORMAL","titlePgc":"酥心 曾经的烂尾楼如何蜕变成网红聚集地","descriptionPgc":"酥心susum是一家集沙龙，花艺为一体的综合性网红民宿，它位于龙华区梅花庄园别墅群中，这一次我们访谈了它的主理人慧儿，共同探寻酥心的前世今生，看它如何从一栋烂尾楼脱胎换骨成为现在的网红聚集地。","remark":"","ifLimitVideo":false,"searchWeight":0,"idx":0,"shareAdTrack":null,"favoriteAdTrack":null,"webAdTrack":null,"date":1557381607000,"promotion":null,"label":null,"labelList":[],"descriptionEditor":"","collected":false,"played":false,"subtitles":[],"lastViewTime":null,"playlists":null,"src":null}
                 * tag : null
                 * id : 0
                 * adIndex : -1
                 */

                private String type;
                private ItemBean data;
                private Object tag;
                private int id;
                private int adIndex;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public ItemBean getData() {
                    return data;
                }

                public void setData(ItemBean data) {
                    this.data = data;
                }

                public Object getTag() {
                    return tag;
                }

                public void setTag(Object tag) {
                    this.tag = tag;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getAdIndex() {
                    return adIndex;
                }

                public void setAdIndex(int adIndex) {
                    this.adIndex = adIndex;
                }


            }
        }
    }
}
