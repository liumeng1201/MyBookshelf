package com.smartjinyu.mybookshelf;

public class JikeJson {
    /**
     * "id": 9787506380263,
     * "name": "人间失格",
     * "subname": "",
     * "author": "太宰治",
     * "translator": "杨伟",
     * "publishing": "作家出版社",
     * "published": "2015-8",
     * "designed": "平装",
     * "code": "9787506380263",
     * "douban": 26647769,  https://book.douban.com/subject/26647769/
     * "doubanScore": 82,
     * "brand": "世界文学名著",
     * "weight": null,
     * "size": null,
     * "pages": "219",
     * "photoUrl": "https://img3.doubanio.com/view/subject/m/public/s28323390.jpg",
     * "localPhotoUrl": "",
     * "price": "25.00元",
     * "froms": "douban_api2",
     * "num": 5,
     * "createTime": "2021-04-23T15:51:58",
     * "uptime": "2021-12-19T16:47:30",
     * "authorIntro": "太宰治，\u201c私小说\u201d领域的天才。宇川端康成、三岛由纪夫齐名，被视为日本战后文学的巅峰人物，后人称其为\u201c无赖派大师\u201d。",
     * "description": "收录《人间失格》《维庸之妻》《Good-bye》《灯笼》《满愿》《美男子与香烟》《皮肤与心》《蟋蟀》《樱桃》",
     * "reviews": null,
     * "tags": null
     */

    public Integer ret;
    public String msg;
    public Data data;

    class Data {
        public Long id;
        public String name;
        public String author;
        public String translator;
        public String publishing;
        public String published;
        public String code;
        public Long douban;
        public String photoUrl;
    }
}
